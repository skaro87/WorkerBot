package se.skaro.workerbot.bot.commands;

public abstract class AbstractCommand implements ICommand {

	protected String syntax;
	protected String helpMessage;

	public String getSyntax() {
		return syntax;
	}
	
	protected String trimMessage(String message){
		return message.substring(1).replace(syntax, "").trim();
	}

}
