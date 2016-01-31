package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.skaro.hextcgbot.util.MessageSender;

import javax.annotation.Resource;
import java.util.List;

/**
 * Sends information about commands.
 */
@Component
public class HelpCommand extends AbstractCommand {

    @Autowired
    private MessageSender messageSender;

    @Resource
    private List<AbstractCommand> botCommands;

    public HelpCommand(String syntax, boolean isCommandCaseSensitive, String description) {
        super(syntax, isCommandCaseSensitive, description);
    }

    @Override
    public void call(String commandSyntax, MessageEvent event) {
        String message = fixWhiteSpaces(getMessageWithoutCommand(commandSyntax, event));
        if (message.isEmpty()) {
            StringBuilder helpMessage = new StringBuilder();
            helpMessage.append("Current commands are: ");
            String separator = "";
            for (AbstractCommand botCommand : botCommands) {
                helpMessage.append(separator).append(botCommand.getSyntax());
                separator = ", ";
            }
            messageSender.respondChannel(event, helpMessage.toString());
        } else {
            for (AbstractCommand botCommand : botCommands) {
                if (botCommand.getSyntax().equalsIgnoreCase("!" + message) || botCommand.getSyntax().equalsIgnoreCase(message)) {
                    messageSender.respondChannel(event, botCommand.getDescription());
                    return;
                }
            }
            messageSender.respondChannel(event, "Unknown command: " + message);
        }
    }
}
