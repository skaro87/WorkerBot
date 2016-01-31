package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.skaro.hextcgbot.model.User;
import se.skaro.hextcgbot.repository.jpa.JpaRepository;
import se.skaro.hextcgbot.twitchbot.excpetions.SearchMessageToShortException;
import se.skaro.hextcgbot.util.MessageSender;

import java.util.List;

/**
 * Gets the IGN associated with a user.
 */
@Component
public class IGNCommand extends AbstractCommand {

    @Autowired
    private MessageSender messageSender;

    public IGNCommand(String syntax, boolean isCommandCaseSensitive, String description) {
        super(syntax, isCommandCaseSensitive, description);
    }

    @Override
    public void call(String commandSyntax, MessageEvent event) {
        String userNick = getUserNick(event);
        if (userNick != null) {
            String message = fixWhiteSpaces(getMessageWithoutCommand(commandSyntax, event)).replaceFirst("^@", "").replaceFirst(" ", "");
            if (!message.isEmpty() && message.length() < SearchMessageToShortException.DEFAULT_MINIMUM_LENGTH) {
                throw new SearchMessageToShortException();
            }

            if (message.isEmpty()) {
                List<User> result = JpaRepository.findUserByName(userNick);
                if (result.isEmpty()) {
                    messageSender.sendMessage(event, userNick + ", you have not registerd your username. To register type !setign 'your IGN'");
                } else {
                    messageSender.respondChannel(event, "IGN for user " + result.get(0).getName() + " is " + result.get(0).getIGN());
                }
            } else {
                List<User> result = JpaRepository.findUserByNameWithWildcards(message);
                if (result.isEmpty()) {
                    messageSender.sendMessage(event, "IGN for user " + message + " not found");
                } else {
                    messageSender.sendMessage(event, "IGN for user " + result.get(0).getName() + " is " + result.get(0).getIGN());
                }
            }
        }
    }
}
