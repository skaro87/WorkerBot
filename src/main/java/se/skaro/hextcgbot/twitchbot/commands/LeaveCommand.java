package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.skaro.hextcgbot.model.User;
import se.skaro.hextcgbot.repository.jpa.JpaRepository;
import se.skaro.hextcgbot.util.MessageSender;

/**
 * WorkerBot will leave the senders channel.
 */
@Component
public class LeaveCommand extends AbstractCommand {

    @Autowired
    private MessageSender messageSender;

    private static final String GOODBYE_TEXT_MESSAGE = "Goodbye. Feel free to add me to your chat again at any time!";

    public LeaveCommand(String syntax, boolean isCommandCaseSensitive, String description) {
        super(syntax, isCommandCaseSensitive, description);
    }

    @Override
    public void call(String commandSyntax, MessageEvent event) {
        String userNick = getUserNick(event);
        if (userNick != null) {
            String channelName = getChannelName(userNick);
            if (event.getBot().getUserChannelDao().containsChannel(channelName)) {
                User user = JpaRepository.findUserByName(userNick).get(0);
                User updatedUser = new User(user.getName(), 0, user.whisperSettings(), user.getIGN());
                JpaRepository.saveOrUpdateUser(updatedUser);
                messageSender.respondChannel(event, "Leaving channel " + channelName);
                messageSender.sendChannelMessage(event, channelName, GOODBYE_TEXT_MESSAGE);
            } else {
                messageSender.respondChannel(event, userNick + ", I am currently not in your channel");
            }
        }
    }
}
