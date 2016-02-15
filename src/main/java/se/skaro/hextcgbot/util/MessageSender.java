package se.skaro.hextcgbot.util;

import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.stereotype.Controller;
import se.skaro.hextcgbot.statistics.ChannelStats;
import se.skaro.hextcgbot.twitchbot.TwitchBot;

@Controller
public class MessageSender {

	/**
	 * Sends message, based on the channel owners whisper settings.
	 *
	 * @param event the event
	 * @param message the message
	 */
	public void sendMessage(MessageEvent event, String message) {
		TwitchBot bot = event.getBot();
		String channelName = event.getChannel().getName();

		if (ChannelStats.getStats().containsKey(channelName) && ChannelStats.getStats().get(channelName).getWhispers() == 1) { // on
			if (event.getTags().get("user-type").equals("mod")
					|| (event.getUser() != null && event.getChannel().getName().endsWith(event.getUser().getNick()))) {
				event.respondChannel(message);
			} else if (event.getUser() != null) {
				bot.getGroupServer().sendWhisper(event.getUser().getNick(), message);
			}
		} else {
			event.respondChannel(message);
		}
	}

	public void sendChannelMessage(MessageEvent event, String channelName, String message) {
		event.getBot().send().message(channelName, message);
	}

	public void respondChannel(MessageEvent event, String message) {
		event.respondChannel(message);
	}
}
