package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;

public class DonateCommand extends AbstractCommand {

    private static final String DONATE_TEXT_MESSAGE = "Donate to WorkerBot at https://www.twitchalerts.com/donate/workerbot";

    @Override
    public void call(String commandSyntax, MessageEvent event) {
        event.respondChannel(DONATE_TEXT_MESSAGE);
    }
}
