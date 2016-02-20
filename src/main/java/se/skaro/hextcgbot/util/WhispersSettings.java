package se.skaro.hextcgbot.util;

import org.springframework.beans.factory.annotation.Configurable;

import java.util.HashMap;
import java.util.Map;

/**
 * Holds settings about whispers mode per user.
 */
@Configurable
public class WhispersSettings {

	private final Map<String, Boolean> settings = new HashMap<>();

	public Boolean getUserWhisperSetting(String user) {
		if (settings.containsKey(user)) {
			return settings.get(user);
		}
		return false;
	}

	public Boolean putUserWhisperSetting(String user, Boolean sendWhispers) {
		return settings.put(user, sendWhispers);
	}
}
