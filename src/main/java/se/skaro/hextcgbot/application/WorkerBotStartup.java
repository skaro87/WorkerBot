package se.skaro.hextcgbot.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.PathResource;
import se.skaro.hextcgbot.model.User;
import se.skaro.hextcgbot.repository.jpa.JpaRepository;
import se.skaro.hextcgbot.twitchbot.DefaultListener;
import se.skaro.hextcgbot.twitchbot.EventListener;
import se.skaro.hextcgbot.twitchbot.TwitchBot;
import se.skaro.hextcgbot.util.PropertyGetter;
import se.skaro.hextcgbot.util.WhispersSettings;

import java.util.HashSet;
import java.util.Set;

import static se.skaro.hextcgbot.util.PropertyGetter.*;

public class WorkerBotStartup {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerBotStartup.class);

    private static final String PROPERTY_GETTER_BEAN_ID = "propertyGetter";
    private static final String WHISPERS_SETTINGS_BEAN_ID = "whispersSettings";
    private static final String COMMAND_LISTENER_BEAN_ID = "eventListener";
    private static final Integer MAXIMUM_NUMBER_OF_CONNECT_ATTEMPTS = 10;

    private PropertyGetter propertyGetter;
    private WhispersSettings whispersSettings;
    private EventListener eventListener;

    public WorkerBotStartup(String applicationContextPath, String propertiesPath) {
        loadBeans(applicationContextPath);
        propertyGetter.loadProperties(propertiesPath);
        JpaRepository.startup();
    }

    public void startBot() {
        String botUsername = propertyGetter.getProperty(USERNAME);
        String oauth = propertyGetter.getProperty(OAUTH);
        eventListener.setChannels(loadUsersChannels(botUsername));

        Integer numberOfAttempts = 0;
        while (numberOfAttempts < MAXIMUM_NUMBER_OF_CONNECT_ATTEMPTS) {
            try {
                TwitchBot bot = new TwitchBot(botUsername, oauth);
                bot.setUseTwitchCapabilities(true);
                bot.addListener(new DefaultListener());
                bot.addListener(eventListener);
                bot.startBot();
            } catch (Exception e) {
                LOGGER.error("Error while starting twitch bot. Number of attempts: " + numberOfAttempts, e);
            }
        }
    }

    private void loadBeans(String applicationContextPath) {
        final GenericApplicationContext ctx = new GenericApplicationContext();
        final XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(ctx);
        xmlReader.loadBeanDefinitions(new PathResource(applicationContextPath));
        ctx.refresh();
        propertyGetter = (PropertyGetter) ctx.getBean(PROPERTY_GETTER_BEAN_ID);
        whispersSettings = (WhispersSettings) ctx.getBean(WHISPERS_SETTINGS_BEAN_ID);
        eventListener = (EventListener) ctx.getBean(COMMAND_LISTENER_BEAN_ID);
    }

    private Set<String> loadUsersChannels(String botUsername) {
        boolean joinDBChannels = Boolean.valueOf(propertyGetter.getProperty(JOIN_DB_CHANNELS));
        Set<String> channels = new HashSet<>();
        String botChannel = "#" + botUsername;
        channels.add(botChannel);
        whispersSettings.putUserWhisperSetting(botChannel, false);

        for (User user : JpaRepository.findUsersToAutoJoin()) {
            String usernameChannel = "#" + user.getName().toLowerCase();
            if (joinDBChannels) {
                channels.add(usernameChannel);
            }
            whispersSettings.putUserWhisperSetting( usernameChannel, user.whisperSettings() == 1);
        }

        for (String channelName : propertyGetter.getProperty(DEFAULT_JOIN_CHANNELS).split(DELIMITER)) {
            if (!channelName.isEmpty()) {
                channels.add(channelName);
            }
        }
        return channels;
    }
}
