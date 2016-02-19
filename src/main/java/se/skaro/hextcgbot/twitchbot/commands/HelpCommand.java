package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.skaro.hextcgbot.util.BotMessageType;
import se.skaro.hextcgbot.util.MessageSender;

import java.util.regex.Pattern;

/**
 * Sends information about commands.
 */
@Component
public class HelpCommand extends AbstractCommand {

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private BotCommands botCommands;

    public HelpCommand(String syntax, boolean isCommandCaseSensitive, String description, BotMessageType botMessageType) {
        super(syntax, isCommandCaseSensitive, description, botMessageType);
    }

    @Override
    public void call(String commandSyntax, MessageEvent event) {
        String message = fixWhiteSpaces(getMessageWithoutCommand(commandSyntax, event));
        if (message.isEmpty()) {
            StringBuilder helpMessage = new StringBuilder();
            helpMessage.append("Current commands are: ");
            String separator = "";
            for (AbstractCommand botCommand : botCommands.getCommands()) {
                helpMessage.append(separator).append(botCommands.getCommandsPrefix()).append(botCommand.getSyntax());
                separator = ", ";
            }
            messageSender.respondChannel(event, helpMessage.toString(), botMessageType);
        } else {
            String messageWithoutPrefix = message.replaceAll("^" + Pattern.quote(botCommands.getCommandsPrefix()), "");
            for (AbstractCommand botCommand : botCommands.getCommands()) {
                if (botCommand.getSyntax().equalsIgnoreCase(messageWithoutPrefix)) {
                    messageSender.respondChannel(event, botCommand.getDescription(), botMessageType);
                    return;
                }
            }
            messageSender.respondChannel(event, "Unknown command: " + message, botMessageType);
        }
    }
}
