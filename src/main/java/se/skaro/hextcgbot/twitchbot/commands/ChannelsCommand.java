package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import se.skaro.hextcgbot.events.MessageSender;

/**
 * Sends back the number of channels the bot is currently in.
 */
public class ChannelsCommand extends AbstractCommand {
    @Override
    public void call(String commandSyntax, MessageEvent event) {
        MessageSender.sendMessage(event, "I am currently in " + event.getBot().getUserChannelDao().getAllChannels().size() + " channels");
    }
}
