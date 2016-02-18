package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.skaro.hextcgbot.repository.jpa.JpaRepository;
import se.skaro.hextcgbot.twitchbot.excpetions.InvalidNumberException;
import se.skaro.hextcgbot.util.BotMessageType;
import se.skaro.hextcgbot.util.MessageSender;

/**
 * Converts gold to platinum.
 */
@Component
public class GoldToPlatCommand extends AbstractCommand {

    @Autowired
    private MessageSender messageSender;

    public GoldToPlatCommand(String syntax, boolean isCommandCaseSensitive, String description, BotMessageType botMessageType) {
        super(syntax, isCommandCaseSensitive, description, botMessageType);
    }

    @Override
    public void call(String commandSyntax, MessageEvent event) {
        try {
            double gold = Double.parseDouble(fixWhiteSpaces(getMessageWithoutCommand(commandSyntax, event)));
            if (gold <= 0) {
                throw new InvalidNumberException();
            }
            messageSender.sendMessage(event, GOLD_DECIMAL_FORMAT.format(gold) + " gold is currently worth "
                    + PLATINUM_DECIMAL_FORMAT.format(gold / JpaRepository.getRatio()) + " platinum", botMessageType);
        } catch (NumberFormatException e) {
            throw new InvalidNumberException();
        }
    }
}
