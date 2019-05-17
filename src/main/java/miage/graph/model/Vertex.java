package miage.graph.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Vertex {

	private String label;
	private Set<Vertex> parents;
	private Set<Vertex> children;
	private List<Link> link;
	
	public Vertex(String label) {
        this.label = label;
        this.link = new ArrayList<>();
        this.parents = new HashSet<>();
        this.children = new HashSet<>();
    }

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Set<Vertex> getParents() {
		return parents;
	}

	public Set<Vertex> getChildren() {
		return children;
	}

	public void setParentChildren(Vertex v) {
		this.children.add(v);
		v.getParents().add(this);
	}

	public List<Link> getLink() {
		return link;
	}

	public void setLink(Link link) {
		boolean isPresent = false;
		for (Link arrayLink: this.link) {
			if (link.getSource() == arrayLink.getSource() 
					&& link.getDestination() == arrayLink.getDestination()) {
				isPresent = true;
			}
		}
		if (!isPresent) {
			this.link.add(link);
		}
	}

	@Override
	public String toString() {
		return "" + link;
	}
}
