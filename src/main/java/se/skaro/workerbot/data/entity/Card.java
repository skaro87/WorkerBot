package se.skaro.workerbot.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "card")
public class Card {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "card_id")
	private long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "formated_name", nullable = false)
	private String formatedName;

	@Column(name = "card_uuid", nullable = false)
	private String uuid;

	@Column(name = "type", nullable = false)
	private String type;

	@Column(name = "faction")
	private String faction;

	@Column(name = "cost", nullable = false)
	private String cost;

	@Column(name = "blood")
	private int blood;

	@Column(name = "diamond")
	private int diamond;

	@Column(name = "ruby")
	private int ruby;

	@Column(name = "sapphire")
	private int sapphire;

	@Column(name = "wild")
	private int wild;

	@Column(name = "traits")
	private String traits;

	@Column(name = "restriction")
	private String restriction;

	@Column(name = "rarity")
	private String rarity;

	@Column(name = "text")
	private String text;

	@Column(name = "flavor_text")
	private String flavorText;

	@Column(name = "atk")
	private int atk;

	@Column(name = "def")
	private int def;

	@Column(name = "set_id")
	private String set;

	@Column(name = "image_url")
	private String imageURL;

	@Column(name = "major_socket")
	private String majorSocket;

	@Column(name = "minor_socket")
	private String minorSocket;

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFormatedName(String formatedName) {
		this.formatedName = formatedName;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setFaction(String faction) {
		this.faction = faction;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public void setBlood(int blood) {
		this.blood = blood;
	}

	public void setDiamond(int diamond) {
		this.diamond = diamond;
	}

	public void setRuby(int ruby) {
		this.ruby = ruby;
	}

	public void setSapphire(int sapphire) {
		this.sapphire = sapphire;
	}

	public void setWild(int wild) {
		this.wild = wild;
	}

	public void setTraits(String traits) {
		this.traits = traits;
	}

	public void setRestriction(String restriction) {
		this.restriction = restriction;
	}

	public void setRarity(String rarity) {
		this.rarity = rarity;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setFlavorText(String flavorText) {
		this.flavorText = flavorText;
	}

	public void setAtk(int atk) {
		this.atk = atk;
	}

	public void setDef(int def) {
		this.def = def;
	}

	public void setSet(String set) {
		this.set = set;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public void setMajorSocket(String majorSocket) {
		this.majorSocket = majorSocket;
	}

	public void setMinorSocket(String minorSocket) {
		this.minorSocket = minorSocket;
	}
	
	

}
