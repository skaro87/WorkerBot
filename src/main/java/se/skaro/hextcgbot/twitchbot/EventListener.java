package se.skaro.hextcgbot.twitchbot;

import java.util.Set;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ConnectEvent;
import org.pircbotx.hooks.events.MessageEvent;
import se.skaro.hextcgbot.twitchbot.commands.BotCommands;

/**
 * The listener interface for receiving command events. The class that is
 * interested in processing a command event implements this interface, and the
 * object created with that class is registered with a component using the
 * component's <code>addCommandListener<code> method. When the command event
 * occurs, that object's appropriate method is invoked.
 */
public class EventListener extends ListenerAdapter {

	private Set<String> channels;

	public EventListener(Set<String> channels) {
		super();
		this.channels = channels;
	}

	@Override
	public void onMessage(final MessageEvent event) throws Exception {
		try {
			String message = event.getMessage();
			String lowercaseMessage = message.toLowerCase();

			if (message.startsWith("!")) {

				for (BotCommands botCommand : BotCommands.values()) {
					if (botCommand.getSyntax().startsWith(message) || (!botCommand.isCommandCaseSensitive()
							&& lowercaseMessage.startsWith(botCommand.getSyntax().toLowerCase()))) {
						botCommand.getCommand().call(botCommand.getSyntax(), event);
						return;
					}
				}

			}
		} catch (Exception e) {
			// TODO: Add logging error here.
			event.respondChannel(">>Initiate synergistic subrout##%---->>!!ERROR!! NO SYNERGY DETECTED");
		}

	}

	@Override
	public void onConnect(ConnectEvent event) throws Exception {

		channels.forEach(s -> {
			event.getBot().sendIRC().joinChannel(s);
			System.out.println(s);
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		super.onConnect(event);
	}

}