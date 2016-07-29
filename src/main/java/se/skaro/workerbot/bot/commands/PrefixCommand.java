package se.skaro.workerbot.bot.commands;

import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.skaro.workerbot.bot.messages.MessageSender;
import se.skaro.workerbot.businesslogic.UserLogic;

@Component
public class PrefixCommand extends AbstractCommand {
	
	public PrefixCommand() {
		this.syntax = "prefix";
	}

	@Autowired
	private UserLogic userLogic;

	@Override
	public void call(ChannelMessageEvent event) {

		if (event != null) {
			String message = trimMessageWithoutRemovingNonAlphanumericalChars(event.getMessage());
			String reply = userLogic.updatePrefix(getUsername(event), message);
			MessageSender.sendMessage(event, reply);
		}

	}

}
