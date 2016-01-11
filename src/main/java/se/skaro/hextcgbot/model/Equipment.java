package se.skaro.hextcgbot.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the CARD database table.
 * 
 */
@Entity
@Table (name="EQUIPMENT")
public final class Equipment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String name;

	private String affectedCardName;
	private String shard;
	private String equipmentSlot;
	private String rarity;
	private String equipmentLocation;
	
	@Column(length=1024)   
	private String text;
	
	private String formatedAffectedCardName;
	private String formatedName;

	public Equipment() {

	}

	public Equipment(String name, String affectedCardName, String shard, String equipmentSlot, String rarity,
			String equipmentLocation, String text) {
		super();
		this.name = name;
		this.affectedCardName = affectedCardName;
		this.shard = shard;
		this.equipmentSlot = equipmentSlot;
		this.rarity = rarity;
		this.equipmentLocation = equipmentLocation;
		this.text = text;
		formatedAffectedCardName = affectedCardName.replaceAll("'", "");
		formatedName = name.replaceAll("'", "");
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getName() {
		return name;
	}

	public String getAffectedCardName() {
		return affectedCardName;
	}

	public String getShard() {
		return shard;
	}

	public String getEquipmentSlot() {
		return equipmentSlot;
	}

	public String getRarity() {
		return rarity;
	}

	public String getEquipmentLocation() {
		return equipmentLocation;
	}

	public String getText() {
		return text;
	}
	

	public String getFormatedAffectedCardName() {
		return formatedAffectedCardName;
	}

	public String getFormatedName() {
		return formatedName;
	}

	@Override
	public String toString() {
		return "["+name+" ("+equipmentSlot+") "+text + ". Found in: "+equipmentLocation+"]".replaceAll("\n", "");
	}

	
}