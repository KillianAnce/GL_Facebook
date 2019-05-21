package miage.graph.model;

import java.util.List;


public class Link {

	private Vertex source;
	private String direction;
	private List<LinkProperties> linkProperties;
	private String relation;
	private Vertex destination;
	
	public Link(Vertex source, String direction, List<LinkProperties> properties, String relation, Vertex destination) {
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

	public List<LinkProperties> getLinkProperties() {
		return linkProperties;
	}

	/**
	 * M�thode permettant d'ajouter des propri�t�s � un lien
	 * 
	 * Remarque : 
	 * - Si le lien n'avait pas de propri�t� lors de sa cr�ation,
	 * la liste contient une valeur null en 1ere entr�e
	 * 
	 * L'ajout v�rifie aussi que la liste ne contient pas cette valeur
	 * @param linkProperties
	 */
	public void setLinkProperties(LinkProperties linkProperties) {
		boolean isPresent = false;
		if (this.linkProperties.get(0)==null) {
			this.linkProperties.clear();
		} else {
			for (LinkProperties property : this.linkProperties) {
				try {
					if (linkProperties.getValue().equals(property.getValue())) {
						isPresent = true;
					}
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
			}
		}
		
		if (!isPresent) {
			this.linkProperties.add(linkProperties);
		}
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
		if (linkProperties.get(0) == null) {
			return source.getLabel() + ":" + relation + ":" + "--" + direction + ":" + destination.getLabel() + "\n";
		}
		if (linkProperties.size() >= 2) {
			return source.getLabel() + ":" + relation + ":" +
					linkProperties.toString()
						.replace("[", "")
						.replace(" ", "")
						.replace("]", "")
						.replace(",", ";")
						.replace("+", ",")
						.trim() + 
						"--" + direction + ":" + destination.getLabel() + "\n";
		}
		return source.getLabel() + ":" + relation + ":" +
		linkProperties.toString()
			.replace("[", "")
			.replace(" ", "")
			.replace("]", "")
			.replace(",", ";")
			.replace("+", ",")
			.trim() + 
			":" + "--" + direction + ":" + destination.getLabel() + "\n";
	}

}
