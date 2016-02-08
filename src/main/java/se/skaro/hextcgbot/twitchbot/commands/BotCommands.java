package se.skaro.hextcgbot.twitchbot.commands;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BotCommands {

    private String commandsPrefix;
    private List<AbstractCommand> commands;

    public String getCommandsPrefix() {
        return commandsPrefix;
    }

    public void setCommandsPrefix(String commandsPrefix) {
        this.commandsPrefix = commandsPrefix;
    }

    public List<AbstractCommand> getCommands() {
        return commands;
    }

    public void setCommands(List<AbstractCommand> commands) {
        this.commands = commands;
    }
}
