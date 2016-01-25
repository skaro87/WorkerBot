package se.skaro.hextcgbot.twitchbot.commands;

import java.util.List;

import org.pircbotx.hooks.events.MessageEvent;
import se.skaro.hextcgbot.events.MessageSender;
import se.skaro.hextcgbot.model.User;
import se.skaro.hextcgbot.repository.jpa.JpaRepository;

/**
 * Gets the IGN associated with a user.
 */
public class IGNCommand extends AbstractCommand {
	@Override
	public void call(String commandSyntax, MessageEvent event) {
		String userNick = getUserNick(event);
		if (userNick != null) {
			String message = fixWhiteSpacesAndSymbols(getMessageWithoutCommand(commandSyntax, event))
					.replaceFirst("^@", "").replaceFirst(" ", "");

			if (message.length() > 4) {
				List<User> result = JpaRepository.findUserByNameWithWildcards(message);

				for (User user : result) {
					MessageSender.sendMessage(event, "IGN for user " + user.getName() + " is " + user.getIGN());
				}

			}

			else if (message.isEmpty()) {
				List<User> result = JpaRepository.findUserByName(userNick);
				if (result.isEmpty()) {
					MessageSender.sendMessage(event,
							userNick + ", you have not registerd your username. To register type !setign 'your IGN'");
				} else {
					event.respondChannel("IGN for user " + result.get(0).getName() + " is " + result.get(0).getIGN());
				}
			}

			else {
				MessageSender.sendMessage(event, "You need at least 4 characters to do a search");
			}
		}

	}
}
