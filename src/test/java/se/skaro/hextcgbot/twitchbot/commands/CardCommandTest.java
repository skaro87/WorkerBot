package se.skaro.hextcgbot.twitchbot.commands;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import se.skaro.hextcgbot.twitchbot.AbstractCommandTest;
import se.skaro.hextcgbot.twitchbot.excpetions.SearchMessageToShortException;

public class CardCommandTest extends AbstractCommandTest {

    @InjectMocks
    private CardCommand subject;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        super.setUp();
    }

    @Test(expected = SearchMessageToShortException.class)
    public void testCallMessageEmpty() throws Exception {
        subject.call("", mockMessageEvent(""));
    }
}