package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.skaro.hextcgbot.model.UserOLD;
import se.skaro.hextcgbot.repository.jpa.JpaRepository;
import se.skaro.hextcgbot.util.BotMessageType;
import se.skaro.hextcgbot.util.MessageSender;

import java.util.List;

/**
 * Sets user in-game-name.
 */
@Component
public class SetIGNCommand extends AbstractCommand {

    @Autowired
    private MessageSender messageSender;

    public SetIGNCommand(String syntax, boolean isCommandCaseSensitive, String description, BotMessageType botMessageType) {
        super(syntax, isCommandCaseSensitive, description, botMessageType);
    }

    @Override
    public void call(String commandSyntax, MessageEvent event) {
        String userNick = getUserNick(event);
        if (userNick != null) {
            String message = fixWhiteSpaces(getMessageWithoutCommand(commandSyntax, event));
            if (!message.isEmpty()) {
                List<UserOLD> result = JpaRepository.findUserByName(userNick);
                UserOLD updatedUser;
                if (result.isEmpty()) {
                    updatedUser = new UserOLD(userNick, 0, 1, message);
                } else {
                    updatedUser = new UserOLD(result.get(0).getName(), result.get(0).isInChannel(), result.get(0).whisperSettings(), message);
                }

                JpaRepository.saveOrUpdateUser(updatedUser);
                messageSender.sendMessage(event, "IGN updated for user " + userNick, botMessageType);
            } else {
                messageSender.sendMessage(event, "You have to type in your IGN.", botMessageType);
            }
        }
    }
}
