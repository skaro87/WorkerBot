package se.skaro.hextcgbot.twitchbot.commands;

import java.util.List;

import org.pircbotx.hooks.events.MessageEvent;
import se.skaro.hextcgbot.events.MessageSender;
import se.skaro.hextcgbot.model.User;
import se.skaro.hextcgbot.repository.jpa.JpaRepository;

/**
 * Sets user in-game-name.
 */
public class SetIGNCommand extends AbstractCommand {
    @Override
    public void call(String commandSyntax, MessageEvent event) {
        String userNick = getUserNick(event);
        if (userNick != null) {
            String message = fixWhiteSpacesAndSymbols(getMessageWithoutCommand(commandSyntax, event));
            System.out.println(message);
            if (!message.isEmpty()) {
            	
            	User updatedUser;
            	
             	List <User> result = JpaRepository.findUserByName(userNick);
             	
             	if (result.isEmpty()){
             		updatedUser = new User(userNick, 0, 1, message);
             	}
             	
             	else {
             		updatedUser = new User(result.get(0).getName(), result.get(0).isInChannel(), result.get(0).whisperSettings(), message);
             	}
            	JpaRepository.saveOrUpdateUser(updatedUser);
            	MessageSender.sendMessage(event, "IGN updated for user "+userNick);
            	
            } else {
                MessageSender.sendMessage(event, "You have to type in your IGN.");
            }
        }
    }
}
