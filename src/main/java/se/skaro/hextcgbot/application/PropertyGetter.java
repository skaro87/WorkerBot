package se.skaro.hextcgbot.application;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyGetter {
	
	private static String OAUTH = "";
	private static String USERNAME = "";
	private static String IMG_PLUGIN_PASSWORD = "";

	public static void getProperties(String name) {
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream(name + ".properties");

			prop.load(input);
			OAUTH = prop.getProperty("oauth");
			USERNAME = prop.getProperty("username");
			IMG_PLUGIN_PASSWORD = prop.getProperty("imgpluginpassword");
			

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static String getOAUTH() {
		return OAUTH;
	}

	public static String getUSERNAME() {
		return USERNAME;
	}

	public static String getIMG_PLUGIN_PASSWORD() {
		return IMG_PLUGIN_PASSWORD;
	}
	
	
}
