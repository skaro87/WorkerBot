package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.skaro.hextcgbot.model.User;
import se.skaro.hextcgbot.repository.jpa.JpaRepository;
import se.skaro.hextcgbot.twitchbot.excpetions.SearchMessageToShortException;
import se.skaro.hextcgbot.util.BotMessageType;
import se.skaro.hextcgbot.util.MessageSender;

import java.util.List;

/**
 * Gets the IGN associated with a user.
 */
@Component
public class IGNCommand extends AbstractCommand {

    @Autowired
    private MessageSender messageSender;

    public IGNCommand(String syntax, boolean isCommandCaseSensitive, String description, BotMessageType botMessageType) {
        super(syntax, isCommandCaseSensitive, description, botMessageType);
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
                    messageSender.sendMessage(event, userNick + ", you have not registered your username. To register type !setign 'your IGN'", botMessageType);
                } else {
                    messageSender.respondChannel(event, "IGN for user " + result.get(0).getName() + " is " + result.get(0).getIGN(), botMessageType);
                }
            } else {
                List<User> result = JpaRepository.findUserByNameWithWildcards(message);
                if (result.isEmpty()) {
                    messageSender.sendMessage(event, "IGN for user " + message + " not found", botMessageType);
                } else {
                    messageSender.sendMessage(event, "IGN for user " + result.get(0).getName() + " is " + result.get(0).getIGN(), botMessageType);
                }
            }
        }
    }
}
