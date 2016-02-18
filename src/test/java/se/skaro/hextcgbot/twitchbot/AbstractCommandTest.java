package se.skaro.hextcgbot.twitchbot;

import org.junit.Before;
import org.mockito.Mock;
import org.pircbotx.Channel;
import org.pircbotx.Configuration;
import org.pircbotx.UserHostmask;
import org.pircbotx.hooks.events.MessageEvent;
import se.skaro.hextcgbot.util.BotMessageType;
import se.skaro.hextcgbot.util.MessageSender;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;

public class AbstractCommandTest {

    @Mock
    protected MessageSender messageSender;

    protected String outContent = null;

    @Before
    public void setUp() {
        doAnswer(invocation -> {
            outContent = (String) invocation.getArguments()[1];
            System.out.println(outContent);
            return null;
        }).when(messageSender).sendMessage(any(MessageEvent.class), anyString(), any(BotMessageType.class));
    }

    protected MessageEvent mockMessageEvent(String message) {
        Configuration.BotFactory botFactory = new Configuration.BotFactory();
        TwitchBot bot = new TwitchBot("username", "oauth", "#username");
        Channel channel = botFactory.createChannel(bot, null);
        UserHostmask userHostmask = botFactory.createUserHostmask(bot, "extbanPrefix", "nick", "login", "hostname");
        return new MessageEvent(null, channel, "channelSource", userHostmask, botFactory.createUser(userHostmask), message, null);
    }
}
