package se.skaro.workerbot.bot.commands;

import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.skaro.workerbot.bot.messages.MessageSender;
import se.skaro.workerbot.businesslogic.UserLogic;

@Component
public class IGNCommand extends AbstractCommand {
	
	@Autowired
	private UserLogic userLogic;

	public IGNCommand() {
		this.syntax = "ign";
	}
	
	@Override
	public void call(ChannelMessageEvent event) {
		
		if (event != null) {
			String message = trimMessage(event.getMessage());
			MessageSender.sendMessage(event, userLogic.getIGNforUser(message));

		}

	}

}
