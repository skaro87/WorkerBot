package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import se.skaro.hextcgbot.application.DeckGetter;
import se.skaro.hextcgbot.events.MessageSender;

/**
 * Gets the deck data for a user.
 */
public class DecksCommand extends AbstractCommand {
    @Override
    public void call(String commandSyntax, MessageEvent event) {
        MessageSender.sendMessage(event, new DeckGetter().getDecks(fixWhiteSpacesAndSymbols(getMessageWithoutCommand(commandSyntax, event))));
    }
}
