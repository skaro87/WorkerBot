package hex.skaro.hextcgbot.application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * The Class StreamGetter.
 */
public class StreamGetter {
	
	/** The url */
	private final String urlString = "https://api.twitch.tv/kraken/streams?game=HEX:+Shards+of+Fate";

	/**
	 * Gets the all streams.
	 *
	 * @return the all streams
	 */
	public ArrayList<String> getAllStreams() {

		try {
			String jsonString = readUrl(urlString);

			JsonElement jelement = new JsonParser().parse(jsonString);
			JsonObject jobject = jelement.getAsJsonObject();
			JsonArray jarray = jobject.getAsJsonArray("streams");
			
			ArrayList<String> streams = new ArrayList<String>();

			for (int i = 0; i < jarray.size(); i++) {
				streams.add(getInfo(jarray.get(i).getAsJsonObject()));
				if (i > 4){
					break;
				}
			}

			return streams;

		} catch (Exception e) {
			return null;
		}

	}


	/**
	 * Gets the info.
	 *
	 * @param stream the stream
	 * @return the info
	 */
	private static String getInfo(JsonObject stream) {
		JsonObject channel = stream.get("channel").getAsJsonObject();
		String status = channel.get("status").toString();

		if (status.length() > 40) {
			status = status.substring(0, 37) + "...";
		}

		return (channel.get("display_name")+ ": " + channel.get("url")).replace("\"", "");

	}

	/**
	 * Reads url, same as in deck getter. Duplicate code!
	 *
	 * @param urlString the url string
	 * @return the string
	 * @throws Exception the exception
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
