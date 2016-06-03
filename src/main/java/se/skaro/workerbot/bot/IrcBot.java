package se.skaro.workerbot.bot;

import org.kitteh.irc.client.library.Client;

import se.skaro.workerbot.bot.messages.MessageHandler;
import se.skaro.workerbot.bot.util.PropertyGetter;

public class IrcBot {

	private Client client;
	private String server;
	private String username;
	private String oauth;
	private boolean joinDBchannels;
	private String prefixes;

	public IrcBot(PropertyGetter propertyGetter) {
		server = propertyGetter.getProperty(PropertyGetter.SERVER);
		username = propertyGetter.getProperty(PropertyGetter.USERNAME);
		oauth = propertyGetter.getProperty(PropertyGetter.OAUTH);
		prefixes = propertyGetter.getProperty(PropertyGetter.PREFIXES);
		joinDBchannels = Boolean.valueOf(propertyGetter.getProperty(PropertyGetter.JOIN_DB_CHANNELS));
	}

	public void connect() {

		client = Client.builder().nick(username).serverPassword(oauth).serverHost(server).build();

		addChannels();

		//TODO: Make MessageHandler a bean
		client.getEventManager().registerEventListener(new MessageHandler(prefixes));
	}

	private void addChannels() {

		// Join channels in database
		if (joinDBchannels) {
			// Join channels here
			
		} else {
			client.addChannel("#skaro87");
		}

	}
}
