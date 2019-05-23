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
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import miage.graph.utils.Reader;

public class Graph {

	private final static Logger LOGGER = Logger.getLogger(Reader.class.getName());
	private Set<Vertex> vertices;

	public Graph() {
		this.vertices = new HashSet<>();
	}

	/**
	 * Ajoute un sommet au graphe
	 * 
	 * @param vertex noeud
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
	 * R�cup�re un sommet � partir de son nom
	 * 
	 * @param label nom du sommet d�sir�
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
	 * Permet de renommer un sommet uniquement si le nom choisi n'est pas d�j�
	 * existant
	 * 
	 * @param name    Nom du sommet � remplacer
	 * @param newName Nouveau nom
	 */
	public void renameVertex(String name, String newName) {
		if (!this.vertices.contains(this.getVertex(newName))) {
			this.getVertex(name).setLabel(newName);
		}
	}

	public void modifyRelation(String vertexSource, String vertexDestination, String newRelation) {
		this.getVertex(vertexSource).getLinkVertex(vertexDestination).setRelation(newRelation);
	}

	/**
	 * Ajout de propri�t�s pour un lien
	 * 
	 * @param source      Noeud source
	 * @param destination Noeud de destination
	 * @param properties  Tableau de propri�t�s � ajouter
	 */
	public void addProperties(String source, String destination, Set<String> properties) {
		Link link = this.getVertex(source).getLinkVertex(destination);
		link.addProperties(properties);
	}

	/**
	 * M�thode ajoutant un lien entre 2 sommets
	 * 
	 * @param source      Sommet de d�part
	 * @param destination Sommet d'arrive
	 * @param direction   Direction du lien (>,<>)
	 * @param properties  Propri�t�s entre les 2 sommets
	 * @param relation    Type de relation (friend, likes,...)
	 */
	public void addSingleEdge(Vertex source, Vertex destination, String direction, List<LinkProperties> properties,
			String relation) {
		source.setParentChildren(destination);
		source.setLink(new Link(source, direction, properties, relation, destination));
	}

	/**
	 * M�thode pour supprimer un sommet Doit d'abord supprimer les liens qui pointe
	 * sur lui Doit ensuite supprimer ses liens Doit enfin supprimer le sommet de la
	 * liste des sommets du graphe
	 * 
	 * @param vertex Sommet qui doit etre supprim�
	 */
	public boolean removeVertex(String vertex) {
		boolean success = false;
		try {
			for (Vertex parent : this.getVertex(vertex).getParents()) {
				parent.getLink().remove(parent.getLinkVertex(vertex));
			}
			this.getVertex(vertex).getLink().clear();
			this.getVertices().remove(this.getVertex(vertex));
			success = true;
		} catch (Exception e) {
			success = false;
		}
		return success;
	}

	/**
	 * M�thode de suppression de lien entre 2 sommets
	 * 
	 * @param vertexSource      Sommet source
	 * @param vertexDestination Sommet de destination
	 */
	public boolean removeLink(String vertexSource, String vertexDestination) {
		boolean success = false;
		try {
			this.getVertex(vertexSource).getLink()
					.remove(this.getVertex(vertexSource).getLinkVertex(vertexDestination));
		} catch (Exception e) {

		}
		return success;
	}

	/**
	 * M�thode ajoutant 2 sommets lorsque il y a une relation mutuelle en les 2
	 * 
	 * @param Vertex1    Sommet de d�part dans un sens et d'arriv� dans l'autre
	 * @param Vertex2    Sommet d'arrive dans un sens et de d�part dans l'autre
	 * @param direction  Direction du lien (>,<>)
	 * @param properties Propri�t�s entre les 2 sommets
	 * @param relation   Type de relation (friend, likes,...)
	 */
	public void addMutualEdge(Vertex v1, Vertex v2, String direction, List<LinkProperties> properties,
			String relation) {
		v1.setParentChildren(v2);
		v2.setParentChildren(v1);
		v1.setLink(new Link(v1, direction, properties, relation, v2));
		v2.setLink(new Link(v2, direction, properties, relation, v1));
	}

	/**
	 * 
	 * Permet de matcher les relations Le premier pour les relations de type name +
	 * (<|>) Exemple : friend >
	 * 
	 * Le deuxi�me pour les relations sans indication de sens
	 * 
	 * 
	 * @param linkParameters
	 * @param relation       relation entre les deux individus (friend par exemple)
	 * @param index          index du tableau
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
	 * M�thode de largueur d'abord
	 * 
	 * @param root     Sommet de d�part
	 * @param visited  L'ensemble des sommets qui sont visit�s par l'algorithme
	 * @param relation Type de relation(friend,likes,...) ainsi que le sens de
	 *                 chaque relation
	 * @param level    Niveau de largeur
	 * @return
	 */
	public Set<String> breadthFirstTraversal(String root, Set<String> visited, String[] relation, int level,
			Set<String> filters) {
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
						linkParameters[0], filters)) {
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
	 * M�thode du parcours en profondeur d'abord
	 * 
	 * @param root     Sommet de d�part
	 * @param visited  L'ensemble des sommets qui sont visit�s par l'algorithme
	 * @param relation Le type de relation qu
	 * @param level    Niveau de profondeur demand�
	 * @param index    Index du tableau
	 * @param newIndex Nouvel index au moment de l'appel r�cursif
	 * @param newLevel D�crementation du niveau
	 * @return
	 */
	public Set<String> depthFirstTraversal(String root, Set<String> visited, String[] relation, int level, int index,
			Set<String> filters) {
		String[] linkParameters = { null, "<>" };
		linkParameters = matcher(linkParameters, relation, index);
		visited.add(this.getVertex(root).getLabel());
		Iterator<Vertex> i = this
				.getAdjVerticesOfVertex(this.getVertex(root), linkParameters[1], linkParameters[0], filters).iterator();
		while (i.hasNext() && level > 0) {
			Vertex v = i.next();
			String suivant = v.getLabel();
			int newIndex = index;
			int newLevel = level - 1;
			if (newIndex < relation.length - 1) {
				newIndex++;
			}
			depthFirstTraversal(suivant, visited, relation, newLevel, newIndex, filters);
		}
		return visited;
	}

	/**
	 * M�thode de recherche permettant de rechercher des elements � partir de
	 * crit�res d�finis : - niveau - liens - mode de parcours
	 * 
	 * @param start   Noeud de d�part de la recherche
	 * @param search  La recherche sous forme d'une chaine de caract�re
	 * @param filters Tableau de filtres
	 * @return
	 */
	public Set<String> search(String start, String search, Set<String> filters) {
		Matcher mode = Pattern.compile("(mode=[A-Za-z]+)").matcher(search);
		String traversal = "";
		Matcher level = Pattern.compile("(niveau=[0-9]+)").matcher(search);
		int levelTraversal = this.getVertices().size();
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
			this.depthFirstTraversal(start, sommetsVisites, linkParameters, levelTraversal, 0, filters);
		} else {
			this.breadthFirstTraversal(start, sommetsVisites, linkParameters, levelTraversal, filters);
		}
		return sommetsVisites;
	}

	/**
	 * 
	 * Permet de r�cup�rer les noeuds parents du noeud courant tout en satisfaisant
	 * les contraintes de filtres, parametres
	 * 
	 * @param setVertices    Tableau qui contient les sommets visit�s
	 * @param startingVertex Noeud de d�part � partir duquel on r�cup�re les liens
	 * @param linkParameter  Param�tres de liens
	 * @return
	 */
	public Set<Vertex> getAdjacentVertexParent(Set<Vertex> setVertices, Vertex startingVertex, String linkParameter,
			Set<String> filters) {

		for (Vertex vertex : startingVertex.getParents()) {
			for (Link link : vertex.getLink()) {
				if (link.getRelation().equals(linkParameter)
						&& (filters == null || Filter.checkFilters(link, filters))) {
					setVertices.add(link.getSource());
				}
			}
		}
		return setVertices;
	}

	/**
	 * 
	 * Permet de r�cup�rer les noeuds fils
	 * 
	 * @param setVertices    Tableau qui contient les sommets visit�s
	 * @param startingVertex Noeud de d�part � partir duquel on r�cup�re les liens
	 * @param linkParameter  Param�tres de liens
	 * @return
	 */
	public Set<Vertex> getAdjacentVertexChildren(Set<Vertex> setVertices, Vertex startingVertex, String linkParameter,
			String direction, Set<String> filters) {

		for (Link link : startingVertex.getLink()) {
			if (checkConditionAdjacentVertexChildren(link, direction, startingVertex, linkParameter)) {
				if (filters == null) {
					setVertices.add(link.getDestination());
				} else {
					if (Filter.checkFilters(link, filters)) {
						setVertices.add(link.getDestination());
					}
				}
			}
		}
		return setVertices;
	}

	/**
	 * V�rifie si la condition des enfants permet de les ajouter � l'ensemble de
	 * sommets
	 * 
	 * @param link           Lien entre les deux sommets
	 * @param direction      Sens du lien
	 * @param startingVertex Sommet de d�part
	 * @param linkParameter  L'intitul� du lien
	 * @return
	 */
	public boolean checkConditionAdjacentVertexChildren(Link link, String direction, Vertex startingVertex,
			String linkParameter) {
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
	 * M�thode permettant de r�cup�rer les sommets adjacents au sommet pass� en
	 * param�tre
	 * 
	 * @param startingVertex sommet de d�part
	 * @param direction      sens du lien
	 * @param linkParameter  l'intitul� du lien
	 * @param filters        tableau de filtres exemple : since=2000
	 * @return
	 */
	public Set<Vertex> getAdjVerticesOfVertex(Vertex startingVertex, String direction, String linkParameter,
			Set<String> filters) {
		Set<Vertex> setVertices = new HashSet<>();

		switch (direction) {
		case ">":
			getAdjacentVertexChildren(setVertices, startingVertex, linkParameter, ">", filters);
			break;
		case "<":
			getAdjacentVertexParent(setVertices, startingVertex, linkParameter, filters);
			break;
		default:
			getAdjacentVertexChildren(setVertices, startingVertex, linkParameter, "", filters);
			getAdjacentVertexParent(setVertices, startingVertex, linkParameter, filters);
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
			LOGGER.severe("Erreur lors de l'export du fichier");
		}
	}

	public Set<Vertex> getVertices() {
		return this.vertices;
	}

	@Override
	public String toString() {
		return "" + vertices.toString().replace("[", "").replace("]", "").replace(", ", "").trim();
	}
}