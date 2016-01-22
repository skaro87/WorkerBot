package se.skaro.hextcgbot.twitchbot.commands;

import java.util.List;

import org.pircbotx.hooks.events.MessageEvent;
import se.skaro.hextcgbot.events.MessageSender;
import se.skaro.hextcgbot.model.Card;
import se.skaro.hextcgbot.model.Gem;
import se.skaro.hextcgbot.repository.jpa.JpaRepository;

/**
 * Sends back the info about a card.
 */
public class GemCommand extends AbstractCommand {
	@Override
	public void call(String commandSyntax, MessageEvent event) {
		String userNick = getUserNick(event);
		if (userNick != null) {
			String input = fixWhiteSpaces(getMessageWithoutCommand(commandSyntax, event));
			if (input.length() > 3) {
				List<Gem> result = JpaRepository.findGemByName(input);
				if (result.isEmpty()) {
					result = JpaRepository.findGemByText(input);
					if (result.isEmpty()) {
						MessageSender.sendMessage(event, "No gem with name/text '" + input + "' found");
					} else {
						sendOutput(result, input, event);
					}
				}
				// one result found. No need to create a StringBuilder and start
				// the
				// for-loop
				else {
					sendOutput(result, input, event);
				}

			} else {
				MessageSender.sendMessage(event, "You need at least 4 characters to do a search");
			}
		}
	}

	private void sendOutput(List<Gem> result, String name, MessageEvent event) {
		if (result.size() == 1) {
			MessageSender.sendMessage(event, result.get(0).toString());
		}
		// more than one found.
		else {
			StringBuilder sb = new StringBuilder();
			sb.append("Found multiple gems: ");
			String separator = "";
			for (Gem gem : result) {
				if (gem.getName().equalsIgnoreCase(name)) {
					sb.delete(0, sb.length());
					sb.append(gem.toString());
					break;
				}

				sb.append(separator);
				sb.append(gem.getName());
				separator = ", ";
			}
			MessageSender.sendMessage(event, sb.toString());
		}
	}
}
