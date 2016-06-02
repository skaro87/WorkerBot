package se.skaro.workerbot.bot.util;

import org.pircbotx.hooks.events.MessageEvent;

public class BotMessage {

    private final MessageEvent event;
    private String message;
    private final String whisperNick;
    private final String channelName;
    private final BotMessageType messageType;
    private final Integer innerPriority;

    public BotMessage(MessageEvent event, String message, String whisperNick, String channelName, BotMessageType messageType, Integer innerPriority) {
        this.event = event;
        this.message = message;
        this.whisperNick = whisperNick;
        this.channelName = channelName;
        this.messageType = messageType;
        this.innerPriority = innerPriority;
    }

    public MessageEvent getEvent() {
        return event;
    }

    public String getMessage() {
        return message;
    }

    public String getWhisperNick() {
        return whisperNick;
    }

    public String getChannelName() {
        return channelName;
    }

    public BotMessageType getMessageType() {
        return messageType;
    }

    public Integer getInnerPriority() {
        return innerPriority;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BotMessage)) return false;

        BotMessage that = (BotMessage) o;

        if (event != null ? !event.equals(that.event) : that.event != null) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        if (whisperNick != null ? !whisperNick.equals(that.whisperNick) : that.whisperNick != null) return false;
        if (channelName != null ? !channelName.equals(that.channelName) : that.channelName != null) return false;
        if (messageType != that.messageType) return false;
        return !(innerPriority != null ? !innerPriority.equals(that.innerPriority) : that.innerPriority != null);

    }

    @Override
    public int hashCode() {
        int result = event != null ? event.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (whisperNick != null ? whisperNick.hashCode() : 0);
        result = 31 * result + (channelName != null ? channelName.hashCode() : 0);
        result = 31 * result + (messageType != null ? messageType.hashCode() : 0);
        result = 31 * result + (innerPriority != null ? innerPriority.hashCode() : 0);
        return result;
    }
}
