package se.skaro.hextcgbot.twitchbot.commands;

public enum BotCommands {
  //  JOIN("!join", false, new JoinCommand(), "Use this command to make WorkerBot join your channel."),
	LEAVE("!leave", false, new LeaveCommand(), "Use this command to make WorkerBot leave your channel."),
    PRICE("!price", false, new PriceCommand(), "Use command !price 'card' to check a card's price."),
    CARD("!card", false, new CardCommand(), "Find the text of a card by typing !card 'cardname'"),
    EQUIPMENT("!equipment", false, new EquipmentCommand(), "Find the text for Equipments by typing !equipment 'cardname'"),
    RATIO("!ratio", false, new RatioCommand(), "Check the current Platinum to Gold ratio"),
    GOLD_TO_PLAT("!gtp", false, new GoldToPlatCommand(), "Type !gtp 'amount' to get the amount of gold in platinum"),
    PLAT_TO_GOLD("!ptg", false, new PlatToGoldCommand(), "Type !ptg 'amount' to get the amount of platinum in gold"),
    ABOUT("!about", false, new AboutCommand(), "Information about WorkerBot"),
    INFO("!info", false, new AboutCommand(), "Information about WorkerBot"),
    DONATE("!donate", false, new DonateCommand(), "Information about where to donate to help pay for server fees"),
    DECKS("!decks", false, new DecksCommand(), "Find the latest 3 decks from a user from HEX:meta by typing !decks 'username'"),
    STREAMS("!streams", false, new StreamsCommand(), "Finds the top 5 current HEX: Shards of Fate streams"),
    CHANNELS("!channels", false, new ChannelsCommand(), "Returns how many channels WorkerBot is currently active in"),
    WHISPERS("!whispers", false, new WhispersCommand(), "Use this to turn whisper mode on and off by typing !whispers 'on/off'"),
    HELP("!help", false, new HelpCommand(), "Find usefull information about a command by typing !help 'commandname'"),
    BUG("!bug", false, new BugCommand(), "Want to post a bug report. Type !bug to get a link to the bug report form"),
    IGN("!ign", false, new IGNCommand(), "Find the IGN for a user by typing !ign 'username' (accepts @ before username) or get your username with just !ign"),
    SET_IGN("!setIGN", false, new SetIGNCommand(), "Set your IGN by typing !setign 'your IGN'. Min 3 and max 100 characters, usernames containing inappropriate and/or long names will be shortened."),
    IMG("!img", false, new ImageCommand(), "Used with the image browser plugin to show images of cards"),
    SHARDSHOPPER("!shardshopper", false, new ShardShopperCommand(), "Information about ShardShopper"),
    CHAMPION("!legend", false, new LegendCommand(), "Find the text of a legend by typing !legend 'name'"),
    GEM("!gem", false, new GemCommand(), "Find the text of a gem by typing !gem 'name/part of text'"),
    KEYWORD("!keyword", false, new KeywordCommand(), "Find the text of a keyword by typing !keyword 'keyword'");

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
