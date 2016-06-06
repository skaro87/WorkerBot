package se.skaro.workerbot.businesslogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.skaro.workerbot.data.repository.CardRepository;

@Component
public class CardLogic {

	// TODO: Make this a property
	private final String IMAGE_URL_BASE = "http://hex.digital-poets.net/";

	@Autowired
	private CardRepository cardRepository;

	public String getCardMessage(String searchMessage) {
		return "getCardMessage()";
	}

	public String getImageLink(String searchMessage) {
		return "getImageLink()";
	}

	public void sendImageRequestToImagePlugin(String message) {

	}

}
