package se.skaro.hextcgbot.statistics;

/**
 * The Class UserChannel. Represents a users channel, with settings and statistics. Stats to be used.
 */
public class UserChannel {
	
	/**
	 * Instantiates a new user channel.
	 *
	 * @param whispers the whispers
	 */
	public UserChannel(int whispers, String prefix){
		this.whispers = whispers;
		this.commandsCard = 0;
		this.commandsEquipment = 0;
		this.commandsOther = 0;
		this.incommingMessages = 0;
		this.prefix = prefix;
	}
	
	public UserChannel(int whispers){
		this.whispers = whispers;
		this.commandsCard = 0;
		this.commandsEquipment = 0;
		this.commandsOther = 0;
		this.incommingMessages = 0;
		this.prefix = "!";
	}

	/** The commands other. */
	private int commandsOther;
	
	/** The commands card. */
	private int commandsCard;
	
	/** The commands equipment. */
	private int commandsEquipment;
	
	/** The commands price. */
	private int commandsPrice;
	
	/** The incomming messages. */
	private int incommingMessages;
	
	/** The whispers. */
	private int whispers;
	
	private String prefix;
	
	/**
	 * Reset.
	 */
	public void reset(){
		commandsCard = 0;
		commandsEquipment = 0;
		commandsPrice = 0;
		commandsOther = 0;
		incommingMessages = 0;
	}

	/**
	 * Adds the command.
	 */
	public  void addCommand() {
		commandsOther++;
	}

	/**
	 * Adds the command price.
	 */
	public  void addCommandPrice() {
		commandsPrice++;
	}

	/**
	 * Adds the command card.
	 */
	public  void addCommandCard() {
		commandsCard++;
	}
	
	/**
	 * Adds the incomming message.
	 */
	public  void addIncommingMessage(){
		incommingMessages++;
	}

	/**
	 * Gets the commands card.
	 *
	 * @return the commands card
	 */
	public  int getCommandsCard() {
		return commandsCard;
	}

	/**
	 * Sets the commands card.
	 *
	 * @param commandsCard the new commands card
	 */
	public  void setCommandsCard(int commandsCard) {
		this.commandsCard = commandsCard;
	}

	/**
	 * Gets the commands equipment.
	 *
	 * @return the commands equipment
	 */
	public  int getCommandsEquipment() {
		return commandsEquipment;
	}

	/**
	 * Sets the commands equipment.
	 *
	 * @param commandsEquipment the new commands equipment
	 */
	public  void setCommandsEquipment(int commandsEquipment) {
		this.commandsEquipment = commandsEquipment;
	}

	/**
	 * Gets the commands price.
	 *
	 * @return the commands price
	 */
	public  int getCommandsPrice() {
		return commandsPrice;
	}

	/**
	 * Sets the commands price.
	 *
	 * @param commandsPrice the new commands price
	 */
	public  void setCommandsPrice(int commandsPrice) {
		this.commandsPrice = commandsPrice;
	}

	/**
	 * Gets the commands other.
	 *
	 * @return the commands other
	 */
	public  int getCommandsOther() {
		return commandsOther;
	}

	/**
	 * Sets the commands other.
	 *
	 * @param commandsOther the new commands other
	 */
	public  void setCommandsOther(int commandsOther) {
		this.commandsOther = commandsOther;
	}

	/**
	 * Gets the incomming messages.
	 *
	 * @return the incomming messages
	 */
	public  int getIncommingMessages() {
		return incommingMessages;
	}

	/**
	 * Sets the incomming messages.
	 *
	 * @param incommingMessages the new incomming messages
	 */
	public  void setIncommingMessages(int incommingMessages) {
		this.incommingMessages = incommingMessages;
	}

	/**
	 * Gets the whispers.
	 *
	 * @return the whispers
	 */
	public  int getWhispers() {
		return whispers;
	}

	/**
	 * Sets the whispers.
	 *
	 * @param whispers the new whispers
	 */
	public  void setWhispers(int whispers) {
		this.whispers = whispers;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	
	

}
