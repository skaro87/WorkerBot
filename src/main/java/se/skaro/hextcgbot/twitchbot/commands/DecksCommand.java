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

		String name = fixWhiteSpacesAndSymbols(getMessageWithoutCommand(commandSyntax, event));

		if (name.length() > 3) {

			MessageSender.sendMessage(event, new DeckGetter().getDecks(name));

		}
		else {
			MessageSender.sendMessage(event, "You need at least 4 characters to do a search");
		}
	}
}
