package se.skaro.hextcgbot.twitchbot.commands;

import java.util.List;

import org.pircbotx.hooks.events.MessageEvent;

import se.skaro.hextcgbot.model.User;
import se.skaro.hextcgbot.repository.jpa.JpaRepository;

/**
 * WorkerBot will join the senders channel.
 */
public class JoinCommand extends AbstractCommand {

	private static final String WELCOME_TEXT_MESSAGE = "Hello! Thank you for using me. For more information about me visit my twitch page";

	@Override
	public void call(String commandSyntax, MessageEvent event) {
		String userNick = getUserNick(event);
		if (userNick != null && event.getMessage().equalsIgnoreCase("!join")) {
			String channelName = getChannelName(userNick);
			if (!event.getBot().getUserChannelDao().containsChannel(channelName)) {
				List<User> result = JpaRepository.findUserByName(userNick);
				User user;

				if (!result.isEmpty()) {
					 user = new User(result.get(0).getName(), 1, result.get(0).whisperSettings(), result.get(0).getIGN());
				}
				
				else {
					//In channel and whispers settings on
				user = new User(userNick, 1, 1, "");
				}
				
				joinChannel(event, channelName, user);

			}
			
			else {
				event.respondChannel(userNick +", I am already in your channel!");
			}

		}
	}

	private void joinChannel(MessageEvent event, String channelName, User user) {
		JpaRepository.saveOrUpdateUser(user);
		event.respondChannel("Joining channel " + channelName);
		event.getBot().sendIRC().joinChannel(channelName);
		event.getBot().send().message(channelName, WELCOME_TEXT_MESSAGE);
	}
}
