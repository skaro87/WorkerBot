package se.skaro.hextcgbot.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The Class User.
 */
@Entity
@Table (name="USER")
public final class UserOLD extends AbstractEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The in channel. */
	private int inChannel;

	/** The whispers. */
	private int whispers;

	/** The ign. */
	private String ign;

	/**
	 * Instantiates a new user.
	 */
	public UserOLD() {
	}

	/**
	 * Instantiates a new user.
	 *
	 * @param name
	 *            the name
	 * @param inChannel
	 *            the in channel
	 * @param whispers
	 *            the whispers
	 * @param ign
	 *            the ign
	 */
	public UserOLD(String name, int inChannel, int whispers, String ign) {
		super();
		this.name = name;
		this.inChannel = inChannel;
		this.ign = ign;
		this.whispers = whispers;
	}
	
	/**
	 * Checks if is in channel.
	 *
	 * @return the int
	 */
	public int isInChannel() {
		return inChannel;
	}

	/**
	 * Whisper settings.
	 *
	 * @return the int
	 */
	public int whisperSettings() {
		return whispers;
	}

	/**
	 * Gets the ign.
	 *
	 * @return the ign
	 */
	public String getIGN() {
		return ign;
	}

}
