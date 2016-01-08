package se.skaro.hextcgbot.model;

public final class DeckInfo {
	
	private final String date;
	private final String champion;
	private final String tournament;
	private final String shards;
	private final String id;

	public DeckInfo(String date, String champion, String tournament, String shards, String id) {
		super();
		this.date = date;
		this.champion = champion;
		this.tournament = tournament;
		this.shards = shards;
		this.id = "http://hexmeta.com/deck/"+id+"/show";
	}
	
	public String linkAndName(){
		return champion + "(" + shards +") "+id;
	}

	public String toString() {
		return date + " | " + tournament + " | " + champion + " (" + shards +") "+id;

	}

}
