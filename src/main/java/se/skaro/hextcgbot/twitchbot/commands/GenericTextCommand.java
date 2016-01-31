package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.skaro.hextcgbot.util.MessageSender;

/**
 * Sends back a query for the bug report tool.
 */
@Component
public class GenericTextCommand extends AbstractCommand {

    @Autowired
    private MessageSender messageSender;

    private final String outputText;

    public GenericTextCommand(String syntax, boolean isCommandCaseSensitive, String description, String outputText) {
        super(syntax, isCommandCaseSensitive, description);
        this.outputText = outputText;
    }

    @Override
    public void call(String commandSyntax, MessageEvent event) {
        messageSender.respondChannel(event, outputText);
    }
}
