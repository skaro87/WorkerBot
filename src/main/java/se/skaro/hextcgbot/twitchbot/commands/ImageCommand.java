package se.skaro.hextcgbot.twitchbot.commands;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.pircbotx.hooks.events.MessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.skaro.hextcgbot.model.Card;
import se.skaro.hextcgbot.repository.jpa.JpaRepository;
import se.skaro.hextcgbot.twitchbot.TwitchBot;
import se.skaro.hextcgbot.twitchbot.excpetions.SearchMessageToShortException;
import se.skaro.hextcgbot.util.BotMessageType;
import se.skaro.hextcgbot.util.MessageSender;
import se.skaro.hextcgbot.util.PropertyGetter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ImageCommand extends AbstractCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageCommand.class);
    private static final Pattern AA_PATTERN = Pattern.compile("(?i)^(aa)?(.*?)(aa)?$");
	private static final String LINK_TO_IMAGE_URL_HOST = "http://hex.digital-poets.net";
	private static final String IMG_PLUGIN_URL = "http://hex.digital-poets.net/staticImage/USER?cooldown=&in=&out=";
    private static final int RESPONSE_ERROR_STATUS_CODE = 201;

    @Autowired
	private MessageSender messageSender;

	@Autowired
	private PropertyGetter propertyGetter;

    public ImageCommand(String syntax, boolean isCommandCaseSensitive, String description, BotMessageType botMessageType) {
        super(syntax, isCommandCaseSensitive, description, botMessageType);
    }

    @Override
    public void call(String commandSyntax, MessageEvent event) {
        boolean searchForAA = false;
        String user = getUserNick(event);
        if (user == null) {
            return;
        }

        String name = fixWhiteSpaces(getMessageWithoutCommand(commandSyntax, event));
        if ("setup".equalsIgnoreCase(name)) {
            TwitchBot bot = event.getBot();
            bot.getGroupServer().sendWhisper(user, IMG_PLUGIN_URL.replace("USER", user));
            return;
        }

        Matcher matcher = AA_PATTERN.matcher(name);
        if (matcher.find()) {
            String aaPrefix = matcher.group(1);
            name = matcher.group(2);
            String aaSuffix = matcher.group(3);
            searchForAA = (aaPrefix != null && !aaPrefix.isEmpty()) || (aaSuffix != null && !aaSuffix.isEmpty());
        }
        if (name.length() < SearchMessageToShortException.DEFAULT_MINIMUM_LENGTH) {
            throw new SearchMessageToShortException();
        }

        List<Card> result = JpaRepository.findCardByFormatedName(name.replaceAll("'", ""));
        if (result.isEmpty()) {
            messageSender.sendMessage(event, "No card with name '" + name + "' found", botMessageType);
            return;
        }
        for (Card card : result) {
            if (card.getFormatedName().equalsIgnoreCase(name)) {
                sendResponse(card, event, user, searchForAA);
                return;
            }
        }
        //Even if there are more results, but not matched, we show first found.
        sendResponse(result.get(0), event, user, searchForAA);
    }

	private void sendResponse(Card card, MessageEvent event, String user, boolean searchForAA) {
		if (!event.getChannel().getName().endsWith(user)) {
			String path = cardInfo(card, searchForAA);
			messageSender.sendMessage(event, LINK_TO_IMAGE_URL_HOST + path.toLowerCase(), botMessageType);
		} else {
			sendURLCall(card, event, user, searchForAA);
		}
	}

	private void sendURLCall(Card card, MessageEvent event, String user, boolean searchForAA) {
		String urlCall = IMG_PLUGIN_URL.replace("USER", user);
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(urlCall);

		List<NameValuePair> urlParameters = new ArrayList<>();
		urlParameters.add(
				new BasicNameValuePair("password", propertyGetter.getProperty(PropertyGetter.IMG_PLUGIN_PASSWORD)));
		urlParameters.add(new BasicNameValuePair("url", cardInfo(card, searchForAA)));

		try {
			post.setEntity(new UrlEncodedFormEntity(urlParameters));

			HttpResponse response = client.execute(post);
			if (!(response.getStatusLine().getStatusCode() == RESPONSE_ERROR_STATUS_CODE)) {
                messageSender.sendWhisper(event, "Could not connect to the image plugin. Try again later", user, botMessageType);
			}

		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	private String cardInfo(Card card, boolean searchForAA) {
		String name = card.getName().replaceAll(" ", "-").replaceAll("'", "-").replaceAll(",", "").toLowerCase();
		if ("yes".equalsIgnoreCase(card.getAa()) && searchForAA) {
			return "/cards/aa/" + name + "-aa.png";
		}

		String set = card.getSet().replaceAll(" ", "-").toLowerCase();
		return "/cards/" + set + "/" + name + ".png";
	}
}
