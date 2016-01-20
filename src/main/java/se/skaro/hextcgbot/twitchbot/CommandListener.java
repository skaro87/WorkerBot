package se.skaro.hextcgbot.twitchbot;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import se.skaro.hextcgbot.twitchbot.commands.BotCommands;

/**
 * The listener interface for receiving command events. The class that is
 * interested in processing a command event implements this interface, and the
 * object created with that class is registered with a component using the
 * component's <code>addCommandListener<code> method. When the command event
 * occurs, that object's appropriate method is invoked.
 */
public class CommandListener extends ListenerAdapter {

	@Override
	public void onMessage(final MessageEvent event) throws Exception {
		try {
			String message = event.getMessage();
			String lowercaseMessage = message.toLowerCase();

			for (BotCommands botCommand : BotCommands.values()) {
				if (botCommand.getSyntax().startsWith(message) || (!botCommand.isCommandCaseSensitive()
						&& lowercaseMessage.startsWith(botCommand.getSyntax().toLowerCase()))) {
					botCommand.getCommand().call(botCommand.getSyntax(), event);
					// TODO: Think about if we need this.
					return;
				}
			}
		} catch (Exception e) {
			// TODO: Add logging error here.
			event.respond("Unexpected error has occurred. Try again later.");
		}

	}

}