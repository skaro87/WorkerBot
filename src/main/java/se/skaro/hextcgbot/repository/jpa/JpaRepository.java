package se.skaro.hextcgbot.repository.jpa;

import se.skaro.hextcgbot.model.*;

import javax.persistence.*;
import java.util.List;

public final class JpaRepository {

	private static final String PERSISTENCE_UNIT_NAME = "WorkerBot";

	private static EntityManagerFactory factory;
	private static EntityManager em;

	public static boolean startup() {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		return true;
	}

	@SuppressWarnings("unchecked")
	public static List<CardOLD> findCardByFormatedName(String name) {
		try {
			em = factory.createEntityManager();
			Query q = em.createQuery("SELECT c FROM Card c WHERE UPPER(c.formatedName) LIKE (:name)");
			q.setParameter("name", "%" + name.toUpperCase() + "%");
			return q.getResultList();
		} finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Equipment> findEquipmentByAffectedCardName(String name, boolean searchExactly) {
		try {
			em = factory.createEntityManager();
			Query q = em.createQuery("SELECT e FROM Equipment e WHERE UPPER(e.formatedAffectedCardName) LIKE (:name)");
			if (searchExactly) {
				q.setParameter("name", name.toUpperCase());
			} else {
				q.setParameter("name", "%" + name.toUpperCase() + "%");
			}
			return q.getResultList();
		} finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<ItemPrice> findPriceByName(String name) {
		try {
			em = factory.createEntityManager();
			Query q = em.createQuery("SELECT i FROM ItemPrice i WHERE UPPER(i.name) LIKE (:name)");
			q.setParameter("name", "%" + name.toUpperCase() + "%");
			return q.getResultList();
		} finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<UserOLD> findUserByName(String name) {
		try {
			em = factory.createEntityManager();
			Query q = em.createQuery("SELECT u FROM User u WHERE UPPER(u.name) LIKE (:name)");
			q.setParameter("name", name.toUpperCase());
			return q.getResultList();
		} finally {
			em.close();
		}
	}
	
	public static List<UserOLD> findUserByNameWithWildcards(String name) {
		return findUserByName("%"+name+"%");
		
	}
	
	@SuppressWarnings("unchecked")
	public static List<UserOLD> findUsersToAutoJoin() {
		try {
			em = factory.createEntityManager();
			Query q = em.createQuery("SELECT u FROM User u WHERE u.inChannel = 1");
			return q.getResultList();
		} finally {
			em.close();
		}
	}

	public static boolean saveOrUpdateUser(UserOLD user) {
		try {
			em = factory.createEntityManager();
			EntityTransaction t = null;
			t = em.getTransaction();
			t.begin();
			em.merge(user);
			t.commit();
			return true;
		} finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<UserOLD> findUsersToJoinChannel() {
		try {
			em = factory.createEntityManager();
			Query q = em.createQuery("SELECT u FROM User u WHERE u.inChannel = 1");
			return q.getResultList();
		} finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	public static Double getRatio() {
		try {
			em = factory.createEntityManager();
			Query q = em.createQuery("SELECT p FROM ItemPrice p WHERE UPPER(p.name) LIKE (:cardName)");
			q.setParameter("cardName", "%" + "Computed Draft Booster Pack".toUpperCase());
			List<ItemPrice> data = q.getResultList();

			if (data.size() == 0) {
				//TODO: Logic here, like get boosterprice and return ratio.
			}

			return (Double.valueOf(data.get(0).getWeightedAverageGold())
					/ Double.valueOf(data.get(0).getWeightedAveragePlatinum()));

		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;

		} finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List<Champion> findChampionByName(String name) {
		try {
			em = factory.createEntityManager();
			Query q = em.createQuery("SELECT c FROM Champion c WHERE UPPER(c.name) LIKE (:name)");
			q.setParameter("name", "%"+name.toUpperCase()+"%");
			return q.getResultList();
		} finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List<Gem> findGemByName(String name) {
		try {
			em = factory.createEntityManager();
			Query q = em.createQuery("SELECT g FROM Gem g WHERE UPPER(g.name) LIKE (:name)");
			q.setParameter("name", "%"+name.toUpperCase()+"%");
			return q.getResultList();
		} finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List<Gem> findGemByText(String text) {
		try {
			em = factory.createEntityManager();
			Query q = em.createQuery("SELECT g FROM Gem g WHERE UPPER(g.text) LIKE (:text)");
			q.setParameter("text", "%"+text.toUpperCase()+"%");
			return q.getResultList();
		} finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List<Keyword> findKeywordByName(String name) {
		try {
			em = factory.createEntityManager();
			Query q = em.createQuery("SELECT k FROM Keyword k WHERE UPPER(k.name) LIKE (:name)");
			q.setParameter("name", "%"+name.toUpperCase()+"%");
			return q.getResultList();
		} finally {
			em.close();
		}
	}

}