package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.skaro.hextcgbot.model.User;
import se.skaro.hextcgbot.repository.jpa.JpaRepository;
import se.skaro.hextcgbot.util.BotMessageType;
import se.skaro.hextcgbot.util.MessageSender;

import java.util.List;

/**
 * WorkerBot will join the senders channel.
 */
@Component
public class JoinCommand extends AbstractCommand {

    @Autowired
    private MessageSender messageSender;

    private static final String WELCOME_TEXT_MESSAGE = "Hello! Thank you for using me. For more information about me visit my twitch page";

    public JoinCommand(String syntax, boolean isCommandCaseSensitive, String description, BotMessageType botMessageType) {
        super(syntax, isCommandCaseSensitive, description, botMessageType);
    }

    @Override
    public void call(String commandSyntax, MessageEvent event) {
        String userNick = getUserNick(event);
        if (userNick != null) {
            String channelName = getChannelName(userNick);
            if (!event.getBot().getUserChannelDao().containsChannel(channelName)) {
                List<User> result = JpaRepository.findUserByName(userNick);
                User user;
                if (!result.isEmpty()) {
                    user = new User(result.get(0).getName(), 1, result.get(0).whisperSettings(), result.get(0).getIGN());
                } else {
                    user = new User(userNick, 1, 1, "");
                }
                joinChannel(event, channelName, user);
            } else {
                messageSender.respondChannel(event, userNick + ", I am already in your channel!", botMessageType);
            }
        }
    }

    private void joinChannel(MessageEvent event, String channelName, User user) {
        JpaRepository.saveOrUpdateUser(user);
        messageSender.respondChannel(event, "Joining channel " + channelName, botMessageType);
        event.getBot().sendIRC().joinChannel(channelName);
        messageSender.sendChannelMessage(event, channelName, WELCOME_TEXT_MESSAGE, botMessageType);
    }
}
