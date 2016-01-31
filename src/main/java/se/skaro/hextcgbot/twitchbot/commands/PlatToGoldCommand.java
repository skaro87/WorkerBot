package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.skaro.hextcgbot.repository.jpa.JpaRepository;
import se.skaro.hextcgbot.util.MessageSender;

/**
 * Converts platinum to gold.
 */
//FIXME: Add min and max check.
@Component
public class PlatToGoldCommand extends AbstractCommand {

    @Autowired
    private MessageSender messageSender;

    public PlatToGoldCommand(String syntax, boolean isCommandCaseSensitive, String description) {
        super(syntax, isCommandCaseSensitive, description);
    }

    @Override
    public void call(String commandSyntax, MessageEvent event) {
        try {
            double plat = Double.parseDouble(fixWhiteSpaces(getMessageWithoutCommand(commandSyntax, event)));
            messageSender.sendMessage(event, PLATINUM_DECIMAL_FORMAT.format(plat) + " platinum is currently worth "
                    + GOLD_DECIMAL_FORMAT.format(plat * JpaRepository.getRatio()) + " gold");
        } catch (NumberFormatException e) {
            //TODO: Send message with info about not parsable value.
        }
    }
}
