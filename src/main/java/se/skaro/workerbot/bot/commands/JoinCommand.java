package se.skaro.workerbot.bot.commands;

import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;
import org.springframework.stereotype.Component;

@Component
public class JoinCommand extends AbstractCommand{

	public JoinCommand() {
		this.syntax = "join";
	}
	
	@Override
	public void call(ChannelMessageEvent event) {
		// TODO Auto-generated method stub
		
	}

}
