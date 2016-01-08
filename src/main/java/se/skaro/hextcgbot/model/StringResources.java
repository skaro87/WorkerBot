package se.skaro.hextcgbot.model;

/**
 * String resources that is being used like credentials
 */
public final class StringResources {
	
	//TODO: REMOVE THIS!!!!

	/** The Constant oauth. */
	private static final String OAUTH = "oauth:phkot061fhl34s32mo6q76es7xxhhv";

	/** The Constant username. */
	private static final String USERNAME = "workerbot";

	/** The Constant clientId. */
	private static final String CLIENT_ID = "2svam4sbfxtdjipwsax266s9qz0907";

	/**
	 * Gets the oauth.
	 *
	 * @return the oauth
	 */
	public static String getOauth() {
		return OAUTH;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public static String getUsername() {
		return USERNAME;
	}

	/**
	 * Gets the client id.
	 *
	 * @return the client id
	 */
	public static String getClientId() {
		return CLIENT_ID;
	}

	/**
	 * Gets the welcome message.
	 *
	 * @return the welcome message
	 */
	public static String getWelcomeMessage() {
		return "Hello! Thank you for using me. For more information about me visit twitch.tv/workerbot";
	}

	/**
	 * Gets the goodbye message.
	 *
	 * @return the goodbye message
	 */
	public static String getGoodbyeMessage() {
		return "Goodbye. Feel free to add me to your chat again at any time!";
	}

	/**
	 * Gets the help text.
	 *
	 * @return the help text
	 */
	public static String getHelpText() {
		return "Current commands are: !card/equipment/price [name], !decks [username], !streams, !ratio, !goldtoplat/plattogold [value], !join/leave, !whispers[on/off], !ign [twitch username], !setign [your IGN], !info and !donate.";
	}
}
