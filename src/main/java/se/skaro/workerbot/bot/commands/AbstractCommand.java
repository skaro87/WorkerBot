package se.skaro.workerbot.bot.commands;

import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;

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
	
	protected String trimMessageWithoutRemovingNonAlphanumericalChars(String message){
		return message.substring(1).replace(syntax, "").trim();
	}
	
	protected String getUsername(ChannelMessageEvent event){
		return event.getActor().getNick();
	}

}
