package se.skaro.workerbot.businesslogic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.skaro.workerbot.data.entity.User;
import se.skaro.workerbot.data.repository.UserRepository;

@Component
public class UserLogic {

	/** The user repository. */
	@Autowired
	private UserRepository userRepository;

	private String prefixes = "!, #";

	/**
	 * Gets the in-game-name for a user. Gets data from the user database and
	 * returns message that WorkerBot will send back to the chat.
	 *
	 * @param message
	 *            the trimmed message
	 * @return response for IGN command
	 */
	public String getIGNforUser(String message) {
		return "getIGNforUser()";
	}

	public String getTwitchNameFromIGN(String message) {
		return "getTwitchNameFromIGN()";
	}

	public String setIGNForUser(String userToUpdate, String message) {
		//TODO: Test this!

		List<User> users = userRepository.findByNameIgnoreCase(userToUpdate);
		User user;
		if (users.isEmpty()) {
			user = new User(userToUpdate, message, false, false);
		} else {
			user = users.get(0);
		}

		user.setIGN(message);
		userRepository.save(user);

		return "IGN updated for user " + userToUpdate;
	}

	public String addOrUpdateSettingForUser(String user, String key, String value) {
		// TODO: Implement this after settings command is used
		return null;
	}

	public String updateWhisperSettings(String userToUpdate, String message) {
		if (message.equalsIgnoreCase("on") || message.equalsIgnoreCase("off")) {

			List<User> users = userRepository.findByNameIgnoreCase(userToUpdate);
			if (users.isEmpty()) {
				return "User " + userToUpdate + " not found. Register your username by typing '!ign <your ign>'";
			}

			User user = users.get(0);

			if (message.equalsIgnoreCase("off")) {
				user.setWhispers(false);
				userRepository.save(user);
				return "Whisper mode off for channel " + userToUpdate;
			} else {
				user.setWhispers(true);
				userRepository.save(user);
				return "Whisper mode on for channel " + userToUpdate;
			}

		}
		return "Invalid message. Use '!whispers on/off' to change whisper settings";
	}

	public String updatePrefix(String userToUpdate, String prefix) {

		if (prefix.equals("!") || prefix.equals("#")) {

			List<User> users = userRepository.findByNameIgnoreCase(userToUpdate);
			if (users.isEmpty()) {
				return "User " + userToUpdate + " not found. Register your username by typing '!ign <your ign>'";
			}

			User user = users.get(0);
			user.setPrefix(prefix);
			userRepository.save(user);

			return "Prefix updated to " + prefix + " for user " + userToUpdate;
		}

		return "Invalid prefix " + prefix + ". Allowed prefixes are: " + prefixes;
	}

}
