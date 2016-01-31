package se.skaro.hextcgbot.twitchbot;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ConnectEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import se.skaro.hextcgbot.twitchbot.commands.AbstractCommand;
import se.skaro.hextcgbot.twitchbot.excpetions.SearchMessageToShortException;
import se.skaro.hextcgbot.util.MessageSender;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * The listener interface for receiving command events. The class that is
 * interested in processing a command event implements this interface, and the
 * object created with that class is registered with a component using the
 * component's <code>addCommandListener<code> method. When the command event
 * occurs, that object's appropriate method is invoked.
 */
@Configurable
public class EventListener extends ListenerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventListener.class);

    @Autowired
    private MessageSender messageSender;

    @Resource
    private List<AbstractCommand> botCommands;

    private Set<String> channels;

    @Override
    public void onMessage(final MessageEvent event) throws Exception {
        try {
            String message = event.getMessage();
            String lowercaseMessage = message.toLowerCase();

            if (message.startsWith("!")) {
                for (AbstractCommand botCommand : botCommands) {
                    if (message.startsWith(botCommand.getSyntax()) || (!botCommand.isCommandCaseSensitive()
                            && lowercaseMessage.startsWith(botCommand.getSyntax().toLowerCase()))) {
                        botCommand.call(botCommand.getSyntax(), event);
                        return;
                    }
                }
            }
        } catch (SearchMessageToShortException e) {
            messageSender.sendMessage(event, e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Unexpected error while receiving message", e);
            event.respondChannel(">>Initiate synergistic subrout##%---->>!!ERROR!! NO SYNERGY DETECTED");
        }

    }

    @Override
    public void onConnect(ConnectEvent event) throws Exception {
        channels.forEach(s -> {
            event.getBot().sendIRC().joinChannel(s);
            System.out.println(s);
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        });

        super.onConnect(event);
    }

    public void setChannels(Set<String> channels) {
        this.channels = channels;
    }
}