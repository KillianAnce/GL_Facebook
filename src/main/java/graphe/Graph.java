package graphe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Graph {

	private Set<Vertex> vertices;

	public Graph() {
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
		v1.setLink(new Link(v2, direction, l, relation, v1));
		v2.setLink(new Link(v1, direction, l, relation, v2));
	}

	public Set<String> breadthFirstTraversal(String root, Set<String> visited, String[] relation, int l) {
		Queue<String> queue = new LinkedList<String>();
		queue.add(root);
		visited.add(root);
		int i = 1;
		int k = 0;
		while (!queue.isEmpty() && i <= l) {
			int size = queue.size();
			for (int j = 1; j <= size; j++) {
				String vertex = queue.poll();
				String[] linkParameters = { null, null };
				String direction = ">";
				if (Pattern.compile("([A-Za-z]+ (<|<>|>))").matcher(relation[k]).find()) {
					linkParameters = relation[k].split(" ");
					direction = linkParameters[1];
				}
				if (Pattern.compile("^([A-Za-z]+)$").matcher(relation[k]).find()) {
					linkParameters[0] = relation[k];
					direction = ">";
				}
				for (Vertex v : this.getAdjVerticesOfVertex(this.getVertex(vertex), direction, linkParameters[0])) {
					setQueue(v, visited, queue);
				}
			}
			if (k < relation.length - 1) {
				k++;
			}
			i++;
		}
		return visited;
	}

	// à faire
	public Set<String> depthFirstTraversal(String root, Set<String> sommetsVisites, String[] relation, int level, int idx) {
		String[] linkParameters = { null, null };
		String direction = ">";
		if (Pattern.compile("([A-Za-z]+ (<|<>|>))").matcher(relation[idx]).find()) {
			linkParameters = relation[idx].split(" ");
			direction = linkParameters[1];
		}
		if (Pattern.compile("^([A-Za-z]+)$").matcher(relation[idx]).find()) {
			linkParameters[0] = relation[idx];
			direction = ">";
		}
		sommetsVisites.add(this.getVertex(root).getLabel());
		Iterator<Vertex> i = this.getAdjVerticesOfVertex(this.getVertex(root), direction, linkParameters[0]).iterator();
		while (i.hasNext() && level > 0) {
			Vertex v = i.next();
			String suivant = v.getLabel();
			if (!sommetsVisites.contains(suivant)) {
				int lx = idx;
				if (direction.equals("<")){
					for (Link link : this.getVertex(root).getLink()) {
						if (link.getRelation().equals(linkParameters[0])) {
							int l = level - 1;
							if (lx < relation.length - 1) {
								lx++;
							}
							depthFirstTraversal(suivant, sommetsVisites, relation, l, lx);
						}
					}
				} else {
					for (Link link : v.getLink()) {
						if (link.getRelation().equals(linkParameters[0])) {
							int l = level - 1;
							if (lx < relation.length - 1) {
								lx++;
							}
							depthFirstTraversal(suivant, sommetsVisites, relation, l, lx);
						}
					}
				}
			}
		}
		return sommetsVisites;
	}

	public void setQueue(Vertex v, Set<String> visited, Queue<String> queue) {
		if (!visited.contains(v.getLabel())) {
			visited.add(v.getLabel());
			queue.add(v.getLabel());
		}
	}

	public Set<String> search(String start, String search) {
		Matcher mode = Pattern.compile("(mode=[A-Za-z]+)").matcher(search);
		String traversal = null;
		Matcher level = Pattern.compile("(niveau=[0-9]+)").matcher(search);
		int levelTraversal = 0;
		Matcher link = Pattern.compile("(liens=\\([a-z]+( (<|<>|>))?((\\,?[a-z]+( (<|<>|>))?)+)?\\))").matcher(search);
		String[] linkParameters = null;
		Set<String> sommetsVisites = new HashSet<String>();

		if (mode.find()) {
			traversal = mode.group(0).split("=")[1];
		}
		if (level.find()) {
			levelTraversal = Integer.parseInt(level.group(0).split("=")[1]);
		}
		if (link.find()) {
			linkParameters = link.group(0).split("=")[1].split("\\(")[1].split("\\)")[0].split("\\,");
		}

		if (traversal.equals("profondeur")) {
			this.depthFirstTraversal(start, sommetsVisites, linkParameters, levelTraversal, 0);
		} else {
			this.breadthFirstTraversal(start, sommetsVisites, linkParameters, levelTraversal);
		}
		return sommetsVisites;
	}

	public Set<Vertex> getAdjVerticesOfVertex(Vertex v, String direction, String linkParameter) {
		Set<Vertex> vertices = new HashSet<Vertex>();
		if (direction.equals(">") || direction == null) {
			for (Vertex vertex : v.getChildren()) {
				for (Link link : vertex.getLink()) {
					if (link.getSource() == v && link.getRelation().equals(linkParameter)) {
						vertices.add(vertex);
					}
				}
			}
			return vertices;
		} else if (direction.equals("<")) {
			for (Link link : v.getLink()) {
				if (link.getRelation().equals(linkParameter)) {
					vertices.add(link.getSource());
				}
			}
			return vertices;
		} else {
			for (Vertex vertexC : v.getChildren()) {
				for (Link l : vertexC.getLink()) {
					if (l.getSource() == v && l.getDirection().equals("<>")) {
						vertices.add(vertexC);
					}
				}
			}
			return vertices;
		}
	}

	public Set<Vertex> getAdjVertices() {
		return this.vertices;
	}

	@Override
	public String toString() {
		return " " + vertices;
	}
}