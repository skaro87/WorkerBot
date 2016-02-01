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
import se.skaro.hextcgbot.util.MessageSender;
import se.skaro.hextcgbot.util.PropertyGetter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ImageCommand extends AbstractCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageCommand.class);
    private static final String LINK_TO_IMAGE_URL_HOST = "http://hex.digital-poets.net";
    private static final String IMG_PLUGIN_URL = "http://hex.digital-poets.net/staticImage/USER?cooldown=&in=&out=";

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private PropertyGetter propertyGetter;

    public ImageCommand(String syntax, boolean isCommandCaseSensitive, String description) {
        super(syntax, isCommandCaseSensitive, description);
    }

    @Override
    public void call(String commandSyntax, MessageEvent event) {

        String user = getUserNick(event);
        if (user != null) {
            String name = fixWhiteSpaces(getMessageWithoutCommand(commandSyntax, event)).replace("'", "");

            if (name.length() > 3) {

                if (name.equalsIgnoreCase("setup")) {

                    TwitchBot bot = event.getBot();
                    bot.getGroupServer().sendWhisper(user, IMG_PLUGIN_URL.replace("USER", user));

                } else {

                    List<Card> result = JpaRepository.findCardByFormatedName(name);
                    if (result.isEmpty()) {
                        messageSender.sendMessage(event, "No card with name '" + name + "' found");
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
                messageSender.sendMessage(event, "You need at least 4 characters to do a search");
            }
        }

    }

    private void sendResponse(Card card, MessageEvent event) {

        if (!event.getChannel().getName().endsWith(event.getUser().getNick())) {
            String path = cardInfo(card);
            messageSender.sendMessage(event, LINK_TO_IMAGE_URL_HOST + path.toLowerCase());

        } else {

            sendURLCall(card, event);

        }
    }

    private void sendURLCall(Card card, MessageEvent event) {
        String urlCall = IMG_PLUGIN_URL.replace("USER", getUserNick(event));
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(urlCall);

        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("password", propertyGetter.getProperty(PropertyGetter.IMG_PLUGIN_PASSWORD)));
        urlParameters.add(new BasicNameValuePair("url", cardInfo(card)));
        try {
            post.setEntity(new UrlEncodedFormEntity(urlParameters));

            HttpResponse response;

            response = client.execute(post);
            if (!(response.getStatusLine().getStatusCode() == 201)) {
                TwitchBot bot = event.getBot();
                bot.getGroupServer().sendWhisper(getUserNick(event), "Could not connect to the image plugin. Try again later");
            }

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private String cardInfo(Card card) {
        String name = card.getName().replaceAll(" ", "-").replaceAll("'", "-").replaceAll(",", "");
        String set = card.getSet().replaceAll(" ", "-");
        return ("/cards/" + set + "/" + name + ".png").toLowerCase();
        //	return ("/cards/" + set + "/" + name + aa + ".png").toLowerCase();
    }
}
