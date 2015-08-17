package hex.skaro;

public class HEXbotMain {

	 public static void main(String[] args) throws Exception {
	        
	        // Now start our bot up.
	        HEXbot bot = new HEXbot();
	        
	        // Enable debugging output.
	        bot.setVerbose(true);
	        
	        // Connect to the IRC server.
	        bot.connect("irc.twitch.tv", 6667, "oauth:vfnmaaat5jkyhhfhlji0ahbc5n5lhu");

	        // Join the #pircbot channel.
	        bot.joinChannel("#skaro87");
	        
	    }

}
