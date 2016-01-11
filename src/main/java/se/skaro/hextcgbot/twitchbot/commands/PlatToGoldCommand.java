package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import se.skaro.hextcgbot.events.MessageSender;
import se.skaro.hextcgbot.repository.jpa.JPADBHandler;

/**
 * Converts platinum to gold.
 */
//FIXME: Add min and max check.
public class PlatToGoldCommand extends AbstractCommand {

    @Override
    public void call(String commandSyntax, MessageEvent event) {
        try {
            double plat = Double.parseDouble(fixWhiteSpaces(getMessageWithoutCommand(commandSyntax, event)));
            MessageSender.sendMessage(event, PLATINUM_DECIMAL_FORMAT.format(plat) + " platinum is currently worth "
                    + GOLD_DECIMAL_FORMAT.format(plat * JPADBHandler.getRatio()) + " gold");
        } catch (NumberFormatException e) {
            //TODO: Send message with info about not parsable value.
        }
    }
}
