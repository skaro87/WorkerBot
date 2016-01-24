package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import se.skaro.hextcgbot.repository.jpa.JpaRepository;

/**
 * Sends the current ratio collected from the database.
 */
public class RatioCommand extends AbstractCommand {

    @Override
    public void call(String commandSyntax, MessageEvent event) {
        event.respondChannel("1 platinum is worth " + GOLD_DECIMAL_FORMAT.format(JpaRepository.getRatio()) + " gold");
    }
}
