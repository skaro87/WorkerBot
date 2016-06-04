package se.skaro.workerbot.bot.commands;

import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;
import org.springframework.stereotype.Component;

@Component
public class EquipCommand extends AbstractCommand {
	
	public EquipCommand() {
		this.syntax = "equip";
	}

	@Override
	public void call(ChannelMessageEvent event) {
		// TODO Auto-generated method stub

	}

}
