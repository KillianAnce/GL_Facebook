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
}
