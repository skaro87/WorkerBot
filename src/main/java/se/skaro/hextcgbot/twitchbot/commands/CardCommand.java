package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import se.skaro.hextcgbot.events.MessageSender;
import se.skaro.hextcgbot.repository.jpa.JPADBHandler;

/**
 * Sends back the info about a card.
 */
public class CardCommand extends AbstractCommand {
    @Override
    public void call(String commandSyntax, MessageEvent event) {
        MessageSender.sendMessage(event, JPADBHandler.getCardData(fixWhiteSpaces(getMessageWithoutCommand(commandSyntax, event))));
    }
}
