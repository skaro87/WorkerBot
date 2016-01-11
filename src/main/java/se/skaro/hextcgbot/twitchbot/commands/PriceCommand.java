package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import se.skaro.hextcgbot.events.MessageSender;
import se.skaro.hextcgbot.repository.jpa.JPADBHandler;

/**
 * Sends back the price of an item.
 */
public class PriceCommand extends AbstractCommand {
    @Override
    public void call(String commandSyntax, MessageEvent event) {
        MessageSender.sendMessage(event, JPADBHandler.getPriceData(fixWhiteSpaces(getMessageWithoutCommand(commandSyntax, event))));
    }
}
