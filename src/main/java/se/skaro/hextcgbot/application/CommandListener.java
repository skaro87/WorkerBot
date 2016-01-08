package se.skaro.hextcgbot.application;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import se.skaro.hextcgbot.events.MessageSender;
import se.skaro.hextcgbot.model.Equipment;
import se.skaro.hextcgbot.model.StringResources;
import se.skaro.hextcgbot.repository.jpa.JPADBHandler;

/**
 * The listener interface for receiving command events. The class that is
 * interested in processing a command event implements this interface, and the
 * object created with that class is registered with a component using the
 * component's <code>addCommandListener<code> method. When the command event
 * occurs, that object's appropriate method is invoked.
 *
 * @see CommandEvent
 */
public class CommandListener extends ListenerAdapter {

	// ---------------- Commands -----------------------

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.pircbotx.hooks.ListenerAdapter#onMessage(org.pircbotx.hooks.events.
	 * MessageEvent)
	 */
	@Override
	public void onMessage(final MessageEvent event) throws Exception {

		try {

			if (event.getMessage().equalsIgnoreCase("!join")) {
				commandJoin(event);
			} else if (event.getMessage().equalsIgnoreCase("!leave")) {
				commandLeave(event);
			} else if (event.getMessage().startsWith("!price")) {
				commandPrice(event);
			} else if (event.getMessage().startsWith("!card")) {
				commandCard(event);
			} else if (event.getMessage().startsWith("!equipment")) {
				commandEquipment(event);
			} else if (event.getMessage().equalsIgnoreCase("!ratio")) {
				commandRatio(event);
			} else if (event.getMessage().startsWith("!goldtoplat")) {
				commandGoldToPlat(event);
			} else if (event.getMessage().startsWith("!plattogold")) {
				commandPlatToGold(event);
			} else if (event.getMessage().equalsIgnoreCase("!info") || event.getMessage().equalsIgnoreCase("!about")) {
				commandInfo(event);
			} else if (event.getMessage().equalsIgnoreCase("!donate")) {
				commandDonate(event);
			} else if (event.getMessage().startsWith("!decks")) {
				commandDecks(event);
			} else if (event.getMessage().equalsIgnoreCase("!streams")) {
				commandStreams(event);
			} else if (event.getMessage().equalsIgnoreCase("!channels")) {
				commandShowChannels(event);
			} else if (event.getMessage().startsWith("!whispers")) {
				commandWhispers(event);
			} else if (event.getMessage().equalsIgnoreCase("!help")) {
				commandHelp(event);
			} else if (event.getMessage().equalsIgnoreCase("!bug")) {
				commandBug(event);
			} else if (event.getMessage().startsWith("!ign")) {
				commandIGN(event);
			} else if (event.getMessage().startsWith("!setign ")) {
				commandSetIGN(event);
			} else if (event.getMessage().equalsIgnoreCase("!users")) {
				commandNoUsers(event);
			}

		} catch (Exception e) {
			event.respond(e.getMessage());
		}
	}
	// --------------- Command functions ---------------------

	/**
	 * Command number of users. Sends a message with the number of users in the
	 * database
	 *
	 * @param event
	 *            the event
	 */
	private void commandNoUsers(MessageEvent event) {
		MessageSender.sendMessage(event, JPADBHandler.getAllUsers());

	}

	/**
	 * Command set ign. Sets user in-game-name.
	 *
	 * @param event
	 *            the event
	 */
	private void commandSetIGN(MessageEvent event) {
		if (event.getMessage().replace("!setign ", "").length() > 3) {
			MessageSender.sendMessage(event,
					JPADBHandler.setUserIGN(event.getMessage().replace("!setign ", ""), event.getUser().getNick()));
		}

	}

	/**
	 * Command ign. Gets the IGN accociated with a user.
	 *
	 * @param event
	 *            the event
	 */
	public void commandIGN(MessageEvent event) {
		try {
			if (event.getMessage().equalsIgnoreCase("!ign")){
				event.respondChannel(JPADBHandler.getUserIGN(event.getUser().getNick()));
			}
			
			else if (event.getMessage().startsWith("!ign @") && event.getMessage().replace("!ign @", "").length() > 3) {
				MessageSender.sendMessage(event, JPADBHandler.getUserIGN(event.getMessage().replace("!ign @", "")));
			}

			else if (event.getMessage().startsWith("!ign ") && event.getMessage().replace("!ign ", "").length() > 3) {
				MessageSender.sendMessage(event, JPADBHandler.getUserIGN(event.getMessage().replace("!ign ", "")));
			}

			else {
				MessageSender.sendMessage(event, "Invalid search");
			}
		} catch (Exception e) {
			MessageSender.sendMessage(event, e.getMessage());
		}

	}

	/**
	 * Command bug. Sends back a querry for the bug report tool.
	 *
	 * @param event
	 *            the event
	 */
	private void commandBug(MessageEvent event) {
		MessageSender.sendMessage(event,
				"If you have encountered a bug, please fill in a bug report at http://goo.gl/forms/hI3s8x2LEb");

	}

	/**
	 * Command help. Sends information about commands.
	 *
	 * @param event
	 *            the event
	 */
	private void commandHelp(MessageEvent event) {
		event.respondChannel(StringResources.getHelpText());

	}

	/**
	 * Command whispers. Makes the channel owner able to change the whisper
	 * settings for chat.
	 *
	 * @param event
	 *            the event
	 */
	private void commandWhispers(MessageEvent event) {
		if (event.getMessage().contains("on")) {
			JPADBHandler.setWhispers(event.getUser().getNick().toLowerCase(), 1);
			event.respondChannel("Whisper mode ON for channel " + event.getUser().getNick());
		}

		else if (event.getMessage().contains("off")) {
			JPADBHandler.setWhispers(event.getUser().getNick().toLowerCase(), 0);
			event.respondChannel("Whisper mode OFF for channel " + event.getUser().getNick());
		}

	}

	/**
	 * Command show channels. Sends back the number of channels the bot is
	 * currently in.
	 *
	 * @param event
	 *            the event
	 */
	private void commandShowChannels(MessageEvent event) {
		MessageSender.sendMessage(event,
				"I am currently in " + event.getBot().getUserChannelDao().getAllChannels().size() + " channels");

	}

	/**
	 * Command streams. Sends back the top streamers online streaming HEX.
	 * (Sends rapid messages, fix this!)
	 *
	 * @param event
	 *            the event
	 */
	private void commandStreams(MessageEvent event) {
		ArrayList<String> streams = new StreamGetter().getAllStreams();
		for (String s : streams) {
			MessageSender.sendMessage(event, s);
		}

	}

	/**
	 * Command decks. Gets the deck data for a user.
	 *
	 * @param event
	 *            the event
	 */
	private void commandDecks(MessageEvent event) {
		MessageSender.sendMessage(event, new DeckGetter().getDecks(event.getMessage().replace("!decks ", "")));

	}

	/**
	 * Command donate. Sends donate-info.
	 *
	 * @param event
	 *            the event
	 */
	private void commandDonate(MessageEvent event) {
		event.respondChannel("Donate to WorkerBot at https://www.twitchalerts.com/donate/workerbot");

	}

	/**
	 * Command info. Sends info about the bot.
	 *
	 * @param event
	 *            the event
	 */
	private void commandInfo(MessageEvent event) {
		event.respondChannel(
				"Version 1.0 | For more information go to twitch.tv/workerbot | Thanks Celendine, Dinotopia, Veetorp and Doc-X for the data | Created by Skaro using PircBotX");

	}

	/**
	 * Command plat to gold. Converts platinum to gold (Are floats the best?
	 * Also add min and max check)
	 *
	 * @param event
	 *            the event
	 */
	private void commandPlatToGold(MessageEvent event) {
		float plat = Float.valueOf(event.getMessage().replace("!plattogold", ""));
		MessageSender.sendMessage(event, new DecimalFormat("#").format(plat) + " platinum is currently worth "
				+ new DecimalFormat("#").format(plat * JPADBHandler.getRatio()) + " gold");

	}

	/**
	 * Command gold to plat. Converts gold to platinum. (Are floats the best?
	 * Also add min and max check)
	 *
	 * @param event
	 *            the event
	 */
	private void commandGoldToPlat(MessageEvent event) {
		float gold = Float.valueOf(event.getMessage().replace("!goldtoplat", ""));
		MessageSender.sendMessage(event, new DecimalFormat("#").format(gold) + " gold is currently worth "
				+ new DecimalFormat("#").format(gold / JPADBHandler.getRatio()) + " platinum");

	}

	/**
	 * Command ratio. Sends the current ratio collected from the database.
	 *
	 * @param event
	 *            the event
	 */
	private void commandRatio(MessageEvent event) {
		event.respondChannel("1 platinum is worth " + new DecimalFormat("#").format(JPADBHandler.getRatio()) + " gold");

	}

	/**
	 * Command equipment. Sends back equipments. (Rapid messages, fix this! Add
	 * logic to another class instead?)
	 *
	 * @param event
	 *            the event
	 */
	private void commandEquipment(MessageEvent event) {

		if (event.getMessage().replace("!equipment ", "").length() > 3) {

			List<Equipment> equipments = JPADBHandler.getEquipmentData(event.getMessage().replace("!equipment ", ""));
			if (equipments.size() < 1) {
				MessageSender.sendMessage(event,
						"No equipment found for card " + event.getMessage().replace("!equipment ", ""));
			} else if (equipments.size() == 1) {
				MessageSender.sendMessage(event, equipments.get(0).toString());
			} else if (equipments.size() == 2) {
				if (equipments.get(0).getAffectedCardName().equals(equipments.get(1).getAffectedCardName())) {
					MessageSender.sendMessage(event, equipments.get(0).toString());
					MessageSender.sendMessage(event, equipments.get(1).toString());
				} else {
					MessageSender.sendMessage(event,
							"Found equipments for multiple cards: [" + equipments.get(0).getAffectedCardName() + "] ["
									+ equipments.get(1).getAffectedCardName() + "]");
				}
			}

			else { // morre than 2 results
				StringBuilder sb = new StringBuilder();
				sb.append("Found equipments for multiple cards: ");
				for (Equipment e : equipments) {
					if (!sb.toString().contains(e.getAffectedCardName())) {
						sb.append("[" + e.getAffectedCardName() + "] ");
					}
				}
				MessageSender.sendMessage(event, sb.toString());
			}

		} else {
			MessageSender.sendMessage(event, "You need at least 4 characters to do a search");
		}

	}

	/**
	 * Command card. Sends back the info about a card.
	 *
	 * @param event
	 *            the event
	 */
	private void commandCard(MessageEvent event) {
		MessageSender.sendMessage(event, JPADBHandler.getCardData(event.getMessage().replace("!card ", "")));

	}

	/**
	 * Command price. Sends back the price of an item.
	 *
	 * @param event
	 *            the event
	 */
	private void commandPrice(MessageEvent event) {
		MessageSender.sendMessage(event, JPADBHandler.getPriceData(event.getMessage().replace("!price ", "")));
	}

	/**
	 * Command join. WorkerBot will join the senders channel.
	 *
	 * @param event
	 *            the event
	 */
	private void commandJoin(MessageEvent event) {
		if (!event.getBot().getUserChannelDao().containsChannel("#" + event.getUser().getNick())) {
			event.respondChannel("Joining channel #" + event.getUser().getNick());
			event.getBot().sendIRC().joinChannel("#" + event.getUser().getNick());
			JPADBHandler.updateUserWhereBotIsInChannel(event.getUser().getNick().toLowerCase(), 1);

			event.getBot().send().message("#" + event.getUser().getNick().toLowerCase(),
					StringResources.getWelcomeMessage());

		}

		else {
			event.respondChannel(event.getUser().getNick() + ", I am already in your channel!");
		}
	}

	/**
	 * Command leave. WorkerBot will leave the senders channel.
	 *
	 * @param event
	 *            the event
	 */
	private void commandLeave(MessageEvent event) {
		if (event.getBot().getUserChannelDao().containsChannel("#" + event.getUser().getNick().toLowerCase())) {

			event.respondChannel("Leaving channel #" + event.getUser().getNick());
			event.getBot().getUserChannelDao().getChannel("#" + event.getUser().getNick().toLowerCase()).send()
					.message(StringResources.getGoodbyeMessage());
			event.getBot().getUserChannelDao().getChannel("#" + event.getUser().getNick().toLowerCase()).send().part();
			JPADBHandler.updateUserWhereBotIsInChannel(event.getUser().getNick().toLowerCase(), 0);

		} else {
			event.respondChannel(event.getUser().getNick() + ", I am currently not in your channel");
		}
	}

}
