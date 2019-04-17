package graphe;

import java.util.ArrayList;

public class Link {

	private Vertex source;
	private String direction;
	private ArrayList<LinkProperties> linkProperties;
	private String relation;
	private Vertex destination;
	
	public Link(Vertex source, String direction, ArrayList<LinkProperties> properties, String relation, Vertex destination) {
		this.source = source;
		this.direction = direction;
		this.linkProperties = properties;
		this.relation = relation;
		this.destination = destination;
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

	public String getRelation() {
		return relation;
	}

	public void setRelation(String name) {
		this.relation = name;
	}

    /**
     * @return Vertex return the source
     */
    public Vertex getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(Vertex source) {
        this.source = source;
    }

    /**
     * @return Vertex return the destination
     */
    public Vertex getDestination() {
        return destination;
    }

    /**
     * @param destination the destination to set
     */
    public void setDestination(Vertex destination) {
        this.destination = destination;
	}
	
	@Override
	public String toString(){
		return "Link [properties " + linkProperties + "]";
	}

}
