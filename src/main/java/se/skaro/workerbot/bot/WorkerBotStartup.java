package se.skaro.workerbot.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.PathResource;

import se.skaro.hextcgbot.twitchbot.EventListener;
import se.skaro.workerbot.bot.util.PropertyGetter;

public class WorkerBotStartup {

	private static final Logger LOGGER = LoggerFactory.getLogger(WorkerBotStartup.class);

	private static final String PROPERTY_GETTER_BEAN_ID = "propertyGetter";
	private static final String COMMAND_LISTENER_BEAN_ID = "eventListener";
	private static final Integer MAXIMUM_NUMBER_OF_CONNECT_ATTEMPTS = 10;

	private PropertyGetter propertyGetter;
	private EventListener eventListener;

	public WorkerBotStartup(String applicationContextPath, String propertiesPath) {
		loadBeans(applicationContextPath);
		propertyGetter.loadProperties(propertiesPath);
	}

	public void startBot() {
		IrcBot bot = new IrcBot(propertyGetter.getProperty(PropertyGetter.SERVER),
				Integer.parseInt(propertyGetter.getProperty(PropertyGetter.SERVER_PORT)),
				propertyGetter.getProperty(PropertyGetter.USERNAME), propertyGetter.getProperty(PropertyGetter.OAUTH),
				propertyGetter.getProperty(propertyGetter.PREFIXES));
		bot.connect();
	}

	private void loadBeans(String applicationContextPath) {
		final GenericApplicationContext ctx = new GenericApplicationContext();
		final XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(ctx);
		xmlReader.loadBeanDefinitions(new PathResource(applicationContextPath));
		ctx.refresh();
		propertyGetter = (PropertyGetter) ctx.getBean(PROPERTY_GETTER_BEAN_ID);
	}

}
