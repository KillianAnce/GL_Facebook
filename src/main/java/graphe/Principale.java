package graphe;

public class Principale {

	public static void main (String[] args) {
		Graphe g = new Graphe();
		Vertex v = new Vertex("Thomas");
		Vertex v1 = new Vertex("E");
		Vertex v2 = new Vertex("B");
		Vertex v3 = new Vertex("C");
		Vertex v4 = new Vertex("D");
		Vertex v5 = new Vertex("G");
		g.addVertex(v);
		g.addVertex(v1);
		g.addVertex(v2);
		g.addVertex(v3);
		g.addVertex(v4);
		g.addVertex(v5);
		g.addSingleEdge(v, v1);
		g.addSingleEdge(v1, v2);
		g.addSingleEdge(v2, v5);
		g.addSingleEdge(v1, v3);
		g.addSingleEdge(v3, v4);
		System.out.println(g.depthFirstTraversal(v));
		System.out.println(g.breadthFirstTraversal(v));
		System.out.println(v2.getParents());
	}
}
