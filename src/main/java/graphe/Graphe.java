package graphe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Graphe {

	private Set<Vertex> vertices;

	public Graphe() {
		this.vertices = new HashSet<Vertex>();
	}

	public Vertex addVertex(Vertex v) {
		Vertex contenu = getVertex(v.getLabel());
		if (contenu == null) {
			vertices.add(v);
		}
		return contenu;
	}

	public Vertex getVertex(String label) {
		for (Vertex vertex : vertices) {
			if (vertex.getLabel().equals(label)) {
				return vertex;
			}
		}
		return null;
	}

	public void addSingleEdge(Vertex v1, Vertex v2, String direction, ArrayList<LinkProperties> l, String relation) {
		v1.setChildren(v2);
		v2.setParents(v1);
		v2.setLink(new Link(v1, direction, l, relation, v2));
	}

	public void addMutualEdge(Vertex v1, Vertex v2, String direction, ArrayList<LinkProperties> l, String relation) {
		v1.setChildren(v2);
		v2.setChildren(v1);
		v1.setParents(v2);
		v2.setParents(v1);
		v1.setLink(new Link(v1, direction, l, relation, v2));
		v2.setLink(new Link(v2, direction, l, relation, v1));
	}

	// En cours de réalisation

	public Set<String> breadthFirstTraversal(String root, int l, String relation) {
		Set<String> visited = new LinkedHashSet<String>();
		Queue<String> queue = new LinkedList<String>();
		queue.add(root);
		visited.add(root);
		int i = 1;
		while (!queue.isEmpty() && i <= l) {
			for (int j = 0; j <= queue.size(); j++) {
				// System.out.println(i);
				// System.out.println("j : " + j);
				// System.out.println("Queue size après : " + queue.size());
				// System.out.println("Avant : " + queue);
				String vertex = queue.poll();
				// System.out.println("Après : " + queue);
				for (Vertex v : this.getAdjVerticesOfVertex(this.getVertex(vertex))) {
					// System.out.println(v);
					for (Link link : v.getLink()) {
						if (link.getSource() == this.getVertex(vertex) && link.getRelation().equals(relation)) {
							if (!visited.contains(v.getLabel())) {
								visited.add(v.getLabel());
								queue.add(v.getLabel());
							}
						}
					}
				}
			}
			i++;
		}
		return visited;
	}
	public Set<String> depthFirstTraversal(String origine, Set<String> sommetsVisites, String relation, int level) {
		sommetsVisites.add(this.getVertex(origine).getLabel());
		Iterator<Vertex> i = this.getVertex(origine).getChildren().iterator();
		while (i.hasNext() && level>0) {
			Vertex v = i.next();
			String suivant = v.getLabel();
			if (!sommetsVisites.contains(suivant)) {
				for (Link link : v.getLink()) {
					if (link.getRelation().equals(relation)) {
						int l  = level - 1;
						depthFirstTraversal(suivant, sommetsVisites, relation, l);
					}
				}
			}
		}
		return sommetsVisites;
	}

	public Set<String> search(String search, Set<String> result){
		Matcher mode = Pattern.compile("(mode=[A-Za-z]+)").matcher(search);
		String traversal = null;
		Matcher level = Pattern.compile("(niveau=[0-9]+)").matcher(search);
		int levelTraversal = 0;
		Matcher link = Pattern.compile("(liens=\\([a-z]+( (<|<>|>))?((\\,?[a-z]+( (<|<>|>))?)+)?\\))").matcher(search);
		String[] linkParameters = null;
		Set<String> sommetsVisites = new HashSet<String>();

		if (mode.find()){
			traversal = mode.group(0).split("=")[1];
		}
		if (level.find()){
			levelTraversal = Integer.parseInt(level.group(0).split("=")[1]);
		}
		if (link.find()){
			linkParameters = link.group(0).split("=")[1].split("\\(")[1].split("\\)")[0].split("\\,");
		}

		if (traversal.equals("profondeur")){
			for (String s : linkParameters){
				System.out.println(s);
				this.depthFirstTraversal("Barbara", sommetsVisites, s, 1);
				System.out.println(sommetsVisites);
			}
		} else {
			for (String s : linkParameters){
				System.out.println(this.breadthFirstTraversal("Barbara", 1, s));
			}
		}	
		return sommetsVisites;
	}

	public Set<Vertex> getAdjVerticesOfVertex(Vertex v) {
		return v.getChildren();
	}

	public Set<Vertex> getAdjVertices() {
		return this.vertices;
	}

	@Override
	public String toString() {
		return " " + vertices;
	}
}