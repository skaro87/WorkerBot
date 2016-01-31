package se.skaro.hextcgbot.twitchbot.excpetions;

public class SearchMessageToShortException extends RuntimeException {

    public static final int DEFAULT_MINIMUM_LENGTH = 4;
    private final int minimumLength;

    public SearchMessageToShortException() {
        super(createMessage(DEFAULT_MINIMUM_LENGTH));
        this.minimumLength = DEFAULT_MINIMUM_LENGTH;
    }

    public SearchMessageToShortException(int minimumLength) {
        super(createMessage(minimumLength));
        this.minimumLength = minimumLength;
    }

    private static String createMessage(int minimumLength) {
        String result = "You need at least " + minimumLength + " character";
        if (minimumLength != 1) {
            result += "s";
        }
        result += " to do a search.";
        return result;
    }
}
