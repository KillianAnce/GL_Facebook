package graphe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import reader.Reader;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GraphTest {

	private Graph graph;
	private Graph g;
	
	@BeforeEach
	public void setUp() throws Exception {
		Reader reader = new Reader("src/main/resources/facebook.txt");
		graph = reader.read();
		g = new Graph();
	}
	
	@Test
	public void grapheEmpty() {
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
	
	@Test
	public void checkPropertiesSince() {
		assertEquals("[Since : 2000]",
				graph.getVertex("Anna").getLink().get(0).getLinkProperties().toString());
	}
	
	@Test
	public void checkPropertiesRole() {
		assertEquals("Role : secretaire", 
				graph.getVertex("BigCO").getLink().get(1).getLinkProperties().get(0).toString());
	}
	
	@Test
	public void checkPropertiesHired() {
		assertEquals("Hired : 2000", 
				graph.getVertex("BigCO").getLink().get(1).getLinkProperties().get(1).toString());
	}
	
	@Test
	public void addPropertiesWhichExists() {
		graph.getVertex("BigCO").getLink().get(1).setLinkProperties(new Hired("2000"));
		int count = (int) graph.getVertex("BigCO").getLink().get(1).getLinkProperties()
				.stream()
				.filter(e -> "Hired : 2000".equals(e.toString())).count();
		assertThat(count, is(1));
	}
	
	@Test
	public void checkPropertiesShared() {
		assertEquals("[Shared : [books, music]]",
				graph.getVertex("Dawn").getLink().get(0).getLinkProperties().toString());
	}
	
	@Test
	public void addPropertyToNode() {
		graph.getVertex("BigCO").getLink().get(0).setLinkProperties(new Hired("2000"));
		assertEquals("[Hired : 2000]",graph.getVertex("BigCO").getLink().get(0).getLinkProperties().toString());
	}
	
	@Test
	public void addPropertiesToNode() {
		graph.getVertex("Jack").getLink().get(0).setLinkProperties(new Hired("2000"));
		graph.getVertex("Jack").getLink().get(0).setLinkProperties(new Role("Bricoleur"));
		graph.getVertex("Jack").getLink().get(0).setLinkProperties(new Since(2000));
		HashSet<String> set = new HashSet<String>();
		set.add("Sport");
		set.add("Bouffe");
		graph.getVertex("Jack").getLink().get(0).setLinkProperties(new Shared(set));
		assertEquals("Shared : [Sport, Bouffe]", (graph.getVertex("Jack").getLink().get(0).getLinkProperties().get(3).toString()));
	}
}
