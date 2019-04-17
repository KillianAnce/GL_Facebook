package graphe;

import java.util.ArrayList;

public class Link {

	private String direction;
	private ArrayList<LinkProperties> linkProperties;
	private String name;
	
	public Link(String direction, ArrayList<LinkProperties> properties, String name) {
		this.direction = direction;
		this.linkProperties = properties;
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

	public void setLinkProperties(LinkProperties linkProperties) {
		this.linkProperties.add(linkProperties);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString(){
		return "Link [Direction=" + direction + " | Name " + name + " | properties " + linkProperties + "]";
	}
}
