package se.skaro.hextcgbot.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

	@Entity
	@Table (name="CHAMPION")
	public final class Champion extends AbstractEntity implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@Column(name = "ChampionSet")
		private String set;
		private int health;
		private String race;
		@Column(name = "class")
		private String championClass;
		private String charge;

		public Champion(String name, String set, int health, String race, String championClass, String charge) {
			super();
			this.name = name;
			this.set = set;
			this.health = health;
			this.race = race;
			this.championClass = championClass;
			this.charge = charge;
		}
		
		public Champion(){
		}

		public String getSet() {
			return set;
		}

		public int getHealth() {
			return health;
		}

		public String getRace() {
			return race;
		}

		public String getChampionClass() {
			return championClass;
		}

		public String getCharge() {
			return charge;
		}

		@Override
		public String toString() {
			return name + ", " + set + ", " + health + " health. " + race
					+ " " + championClass + ". " + charge;
		}
		
		

	}

