package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import se.skaro.hextcgbot.events.MessageSender;
import se.skaro.hextcgbot.repository.jpa.JPADBHandler;

/**
 * Converts gold to platinum.
 */
//FIXME: Add min and max check.
public class GoldToPlatCommand extends AbstractCommand {

    @Override
    public void call(String commandSyntax, MessageEvent event) {
        try {
            double gold = Double.parseDouble(fixWhiteSpaces(getMessageWithoutCommand(commandSyntax, event)));
            MessageSender.sendMessage(event, GOLD_DECIMAL_FORMAT.format(gold) + " gold is currently worth "
                    + PLATINUM_DECIMAL_FORMAT.format(gold / JPADBHandler.getRatio()) + " platinum");
        } catch (NumberFormatException e) {
            //TODO: Send message with info about not parsable value.
        }
    }
}
