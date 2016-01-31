package se.skaro.hextcgbot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configurable
public class PropertyGetter {

	public static final String DELIMITER = ",";
	public static final String USERNAME = "username";
	public static final String OAUTH = "oauth";
	public static final String JOIN_DB_CHANNELS = "joinDBChannels";
	public static final String DEFAULT_JOIN_CHANNELS = "defaultJoinChannels";
	public static final String IMG_PLUGIN_PASSWORD = "imgpluginpassword";

	private static final Logger LOGGER = LoggerFactory.getLogger(PropertyGetter.class);

	private Properties properties;

	public void loadProperties(String propertiesPath) {
		properties = new Properties();
		try (InputStream input = new FileInputStream(propertiesPath)) {
			properties.load(input);
		} catch (IOException e) {
			LOGGER.error("Unexpected error while reading properties file");
		}
	}

	public String getProperty(String propertyName) {
		return properties.getProperty(propertyName);
	}
}
