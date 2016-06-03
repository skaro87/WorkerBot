package se.skaro.workerbot.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.PathResource;

import se.skaro.workerbot.bot.util.PropertyGetter;

public class WorkerBotStartup {

	private static final Logger LOGGER = LoggerFactory.getLogger(WorkerBotStartup.class);

	private static final String PROPERTY_GETTER_BEAN_ID = "propertyGetter";
	
	

	private PropertyGetter propertyGetter;
	private IrcBot bot;

	public WorkerBotStartup(String applicationContextPath, String propertiesPath) {
		loadBeans(applicationContextPath);
		propertyGetter.loadProperties(propertiesPath);
		bot = new IrcBot(propertyGetter);
	}

	public void startBot() {
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
