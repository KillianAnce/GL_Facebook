package graphe;

import java.util.HashSet;
import java.util.Set;

public class Vertex {

	private String label;
	private Set<Vertex> parents;
	private Set<Link> lien;
	
	public Vertex(String label, Set<Link> lien) {
        this.label = label;
        this.lien = new HashSet<Link>();
        this.parents = new HashSet<Vertex>();
    }
	
    @Override
	public String toString() {
		return "Vertex [label=" + label + ", parents=" + parents + "]";
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

	public Set<Link> getLien() {
		return lien;
	}

	public void setLien(Set<Link> lien) {
		this.lien = lien;
	}

}
