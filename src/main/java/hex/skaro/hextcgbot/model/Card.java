/*
 * 
 */
package hex.skaro.hextcgbot.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the CARD database table.
 * 
 */
@Entity
@NamedQuery(name = "Card.findAll", query = "SELECT c FROM Card c")
public final class Card implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The name. */
	@Id
	private String name;

	/** The formated name. */
	private String formatedName;

	/** The set. */
	@Column(name = "SETID")
	private String set;
	
	/** The shard. */
	private String shard;
	
	/** The rarity. */
	private String rarity;
	
	/** The type. */
	private String type;
	
	/** The quick. */
	private String quick;
	
	/** The trait1. */
	private String trait1;
	
	/** The trait2. */
	private String trait2;
	
	/** The trait3. */
	private String trait3;
	
	/** The faction. */
	private String faction;
	
	/** The cost. */
	private String cost;
	
	/** The blood. */
	private int blood;
	
	/** The diamond. */
	private int diamond;
	
	/** The ruby. */
	private int ruby;
	
	/** The sapphire. */
	private int sapphire;
	
	/** The wild. */
	private int wild;
	
	/** The attack. */
	private String attack;
	
	/** The defense. */
	private String defense;

	/** The text. */
	@Column(length = 1024)
	private String text;

	/** The unique. */
	@Column(name = "ISUNIQUE")
	private String unique;

	/** The major. */
	private int major;
	
	/** The minor. */
	private int minor;

	/**
	 * Instantiates a new card.
	 */
	public Card() {
	}

	/**
	 * Instantiates a new card.
	 *
	 * @param name the name
	 * @param formatedName the formated name
	 * @param set the set
	 * @param shard the shard
	 * @param rarity the rarity
	 * @param type the type
	 * @param quick the quick
	 * @param trait1 the trait1
	 * @param trait2 the trait2
	 * @param trait3 the trait3
	 * @param faction the faction
	 * @param cost the cost
	 * @param blood the blood
	 * @param diamond the diamond
	 * @param ruby the ruby
	 * @param sapphire the sapphire
	 * @param wild the wild
	 * @param attack the attack
	 * @param defense the defense
	 * @param text the text
	 * @param unique the unique
	 * @param major the major
	 * @param minor the minor
	 */
	public Card(String name, String formatedName, String set, String shard, String rarity, String type, String quick,
			String trait1, String trait2, String trait3, String faction, String cost, int blood, int diamond, int ruby,
			int sapphire, int wild, String attack, String defense, String text, String unique, int major, int minor) {
		super();
		this.name = name;
		this.formatedName = formatedName;
		this.set = set;
		this.shard = shard;
		this.rarity = rarity;
		this.type = type;
		this.quick = quick;
		this.trait1 = trait1;
		this.trait2 = trait2;
		this.trait3 = trait3;
		this.faction = faction;
		this.cost = cost;
		this.blood = blood;
		this.diamond = diamond;
		this.ruby = ruby;
		this.sapphire = sapphire;
		this.wild = wild;
		this.attack = attack;
		this.defense = defense;
		this.text = text;
		this.unique = unique;
		this.major = major;
		this.minor = minor;
	}

	/**
	 * Gets the serialversionuid.
	 *
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the formated name.
	 *
	 * @return the formated name
	 */
	public String getFormatedName() {
		return formatedName;
	}

	/**
	 * Gets the sets the.
	 *
	 * @return the sets the
	 */
	public String getSet() {
		return set;
	}

	/**
	 * Gets the shard.
	 *
	 * @return the shard
	 */
	public String getShard() {
		return shard;
	}

	/**
	 * Gets the rarity.
	 *
	 * @return the rarity
	 */
	public String getRarity() {
		return rarity;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Gets the quick.
	 *
	 * @return the quick
	 */
	public String getQuick() {
		return quick;
	}

	/**
	 * Gets the trait1.
	 *
	 * @return the trait1
	 */
	public String getTrait1() {
		return trait1;
	}

	/**
	 * Gets the trait2.
	 *
	 * @return the trait2
	 */
	public String getTrait2() {
		return trait2;
	}

	/**
	 * Gets the trait3.
	 *
	 * @return the trait3
	 */
	public String getTrait3() {
		return trait3;
	}

	/**
	 * Gets the faction.
	 *
	 * @return the faction
	 */
	public String getFaction() {
		return faction;
	}

	/**
	 * Gets the cost.
	 *
	 * @return the cost
	 */
	public String getCost() {
		return cost;
	}

	/**
	 * Gets the blood.
	 *
	 * @return the blood
	 */
	public int getBlood() {
		return blood;
	}

	/**
	 * Gets the diamond.
	 *
	 * @return the diamond
	 */
	public int getDiamond() {
		return diamond;
	}

	/**
	 * Gets the ruby.
	 *
	 * @return the ruby
	 */
	public int getRuby() {
		return ruby;
	}

	/**
	 * Gets the sapphire.
	 *
	 * @return the sapphire
	 */
	public int getSapphire() {
		return sapphire;
	}

	/**
	 * Gets the wild.
	 *
	 * @return the wild
	 */
	public int getWild() {
		return wild;
	}

	/**
	 * Gets the attack.
	 *
	 * @return the attack
	 */
	public String getAttack() {
		return attack;
	}

	/**
	 * Gets the defense.
	 *
	 * @return the defense
	 */
	public String getDefense() {
		return defense;
	}

	/**
	 * Gets the body.
	 *
	 * @return the body
	 */
	public String getBody() {
		return text;
	}

	/**
	 * Gets the unique.
	 *
	 * @return the unique
	 */
	public String getUnique() {
		return unique;
	}

	/**
	 * Gets the minor.
	 *
	 * @return the minor
	 */
	public int getMinor() {
		return minor;
	}

	/**
	 * Gets the major.
	 *
	 * @return the major
	 */
	public int getMajor() {
		return major;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return "[" + getText() + "]";
	}

	/**
	 * Gets the text. This will be changed when the new data is collected.
	 *
	 * @return the text
	 */
	private String getText() {

		StringBuilder sb = new StringBuilder();

		sb.append(name + " (" + set + " " + rarity + ") " + cost + " cost ");

		// shard cost
		if (blood + diamond + ruby + sapphire + wild > 0){
		sb.append("(");

		if (blood > 0) {
			for (int i = 0; i < blood; i++) {
				sb.append("B");
			}
		}

		if (diamond > 0) {
			for (int i = 0; i < diamond; i++) {
				sb.append("D");
			}
		}

		if (ruby > 0) {
			for (int i = 0; i < ruby; i++) {
				sb.append("R");
			}
		}

		if (sapphire > 0) {
			for (int i = 0; i < sapphire; i++) {
				sb.append("S");
			}
		}

		if (wild > 0) {
			for (int i = 0; i < wild; i++) {
				sb.append("W");
			}
		}

		sb.append(")");
		}
		// ------
		sb.append(" ");

		if (type.equals("Troop")) {
			sb.append(attack + "/" + defense + " ");
		}

		if (quick.equals("Yes")) {
			sb.append("Quick ");
		}

		if (unique.equals("Yes")) {
			sb.append("Unique ");
		}

		sb.append(type);
		
		if (type.equals("Troop")){
			if (trait1.length() > 0){
				sb.append(" - "+trait1);
			}
			if (trait2.length() > 0){
				sb.append(", "+trait2);
			}
			if (trait3.length() > 0){
				sb.append(", "+trait3);
			}
		}

		sb.append(". " + text);

		return sb.toString().replaceAll("\n", " ");
	}
}