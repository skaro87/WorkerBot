package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import se.skaro.hextcgbot.repository.jpa.JPADBHandler;

/**
 * Makes the channel owner able to change the whisper settings for chat.
 */
public class WhispersCommand extends AbstractCommand {
    @Override
    public void call(String commandSyntax, MessageEvent event) {
        String userNick = getUserNick(event);
        if (userNick != null) {
            String message = fixWhiteSpaces(getMessageWithoutCommand(commandSyntax, event));
            if ("on".equalsIgnoreCase(message)) {
                JPADBHandler.setWhispers(userNick.toLowerCase(), 1);
                event.respondChannel("Whisper mode ON for channel " + userNick);
            } else if ("off".equalsIgnoreCase(message)) {
                JPADBHandler.setWhispers(userNick.toLowerCase(), 0);
                event.respondChannel("Whisper mode OFF for channel " + userNick);
            } else {
                //TODO: Send message about invalid argument.
            }
        }
    }
}
