package se.skaro.hexbot.api;

public class DeckTest {

	public static void main(String[] args) {

		System.out.println(hashCode2("skaro87"));

	}

	private static long hashCode2(String user) {
		String str = user.substring(0, 3);
		long hash = 0;
		if (str.length() == 0)
			return hash;
		for (int i = 0; i < str.length(); i++) {
			char character = str.charAt(i);
			hash = ((hash << 5) - hash) + character;
			hash = hash & hash; // Convert to 32bit integer
		}
		return hash;

	}

}
