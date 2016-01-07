package se.skaro.hextcgbot.application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import se.skaro.hextcgbot.model.DeckInfo;

/**
 * The Class DeckGetter. Gets deck info from hex:meta
 */
public class DeckGetter {

	/**
	 * Gets the decks.
	 *
	 * @param user
	 *            the user
	 * @return the decks
	 */
	public String getDecks(String user) {

		StringBuilder sb = new StringBuilder();
		ArrayList<DeckInfo> decks = new ArrayList<>();

		try {

			String output = readUrl(
					"http://hexmeta.com/deck?_search=true&rows=50&page=1&sord=desc&constructed=1&player=" + user);

			// add constructed=1

			JsonElement jelement = new JsonParser().parse(output);
			JsonObject jobject = jelement.getAsJsonObject();
			JsonArray jarray = jobject.getAsJsonArray("data");

			System.out.println(jarray.size());

			int size = jarray.size();

			if (size == 0) {
				return "No decks from user " + user + " found";
			}

			else {
				ArrayList<JsonObject> userData = new ArrayList<JsonObject>();

				for (int j = 0; j < jarray.size(); j++) {
					jobject = jarray.get(j).getAsJsonObject();

					String player = jobject.get("player").toString().replace("\"", "");

					if (player.equalsIgnoreCase(user)) {
						userData.add(jobject);
					}

				}

				if (userData.size() == 0) {
					return "No decks from user " + user + " found";
				}

				for (JsonObject o : userData) {

					try {

						String id = o.get("id").toString().replace("\"", "");

						JsonObject championJson = o.getAsJsonObject("champion");
						String champion = championJson.get("name").toString().replace("\"", "");

						JsonObject tournamentJson = o.getAsJsonObject("tournament");

						String date = tournamentJson.get("start").toString().split(" ")[0].replace("\"", "");
						String type = parseTournamentType(tournamentJson.get("type").toString().replace("\"", ""));

						JsonObject shardsJson = o.getAsJsonObject("colors");
						String shards = getShards(shardsJson);

						decks.add(new DeckInfo(date, champion, type, shards, id));

						if (decks.size() > 2)
							break;

					} catch (Exception e) {

					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "No decks found";
		}
		sb.append("Latest decks played by " + user + ":");
		for (DeckInfo deck : decks) {
			sb.append(" " + deck.linkAndName() + " ");
		}

		return sb.toString();

	}

	/**
	 * Gets the shards.
	 *
	 * @param shardsJson
	 *            the shards json
	 * @return the shards
	 */
	private String getShards(JsonObject shardsJson) {
		StringBuilder sb = new StringBuilder();

		int blood = shardsJson.get("blood").getAsInt();
		int diamond = shardsJson.get("diamond").getAsInt();
		int ruby = shardsJson.get("ruby").getAsInt();
		int sapphire = shardsJson.get("sapphire").getAsInt();
		int wild = shardsJson.get("wild").getAsInt();

		for (int i = 0; i < blood; i++) {
			sb.append("B");
		}
		for (int i = 0; i < diamond; i++) {
			sb.append("D");
		}
		for (int i = 0; i < ruby; i++) {
			sb.append("R");
		}
		for (int i = 0; i < sapphire; i++) {
			sb.append("S");
		}
		for (int i = 0; i < wild; i++) {
			sb.append("W");
		}

		return sb.toString();
	}

	/**
	 * Parses the tournament type.
	 *
	 * @param type
	 *            the type
	 * @return the string
	 */
	private String parseTournamentType(String type) {
		if (type.equals("live-daily-constructed")) {
			return "Daily";
		}
		if (type.equals("Invitational Qualifier")) {
			return "IQ";
		}
		if (type.equals("Invitational Qualifier Top 8")) {
			return "IQ TOP 8";
		}
		return type;
	}

	/**
	 * Reads url.
	 *
	 * @param urlString
	 *            the url string
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	private static String readUrl(String urlString) throws Exception {
		BufferedReader reader = null;
		try {
			URL url = new URL(urlString);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1)
				buffer.append(chars, 0, read);

			return buffer.toString();
		} finally {
			if (reader != null)
				reader.close();
		}
	}

}
