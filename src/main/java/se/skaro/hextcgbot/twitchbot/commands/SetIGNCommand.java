package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import se.skaro.hextcgbot.events.MessageSender;
import se.skaro.hextcgbot.repository.jpa.JPADBHandler;

/**
 * Sets user in-game-name.
 */
public class SetIGNCommand extends AbstractCommand {
    @Override
    public void call(String commandSyntax, MessageEvent event) {
        String userNick = getUserNick(event);
        if (userNick != null) {
            String message = fixWhiteSpaces(getMessageWithoutCommand(commandSyntax, event));
            if (!message.isEmpty()) {
                MessageSender.sendMessage(event, JPADBHandler.setUserIGN(message, userNick));
            } else {
                MessageSender.sendMessage(event, "You have to type in your IGN.");
            }
        }
    }
}
