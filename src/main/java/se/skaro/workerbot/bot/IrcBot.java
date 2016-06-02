package se.skaro.workerbot.bot;

import org.kitteh.irc.client.library.Client;
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;
import org.kitteh.irc.lib.net.engio.mbassy.listener.Handler;

import se.skaro.workerbot.bot.messages.MessageHandler;

public class IrcBot {

	private Client client;
	private String server;	
	private int port;
	private String username;
	private String oauth;
	private String prefixes;

	public IrcBot(String server, int port, String username, String oauth, String prefixes) {
		this.server = server;
		this.port = port;
		this.username = username;
		this.oauth = oauth;
		this.prefixes = prefixes;
	}

	public void connect() {

		client = Client.builder().nick(username).serverPassword(oauth).serverHost(server).build();

		addChannels();
		
		this.client.getEventManager().registerEventListener(new MessageHandler(prefixes));
	}

	private void addChannels() {
		client.addChannel("#skaro87");
		
	}
	
	
}
