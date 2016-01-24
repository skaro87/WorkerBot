package se.skaro.hextcgbot.twitchbot.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.pircbotx.hooks.events.MessageEvent;

import se.skaro.hextcgbot.application.PropertyGetter;
import se.skaro.hextcgbot.events.MessageSender;
import se.skaro.hextcgbot.model.Card;
import se.skaro.hextcgbot.repository.jpa.JpaRepository;
import se.skaro.hextcgbot.twitchbot.TwitchBot;

public class ImageCommand extends AbstractCommand {

	private String user = "";
	private static String LINK_TO_IMAGE_URL_HOST = "http://hex.digital-poets.net";
	private static String IMG_PLUGIN_URL = "http://hex.digital-poets.net/staticImage/USER?cooldown=&in=&out=";

	@Override
	public void call(String commandSyntax, MessageEvent event) {

		user = event.getUser().getNick();
		if (user != null) {
			String name = fixWhiteSpacesAndSymbols(getMessageWithoutCommand(commandSyntax, event)).replace("'", "");

			if (name.length() > 3) {

				if (name.equalsIgnoreCase("setup")) {

					TwitchBot bot = event.getBot();
					bot.getGroupServer().sendWhisper(user, IMG_PLUGIN_URL.replace("USER", user));

				}

				else {

					List<Card> result = JpaRepository.findCardByFormatedName(name);
					if (result.isEmpty()) {
						MessageSender.sendMessage(event, "No card with name '" + name + "' found");
					}
					// one result found. No need to create a StringBuilder and start the for-loop

					else if (result.size() == 1) {
						sendResponse(result.get(0), event);
					}
					// more than one found.
					else {
						for (Card card : result) {
							if (card.getFormatedName().equalsIgnoreCase(name)) {
								sendResponse(card, event);
								return;
							}

						}
						sendResponse(result.get(0), event);
					}
				}

			} else {
				MessageSender.sendMessage(event, "You need at least 4 characters to do a search");
			}
		}

	}

	private void sendResponse(Card card, MessageEvent event) {

		if (!event.getChannel().getName().endsWith(event.getUser().getNick())) {
			String path = cardInfo(card);
			MessageSender.sendMessage(event, LINK_TO_IMAGE_URL_HOST + path.toLowerCase());

		} else {

			sendURLCall(card, event);

		}
	}

	private void sendURLCall(Card card, MessageEvent event) {

		String urlCall = IMG_PLUGIN_URL.replace("USER", user);
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(urlCall);

		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("password", PropertyGetter.getIMG_PLUGIN_PASSWORD()));
		urlParameters.add(new BasicNameValuePair("url", cardInfo(card)));
		try {
			post.setEntity(new UrlEncodedFormEntity(urlParameters));

			HttpResponse response;

			response = client.execute(post);
			if (!(response.getStatusLine().getStatusCode() == 201)) {
				TwitchBot bot = event.getBot();
				bot.getGroupServer().sendWhisper(user, "Could not connect to the image plugin. Try again later");
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String cardInfo(Card card) {
		String name = card.getName().replaceAll(" ", "-").replaceAll("'", "-").replaceAll(",", "");
		String set = card.getSet().replaceAll(" ", "-");
		return ("/cards/" + set + "/" + name + ".png").toLowerCase();
	}

}
