package se.skaro.hextcgbot.twitchbot.commands;

import java.util.List;

import org.pircbotx.hooks.events.MessageEvent;

import se.skaro.hextcgbot.events.MessageSender;
import se.skaro.hextcgbot.model.User;
import se.skaro.hextcgbot.repository.jpa.JpaRepository;
import se.skaro.hextcgbot.statistics.ChannelStats;
import se.skaro.hextcgbot.statistics.UserChannel;

/**
 * Makes the channel owner able to change the whisper settings for chat.
 */
public class WhispersCommand extends AbstractCommand {
	@Override
	public void call(String commandSyntax, MessageEvent event) {

		String userNick = getUserNick(event);
		if (userNick != null) {
			String message = fixWhiteSpaces(getMessageWithoutCommand(commandSyntax, event));

			List<User> users = JpaRepository.findUserByName(userNick);

			if (!users.isEmpty()) {
				
				User user = users.get(0);

				if ("on".equalsIgnoreCase(message)) {
					JpaRepository.saveOrUpdateUser(new User(user.getName(), user.isInChannel(), 1, user.getIGN()));
					ChannelStats.getStats().put("#"+user.getName(), new UserChannel(1));
					event.respondChannel("Whisper mode ON for channel " + userNick);
					
				} else if ("off".equalsIgnoreCase(message)) {
					JpaRepository.saveOrUpdateUser(new User(user.getName(), user.isInChannel(), 0, user.getIGN()));
					ChannelStats.getStats().put("#"+user.getName(), new UserChannel(0));
					event.respondChannel("Whisper mode OFF for channel " + userNick);
					
				} else {
					event.respondChannel("Invalid command " + event.getMessage()
							+ ". Use !whispers 'on/off' to change whisper settings");
				}
			}
			else {
				MessageSender.sendMessage(event, userNick+": You have to have WorkerBot join your channel before you can change whisper settings.");
			}
		}
	}
}
