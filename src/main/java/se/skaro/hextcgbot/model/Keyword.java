package se.skaro.hextcgbot.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

	@Entity
	@Table (name="KEYWORD")
	public final class Keyword extends AbstractEntity implements Serializable {
		private static final long serialVersionUID = 1L;

		private String explanation;
		
		
		public Keyword(String name, String explanation) {
			this.name = name;
			this.explanation = explanation;
		}
		public Keyword(){
			
		}
		public String getExplanation() {
			return explanation;
		}
		@Override
		public String toString() {
			return name+": "+explanation;
		}
			
		

	}