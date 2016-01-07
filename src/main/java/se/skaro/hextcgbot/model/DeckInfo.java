package se.skaro.hextcgbot.model;

public final class DeckInfo {
	
	private String date;
	private String champion;
	private String tournament;
	private String shards;
	private String id;

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