package se.skaro.workerbot.bot.messages;

import java.util.Arrays;
import java.util.List;

import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;
import org.kitteh.irc.lib.net.engio.mbassy.listener.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import se.skaro.workerbot.bot.commands.AbstractCommand;
import se.skaro.workerbot.bot.commands.BotCommands;
import se.skaro.workerbot.data.entity.User;
import se.skaro.workerbot.data.repository.UserRepository;

@Configuration
public class MessageHandler {

	private List<String> prefixes;

	@Autowired
	private BotCommands botCommands;

	@Autowired
	private UserRepository userRepository;

	@Handler
	public void onMessage(ChannelMessageEvent event) {
				
		if (event.getMessage() != null && event.getChannel() != null && event.getActor() != null) {
			String firstChar = event.getMessage().substring(0, 1);

			if (prefixes.contains(firstChar)) {
				String message = event.getMessage().substring(1, event.getMessage().length());
				List<User> users = userRepository.findByNameIgnoreCase(event.getActor().getNick());
				if (!users.isEmpty()) {
					
					User user = users.get(0);
					if (user.getPrefix().equals(firstChar)) {

						for (AbstractCommand botCommand : botCommands.getCommands()) {

							// TODO: Better logic here, so that !champ and
							// !champion doesn't point to the same command
							if (message.toLowerCase().startsWith(botCommand.getSyntax().toLowerCase())) {
								botCommand.call(event);
								return;
							}
						} // for loop end
					}
				} // user is returned
			} // global prefix check
		} // null check
	}

	public void setupPrefixAndCommnads(String prefix) {
		prefixes = Arrays.asList(prefix.split(","));
		botCommands.setup();
	}

}
