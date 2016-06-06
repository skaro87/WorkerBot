package se.skaro.workerbot.bot.commands;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractCommand.
 */
public abstract class AbstractCommand implements ICommand {

	/** The syntax. */
	protected String syntax;
	
	/** help message, used with {@link HelpCommand} */
	protected String helpMessage;

	/**
	 * Gets the syntax. The syntax is the name of the command, and is used to check if the input starts with the syntax
	 *
	 * @return the syntax
	 */
	public String getSyntax() {
		return syntax;
	}
	
	/**
	 * Trims messages removing the prefix, syntax and all non-alphanumerical characters
	 *
	 * @param message the message
	 * @return the string
	 */
	protected String trimMessage(String message){
		return message.substring(1).replace(syntax, "").replaceAll("[^A-Za-z0-9 ]", "").trim();
	}

}
