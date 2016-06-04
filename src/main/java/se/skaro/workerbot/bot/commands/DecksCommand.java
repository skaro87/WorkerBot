package se.skaro.workerbot.bot.commands;

import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;
import org.springframework.stereotype.Component;

@Component
public class DecksCommand extends AbstractCommand{

	public DecksCommand(){
		this.syntax = "decks";
	}
	
	@Override
	public void call(ChannelMessageEvent event) {
		// TODO Auto-generated method stub
		
	}

}
