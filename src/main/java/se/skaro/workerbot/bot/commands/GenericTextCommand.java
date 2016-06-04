package se.skaro.workerbot.bot.commands;

import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;

import se.skaro.workerbot.bot.messages.MessageSender;


public class GenericTextCommand extends AbstractCommand {

	private String message;
	
	public GenericTextCommand(String syntax, String message, String helpMessage) {
		this.syntax = syntax;
		this.message = message;
		this.helpMessage = helpMessage;
	}

	@Override
	public void call(ChannelMessageEvent event) {
		MessageSender.sendMessage(event, message);
	}

}
