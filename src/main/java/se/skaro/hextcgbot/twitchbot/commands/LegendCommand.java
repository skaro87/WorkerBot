package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.skaro.hextcgbot.model.Champion;
import se.skaro.hextcgbot.repository.jpa.JpaRepository;
import se.skaro.hextcgbot.twitchbot.excpetions.SearchMessageToShortException;
import se.skaro.hextcgbot.util.MessageSender;

import java.util.List;

/**
 * Sends back the info about a legend.
 */
@Component
public class LegendCommand extends AbstractCommand {

    @Autowired
    private MessageSender messageSender;

    public LegendCommand(String syntax, boolean isCommandCaseSensitive, String description) {
        super(syntax, isCommandCaseSensitive, description);
    }

    @Override
    public void call(String commandSyntax, MessageEvent event) {
        String userNick = getUserNick(event);
        if (userNick != null) {
            String name = fixWhiteSpaces(getMessageWithoutCommand(commandSyntax, event));
            if (name.length() < SearchMessageToShortException.DEFAULT_MINIMUM_LENGTH) {
                throw new SearchMessageToShortException();
            }

            List<Champion> result = JpaRepository.findChampionByName(name);
            if (result.isEmpty()) {
                messageSender.sendMessage(event, "No champion with name '" + name + "' found");
                return;
            }
            if (result.size() == 1) {
                messageSender.sendMessage(event, result.get(0).toString());
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Found multiple champions: ");
            String separator = "";
            for (Champion c : result) {
                if (c.getName().equalsIgnoreCase(name)) {
                    sb.delete(0, sb.length());
                    sb.append(c.toString());
                    break;
                }

                sb.append(separator);
                sb.append(c.getName());
                separator = ", ";
            }
            messageSender.sendMessage(event, sb.toString());
        }
    }
}
