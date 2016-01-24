package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import se.skaro.hextcgbot.application.StreamGetter;
import se.skaro.hextcgbot.events.MessageSender;

/**
 * Sends back the top streamers online streaming HEX
 */
//FIXME: Sends rapid messages, fix this.
public class StreamsCommand extends AbstractCommand {
    @Override
    public void call(String commandSyntax, MessageEvent event) {
        for (String s : new StreamGetter().getAllStreams()) {
            MessageSender.sendMessage(event, s);
        }
    }
}
