package se.skaro.workerbot.bot.commands;

import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;
import org.springframework.stereotype.Component;

@Component
public class WhisperSettingsCommand extends AbstractCommand {
	
	public WhisperSettingsCommand() {
		this.syntax = "whispers";
	}

	@Override
	public void call(ChannelMessageEvent event) {
		// TODO Auto-generated method stub

	}

}
