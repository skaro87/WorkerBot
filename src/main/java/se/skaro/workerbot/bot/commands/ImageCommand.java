package se.skaro.workerbot.bot.commands;

import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.skaro.workerbot.bot.messages.MessageSender;
import se.skaro.workerbot.businesslogic.CardLogic;

@Component
public class ImageCommand extends AbstractCommand {

	public ImageCommand() {
		this.syntax = "image";
	}

	@Autowired
	private CardLogic cardLogic;

	@Override
	public void call(ChannelMessageEvent event) {

		if (event != null) {
			String message = trimMessage(event.getMessage());
			if (("#" + event.getActor().getNick()).equalsIgnoreCase(event.getChannel().getName())) {
				cardLogic.sendImageRequestToImagePlugin(message);
			} else {
				MessageSender.sendMessage(event, cardLogic.getImageLink(message));
			}

		}

	}

}
