package miage.graph.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Graph {

	private Set<Vertex> vertices;

	public Graph() {
		this.vertices = new HashSet<>();
	}

	/**
	 * Ajoute un sommet au graphe
	 * 
	 * @param vertex
	 * @return
	 */
	public Vertex addVertex(Vertex vertex) {
		Vertex contenu = getVertex(vertex.getLabel());
		if (contenu == null) {
			vertices.add(vertex);
		}
		return contenu;
	}

	/**
	 * Récupère un sommet à partir de son nom
	 * 
	 * @param label nom du sommet désiré
	 * @return
	 */
	public Vertex getVertex(String label) {
		for (Vertex vertex : vertices) {
			if (vertex.getLabel().equals(label)) {
				return vertex;
			}
		}
		return null;
	}

	/**
	 * Méthode ajoutant un lien entre 2 sommets
	 * 
	 * @param source      Sommet de départ
	 * @param destination Sommet d'arrive
	 * @param direction   Direction du lien (>,<>)
	 * @param properties  Propriétés entre les 2 sommets
	 * @param relation    Type de relation (friend, likes,...)
	 */
	public void addSingleEdge(Vertex source, Vertex destination, String direction, List<LinkProperties> properties,
			String relation) {
		source.setParentChildren(destination);
		destination.setLink(new Link(source, direction, properties, relation, destination));
	}

	/**
	 * Méthode ajoutant 2 sommets lorsque il y a une relation mutuelle en les 2
	 * 
	 * @param Vertex1    Sommet de départ dans un sens et d'arrivé dans l'autre
	 * @param Vertex2    Sommet d'arrive dans un sens et de départ dans l'autre
	 * @param direction  Direction du lien (>,<>)
	 * @param properties Propriétés entre les 2 sommets
	 * @param relation   Type de relation (friend, likes,...)
	 */
	public void addMutualEdge(Vertex v1, Vertex v2, String direction, List<LinkProperties> properties,
			String relation) {
		v1.setParentChildren(v2);
		v2.setParentChildren(v1);
		v1.setLink(new Link(v2, direction, properties, relation, v1));
		v2.setLink(new Link(v1, direction, properties, relation, v2));
	}

	/**
	 * 
	 * @param linkParameters
	 * @param relation
	 * @param index
	 * @return
	 */
	public String[] matcher(String[] linkParameters, String[] relation, int index) {
		if (Pattern.compile("([A-Za-z]+ (<|>))").matcher(relation[index]).find()) {
			linkParameters = relation[index].split(" ");
		}
		if (Pattern.compile("^([A-Za-z]+)$").matcher(relation[index]).find()) {
			linkParameters[0] = relation[index];
		}
		return linkParameters;
	}

	/**
	 * Méthode de largueur d'abord
	 * 
	 * @param root     Sommet de départ
	 * @param visited  L'ensemble des sommets qui sont visités par l'algorithme
	 * @param relation Type de relation(friend,likes,...) ainsi que le sens de
	 *                 chaque relation
	 * @param level    Niveau de largeur
	 * @return
	 */
	public Set<String> breadthFirstTraversal(String root, Set<String> visited, String[] relation, int level) {
		Queue<String> queue = new LinkedList<>();
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
				for (Vertex v : this.getAdjVerticesOfVertex(this.getVertex(vertex), linkParameters[1],
						linkParameters[0])) {
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

	/**
	 * Méthode du parcours en profondeur d'abord
	 * 
	 * @param root     Sommet de départ
	 * @param visited  L'ensemble des sommets qui sont visités par l'algorithme
	 * @param relation Le type de relation qu
	 * @param level    Niveau de profondeur demandé
	 * @param idx
	 * @return
	 */
	public Set<String> depthFirstTraversal(String root, Set<String> visited, String[] relation, int level, int idx) {
		String[] linkParameters = { null, "<>" };
		linkParameters = matcher(linkParameters, relation, idx);
		visited.add(this.getVertex(root).getLabel());
		Iterator<Vertex> i = this.getAdjVerticesOfVertex(this.getVertex(root), linkParameters[1], linkParameters[0])
				.iterator();
		while (i.hasNext() && level > 0) {
			Vertex v = i.next();
			String suivant = v.getLabel();
			int lx = idx;
			int l = level - 1;
			if (lx < relation.length - 1) {
				lx++;
			}
			depthFirstTraversal(suivant, visited, relation, l, lx);
		}
		return visited;
	}

	/**
	 * Méthode de recherche permettant de
	 * 
	 * @param start
	 * @param search
	 * @return
	 */
	public Set<String> search(String start, String search) {
		Matcher mode = Pattern.compile("(mode=[A-Za-z]+)").matcher(search);
		String traversal = "";
		Matcher level = Pattern.compile("(niveau=[0-9]+)").matcher(search);
		int levelTraversal = this.getAdjVertices().size();
		Matcher link = Pattern.compile("(liens=\\([a-z]+( (<|<>|>))?((\\,?[a-z]+( (<|<>|>))?)+)?\\))").matcher(search);
		String[] linkParameters = null;
		Set<String> sommetsVisites = new HashSet<>();

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

	/**
	 * 
	 * @param setVertices
	 * @param startingVertex
	 * @param linkParameter
	 * @return
	 */
	public Set<Vertex> addAdjacentVertex(Set<Vertex> setVertices, Vertex startingVertex, String linkParameter) {
		for (Link link : startingVertex.getLink()) {
			if (link.getRelation().equals(linkParameter)) {
				setVertices.add(link.getSource());
			}
		}
		return setVertices;
	}

	/**
	 * 
	 * @param setVertices
	 * @param startingVertex
	 * @param linkParameter
	 * @return
	 */
	public Set<Vertex> addAdjacentVertexChildren(Set<Vertex> setVertices, Vertex startingVertex, String linkParameter,
			String direction) {

		for (Vertex vertex : startingVertex.getChildren()) {
			for (Link link : vertex.getLink()) {
				if (checkConditionAdjacentVertexChildren(link, direction, startingVertex, linkParameter)) 
					setVertices.add(vertex);
			}
		}
		return setVertices;
	}

	/**
	 * Check si la condition des enfants permet de les ajouter à l'ensemble de sommets
	 * @param link
	 * @param direction
	 * @param startingVertex
	 * @param linkParameter
	 * @return
	 */
	public boolean checkConditionAdjacentVertexChildren(Link link, String direction, Vertex startingVertex, String linkParameter) {
		boolean sameParameter = link.getRelation().equals(linkParameter);
		boolean isTrue = false;
		if (direction.equals(">")) {
			if (link.getSource() == startingVertex && sameParameter)
				isTrue = true;
		} else {
			if (sameParameter) {
				isTrue = true;
			}
		}
		return isTrue;
	}

	/**
	 * Méthode permettant de récupérer les sommets adjacents au sommet passé en
	 * paramètre
	 * 
	 * @param startingVertex sommet de départ
	 * @param direction
	 * @param linkParameter
	 * @return
	 */
	public Set<Vertex> getAdjVerticesOfVertex(Vertex startingVertex, String direction, String linkParameter) {
		Set<Vertex> setVertices = new HashSet<>();

		switch (direction) {
		case ">":
			addAdjacentVertexChildren(setVertices, startingVertex, linkParameter, ">");
			break;
		case "<":
			addAdjacentVertex(setVertices, startingVertex, linkParameter);
			break;
		default:
			addAdjacentVertexChildren(setVertices, startingVertex, linkParameter, "");
			addAdjacentVertex(setVertices, startingVertex, linkParameter);
			break;

		}
		return setVertices;

	}

	/**
	 * Export du graphe en fichier .txt
	 * 
	 * @param filename Nom du fichier pour l'import
	 */
	public void export(String filename) {
		try (PrintWriter export = new PrintWriter(new BufferedWriter(new FileWriter(filename + ".txt")))) {

			export.println(this.toString());
		} catch (IOException e) {
			e.printStackTrace();
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