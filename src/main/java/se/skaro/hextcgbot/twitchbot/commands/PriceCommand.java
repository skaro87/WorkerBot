package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.skaro.hextcgbot.model.ItemPrice;
import se.skaro.hextcgbot.repository.jpa.JpaRepository;
import se.skaro.hextcgbot.twitchbot.excpetions.SearchMessageToShortException;
import se.skaro.hextcgbot.util.MessageSender;

import java.util.List;

/**
 * Sends back the price of an item.
 */
@Component
public class PriceCommand extends AbstractCommand {

    @Autowired
    private MessageSender messageSender;

    public PriceCommand(String syntax, boolean isCommandCaseSensitive, String description) {
        super(syntax, isCommandCaseSensitive, description);
    }

    @Override
    public void call(String commandSyntax, MessageEvent event) {
        String name = fixWhiteSpaces(getMessageWithoutCommand(commandSyntax, event));
        if (name.length() < SearchMessageToShortException.DEFAULT_MINIMUM_LENGTH) {
            throw new SearchMessageToShortException();
        }

        List<ItemPrice> result = JpaRepository.findPriceByName(name);
        if (result.isEmpty()) {
            messageSender.sendMessage(event, "No price found for '" + name + "'");
        } else {
            Double ratio = JpaRepository.getRatio();
            StringBuilder sb = new StringBuilder();
            String separator = "";
            for (ItemPrice item : result) {
                sb.append(separator);
                sb.append(item.getListedPrice(ratio));
                separator = ", ";
            }
            messageSender.sendMessage(event, sb.toString());
        }
    }
}
