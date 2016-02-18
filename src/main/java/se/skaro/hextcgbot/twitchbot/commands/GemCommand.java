package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.skaro.hextcgbot.model.Gem;
import se.skaro.hextcgbot.repository.jpa.JpaRepository;
import se.skaro.hextcgbot.twitchbot.excpetions.SearchMessageToShortException;
import se.skaro.hextcgbot.util.BotMessageType;
import se.skaro.hextcgbot.util.MessageSender;

import java.util.List;

/**
 * Sends back the info about a card.
 */
@Component
public class GemCommand extends AbstractCommand {

    @Autowired
    private MessageSender messageSender;

    public GemCommand(String syntax, boolean isCommandCaseSensitive, String description, BotMessageType botMessageType) {
        super(syntax, isCommandCaseSensitive, description, botMessageType);
    }

    @Override
    public void call(String commandSyntax, MessageEvent event) {
        String userNick = getUserNick(event);
        if (userNick != null) {
            String input = fixWhiteSpaces(getMessageWithoutCommand(commandSyntax, event));
            if (input.length() < SearchMessageToShortException.DEFAULT_MINIMUM_LENGTH) {
                throw new SearchMessageToShortException();
            }

            List<Gem> result = JpaRepository.findGemByName(input);
            if (result.isEmpty()) {
                result = JpaRepository.findGemByText(input);
                if (result.isEmpty()) {
                    messageSender.sendMessage(event, "No gem with name/text '" + input + "' found", botMessageType);
                } else {
                    sendOutput(result, input, event);
                }
            } else {
                sendOutput(result, input, event);
            }
        }
    }

    private void sendOutput(List<Gem> result, String name, MessageEvent event) {
        if (result.size() == 1) {
            messageSender.sendMessage(event, result.get(0).toString(), botMessageType);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Found multiple gems: ");
            String separator = "";
            for (Gem gem : result) {
                if (gem.getName().equalsIgnoreCase(name)) {
                    sb.delete(0, sb.length());
                    sb.append(gem.toString());
                    break;
                }

                sb.append(separator);
                sb.append(gem.getName());
                separator = ", ";
            }
            messageSender.sendMessage(event, sb.toString(), botMessageType);
        }
    }
}
