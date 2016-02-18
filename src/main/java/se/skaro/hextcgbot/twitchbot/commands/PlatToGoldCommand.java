package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.skaro.hextcgbot.repository.jpa.JpaRepository;
import se.skaro.hextcgbot.twitchbot.excpetions.InvalidNumberException;
import se.skaro.hextcgbot.util.BotMessageType;
import se.skaro.hextcgbot.util.MessageSender;

/**
 * Converts platinum to gold.
 */
@Component
public class PlatToGoldCommand extends AbstractCommand {

    @Autowired
    private MessageSender messageSender;

    public PlatToGoldCommand(String syntax, boolean isCommandCaseSensitive, String description, BotMessageType botMessageType) {
        super(syntax, isCommandCaseSensitive, description, botMessageType);
    }

    @Override
    public void call(String commandSyntax, MessageEvent event) {
        try {
            double plat = Double.parseDouble(fixWhiteSpaces(getMessageWithoutCommand(commandSyntax, event)));
            if (plat <= 0) {
                throw new InvalidNumberException();
            }
            messageSender.sendMessage(event, PLATINUM_DECIMAL_FORMAT.format(plat) + " platinum is currently worth "
                    + GOLD_DECIMAL_FORMAT.format(plat * JpaRepository.getRatio()) + " gold", botMessageType);
        } catch (NumberFormatException e) {
            throw new InvalidNumberException();
        }
    }
}
