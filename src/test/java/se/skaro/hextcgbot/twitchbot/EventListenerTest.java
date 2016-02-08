package se.skaro.hextcgbot.twitchbot;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.pircbotx.hooks.events.MessageEvent;
import se.skaro.hextcgbot.twitchbot.commands.AbstractCommand;
import se.skaro.hextcgbot.twitchbot.commands.BotCommands;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

public class EventListenerTest extends AbstractCommandTest {

    private static final int NUMBER_OF_BOT_COMMANDS = 10;
    private static final String COMMANDS_PREFIX = "!";

    @Mock
    private BotCommands botCommands;

    @InjectMocks
    private EventListener subject;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        super.setUp();
        when(botCommands.getCommandsPrefix()).thenReturn(COMMANDS_PREFIX);
        when(botCommands.getCommands()).thenReturn(mockBotCommands());
    }

    @Test
    public void testOnMessageNoCommand() throws Exception {
        subject.onMessage(mockMessageEvent("!notExistingCommand"));
        assertNull(outContent);
    }

    @Test
    public void testOnMessageCommandsPrefix() throws Exception {
        for (int i = 0; i < NUMBER_OF_BOT_COMMANDS; i++) {
            final String testName = createTestName(i);
            outContent = null;
            subject.onMessage(mockMessageEvent(testName));
            assertNull(outContent);
        }
    }

    @Test
    public void testOnMessageAllCommandsCaseInsensitive() throws Exception {
        for (int i = 0; i < NUMBER_OF_BOT_COMMANDS; i++) {
            final String testName = createTestName(i);
            outContent = null;
            subject.onMessage(mockMessageEvent(COMMANDS_PREFIX + testName));
            assertEquals(testName, outContent);
        }
    }

    @Test
    public void testOnMessageAllCommandsCaseSensitive() throws Exception {
        for (int i = 0; i < NUMBER_OF_BOT_COMMANDS; i++) {
            final String testName = createTestName(i);
            outContent = null;
            subject.onMessage(mockMessageEvent(COMMANDS_PREFIX + testName.toUpperCase()));
            if (i % 2 == 0) {
                assertNull(outContent);
            } else {
                assertEquals(testName, outContent);
            }
        }
    }

    @Test
    public void testOnMessageJoinedCommandSyntax() throws Exception {
        subject.onMessage(mockMessageEvent(COMMANDS_PREFIX + createTestName(0) + createTestName(0)));
        assertNull(outContent);
    }

    private List<AbstractCommand> mockBotCommands() {
        ArrayList<AbstractCommand> mock = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_BOT_COMMANDS; i++) {
            final String testName = createTestName(i);
            mock.add(new AbstractCommand(testName, i % 2 == 0, testName) {
                @Override
                public void call(String commandSyntax, MessageEvent event) {
                    messageSender.sendMessage(event, testName);
                }
            });
        }
        return mock;
    }

    private String createTestName(int testNumber) {
        return "test" + testNumber;
    }
}