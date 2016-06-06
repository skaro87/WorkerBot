package se.skaro.workerbot.bot.commands;

import java.util.List;

import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.skaro.workerbot.bot.messages.MessageSender;
import se.skaro.workerbot.businesslogic.UserLogic;
import se.skaro.workerbot.data.entity.User;
import se.skaro.workerbot.data.repository.UserRepository;

@Component
public class TwitchNameCommand extends AbstractCommand {

	@Autowired
	private UserLogic userLogic;

	public TwitchNameCommand() {
		this.syntax = "whois"; // TODO: Name?
	}

	@Override
	public void call(ChannelMessageEvent event) {

		if (event != null) {
			String message = trimMessage(event.getMessage());
			MessageSender.sendMessage(event, userLogic.getTwitchNameFromIGN(message));

		}

	}

}
