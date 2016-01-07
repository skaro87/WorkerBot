package se.skaro.hextcgbot.application;

import java.util.HashSet;
import java.util.Set;

import se.skaro.hextcgbot.model.StringResources;
import se.skaro.hextcgbot.model.User;
import se.skaro.hextcgbot.repository.jpa.JPADBHandler;
import se.skaro.hextcgbot.statistics.ChannelStats;
import se.skaro.hextcgbot.statistics.UserChannel;
import se.skaro.hextcgbot.twitchbot.DefaultListener;
import se.skaro.hextcgbot.twitchbot.TwitchBot;

/**
 * The Class Main.
 */
public class Main {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		JPADBHandler.startup();

		Set<String> channels = new HashSet<String>();

		channels.add("#" + StringResources.getUsername());

		ChannelStats.getStats().put("#" + StringResources.getUsername(), new UserChannel(0));

		for (User u : JPADBHandler.getAllUserWhereBotIsInChannel()) {
			channels.add("#" + u.getName().toLowerCase());
			ChannelStats.getStats().put("#" + u.getName().toLowerCase(), new UserChannel(u.whisperSettings()));
		}

		String[] channelArray = new String[channels.size()];

		int i = 0;
		for (String s : channels) {
			channelArray[i++] = s;
		}

		try {
			TwitchBot bot = new TwitchBot(StringResources.getUsername(), StringResources.getOauth(), channelArray);
			bot.setUseTwitchCapabilities(true);
			bot.addListener(new DefaultListener());
			bot.addListener(new CommandListener());
			bot.startBot();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
