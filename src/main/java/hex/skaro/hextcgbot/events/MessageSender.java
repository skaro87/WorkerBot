
package hex.skaro.hextcgbot.events;

import org.pircbotx.hooks.events.MessageEvent;

import hex.skaro.hextcgbot.statistics.ChannelStats;
import hex.skaro.hextcgbot.twitchbot.TwitchBot;

/**
 * The Class MessageSender.
 */
public class MessageSender {

	/**
	 * Sends message, based on the channel owners whisper settings.
	 *
	 * @param event the event
	 * @param message the message
	 */
	public static void sendMessage(MessageEvent event, String message) {
		TwitchBot bot = event.getBot();

		if (ChannelStats.getStats().get(event.getChannel().getName()).getWhispers() == 1) { //whisper mode on
			if (event.getTags().get("user-type").equals("mod")
					|| event.getChannel().getName().endsWith(event.getUser().getNick())) {
				event.respondChannel(message);
			}

			else {
				bot.getGroupServer().sendWhisper(event.getUser().getNick(), message);
			}
		}

		else {
			event.respondChannel(message);
		}
	}
	
}
