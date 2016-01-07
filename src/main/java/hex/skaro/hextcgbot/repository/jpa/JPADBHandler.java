package hex.skaro.hextcgbot.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import hex.skaro.hextcgbot.model.Card;
import hex.skaro.hextcgbot.model.Equipment;
import hex.skaro.hextcgbot.model.ItemPrice;
import hex.skaro.hextcgbot.model.User;
import hex.skaro.hextcgbot.statistics.ChannelStats;
import hex.skaro.hextcgbot.statistics.UserChannel;

/**
 * The Class JPADBHandler. Handles all the data searches/updates for the bot. (This class should be updated to not be this cluttered!)
 */
public class JPADBHandler {

	/** The Constant PERSISTENCE_UNIT_NAME. */
	private static final String PERSISTENCE_UNIT_NAME = "HEXbot";
	
	/** The factory. */
	private static EntityManagerFactory factory;
	
	/** The em. */
	private static EntityManager em;

	/**
	 * Startup.
	 */
	public static void startup() {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}

	/**
	 * Gets the card data.
	 *
	 * @param card the card
	 * @return the card data
	 */
	@SuppressWarnings("unchecked")
	public static String getCardData(String card) {
		StringBuilder sb = new StringBuilder();
		card = card.replaceAll("'", "");

		if (card.length() > 3) {

			try {

				em = factory.createEntityManager();
				Query q = em.createQuery("SELECT c FROM Card c WHERE UPPER(c.formatedName)=UPPER('" + card + "')");
				List<Card> data = q.getResultList();

				if (data.size() != 1) {
					q = em.createQuery("SELECT c FROM Card c WHERE UPPER(c.formatedName) LIKE (:cardName)");
					q.setParameter("cardName", "%" + card.toUpperCase() + "%");
					data = q.getResultList();

					if (data.size() == 1) {
						sb.append(data.get(0).toString());
					}

					else if (data.size() < 1) {
						sb.append("Could not find card " + card);
					}

					else { // more than one found

						sb.append("Found multiple cards: ");
						for (int i = 0; i < data.size(); i++) {
							sb.append(data.get(i).getName());
							if (i + 1 == data.size()) {
								break;
							}
							sb.append(", ");
						}
					}
				}

				else { // if data.size() == 1, return the data about the card
					sb.append(data.get(0).toString());
				}

			} finally {
				em.close();
			}
			return sb.toString();
		}
		return "You need at least 4 characters to do a search";

	}

	/**
	 * Gets the equipment data.
	 *
	 * @param card the card
	 * @return the equipment data
	 */
	@SuppressWarnings("unchecked")
	public static List<Equipment> getEquipmentData(String card) {
		card = card.replaceAll("'", "");

		try {

			em = factory.createEntityManager();
			Query q = em.createQuery(
					"SELECT e FROM Equipment e WHERE UPPER(e.formatedAffectedCardName)=UPPER('" + card + "')");
			List<Equipment> data = q.getResultList();

			if (data.size() > 0) {
				return data;
			}

			else {
				q = em.createQuery(
						"SELECT c FROM Equipment c WHERE UPPER(c.formatedAffectedCardName) LIKE (:cardName)");
				q.setParameter("cardName", "%" + card.toUpperCase() + "%");
				data = q.getResultList();

				return data;

			}

		} finally {
			em.close();
		}

	}

	/**
	 * Gets the price data.
	 *
	 * @param card the card
	 * @return the price data
	 */
	@SuppressWarnings("unchecked")
	public static String getPriceData(String card) {
		String message = "";
		card = card.replaceAll("'", "");

		if (card.length() > 3) {

			float platRatio = getRatio();

			try {

				em = factory.createEntityManager();
				Query q = em.createQuery("SELECT p FROM ItemPrice p WHERE UPPER(p.name) LIKE (:cardName)");
				q.setParameter("cardName", "%" + card.toUpperCase() + "%");
				List<ItemPrice> data = q.getResultList();

				if (data.size() == 0) {
					return "No sales for " + card + " found in the last 14 days";
				}

				for (ItemPrice item : data) {
					message += item.getListedPrice(platRatio) + " ";
				}

			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				em.close();
			}

			return message;

		}

		return "You need at least 4 characters to do a search";

	}

	/**
	 * Gets the ratio.
	 *
	 * @return the ratio
	 */
	@SuppressWarnings("unchecked")
	public static float getRatio() {

		try {

			em = factory.createEntityManager();
			Query q = em.createQuery("SELECT p FROM ItemPrice p WHERE UPPER(p.name) LIKE (:cardName)");
			q.setParameter("cardName", "%" + "Computed Draft Booster Pack".toUpperCase());
			List<ItemPrice> data = q.getResultList();

			if (data.size() == 0) {
			}

			return (Float.valueOf(data.get(0).getWeightedAverageGold())
					/ Float.valueOf(data.get(0).getWeightedAveragePlatinum()));

		} catch (Exception e) {
			e.printStackTrace();
			return 0;

		} finally {
			em.close();
		}

	}

	/**
	 * Gets the all user where bot is in channel.
	 *
	 * @return the all user where bot is in channel
	 */
	@SuppressWarnings("unchecked")
	public static List<User> getAllUserWhereBotIsInChannel() {

		em = factory.createEntityManager();
		Query q = em.createQuery("SELECT p FROM User p WHERE UPPER(p.inChannel) = ('1')");
		List<User> data = q.getResultList();
		return data;

	}

	/**
	 * Gets the user ign.
	 *
	 * @param user the user
	 * @return the user ign
	 */
	@SuppressWarnings("unchecked")
	public static String getUserIGN(String user) {

		try {
			em = factory.createEntityManager();
			Query q = em.createQuery("SELECT p FROM User p WHERE UPPER(p.name) = (:user)");
			q.setParameter("user", user);
			List<User> data = q.getResultList();
			if (data.size() > 0 && data.get(0).getIGN() != null) {
				return "IGN for user "+user+" is "+data.get(0).getIGN();

			} else {
				return "Could not find IGN for user " + user;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Could not find IGN for user " + user;
		}

	}

	/**
	 * Update user data.
	 *
	 * @param name the name
	 * @param inChannel the in channel
	 * @param whispers the whispers
	 * @param ign the ign
	 */
	public static void updateUserData(String name, int inChannel, int whispers, String ign) {

		try {
			em = factory.createEntityManager();
			EntityTransaction t = null;
			t = em.getTransaction();
			t.begin();
			em.merge(new User(name, inChannel, whispers, ign));
			t.commit();
		} finally {
			em.close();
		}
	}

	/**
	 * Update user where bot is in channel.
	 *
	 * @param user the user
	 * @param i the i
	 */
	@SuppressWarnings("unchecked")
	public static void updateUserWhereBotIsInChannel(String user, int i) {
		try {
			em = factory.createEntityManager();
			Query q = em.createQuery("SELECT p FROM User p WHERE UPPER(p.name) = (:name)");
			q.setParameter("name", user);
			List<User> data = q.getResultList();
			EntityTransaction t = null;

			if (data.size() > 0) {
				t = em.getTransaction();
				t.begin();
				em.merge(new User(data.get(0).getName(), i, data.get(0).whisperSettings(), data.get(0).getIGN()));
				t.commit();
			}

			else {
				t = em.getTransaction();
				t.begin();
				em.merge(new User(user, i, 1, ""));
				t.commit();
			}

			ChannelStats.getStats().put("#" + user, new UserChannel(1));

		} finally {
			em.close();
		}

	}

	/**
	 * Sets the whispers.
	 *
	 * @param user the user
	 * @param whispers the whispers
	 */
	@SuppressWarnings("unchecked")
	public static void setWhispers(String user, int whispers) {
		try {
			em = factory.createEntityManager();
			Query q = em.createQuery("SELECT p FROM User p WHERE UPPER(p.name) = (:name)");
			q.setParameter("name", user);
			List<User> data = q.getResultList();
			EntityTransaction t = null;

			if (data.size() > 0) {
				t = em.getTransaction();
				t.begin();
				em.merge(new User(data.get(0).getName(), data.get(0).isInChannel(), whispers, data.get(0).getIGN()));
				t.commit();

			}

			else {
				t = em.getTransaction();
				t.begin();
				em.merge(new User(user, 0, whispers, ""));
				t.commit();
			}

			ChannelStats.getStats().put("#" + user, new UserChannel(whispers));

		} finally {
			em.close();
		}

	}

	/**
	 * Sets the user ign.
	 *
	 * @param ign the ign
	 * @param twitchName the twitch name
	 * @return the string
	 */
	@SuppressWarnings("unchecked")
	public static String setUserIGN(String ign, String twitchName) {
		try {
			em = factory.createEntityManager();
			Query q = em.createQuery("SELECT p FROM User p WHERE UPPER(p.name) = (:name)");
			q.setParameter("name", ign);
			List<User> data = q.getResultList();
			EntityTransaction t = null;

			if (data.size() > 0) {
				t = em.getTransaction();
				t.begin();
				em.merge(
						new User(data.get(0).getName(), data.get(0).isInChannel(), data.get(0).whisperSettings(), ign));
				t.commit();
				return "IGN "+ign+" set for user "+twitchName;

			}

			else {
				t = em.getTransaction();
				t.begin();
				em.merge(new User(twitchName, 0, 1, ign));
				t.commit();
				return "IGN "+ign+" set for user "+twitchName;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "Could not set IGN for user "+twitchName;
		} finally {
			em.close();
		}

	}

	/**
	 * Gets the all users.
	 *
	 * @return the all users
	 */
	@SuppressWarnings("unchecked")
	public static String getAllUsers() {
		
		try {
			em = factory.createEntityManager();
			Query q = em.createQuery("SELECT p FROM User p");
			List<User> data = q.getResultList();
			
				return "WorkerBot currently have "+data.size() + " active users";
			
		} catch (Exception e) {
			return "Could not find number of users";
		}
		
	}
}
