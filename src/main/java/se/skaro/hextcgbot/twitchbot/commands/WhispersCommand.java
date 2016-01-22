package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;

import se.skaro.hextcgbot.model.User;
import se.skaro.hextcgbot.repository.jpa.JpaRepository;

/**
 * Makes the channel owner able to change the whisper settings for chat.
 */
public class WhispersCommand extends AbstractCommand {
    @Override
    public void call(String commandSyntax, MessageEvent event) {
        String userNick = getUserNick(event);
        if (userNick != null && event.getBot().getUserChannelDao().containsChannel(getChannelName(userNick))) {
            String message = fixWhiteSpacesAndSymbols(getMessageWithoutCommand(commandSyntax, event));
            
            User user = JpaRepository.findUserByName(userNick).get(0);
            
            if ("on".equalsIgnoreCase(message)) {
                JpaRepository.saveOrUpdateUser(new User(user.getName(), user.isInChannel(), 1, user.getIGN()));
                event.respondChannel("Whisper mode ON for channel " + userNick);
            } else if ("off".equalsIgnoreCase(message)) {
            	  JpaRepository.saveOrUpdateUser(new User(user.getName(), user.isInChannel(), 0, user.getIGN()));
                event.respondChannel("Whisper mode OFF for channel " + userNick);
            } else {
            	event.respondChannel("Invalid command "+event.getMessage() + ". Use !whispers 'on/off' to change whisper settings");
            }
        }
    }
}
