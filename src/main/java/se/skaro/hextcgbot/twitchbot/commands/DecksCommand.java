package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.skaro.hextcgbot.application.DeckGetter;
import se.skaro.hextcgbot.twitchbot.excpetions.SearchMessageToShortException;
import se.skaro.hextcgbot.util.BotMessageType;
import se.skaro.hextcgbot.util.MessageSender;

/**
 * Gets the deck data for a user.
 */
@Component
public class DecksCommand extends AbstractCommand {

    @Autowired
    private MessageSender messageSender;

    public DecksCommand(String syntax, boolean isCommandCaseSensitive, String description, BotMessageType botMessageType) {
        super(syntax, isCommandCaseSensitive, description, botMessageType);
    }

    @Override
    public void call(String commandSyntax, MessageEvent event) {
        String name = fixWhiteSpaces(getMessageWithoutCommand(commandSyntax, event));
        if (name.length() < SearchMessageToShortException.DEFAULT_MINIMUM_LENGTH) {
            throw new SearchMessageToShortException();
        }

        messageSender.sendMessage(event, new DeckGetter().getDecks(name), botMessageType);
    }
}
