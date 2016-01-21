package se.skaro.hextcgbot.twitchbot.commands;

import java.util.List;

import org.pircbotx.hooks.events.MessageEvent;
import se.skaro.hextcgbot.events.MessageSender;
import se.skaro.hextcgbot.model.Card;
import se.skaro.hextcgbot.repository.jpa.JpaRepository;

/**
 * Sends back the info about a card.
 */
public class CardCommand extends AbstractCommand {
	@Override
	public void call(String commandSyntax, MessageEvent event) {
		String name = fixWhiteSpaces(getMessageWithoutCommand(commandSyntax, event));
		List<Card> result = JpaRepository.findCardByFormatedName(name);
		if (result.isEmpty()) {
			MessageSender.sendMessage(event, "No card with name '" + name + "' found");
		}
		// one result found. No need to create a StringBuilder and start the for-loop
		else if (result.size() == 1) {
			MessageSender.sendMessage(event, result.get(0).toString());
		}
		// more than one found.
		else {
			StringBuilder sb = new StringBuilder();
			sb.append("Found multiple cards: ");
			String separator = "";
			for (Card card : result) {
				if (card.getFormatedName().equalsIgnoreCase(name)) {
					sb.delete(0, sb.length());
					sb.append(card.toString());
					break;
				}

				sb.append(separator);
				sb.append(card.getName());
				separator = ", ";
			}
			MessageSender.sendMessage(event, sb.toString());
		}

	}
}
