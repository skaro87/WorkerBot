package hex.skaro;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map.Entry;
import java.util.Set;

import org.jibble.pircbot.*;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class HEXbot extends PircBot {

	public HEXbot() {
		this.setName("HEX_TCG_bot");
	}

	@Override
	public void onMessage(String channel, String sender, String login,
			String hostname, String message) {

		if (message.startsWith("!card")) {
			if (message.length() > 6) {
				sendMessage(channel, getCardInfo(message.substring(6)));
			}
		}
	}

	public String getCardInfo(String input) {
		String hexdbapi = "http://hexdbapi.maximumstock.net/api/v1/objects?name=";
		JsonObject jsonResponse = getCardByName(hexdbapi
				+ input.replace(" ", "+"));

		try {
			String name = getElement("name", jsonResponse).toString().replaceAll("\"", "");
			String cost = getElement("cost", jsonResponse).toString().replaceAll("\"", "");
			String atk = getElement("atk", jsonResponse).toString();
			String health = getElement("health", jsonResponse).toString();
			String threshold = getElement("threshold", jsonResponse).toString().replaceAll("\"", "");
			String type = getElement("type", jsonResponse).toString().replaceAll("\"", "");
			String sub_type = getElement("sub_type", jsonResponse).toString().replaceAll("\"", "");
			String text = getElement("text", jsonResponse).toString().replaceFirst("\\n\\n", ". ").replace("\\n\\n", " ");
			
		
			if (type.equals("\"[Troop]\"")){ //if troop
			return name +", "+cost+" cost "+threshold+" threshold. "+atk+"/"+health+" "+type.substring(1, type.length()-1)+" - "+sub_type+". "+text;
			}
			
			//else
			return name +", "+cost+" cost "+threshold+" threshold. "+" "+type.substring(1, type.length()-1)+". "+text;

		} catch (NullPointerException e) {
			// when card is not found
			return "Card "+input + " not found.";
		}
	}

	public static JsonObject getCardByName(String myURL) {
		// Connect to the URL using java's native library
		URL url;
		try {
			url = new URL(myURL);

			HttpURLConnection request = (HttpURLConnection) url
					.openConnection();
			request.connect();

			// Convert to a JSON object to print data
			JsonParser jp = new JsonParser();
			JsonElement root = jp.parse(new InputStreamReader(
					(InputStream) request.getContent())); // Convert the input
															// stream to a json
															// element
			JsonObject rootobj = root.getAsJsonObject(); // May be an array, may
															// be an object.
			return rootobj;

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static JsonElement getElement(String value, JsonObject jObj) {

		// check current level for the key before descending
		if (jObj.isJsonObject() && jObj.has(value)) {
			return jObj.get(value);
		}

		// get all entry sets
		Set<Entry<String, JsonElement>> entries = jObj.entrySet();

		for (Entry<String, JsonElement> entry : entries) {

			// cache the current value since retrieval is done so much
			JsonElement curVal = entry.getValue();

			if (curVal.isJsonArray()) {
				for (JsonElement el : curVal.getAsJsonArray()) {
					// recursively traverse the sub-tree
					JsonElement res = getElement(value, el.getAsJsonObject());
					if (res != null)
						return res;
				}
			} else if (curVal.isJsonObject()) {
				// traverse sub-node
				return getElement(value, curVal.getAsJsonObject());
			}
		}
		return null;
	}

}