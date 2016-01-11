package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import se.skaro.hextcgbot.repository.jpa.JPADBHandler;

/**
 * WorkerBot will join the senders channel.
 */
public class JoinCommand extends AbstractCommand {

    private static final String WELCOME_TEXT_MESSAGE = "Hello! Thank you for using me. For more information about me visit twitch.tv/workerbot";

    @Override
    public void call(String commandSyntax, MessageEvent event) {
        String userNick = getUserNick(event);
        if (userNick != null) {
            String channelName = getChannelName(userNick);
            if (!event.getBot().getUserChannelDao().containsChannel(channelName)) {
                event.respondChannel("Joining channel " + channelName);
                event.getBot().sendIRC().joinChannel(channelName);
                JPADBHandler.updateUserWhereBotIsInChannel(userNick.toLowerCase(), 1);
                event.getBot().send().message(channelName, WELCOME_TEXT_MESSAGE);
            } else {
                event.respondChannel(userNick + ", I am already in your channel!");
            }
        }
    }
}
