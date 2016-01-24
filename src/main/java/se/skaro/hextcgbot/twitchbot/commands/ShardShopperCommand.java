package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;

public class ShardShopperCommand extends AbstractCommand {
	
	private static String SHARDSHOPPER_MESSAGE = "ShardShopper coming soon!";

	@Override
	public void call(String commandSyntax, MessageEvent event) {
		
		String message = fixWhiteSpacesAndSymbols(getMessageWithoutCommand(commandSyntax, event));
		
		if (message.equals("")){
			event.respondChannel(SHARDSHOPPER_MESSAGE);
		}

	}

}
