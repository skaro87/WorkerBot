package se.skaro.workerbot.bot.messages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;
import org.kitteh.irc.client.library.event.client.ClientConnectedEvent;
import org.kitteh.irc.lib.net.engio.mbassy.listener.Handler;

import se.skaro.workerbot.bot.util.PropertyGetter;

public class MessageHandler {
	
	private List<String> prefix;
	
	public MessageHandler(String prefixes){
		prefix = new ArrayList<String>();
		prefix.addAll(Arrays.asList(prefixes.split(PropertyGetter.DELIMITER)));
		
	}
	
	@Handler
	public void onMessage(ChannelMessageEvent event){
		System.out.println("message: "+event.getMessage() + ", channel: "+event.getChannel().getName());
		
		if (prefix.contains(event.getMessage().substring(0, 1))){
			//Message Logic here
		}
	}

}
