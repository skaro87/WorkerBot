package se.skaro.workerbot.bot.commands;

import java.util.List;

import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.skaro.workerbot.bot.messages.MessageSender;
import se.skaro.workerbot.data.entity.User;
import se.skaro.workerbot.data.repository.UserRepository;

@Component
public class IGNCommand extends AbstractCommand {
	
	@Autowired
	private UserRepository userRepository;

	public IGNCommand() {
		this.syntax = "ign";
	}
	
	@Override
	public void call(ChannelMessageEvent event) {
		
		if (event != null) {
			String message = trimMessage(event.getMessage());

			List<User> users = userRepository.findByNameIgnoreCase(message);

			if (users.isEmpty()) {
				MessageSender.sendMessage(event, "No user with name " + message + " found");
			} else {
				
				if (users.size() == 1){
					MessageSender.sendMessage(event, "IGN for user "+message+" is "+users.get(0).getIgn());
				}
				
				else {
					MessageSender.sendMessage(event, "Multiple users found");
				}
			}

		}

	}

}
