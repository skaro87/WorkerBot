package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;

public class ShardShopperCommand extends AbstractCommand {
	
	private static String SHARDSHOPPER_MESSAGE = "Check out ShardShopper at http://fiveshards.com/shard-shopper/";

	@Override
	public void call(String commandSyntax, MessageEvent event) {
		
		String message = fixWhiteSpaces(getMessageWithoutCommand(commandSyntax, event));
		
		if (message.equals("")){
			event.respondChannel(SHARDSHOPPER_MESSAGE);
		}

	}

}
