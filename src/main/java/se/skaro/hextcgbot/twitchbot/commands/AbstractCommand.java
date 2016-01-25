package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

public abstract class AbstractCommand {
    protected static final String CHANNEL_PREFIX = "#";
    protected static final DecimalFormat GOLD_DECIMAL_FORMAT = new DecimalFormat("#");
    protected static final DecimalFormat PLATINUM_DECIMAL_FORMAT = new DecimalFormat("#");

    public abstract void call(String commandSyntax, MessageEvent event);

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
