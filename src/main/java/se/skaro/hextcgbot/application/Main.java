package se.skaro.hextcgbot.application;

import se.skaro.hextcgbot.model.User;
import se.skaro.hextcgbot.repository.jpa.JpaRepository;
import se.skaro.hextcgbot.statistics.ChannelStats;
import se.skaro.hextcgbot.statistics.UserChannel;
import se.skaro.hextcgbot.twitchbot.CommandListener;
import se.skaro.hextcgbot.twitchbot.DefaultListener;
import se.skaro.hextcgbot.twitchbot.TwitchBot;

import java.util.HashSet;
import java.util.Set;

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
	
	private static final String PROPERTY_FILE_NAME = "workerbot";
	
	public static void main(String[] args) {

		PropertyGetter.getProperties(PROPERTY_FILE_NAME);
		Main workerbot = new Main();
		JpaRepository.startup();
		workerbot.startBot();

	}
	
	private void startBot(){
		
		Set<String> channels = new HashSet<>(); 

		channels.add("#" + PropertyGetter.getUSERNAME());

		ChannelStats.getStats().put("#" + PropertyGetter.getUSERNAME(), new UserChannel(0));

		for (User u : JpaRepository.findUsersToAutoJoin()) {
			channels.add("#" + u.getName().toLowerCase());
			ChannelStats.getStats().put("#" + u.getName().toLowerCase(), new UserChannel(u.whisperSettings()));
		}
		/*
		String[] channelArray = new String[channels.size()];

		int i = 0;
		for (String s : channels) {
			channelArray[i++] = s;
		}
		*/
		
		//"#celendine", "#dinotropia",
		try {
			TwitchBot bot = new TwitchBot(PropertyGetter.getUSERNAME(), PropertyGetter.getOAUTH(), "#" +PropertyGetter.getUSERNAME(), "#skaro87");
			bot.setUseTwitchCapabilities(true);
			bot.addListener(new DefaultListener());
			bot.addListener(new CommandListener());
			bot.startBot();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
