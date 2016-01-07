package se.skaro.hextcgbot.statistics;

import java.util.HashMap;

// TODO: Auto-generated Javadoc
/**
 * The Class ChannelStats. Holds statistics (future) and settings (now) for channels
 */
public class ChannelStats {
	
	/** The channels. */
	private static HashMap<String, UserChannel> channels = new HashMap<String, UserChannel>();
	
	/**
	 * Gets the stats.
	 *
	 * @return the stats
	 */
	public static HashMap<String, UserChannel> getStats(){
		return channels;
	}
	
	/**
	 * Gets the channel counter.
	 *
	 * @param channelName the channel name
	 * @return the channel counter
	 */
	public UserChannel getChannelCounter(String channelName){
		return channels.get(channelName);
	}

}
