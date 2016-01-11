package se.skaro.hextcgbot.twitchbot.commands;

//TODO: Set proper command descriptions.
public enum BotCommands {
    JOIN("!join", false, new JoinCommand(), "Use this command to make WorkerBot join your channel."),
    LEAVE("!leave", false, new LeaveCommand(), "Use this command to make WorkerBot leave your channel."),
    PRICE("!price", false, new PriceCommand(), "Use command !price [card] to check card's price."),
    CARD("!card", false, new CardCommand(), "Card command"),
    EQUIPMENT("!equipment", false, new EquipmentCommand(), "Equipment command"),
    RATIO("!ratio", false, new RatioCommand(), "Ratio command"),
    GOLD_TO_PLAT("!goldToPlat", false, new GoldToPlatCommand(), "Gold to platinum command"),
    PLAT_TO_GOLD("!platToGold", false, new PlatToGoldCommand(), "Gold to platinum command"),
    ABOUT("!about", false, new AboutCommand(), "About command"),
    INFO("!info", false, new AboutCommand(), "Info command"),
    DONATE("!donate", false, new DonateCommand(), "Donate command"),
    DECKS("!decks", false, new DecksCommand(), "Decks command"),
    STREAMS("!streams", false, new StreamsCommand(), "Streams command"),
    CHANNELS("!channels", false, new ChannelsCommand(), "Channels command"),
    WHISPERS("!whispers", false, new WhispersCommand(), "Whispers command"),
    HELP("!help", false, new HelpCommand(), "Help command"),
    BUG("!bug", false, new BugCommand(), "Bug command"),
    IGN("!ign", false, new IGNCommand(), "IGN command"),
    SET_IGN("!setIGN", false, new SetIGNCommand(), "Set IGN command"),
    USERS("!users", false, new UsersCommand(), "Users command");

    private final String syntax;
    private final boolean isCommandCaseSensitive;
    private final AbstractCommand command;
    private final String description;

    BotCommands(String syntax, boolean isCommandCaseSensitive, AbstractCommand command, String description) {
        this.syntax = syntax;
        this.isCommandCaseSensitive = isCommandCaseSensitive;
        this.command = command;
        this.description = description;
    }

    public String getSyntax() {
        return syntax;
    }

    public boolean isCommandCaseSensitive() {
        return isCommandCaseSensitive;
    }

    public AbstractCommand getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }
}
