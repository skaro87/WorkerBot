package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import se.skaro.hextcgbot.events.MessageSender;

/**
 * Sends back a query for the bug report tool.
 */
public class BugCommand extends AbstractCommand {

    private static final String BUG_TEXT_MESSAGE = "If you have encountered a bug, please fill in a bug report at http://goo.gl/forms/hI3s8x2LEb";

    @Override
    public void call(String commandSyntax, MessageEvent event) {
        MessageSender.sendMessage(event, BUG_TEXT_MESSAGE);
    }
}
