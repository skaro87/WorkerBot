package se.skaro.workerbot.bot;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.kitteh.irc.client.library.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import se.skaro.workerbot.bot.messages.MessageHandler;
import se.skaro.workerbot.data.repository.UserRepository;

@Configuration
@PropertySource("file:conf/workerbot.properties")
public class IrcBot {

	private Client client;

	@Autowired
	private MessageHandler messageHandler;

	@Autowired
	private UserRepository userRepository;

	@Value("${bot.server}")
	private String server;

	@Value("${bot.username}")
	private String username;

	@Value("${bot.oauth}")
	private String oauth;

	@Value("${bot.joinDBChannels}")
	private boolean joinDBchannels;

	@Value("${bot.defaultJoinChannels}")
	private String defaultChannels;

	@Value("${bot.prefixes}")
	private String prefixString;

	public void connect() {

		messageHandler.setupPrefixAndCommnads(prefixString);
		
		// TODO: Remove debug output (listen-stuff)
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");

		client = Client.builder().nick(username).serverPassword(oauth).serverHost(server)
				.listenInput(line -> System.out.println(sdf.format(new Date()) + ' ' + "[I] " + line))
				.listenOutput(line -> System.out.println(sdf.format(new Date()) + ' ' + "[O] " + line))
				.listenException(Throwable::printStackTrace).build();
		
		//CAP REQ :twitch.tv/commands
		client.sendRawLine("CAP REQ :twitch.tv/commands");

		joinChannels();


		client.getEventManager().registerEventListener(messageHandler);
	}

	private void joinChannels() {

		// TODO: Join channels "slow" to go under the twitch channel limit

		// join default channels in property file
		List<String> defChannels = Arrays.asList(defaultChannels.split(","));
		defChannels.forEach(s -> {
			client.addChannel("#" + s);
		});

		// join channels in user table
		if (joinDBchannels) {
			List<String> dbChannels = userRepository.findAutoJoinChannels();

			dbChannels.forEach(s -> {
				client.addChannel("#" + s);
			});
		}

	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer xxxpropertyConfig() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
