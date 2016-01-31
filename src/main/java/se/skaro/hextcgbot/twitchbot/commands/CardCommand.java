package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.skaro.hextcgbot.model.Card;
import se.skaro.hextcgbot.repository.jpa.JpaRepository;
import se.skaro.hextcgbot.twitchbot.excpetions.SearchMessageToShortException;
import se.skaro.hextcgbot.util.MessageSender;

import java.util.List;

/**
 * Sends back the info about a card.
 */
@Component
public class CardCommand extends AbstractCommand {

    @Autowired
    private MessageSender messageSender;

    public CardCommand() {
    }

    public CardCommand(String syntax, boolean isCommandCaseSensitive, String description) {
        super(syntax, isCommandCaseSensitive, description);
    }

    @Override
    public void call(String commandSyntax, MessageEvent event) {
        String name = fixWhiteSpaces(getMessageWithoutCommand(commandSyntax, event));
        if (name.length() < SearchMessageToShortException.DEFAULT_MINIMUM_LENGTH) {
            throw new SearchMessageToShortException();
        }

        List<Card> result = JpaRepository.findCardByFormatedName(name.replace("'", ""));
        if (result.isEmpty()) {
            messageSender.sendMessage(event, "No card with name '" + name + "' found");
            return;
        }
        if (result.size() == 1) {
            messageSender.sendMessage(event, result.get(0).toString());
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Found multiple cards: ");
        String separator = "";
        for (Card card : result) {
            if (card.getFormatedName().equalsIgnoreCase(name)) {
                sb.delete(0, sb.length());
                sb.append(card.toString());
                break;
            }

            sb.append(separator);
            sb.append(card.getName());
            separator = ", ";
        }
        messageSender.sendMessage(event, sb.toString());
    }
}
