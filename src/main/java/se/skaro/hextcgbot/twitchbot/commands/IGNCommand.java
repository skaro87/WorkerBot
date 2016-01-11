package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import se.skaro.hextcgbot.events.MessageSender;
import se.skaro.hextcgbot.repository.jpa.JPADBHandler;

/**
 * Gets the IGN associated with a user.
 */
public class IGNCommand extends AbstractCommand {
    @Override
    public void call(String commandSyntax, MessageEvent event) {
        String userNick = getUserNick(event);
        if (userNick != null) {
            String message = fixWhiteSpaces(getMessageWithoutCommand(commandSyntax, event)).replaceFirst("^@", "");
            if (message.isEmpty()) {
                event.respondChannel(JPADBHandler.getUserIGN(userNick));
            } else if (message.length() > 3) {
                MessageSender.sendMessage(event, JPADBHandler.getUserIGN(message));
            } else {
                MessageSender.sendMessage(event, "You need at least 4 characters to do a search");
            }
        }
    }
}
