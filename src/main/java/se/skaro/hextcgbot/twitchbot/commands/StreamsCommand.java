package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.skaro.hextcgbot.application.StreamGetter;
import se.skaro.hextcgbot.util.MessageSender;

/**
 * Sends back the top streamers online streaming HEX
 */
//FIXME: Sends rapid messages, fix this.
@Component
public class StreamsCommand extends AbstractCommand {

    @Autowired
    private MessageSender messageSender;

    public StreamsCommand(String syntax, boolean isCommandCaseSensitive, String description) {
        super(syntax, isCommandCaseSensitive, description);
    }

    @Override
    public void call(String commandSyntax, MessageEvent event) {
        for (String s : new StreamGetter().getAllStreams()) {
            messageSender.sendMessage(event, s);
        }
    }
}
