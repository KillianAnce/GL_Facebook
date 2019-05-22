package miage.graph.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	 * M�thode d'ajout de propri�t�s � un lien entre un noeud source
	 * et un noeud de destination
	 * @param properties	Tableau des propri�t�s
	 */
	public void addProperties(Set<String> properties) {
		for (String property : properties) {
			String[] splitProperty = property.split("=");
			switch (splitProperty[0]) {
			case "since":
				this.setLinkProperties(new Since(splitProperty[1]));
				break;
			case "hired":
				this.setLinkProperties(new Hired(splitProperty[1]));
				break;
			case "role":
				this.setLinkProperties(new Role(splitProperty[1]));
				break;
			case "shared":
				String[] shared = splitProperty[1].split(",");
				Set<String> elementsShared = new HashSet<>();
				for (String elementShared : shared) {
					elementsShared.add(elementShared);
				}
				this.setLinkProperties(new Shared(elementsShared));				
			}
		}
	}

	/**
	 * M�thode permettant d'ajouter des propri�t�s � un lien
	 * 
	 * Remarque : - Si le lien n'avait pas de propri�t� lors de sa cr�ation, la
	 * liste contient une valeur null en 1ere entr�e
	 * 
	 * L'ajout v�rifie aussi que la liste ne contient pas cette valeur
	 * 
	 * @param linkProperties
	 */
	public void setLinkProperties(LinkProperties linkProperties) {
		boolean isPresent = false;
		if (this.linkProperties.get(0) == null) {
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
	 * @return Vertex Retourne le sommet source du lien
	 */
	public Vertex getSource() {
		return source;
	}

	/**
	 * @param source Ajouter le sommet de source du lien
	 */
	public void setSource(Vertex source) {
		this.source = source;
	}

	/**
	 * @return Vertex Retourne le sommet de destination du lien
	 */
	public Vertex getDestination() {
		return destination;
	}

	/**
	 * @param destination Ajouter le sommet de destination du lien
	 */
	public void setDestination(Vertex destination) {
		this.destination = destination;
	}
	
	@Override
	public String toString() {
		String toString = "";
		if (linkProperties == null) {
			toString = source.getLabel() + ":" + relation + ":" + "--" + direction + ":" + destination.getLabel()
					+ "\n";
		} else {
			if (linkProperties.size() >= 2) {
				toString = source.getLabel()
						+ ":" + relation + ":" + linkProperties.toString().replace("[", "").replace(" ", "")
								.replace("]", "").replace(",", ";").replace("+", ",").trim()
						+ "--" + direction + ":" + destination.getLabel() + "\n";
			} else {
				toString = source.getLabel() + ":" + relation + ":"
						+ linkProperties.toString().replace("[", "").replace(" ", "").replace("]", "").replace(",", ";")
								.replace("+", ",").trim()
						+ ":" + "--" + direction + ":" + destination.getLabel() + "\n";
			}
		}
		return toString;
	}
}
