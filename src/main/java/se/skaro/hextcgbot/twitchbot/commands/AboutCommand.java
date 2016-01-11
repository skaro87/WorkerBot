package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;

/**
 * Sends info about the bot.
 */
public class AboutCommand extends AbstractCommand {

    private static final String ABOUT_TEXT_MESSAGE = "Version 1.1 " +
            "| For more information go to twitch.tv/workerbot " +
            "| Thanks Celendine, Dinotopia, Veetorp and Doc-X for the data " +
            "| Coded in Java by Skaro and Risterral ";

    @Override
    public void call(String commandSyntax, MessageEvent event) {
        event.respondChannel(ABOUT_TEXT_MESSAGE);
    }
}
