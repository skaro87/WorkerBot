package se.skaro.workerbot.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private long id;
	
	@Column(name = "twitch_name", nullable = false, length = 100, unique=true)
	private String name;
	
	@Column (name = "hex_ign", length = 100)
	private String ign;
	
	@Column (name = "in_channel")
	@Type(type="boolean")
	private boolean inChannel;
	
	@Column (name = "whisper_on")
	@Type(type="boolean")
	private boolean whisperOn;
	
	@Column (name = "prefix", length = 1)
	private String prefix;

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getIgn() {
		return ign;
	}

	public boolean isInChannel() {
		return inChannel;
	}

	public boolean isWhisperOn() {
		return whisperOn;
	}

	public String getPrefix() {
		return prefix;
	}
	
	// More settings?
	
	
	

}
