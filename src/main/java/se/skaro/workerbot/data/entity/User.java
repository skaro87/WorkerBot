package se.skaro.workerbot.data.entity;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.Type;

@Entity
@NamedQueries({
@NamedQuery(name = "User.findAutoJoinChannels", query = "SELECT u.name from User u WHERE u.inChannel = 1"),
//@NamedQuery(name = "User.getPrefixForUser", query = "SELECT u.prefix from User u WHERE u.name LIKE ?1")
@NamedQuery (name ="User.findByIgnContains", query = "Select u from User u WHERE UPPER(u.ign) LIKE CONCAT('%', ?1, '%')")
})
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
	
	@Column (name = "commands")
	@ElementCollection
	private Map<String, Boolean> commands;

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
