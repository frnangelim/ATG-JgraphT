package ativ.atg;

public class Node {
	private String Id;
    private String Label;
    private int weight;
    
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
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
