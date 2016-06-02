package se.skaro.workerbot.bot.commands;

import org.pircbotx.hooks.events.MessageEvent;

public interface ICommand {
    void call(String commandSyntax, MessageEvent event);
}
