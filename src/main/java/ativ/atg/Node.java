package ativ.atg;

public class Node {
	private String Id;
    private String Label;
    private int weight;
	private String collaborative;
	private int distinctArtists;
	private String artist;
    
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		artist = artist;
	}

	public String isCollaborative() {
		return collaborative;
	}

	public void setCollaborative(String collaborative) {
		collaborative = collaborative;
	}

	public int getDistinctArtists() {
		return distinctArtists;
	}

	public void setDistinctArtists(int distinctArtists) {
		distinctArtists = distinctArtists;
	}

	public String getLabel() {
		return Label;
	}
	public void setLabel(String label) {
		Label = label;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	@Override
	public String toString() {
		if(weight!=0) {
			return "Node [Id=" + Id + ", Label=" + Label + ", weight=" + weight + "]";
		}
		else return "Node [Id=" + Id + ", Label=" + Label + "]";
	}
}
