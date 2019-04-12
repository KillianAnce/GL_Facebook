package graphe;

import java.util.ArrayList;

public class Link {

	private String direction;
	private ArrayList<LinkProperties> linkProperties;
	private String name;
	
	public Link(String direction, ArrayList<LinkProperties> linkProperties, String name) {
		this.direction = direction;
		this.linkProperties = linkProperties;
		this.name = name;
	}
	
	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public ArrayList<LinkProperties> getLinkProperties() {
		return linkProperties;
	}

	public void setLinkProperties(ArrayList<LinkProperties> linkProperties) {
		this.linkProperties = linkProperties;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
