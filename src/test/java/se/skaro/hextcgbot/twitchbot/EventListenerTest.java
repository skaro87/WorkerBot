package se.skaro.hextcgbot.twitchbot;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.pircbotx.hooks.events.MessageEvent;
import se.skaro.hextcgbot.twitchbot.commands.AbstractCommand;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;


public class EventListenerTest extends AbstractCommandTest {

    private static final int NUMBER_OF_BOT_COMMANDS = 10;

    @Mock
    private List<AbstractCommand> botCommands;

    @InjectMocks
    private EventListener subject;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        super.setUp();
        when(botCommands.iterator()).thenReturn(mockBotCommands().iterator());
    }

    @Test
    public void testOnMessageNoCommand() throws Exception {
        subject.onMessage(mockMessageEvent("!notExistingCommand"));
        assertNull(outContent);
    }

    @Test
    public void testOnMessageAllCommands() throws Exception {
        for (int i = 0; i < NUMBER_OF_BOT_COMMANDS; i++) {
            final String testName = createTestName(i);
            subject.onMessage(mockMessageEvent("!" + testName));
            assertEquals(testName, outContent);
        }
    }

    private List<AbstractCommand> mockBotCommands() {
        ArrayList<AbstractCommand> mock = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_BOT_COMMANDS; i++) {
            final String testName = createTestName(i);
            mock.add(new AbstractCommand("!" + testName, false, testName) {
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