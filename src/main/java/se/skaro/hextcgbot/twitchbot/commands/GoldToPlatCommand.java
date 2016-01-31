package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.skaro.hextcgbot.repository.jpa.JpaRepository;
import se.skaro.hextcgbot.util.MessageSender;

/**
 * Converts gold to platinum.
 */
//FIXME: Add min and max check.
@Component
public class GoldToPlatCommand extends AbstractCommand {

    @Autowired
    private MessageSender messageSender;

    public GoldToPlatCommand(String syntax, boolean isCommandCaseSensitive, String description) {
        super(syntax, isCommandCaseSensitive, description);
    }

    @Override
    public void call(String commandSyntax, MessageEvent event) {
        try {
            double gold = Double.parseDouble(fixWhiteSpaces(getMessageWithoutCommand(commandSyntax, event)));
            messageSender.sendMessage(event, GOLD_DECIMAL_FORMAT.format(gold) + " gold is currently worth "
                    + PLATINUM_DECIMAL_FORMAT.format(gold / JpaRepository.getRatio()) + " platinum");
        } catch (NumberFormatException e) {
            //TODO: Send message with info about not parsable value.
        }
    }
}
