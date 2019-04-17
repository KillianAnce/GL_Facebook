package graphe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Vertex {

	private String label;
	private Set<Vertex> parents;
	private Set<Vertex> children;
	private ArrayList<Link> link;
	
	public Vertex(String label) {
        this.label = label;
        this.link = new ArrayList<Link>();
        this.parents = new HashSet<Vertex>();
        this.children = new HashSet<Vertex>();
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

	public void setParents(Vertex v) {
		this.parents.add(v);
	}

	public Set<Vertex> getChildren() {
		return children;
	}

	public void setChildren(Vertex v) {
		this.children.add(v);
	}

	public ArrayList<Link> getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link.add(link);
	}

	@Override
	public String toString() {
		return "Vertex [" + label + " " + link + "]";
	}
}
