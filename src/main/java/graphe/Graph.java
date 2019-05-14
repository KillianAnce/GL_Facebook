package graphe;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

	public String[] matcher(String[] linkParameters, String[] relation, int index) {
		if (Pattern.compile("([A-Za-z]+ (<|>))").matcher(relation[index]).find()) {
			linkParameters = relation[index].split(" ");
		}
		if (Pattern.compile("^([A-Za-z]+)$").matcher(relation[index]).find()) {
			linkParameters[0] = relation[index];
		}
		return linkParameters;
	}
	
	public Set<String> breadthFirstTraversal(String root, Set<String> visited, String[] relation, int level) {
		Queue<String> queue = new LinkedList<String>();
		queue.add(root);
		visited.add(root);
		int iterator = 1;
		int index = 0;
		while (!queue.isEmpty() && iterator <= level) {
			int size = queue.size();
			for (int j = 1; j <= size; j++) {
				String vertex = queue.poll();
				String[] linkParameters = { null, "<>" };
				linkParameters = matcher(linkParameters, relation, index);
				for (Vertex v : this.getAdjVerticesOfVertex(this.getVertex(vertex), linkParameters[1], linkParameters[0])) {
					visited.add(v.getLabel());
					queue.add(v.getLabel());
				}
			}
			if (index < relation.length - 1) {
				index++;
			}
			iterator++;
		}
		return visited;
	}

	public Set<String> depthFirstTraversal(String root, Set<String> sommetsVisites, String[] relation, int level, int idx) {
		String[] linkParameters = { null, "<>" };
		linkParameters = matcher(linkParameters, relation, idx);
		sommetsVisites.add(this.getVertex(root).getLabel());
		Iterator<Vertex> i = this.getAdjVerticesOfVertex(this.getVertex(root), linkParameters[1], linkParameters[0]).iterator();
		while (i.hasNext() && level > 0) {
			Vertex v = i.next();
			String suivant = v.getLabel();
			int lx = idx;
			int l = level - 1;
			if (lx < relation.length - 1) {
				lx++;
			}
			depthFirstTraversal(suivant, sommetsVisites, relation, l, lx);
		}
		return sommetsVisites;
	}

	public Set<String> search(String start, String search) {
		Matcher mode = Pattern.compile("(mode=[A-Za-z]+)").matcher(search);
		String traversal = null;
		Matcher level = Pattern.compile("(niveau=[0-9]+)").matcher(search);
		int levelTraversal = this.getAdjVertices().size();
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
					if (l.getRelation().equals(linkParameter)) {
						vertices.add(vertexC);
					}
				}
			}
			for (Link l : v.getLink()) {
				if (l.getRelation().equals(linkParameter)) {
					vertices.add(l.getSource());
				}
			}
			return vertices;
		}
	}
	
	public void export(String filename) {
		try {
			@SuppressWarnings("resource")
			PrintWriter export =  new PrintWriter(new BufferedWriter
				   (new FileWriter(filename + ".txt")));
			export.println(this.toString());
			export.close();
		} catch (Exception e) {
			
		}
	}

	public Set<Vertex> getAdjVertices() {
		return this.vertices;
	}

	@Override
	public String toString() {
		return "" + vertices.toString().replace("[", "").replace("]", "").replace(", ", "").trim();
	}
}