package reader;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import graphe.Graphe;
import graphe.Vertex;

class Traversal  {
	
	private static Graphe graph;
	

	
	@BeforeAll
    public static void setUp() throws Exception {
		Reader reader = new Reader("src/main/resources/facebook.txt");
		graph = reader.read();

    }
	
	
	/**
	 * Check if a 
	 */
	@Test
	public void DescendantFriendshipLevel1() {
		assertEquals(graph.breadthFirstTraversal("Barbara",1, "friend").contains("Carol"), true);
	}
	
	@Test
	public void DescendantFriendshipLevel2() {
		assertEquals(graph.breadthFirstTraversal("Barbara", 2, "friend").contains("Dawn"), true);
		assertEquals(graph.breadthFirstTraversal("Barbara", 2, "friend").contains("Jill"), true);
	}
	
	@Test
	public void DescendantFriendshipLevel3() {
		assertEquals(graph.breadthFirstTraversal("Barbara", 3, "friend").contains("Jill"), true);
	}
	
	@Test
	public void NotDescendantFriendship() {
		assertEquals(graph.breadthFirstTraversal("Barbara", 2, "friend").contains("testPerson"), false);
	}
	
	
	
	
	

//	@Test
//	public void NoDescendantFriendship() {
//		Graphe g = new Graphe();
//		Vertex v = new Vertex("Barbara");
//		Vertex v1 = new Vertex("Anna");
//		Vertex v2 = new Vertex("Dawn");
//		g.addVertex(v);
//		g.addVertex(v1);
//		g.addVertex(v2);
//		g.addSingleEdge(v, v1, ">", null, "friend");
//		assertEquals(g.breadthFirstTraversal("Barbara", 3, "friend").contains("Dawn"), false);
//	}
//
//	@Test
//	public void readerCorrect() throws IOException {
//		Reader r = new Reader("src/test/java/reader/facebook.txt");
//		Graphe g = r.read();
//		assertEquals(g.breadthFirstTraversal("Barbara", 1, "friend").contains("Anna"), true);
//		for (Link l : g.getVertex("BigCO").getLink()){
//			assertEquals(l.getRelation(), "employeof");
//		}
//		assertEquals(g.getAdjVertices().size(), 13);
//	}
	
}
