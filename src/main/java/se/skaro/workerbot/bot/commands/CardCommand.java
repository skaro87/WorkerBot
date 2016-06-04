package se.skaro.workerbot.bot.commands;

import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;
import org.springframework.stereotype.Component;

@Component
public class CardCommand extends AbstractCommand {
	
	public CardCommand(){
		this.syntax = "card";
	}

	@Override
	public void call(ChannelMessageEvent event) {
		System.out.println("CARD");

	}

}
