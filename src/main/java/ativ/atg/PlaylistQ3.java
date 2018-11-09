package ativ.atg;

public class PlaylistQ3 {
    private String Id;
    private String Label;
    
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
	@Override
	public String toString() {
		return "MusicQ1 [Id=" + Id + ", Label=" + Label + "]";
	}
}