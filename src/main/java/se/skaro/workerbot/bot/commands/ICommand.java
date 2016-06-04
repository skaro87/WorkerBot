package se.skaro.workerbot.bot.commands;

import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;

public interface ICommand {
    void call(ChannelMessageEvent event);
}
