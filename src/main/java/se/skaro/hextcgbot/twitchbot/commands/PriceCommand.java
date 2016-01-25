package se.skaro.hextcgbot.twitchbot.commands;

import java.util.List;

import org.pircbotx.hooks.events.MessageEvent;
import se.skaro.hextcgbot.events.MessageSender;
import se.skaro.hextcgbot.model.ItemPrice;
import se.skaro.hextcgbot.repository.jpa.JpaRepository;

/**
 * Sends back the price of an item.
 */
public class PriceCommand extends AbstractCommand {
	@Override
	public void call(String commandSyntax, MessageEvent event) {
		String name = fixWhiteSpaces(getMessageWithoutCommand(commandSyntax, event));

		if (name.length() > 3) {

			List<ItemPrice> result = JpaRepository.findPriceByName(name);
			Double ratio = JpaRepository.getRatio();

			StringBuilder sb = new StringBuilder();
			String seperator = "";
			for (ItemPrice item : result) {
				sb.append(seperator);
				sb.append(item.getListedPrice(ratio));
				seperator = ", ";
			}

			if (sb.length() < 1) {
				sb.append("No price found for '" + name + "'");
			}
			MessageSender.sendMessage(event, sb.toString());

		}
		
		else {
			 MessageSender.sendMessage(event, "You need at least 4 characters to do a search");
		}
	} 
}
