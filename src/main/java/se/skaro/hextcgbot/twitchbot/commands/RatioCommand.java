package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.skaro.hextcgbot.repository.jpa.JpaRepository;
import se.skaro.hextcgbot.util.BotMessageType;
import se.skaro.hextcgbot.util.MessageSender;

/**
 * Sends the current ratio collected from the database.
 */
@Component
public class RatioCommand extends AbstractCommand {

    @Autowired
    private MessageSender messageSender;

    public RatioCommand(String syntax, boolean isCommandCaseSensitive, String description, BotMessageType botMessageType) {
        super(syntax, isCommandCaseSensitive, description, botMessageType);
    }

    @Override
    public void call(String commandSyntax, MessageEvent event) {
        messageSender.respondChannel(event, "1 platinum is worth " + GOLD_DECIMAL_FORMAT.format(JpaRepository.getRatio()) + " gold", botMessageType);
    }
}
