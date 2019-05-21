package miage.graph.model;

public class LinkProperties {
    private String value;

    public LinkProperties(String value){
        this.value = value;
    }
    
    public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
    public String toString(){
        return value;
    }
}
