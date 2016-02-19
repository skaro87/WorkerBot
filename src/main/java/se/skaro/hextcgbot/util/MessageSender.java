package se.skaro.hextcgbot.util;

import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.stereotype.Controller;
import se.skaro.hextcgbot.statistics.ChannelStats;
import se.skaro.hextcgbot.twitchbot.TwitchBot;

import java.util.*;

@Controller
public class MessageSender {

    private static final int MESSAGE_SEND_TIME_LIMIT = 1510;
    private static final int GROUPED_MESSAGE_DELAY = 50;
    private static final int SINGLE_MESSAGE_LIMIT = 490;

    private Long lastMessageSendTime = new Date().getTime();
	private volatile boolean isTimerRunning = false;
	private volatile List<BotMessage> messagesToSend = new ArrayList<>();

	/**
	 * Sends message, based on the channel owners whisper settings.
	 *
	 * @param event the event
	 * @param message the message
	 */
	public void sendMessage(MessageEvent event, String message, BotMessageType botMessageType) {
		String channelName = event.getChannel().getName();
		if (ChannelStats.getStats().containsKey(channelName) && ChannelStats.getStats().get(channelName).getWhispers() == 1) { // on
			if (event.getTags().get("user-type").equals("mod")
					|| (event.getUser() != null && event.getChannel().getName().endsWith(event.getUser().getNick()))) {
                prepareMessageToSend(event, message, null, null, botMessageType, null);
			} else if (event.getUser() != null) {
                prepareMessageToSend(event, message, event.getUser().getNick(), null, botMessageType, null);
			}
		} else {
            prepareMessageToSend(event, message, null, null, botMessageType, null);
		}
	}

	public void sendChannelMessage(MessageEvent event, String channelName, String message, BotMessageType botMessageType) {
        prepareMessageToSend(event, message, null, channelName, botMessageType, null);
	}

	public void respondChannel(MessageEvent event, String message, BotMessageType botMessageType) {
        prepareMessageToSend(event, message, null, null, botMessageType, null);
	}

    public void prepareMessageToSend(final MessageEvent event, final String message, final String whisperNick,
                            final String channelName, final BotMessageType messageType, final Integer innerPriority) {
        Long currentTime = new Date().getTime();
        if (messageType.isTryToGroup() && messagesToSend.isEmpty() && currentTime - lastMessageSendTime > MESSAGE_SEND_TIME_LIMIT) {
            lastMessageSendTime = currentTime - MESSAGE_SEND_TIME_LIMIT + GROUPED_MESSAGE_DELAY;
        }
        sendMessage(event, message, whisperNick, channelName, messageType, innerPriority);
    }

	private void sendMessage(final MessageEvent event, final String message, final String whisperNick,
							final String channelName, final BotMessageType messageType, final Integer innerPriority) {
		Long currentTime = new Date().getTime();
		if (!isTimerRunning && currentTime - lastMessageSendTime > MESSAGE_SEND_TIME_LIMIT) {
			sendBotMessage(event, message, whisperNick, channelName);
			lastMessageSendTime = new Date().getTime();
			if (!messagesToSend.isEmpty()) {
				isTimerRunning = true;
				new Timer().schedule(new SendMessageTask(), MESSAGE_SEND_TIME_LIMIT);
			}
		} else {
			messagesToSend.add(new BotMessage(event, message, whisperNick, channelName, messageType, innerPriority));
			organizeMessages();
			if (!isTimerRunning) {
				isTimerRunning = true;
				new Timer().schedule(new SendMessageTask(), MESSAGE_SEND_TIME_LIMIT - currentTime + lastMessageSendTime);
			}
		}
	}

	private synchronized void organizeMessages() {
		List<BotMessage> groupedList = new ArrayList<>();
		Map<BotMessageType, Map<String, BotMessage>> priorityMap = new HashMap<>();
		for (BotMessage message : messagesToSend) {
			if (message.getMessageType().isTryToGroup()) {
				if (priorityMap.containsKey(message.getMessageType())) {
                    organizePriorityMessageMap(groupedList, message, priorityMap.get(message.getMessageType()));
				} else {
					priorityMap.put(message.getMessageType(), new HashMap<>(Collections.singletonMap("", message)));
				}
			} else {
				groupedList.add(message);
			}
		}

        for (Map<String, BotMessage> botMessageMap : priorityMap.values()) {
            groupedList.addAll(botMessageMap.values());
        }
		messagesToSend = groupedList;

		Collections.sort(messagesToSend, (o1, o2) -> {
            if (!o1.getMessageType().getPriority().equals(o2.getMessageType().getPriority()))
                return o2.getMessageType().getPriority() - o1.getMessageType().getPriority();
            if (o1.getInnerPriority() != null && o2.getInnerPriority() != null)
                return o2.getInnerPriority() - o1.getInnerPriority();
            if (o1.getInnerPriority() != null) return -1;
            if (o2.getInnerPriority() != null) return 1;
            return 0;
        });
	}

    private synchronized void organizePriorityMessageMap(List<BotMessage> groupedList, BotMessage message, Map<String, BotMessage> priorityMessageMap) {
        String key = getBotMessageKey(message);
        if (priorityMessageMap.containsKey(key)) {
            BotMessage priorityMessage = priorityMessageMap.get(key);
            String newMessage = priorityMessage.getMessage() + " " + message.getMessage();
            if (newMessage.length() < SINGLE_MESSAGE_LIMIT) {
                priorityMessage.setMessage(newMessage);
            } else {
                if (priorityMessage.getMessage().length() >= message.getMessage().length()) {
                    groupedList.add(priorityMessageMap.put(key, message));
                } else {
                    groupedList.add(message);
                }
            }
        } else {
            priorityMessageMap.put(key, message);
        }
    }

    private String getBotMessageKey(BotMessage message) {
        if (message.getWhisperNick() != null && !message.getWhisperNick().isEmpty()) {
            return message.getWhisperNick();
        }
        if (message.getChannelName() != null && !message.getChannelName().isEmpty()) {
            return message.getChannelName();
        }
        return "";
    }

    private void sendBotMessage(MessageEvent event, String message, String whisperNick, String channelName) {
        if (event == null || event.getBot() == null || message == null || message.isEmpty()) {
            return;
        }

        TwitchBot bot = event.getBot();
        if (whisperNick != null && !whisperNick.isEmpty()) {
            bot.getGroupServer().sendWhisper(whisperNick, message);
        } else if (channelName != null && !channelName.isEmpty()) {
            bot.send().message(channelName, message);
        } else {
            event.respondChannel(message);
        }
    }

	private class SendMessageTask extends TimerTask {

		@Override
		public void run() {
			BotMessage messageToSend = messagesToSend.get(0);
			messagesToSend.remove(messageToSend);
			isTimerRunning = false;
			sendMessage(messageToSend.getEvent(), messageToSend.getMessage(), messageToSend.getWhisperNick(),
					messageToSend.getChannelName(), messageToSend.getMessageType(), messageToSend.getInnerPriority());
		}
	}
}
