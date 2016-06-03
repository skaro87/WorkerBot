package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.skaro.hextcgbot.model.UserOLD;
import se.skaro.hextcgbot.repository.jpa.JpaRepository;
import se.skaro.hextcgbot.statistics.ChannelStats;
import se.skaro.hextcgbot.statistics.UserChannel;
import se.skaro.hextcgbot.util.BotMessageType;
import se.skaro.hextcgbot.util.MessageSender;

import java.util.List;

/**
 * Makes the channel owner able to change the whisper settings for chat.
 */
@Component
public class WhispersCommand extends AbstractCommand {

    @Autowired
    private MessageSender messageSender;

    public WhispersCommand(String syntax, boolean isCommandCaseSensitive, String description, BotMessageType botMessageType) {
        super(syntax, isCommandCaseSensitive, description, botMessageType);
    }

    @Override
    public void call(String commandSyntax, MessageEvent event) {
        String userNick = getUserNick(event);
        if (userNick != null) {
            String message = fixWhiteSpaces(getMessageWithoutCommand(commandSyntax, event));
            List<UserOLD> users = JpaRepository.findUserByName(userNick);
            if (!users.isEmpty()) {
                UserOLD user = users.get(0);
                if ("on".equalsIgnoreCase(message)) {
                    JpaRepository.saveOrUpdateUser(new UserOLD(user.getName(), user.isInChannel(), 1, user.getIGN()));
                    ChannelStats.getStats().put("#" + user.getName(), new UserChannel(1));
                    messageSender.respondChannel(event, "Whisper mode ON for channel " + userNick, botMessageType);
                } else if ("off".equalsIgnoreCase(message)) {
                    JpaRepository.saveOrUpdateUser(new UserOLD(user.getName(), user.isInChannel(), 0, user.getIGN()));
                    ChannelStats.getStats().put("#" + user.getName(), new UserChannel(0));
                    messageSender.respondChannel(event, "Whisper mode OFF for channel " + userNick, botMessageType);
                } else {
                    messageSender.respondChannel(event, "Invalid command " + event.getMessage()
                            + ". Use !whispers 'on/off' to change whisper settings", botMessageType);
                }
            } else {
                messageSender.sendMessage(event, userNick + ": You have to have WorkerBot join your channel before you can change whisper settings.", botMessageType);
            }
        }
    }
}
