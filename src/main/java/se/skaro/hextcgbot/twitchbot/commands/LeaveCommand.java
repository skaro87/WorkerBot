package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;

import se.skaro.hextcgbot.model.User;
import se.skaro.hextcgbot.repository.jpa.JpaRepository;

/**
 * WorkerBot will leave the senders channel.
 */
public class LeaveCommand extends AbstractCommand {

    private static final String GOODBYE_TEXT_MESSAGE = "Goodbye. Feel free to add me to your chat again at any time!";

    @Override
    public void call(String commandSyntax, MessageEvent event) {
        String userNick = getUserNick(event);
        if (userNick != null) {
            String channelName = getChannelName(userNick);
            if (event.getBot().getUserChannelDao().containsChannel(channelName)) {            	
            	User user = JpaRepository.findUserByName(userNick).get(0);
            	User updatedUser = new User(user.getName(), 0, user.whisperSettings(), user.getIGN());
            	JpaRepository.saveOrUpdateUser(updatedUser);           	
                event.respondChannel("Leaving channel " + channelName);
                event.getBot().getUserChannelDao().getChannel(channelName).send().message(GOODBYE_TEXT_MESSAGE);
                event.getBot().getUserChannelDao().getChannel(channelName).send().part();               
            } 
            
            else {
                event.respondChannel(userNick + ", I am currently not in your channel");
            }
        }
    }
}
