package se.skaro.hextcgbot.twitchbot.commands;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.pircbotx.hooks.events.MessageEvent;

import se.skaro.hextcgbot.events.MessageSender;
import se.skaro.hextcgbot.model.Card;
import se.skaro.hextcgbot.repository.jpa.JpaRepository;
import se.skaro.hextcgbot.twitchbot.TwitchBot;

public class ImageCommand extends AbstractCommand {

	String user = "";
	private static String LINK_TO_IMAGE_PATH = "http://hex.digital-poets.net/cards/";

	@Override
	public void call(String commandSyntax, MessageEvent event) {

		user = event.getUser().getNick();
		if (user != null) {
			String name = fixWhiteSpacesAndSymbols(getMessageWithoutCommand(commandSyntax, event)).replace("'", "");

			if (name.length() > 3) {

				if (name.equalsIgnoreCase("setup")) {
					TwitchBot bot = event.getBot();
					String setupUrl = "http://46.101.226.88:8080/heximg/?user=" + user
							+ "&bg=33FF33&cooldown=10&in=&out=&ref=" + hashPassword(user);
					bot.getGroupServer().sendWhisper(event.getUser().getNick(), setupUrl.replaceAll(" ", ""));
				}

				else {

					List<Card> result = JpaRepository.findCardByFormatedName(name);
					if (result.isEmpty()) {
						MessageSender.sendMessage(event, "No card with name '" + name + "' found");
					}
					// one result found. No need to create a StringBuilder and
					// start
					// the
					// for-loop
					else if (result.size() == 1) {
						sendUrlCall(result.get(0), event, true);
					}
					// more than one found.
					else {
						for (Card card : result) {
							if (card.getFormatedName().equalsIgnoreCase(name)) {
								sendUrlCall(card, event, true);
								return;
							}

						}
						sendUrlCall(result.get(0), event, true);
					}
				}

			} else {
				MessageSender.sendMessage(event, "You need at least 4 characters to do a search");
			}
		}

	}

	private void sendUrlCall(Card card, MessageEvent event, boolean aa) {

		if (!event.getChannel().getName().endsWith(event.getUser().getNick())) {
			String path = cardInfo(card);
			MessageSender.sendMessage(event, LINK_TO_IMAGE_PATH + path.toLowerCase() + ".png");

		} else {

			URL url = null;
			try {
				url = new URL(getUrlString(card));

				InputStream is = url.openStream();
				is.close();
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
			}

		}
	}

	private String getUrlString(Card card) {
		String unformatedUrl = "http://46.101.226.88:8080/heximg/setimg?user=USERNAME&img=IMAGE";
		return unformatedUrl.replace("USERNAME", user).replace("IMAGE", cardInfo(card).toLowerCase());
	}

	private String cardInfo(Card card) {
		String name = card.getName().replaceAll(" ", "-").replaceAll("'", "-").replaceAll(",", "");
		String set = card.getSet().replaceAll(" ", "-");
		return set + "/" + name;
	}

	private String hashPassword(String user) {
		return ("" + user.hashCode()).substring(0, 4);
	}

}
