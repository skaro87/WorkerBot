package se.skaro.workerbot.bot.commands;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BotCommands {

	private List<AbstractCommand> commands;

	public List<AbstractCommand> getCommands() {
		return commands;
	}

	public void setCommands(List<AbstractCommand> commands) {
		this.commands = commands;
	}
}
