package se.skaro.workerbot.bot.messages;

import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;

public class MessageSender {

	public static void sendMessage(ChannelMessageEvent event, String message) {

		// TODO: MessageSender to send messages (40 messages per minute, 100
		// whispers per minute)
		event.getClient().sendMessage(event.getChannel(), message);

		// event.getClient().sendRawLine("PRIVMSG #jtv :/w skaro87 test");

	}

}
