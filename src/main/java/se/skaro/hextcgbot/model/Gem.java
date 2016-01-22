package se.skaro.hextcgbot.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table (name="GEM")
public class Gem extends AbstractEntity{
	
	private String socket;
	private String text;
	
	public Gem(String name, String socket, String text) {
		this.name = name;
		this.socket = socket;
		this.text = text;
	}
	public Gem(){		
	}
	public String getSocket() {
		return socket;
	}
	public String getText() {
		return text;
	}
	@Override
	public String toString() {
		return name +". Socketable " + socket + ": " + text;
	}
	
	

}
