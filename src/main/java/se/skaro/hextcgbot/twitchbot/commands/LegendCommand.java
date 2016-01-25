/*
 * 
 */
package se.skaro.hextcgbot.twitchbot.commands;

import java.util.List;

import org.pircbotx.hooks.events.MessageEvent;
import se.skaro.hextcgbot.events.MessageSender;
import se.skaro.hextcgbot.model.Card;
import se.skaro.hextcgbot.model.Champion;
import se.skaro.hextcgbot.repository.jpa.JpaRepository;

/**
 * Sends back the info about a card.
 */
public class LegendCommand extends AbstractCommand {
	@Override
	public void call(String commandSyntax, MessageEvent event) {

		String userNick = getUserNick(event);
		if (userNick != null) {
			String name = fixWhiteSpaces(getMessageWithoutCommand(commandSyntax, event));
			if (name.length() > 3) {
				List<Champion> result = JpaRepository.findChampionByName(name);
				if (result.isEmpty()) {
					MessageSender.sendMessage(event, "No champion with name '" + name + "' found");
				}
				// one result found. No need to create a StringBuilder and start
				// the
				// for-loop
				else if (result.size() == 1) {
					MessageSender.sendMessage(event, result.get(0).toString());
				}
				// more than one found.
				else {
					StringBuilder sb = new StringBuilder();
					sb.append("Found multiple champions: ");
					String separator = "";
					for (Champion c : result) {
						if (c.getName().equalsIgnoreCase(name)) {
							sb.delete(0, sb.length());
							sb.append(c.toString());
							break;
						}

						sb.append(separator);
						sb.append(c.getName());
						separator = ", ";
					}
					MessageSender.sendMessage(event, sb.toString());
				}
			} else {
				MessageSender.sendMessage(event, "You need at least 4 characters to do a search");
			}
		}
	}
}
