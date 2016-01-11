package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;

/**
 * Sends information about commands.
 */
public class HelpCommand extends AbstractCommand {
    @Override
    public void call(String commandSyntax, MessageEvent event) {
        String message = fixWhiteSpaces(getMessageWithoutCommand(commandSyntax, event));
        if (message.isEmpty()) {
            StringBuilder helpMessage = new StringBuilder();
            helpMessage.append("Current commands are: ");
            String separator = "";
            for (BotCommands botCommand : BotCommands.values()) {
                helpMessage.append(separator).append(botCommand.getSyntax());
                separator = ", ";
            }
            event.respondChannel(helpMessage.toString());
        } else {
            for (BotCommands botCommand : BotCommands.values()) {
                if (botCommand.getSyntax().equalsIgnoreCase("!"+message) || botCommand.getSyntax().equalsIgnoreCase(message)) {
                    event.respondChannel(botCommand.getDescription());
                    return;
                }
            }
            event.respondChannel("Unknown command: " + message);
        }
    }
}
