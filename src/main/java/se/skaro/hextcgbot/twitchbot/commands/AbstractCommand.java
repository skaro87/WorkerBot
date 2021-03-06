package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import se.skaro.hextcgbot.util.BotMessageType;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

public abstract class AbstractCommand implements ICommand {
    protected static final String CHANNEL_PREFIX = "#";
    protected static final DecimalFormat GOLD_DECIMAL_FORMAT = new DecimalFormat("#");
    protected static final DecimalFormat PLATINUM_DECIMAL_FORMAT = new DecimalFormat("#");

    protected final String syntax;
    protected final boolean isCommandCaseSensitive;
    protected final String description;
    protected final BotMessageType botMessageType;

    public AbstractCommand() {
        this.syntax = "";
        this.isCommandCaseSensitive = false;
        this.description = "";
        this.botMessageType = BotMessageType.DEFAULT;
    }

    public AbstractCommand(String syntax, boolean isCommandCaseSensitive, String description, BotMessageType botMessageType) {
        this.syntax = syntax;
        this.isCommandCaseSensitive = isCommandCaseSensitive;
        this.description = description;
        this.botMessageType = botMessageType;
    }

    public abstract void call(String commandSyntax, MessageEvent event);

    public String getSyntaxPattern(String commandPrefix) {
        return (isCommandCaseSensitive ? "" : "(?i)^") + Pattern.quote(commandPrefix + syntax) + "(\\s+.+)?";
    }

    public String getSyntax() {
        return syntax;
    }

    public boolean isCommandCaseSensitive() {
        return isCommandCaseSensitive;
    }

    public String getDescription() {
        return description;
    }

    public BotMessageType getBotMessageType() {
        return botMessageType;
    }

    protected static String getUserNick(MessageEvent event) {
        if (event.getUser() != null) {
            return event.getUser().getNick();
        }
        return null;
    }

    protected static String getChannelName(String userNick) {
        if (userNick != null) {
            return CHANNEL_PREFIX + userNick.toLowerCase();
        }
        return null;
    }

    protected static String getMessageWithoutCommand(String commandSyntax, MessageEvent event) {
        return event.getMessage().replaceFirst("(?i)" + Pattern.quote(commandSyntax), "");
    }

    protected static String fixWhiteSpaces(String message) {
        return message.trim().replaceAll("\\s+", " ");
    }
}
