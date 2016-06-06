package se.skaro.workerbot.businesslogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.skaro.workerbot.data.entity.User;
import se.skaro.workerbot.data.repository.UserRepository;

@Component
public class UserLogic {

	/** The user repository. */
	@Autowired
	private UserRepository userRepository;

	/**
	 * Gets the in-game-name for a user.
	 * Gets data from the user database and returns message that WorkerBot will send back to the chat.
	 *
	 * @param message the trimmed message
	 * @return response for IGN command
	 */
	public String getIGNforUser(String message) {
		return "getIGNforUser()";
	}

	public String getTwitchNameFromIGN(String message) {
		return "getTwitchNameFromIGN()";
	}
	
	public User addOrUpdateSettingForUser(String key, String value){
		return null;		
	}
	
	public User updateWhisperSettings (boolean whispers){
		return null;
	}
	
	public User updatePrefix (String prefix) {
		return null;
	}

}
