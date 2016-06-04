package se.skaro.workerbot.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TextCommand {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "command_id")
	private long id;
	
	@Column(name = "syntax")
	private String syntax;
	
	@Column(name = "message")
	private String message;
	
	@Column(name = "help_message")
	private String helpMessage;

	public long getId() {
		return id;
	}

	public String getSyntax() {
		return syntax;
	}

	public String getMessage() {
		return message;
	}

	public String getHelpMessage() {
		return helpMessage;
	}
	
	

}
