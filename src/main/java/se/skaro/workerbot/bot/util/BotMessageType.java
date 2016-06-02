package se.skaro.workerbot.bot.util;

public enum BotMessageType {
    DEFAULT(0, true),
    LOWEST_GROUPED(1, true),
    LOWEST(2, false),
    LOW_GROUPED(3, true),
    LOW(4, false),
    NORMAL_GROUPED(5, true),
    NORMAL(6, false),
    HIGH_GROUPED(7, true),
    HIGH(8, false),
    HIGHEST_GROUPED(9, true),
    HIGHEST(10, false);

    private final Integer priority;
    private final boolean tryToGroup;

    BotMessageType(Integer priority, boolean tryToGroup) {
        this.priority = priority;
        this.tryToGroup = tryToGroup;
    }

    public Integer getPriority() {
        return priority;
    }

    public boolean isTryToGroup() {
        return tryToGroup;
    }
}
