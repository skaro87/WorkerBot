package se.skaro.hextcgbot.model;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractEntity {
	
	@Id
	protected String name;
	
	public String getName() {
		return name;
	}

}
