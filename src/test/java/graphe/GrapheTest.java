package graphe;

import static org.junit.jupiter.api.Assertions.assertEquals; 

import java.io.IOException;

import org.junit.jupiter.api.Test;

import reader.Reader;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GrapheTest {

	@Test
	public void grapheEmpty() {
		Graphe g = new Graphe();
		assertThat(g.getAdjVertices().size(), is(0));
	}

	@Test
	public void grapheNotEmpty() {
		Graphe g = new Graphe();
		Vertex v = new Vertex("Thomas");
		g.addVertex(v);
		assertThat(g.getAdjVertices().size(), is(1));
	}

	@Test
	public void nodeWithoutRelation() {
		Graphe g = new Graphe();
		Vertex v = new Vertex("Thomas");
		Vertex v1 = new Vertex("Killian");
		g.addVertex(v);
		g.addVertex(v1);
		assertEquals(g.getAdjVerticesOfVertex(v).contains(v1), false);
		assertEquals(g.getAdjVerticesOfVertex(v1).contains(v), false);
	}

	@Test
	public void nodeWithSimpleRelation() {
		Graphe g = new Graphe();
		Vertex v = new Vertex("Thomas");
		Vertex v1 = new Vertex("Killian");
		g.addVertex(v);
		g.addVertex(v1);
		g.addSingleEdge(v, v1, ">", null, "friend");
		assertEquals(g.getAdjVerticesOfVertex(v).contains(v1), true);
		assertEquals(g.getAdjVerticesOfVertex(v1).contains(v), false);
	}

	@Test
	public void nodeWithMutualRelation() {
		Graphe g = new Graphe();
		Vertex v = new Vertex("Thomas");
		Vertex v1 = new Vertex("Killian");
		g.addVertex(v);
		g.addVertex(v1);
		g.addMutualEdge(v, v1, "<>", null, "friend");
		assertEquals(g.getAdjVerticesOfVertex(v).contains(v1), true);
		assertEquals(g.getAdjVerticesOfVertex(v1).contains(v), true);
	}

	@Test
	public void DescendantFriendship() {
		Graphe g = new Graphe();
		Vertex v = new Vertex("Barbara");
		Vertex v1 = new Vertex("Carole");
		Vertex v2 = new Vertex("Dawn");
		g.addVertex(v);
		g.addVertex(v1);
		g.addVertex(v2);
		g.addSingleEdge(v, v1, ">", null, "friend");
		g.addSingleEdge(v1, v2, ">", null, "friend");
		assertEquals(g.depthFirstTraversal("Barbara", 2, "friend").contains("Dawn"), true);
	}

	@Test
	public void NoDescendantFriendship() {
		Graphe g = new Graphe();
		Vertex v = new Vertex("Barbara");
		Vertex v1 = new Vertex("Anna");
		Vertex v2 = new Vertex("Dawn");
		g.addVertex(v);
		g.addVertex(v1);
		g.addVertex(v2);
		g.addSingleEdge(v, v1, ">", null, "friend");
		assertEquals(g.breadthFirstTraversal("Barbara", 3, "friend").contains("Dawn"), false);
	}

	@Test
	public void readerCorrect() throws IOException {
		Reader r = new Reader("src/test/java/reader/facebook.txt");
		Graphe g = r.read();
		assertEquals(g.breadthFirstTraversal("Barbara", 1, "friend").contains("Anna"), true);
		for (Link l : g.getVertex("BigCO").getLink()){
			assertEquals(l.getRelation(), "employeof");
		}
		assertEquals(g.getAdjVertices().size(), 13);
	}
}
