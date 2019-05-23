package miage.graph.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import miage.graph.model.Graph;
import miage.graph.model.Hired;
import miage.graph.model.LinkProperties;
import miage.graph.model.Role;
import miage.graph.model.Shared;
import miage.graph.model.Since;
import miage.graph.model.Vertex;

public class Reader {
	
	private final static Logger LOGGER = Logger.getLogger(Reader.class.getName());

	private Reader() {
	}

	/**
	 * Méthode de création de 2 sommets qui correspondent à 1 ligne du fichier
	 * d'import
	 * 
	 * @param graph    Instance de la classe Graph représentant le graphe
	 *                 entièrement
	 * @param vertices Les 2 noms sommets que l'on va créer
	 * @return
	 */
	private static ArrayList<Vertex> vertexCreation(Graph graph, String[] vertices) {
		ArrayList<Vertex> listVertex = new ArrayList<>();
		Vertex vertex1 = new Vertex(vertices[0]);
		Vertex vertex2 = new Vertex(vertices[vertices.length - 1]);
		Vertex tmp = graph.addVertex(vertex1);
		Vertex tmp2 = graph.addVertex(vertex2);
		vertex1 = (tmp == null) ? vertex1 : tmp;
		vertex2 = (tmp2 == null) ? vertex2 : tmp2;
		listVertex.add(vertex1);
		listVertex.add(vertex2);
		return listVertex;
	}

	/**
	 * Méthode permettant de créer les différentes propriétés d'un lien donné
	 * 
	 * @param graph    Instance de la classe Graph représentant le graphe
	 *                 entièrement
	 * @param vertex Liste contenant les 2 sommets
	 * @param vertices Les 2 noms sommets que l'on va créer
	 */
	private static void linkCreation(Graph graph, ArrayList<Vertex> vertex, String[] vertices) {

		String relation = vertices[1];
		String[] link = vertices[vertices.length - 2].split("--");
		String direction = link[1];
		LinkProperties o = null;
		String[] linkProperties = link[0].split(";");
		ArrayList<LinkProperties> properties = new ArrayList<>();
		for (String s : linkProperties) {
			String[] el = s.split("=");
			switch (el[0]) {
			case "shared":
				o = new Shared();
				String[] values = el[1].split(",");
				for (String value : values) {
					((Shared) o).setShared(value);
				}
				break;
			case "since":
				o = new Since(el[1]);
				break;
			case "role":
				o = new Role(el[1]);
				break;
			case "hired":
				o = new Hired(el[1]);
				break;
			default:
			}
			properties.add(o);
		}
		addEdge(graph, vertex, direction, properties, relation);
	}

	/**
	 * Ajout de lien(s) entre les 2 sommets
	 * 
	 * @param graph      graphe représentant le fichier
	 * @param vertex     Liste contenant les 2 sommets
	 * @param direction  Concerne la direction du lien (>,<>)
	 * @param properties Liste des propriétés entre ces 2 sommets
	 * @param relation   Type de relation du lien
	 */
	private static void addEdge(Graph graph, ArrayList<Vertex> vertex, String direction, ArrayList<LinkProperties> properties,
			String relation) {
		
		if (direction.equals(">")) {
			graph.addSingleEdge(vertex.get(0), vertex.get(1), direction, properties, relation);
		} else {
			graph.addMutualEdge(vertex.get(0), vertex.get(1), direction, properties, relation);
		}	
	}

	/**
	 * Méthode permettant de lire un fichier donné en entrée pour le transformer en
	 * graphe
	 * 
	 * @return retourne le graphe représentant le fichier passé en paramètre
	 * @throws IOException
	 */
	public static Graph read(String filename) throws IOException {
		Pattern pattern = Pattern.compile("^[A-Za-z]+\\:[A-Za-z_]+\\:((([A-Za-z]+(\\=([A-Za-z]+|[0-9]+|[A-Za-z]+ ?[0-9]+|([A-Za-z0-9]+\\,?)+))?)(\\;)?)+)?--(>|<>)\\:[A-Za-z]+");
		Graph graph = new Graph();
		String line = "";
		try (BufferedReader bufferReader = new BufferedReader(new FileReader(filename))) {
			while ((line = bufferReader.readLine()) != null) {
				if (pattern.matcher(line) != null) {
					String[] vertices = line.split(":");
					ArrayList<Vertex> vertex = vertexCreation(graph, vertices);
					linkCreation(graph, vertex, vertices);
				} 
			}
		} catch (FileNotFoundException fnfe) {
			LOGGER.severe(fnfe.getMessage());
		}
		return graph;
	}
}