package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.skaro.hextcgbot.model.Keyword;
import se.skaro.hextcgbot.repository.jpa.JpaRepository;
import se.skaro.hextcgbot.twitchbot.excpetions.SearchMessageToShortException;
import se.skaro.hextcgbot.util.MessageSender;

import java.util.List;

/**
 * Sends back the info about a card.
 */
@Component
public class KeywordCommand extends AbstractCommand {

    @Autowired
    private MessageSender messageSender;

    public KeywordCommand(String syntax, boolean isCommandCaseSensitive, String description) {
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

            List<Keyword> result = JpaRepository.findKeywordByName(name);
            if (result.isEmpty()) {
                messageSender.sendMessage(event, "Keyword '" + name + "' not found");
                return;
            }
            if (result.size() == 1) {
                messageSender.sendMessage(event, result.get(0).toString());
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Found multiple cards: ");
            String separator = "";
            for (Keyword k : result) {
                if (k.getName().equalsIgnoreCase(name)) {
                    sb.delete(0, sb.length());
                    sb.append(k.toString());
                    break;
                }

                sb.append(separator);
                sb.append(k.getName());
                separator = ", ";
            }
            messageSender.sendMessage(event, sb.toString());
        }
    }
}
