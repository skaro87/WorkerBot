package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.skaro.hextcgbot.util.MessageSender;

/**
 * Sends back the number of channels the bot is currently in.
 */
@Component
public class ChannelsCommand extends AbstractCommand {

    @Autowired
    private MessageSender messageSender;

    public ChannelsCommand(String syntax, boolean isCommandCaseSensitive, String description) {
        super(syntax, isCommandCaseSensitive, description);
    }

    @Override
    public void call(String commandSyntax, MessageEvent event) {
        messageSender.sendMessage(event, "I am currently in " + event.getBot().getUserChannelDao().getAllChannels().size() + " channels");
    }
}
