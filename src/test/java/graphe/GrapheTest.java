package graphe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GrapheTest {

	@Test
	public void grapheEmpty() {
		Graph g = new Graph();
		assertThat(g.getAdjVertices().size(), is(0));
	}

	@Test
	public void grapheNotEmpty() {
		Graph g = new Graph();
		Vertex v = new Vertex("Thomas");
		g.addVertex(v);
		assertThat(g.getAdjVertices().size(), is(1));
	}

	@Test
	public void nodeWithoutRelation() {
		Graph g = new Graph();
		Vertex v = new Vertex("Thomas");
		Vertex v1 = new Vertex("Killian");
		g.addVertex(v);
		g.addVertex(v1);
		assertEquals(g.getAdjVerticesOfVertex(v, ">", "friend").contains(v1), false);
		assertEquals(g.getAdjVerticesOfVertex(v1, ">", "friend").contains(v), false);
	}

	@Test
	public void nodeWithSimpleRelation() {
		Graph g = new Graph();
		Vertex v = new Vertex("Thomas");
		Vertex v1 = new Vertex("Killian");
		g.addVertex(v);
		g.addVertex(v1);
		g.addSingleEdge(v, v1, ">", null, "friend");
		assertEquals(g.getAdjVerticesOfVertex(v, ">", "friend").contains(v1), true);
		assertEquals(g.getAdjVerticesOfVertex(v1, ">", "friend").contains(v), false);
	}

	@Test
	public void nodeWithMutualRelation() {
		Graph g = new Graph();
		Vertex v = new Vertex("Thomas");
		Vertex v1 = new Vertex("Killian");
		g.addVertex(v);
		g.addVertex(v1);
		g.addMutualEdge(v, v1, "<>", null, "friend");
		assertEquals(g.getAdjVerticesOfVertex(v, ">", "friend").contains(v1), true);
		assertEquals(g.getAdjVerticesOfVertex(v1, ">", "friend").contains(v), true);
	}
}
