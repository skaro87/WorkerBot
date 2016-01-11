package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import se.skaro.hextcgbot.events.MessageSender;
import se.skaro.hextcgbot.repository.jpa.JPADBHandler;

/**
 * Sends a message with the number of users in the database.
 */
public class UsersCommand extends AbstractCommand {

    @Override
    public void call(String commandSyntax, MessageEvent event) {
        MessageSender.sendMessage(event, JPADBHandler.getAllUsers());
    }
}
