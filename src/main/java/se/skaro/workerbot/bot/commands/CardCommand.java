package se.skaro.workerbot.bot.commands;

import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.skaro.workerbot.bot.messages.MessageSender;
import se.skaro.workerbot.businesslogic.CardLogic;

@Component
public class CardCommand extends AbstractCommand {

	public CardCommand() {
		this.syntax = "card";
	}

	@Autowired
	private CardLogic cardLogic;

	@Override
	public void call(ChannelMessageEvent event) {

		if (event != null) {
			String message = trimMessage(event.getMessage());
			MessageSender.sendMessage(event, cardLogic.getCardMessage(message));

		}

	}

}
