package se.skaro.workerbot.bot.commands;

import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand extends AbstractCommand {
	
	public HelpCommand() {
		this.syntax = "help";
	}

	@Autowired
	private BotCommands botCommands;

	@Override
	public void call(ChannelMessageEvent event) {
		
		

	}

}
