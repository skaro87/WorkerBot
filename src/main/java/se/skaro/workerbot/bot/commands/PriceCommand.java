package se.skaro.workerbot.bot.commands;

import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;
import org.springframework.stereotype.Component;

@Component
public class PriceCommand extends AbstractCommand {

	public PriceCommand() {
		this.syntax = "price";
	}
	
	@Override
	public void call(ChannelMessageEvent event) {
		// TODO Auto-generated method stub

	}

}