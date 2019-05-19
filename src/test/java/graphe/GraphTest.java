package graphe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import miage.graph.model.Graph;
import miage.graph.model.Hired;
import miage.graph.model.Role;
import miage.graph.model.Shared;
import miage.graph.model.Since;
import miage.graph.model.Vertex;
import miage.graph.utils.Reader;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GraphTest {

	private Graph graph;
	private Graph g;
	
	@BeforeEach
	public void setUp() throws Exception {
		graph = Reader.read("src/main/resources/facebook.txt");
		g = new Graph();
	}
	
	@Test
	public void grapheEmpty() {
		assertThat(g.getVertices().size(), is(0));
	}

	@Test
	public void grapheNotEmpty() {
		Graph g = new Graph();
		Vertex v = new Vertex("Thomas");
		g.addVertex(v);
		assertThat(g.getVertices().size(), is(1));
	}

	@Test
	public void nodeWithoutRelation() {
		Graph g = new Graph();
		Vertex v = new Vertex("Thomas");
		Vertex v1 = new Vertex("Killian");
		g.addVertex(v);
		g.addVertex(v1);
		assertEquals(false, g.getAdjVerticesOfVertex(v, ">", "friend", null).contains(v1));
		assertEquals(false, g.getAdjVerticesOfVertex(v1, ">", "friend", null).contains(v));
		
	}

	@Test
	public void nodeWithSimpleRelation() {
		Graph g = new Graph();
		Vertex v = new Vertex("Thomas");
		Vertex v1 = new Vertex("Killian");
		g.addVertex(v);
		g.addVertex(v1);
		g.addSingleEdge(v, v1, ">", null, "friend");
		assertEquals(true, g.getAdjVerticesOfVertex(v, ">", "friend", null).contains(v1));
		assertEquals(false, g.getAdjVerticesOfVertex(v1, ">", "friend", null).contains(v));
	}

	@Test
	public void nodeWithMutualRelation() {
		Graph g = new Graph();
		Vertex v = new Vertex("Thomas");
		Vertex v1 = new Vertex("Killian");
		g.addVertex(v);
		g.addVertex(v1);
		g.addMutualEdge(v, v1, "<>", null, "friend");
		assertEquals(true, g.getAdjVerticesOfVertex(v, ">", "friend", null).contains(v1));
		assertEquals(true, g.getAdjVerticesOfVertex(v1, ">", "friend", null).contains(v));
	}
	
	@Test
	public void checkPropertiesSince() {
		assertEquals("[since=2000]",
				graph.getVertex("Anna").getLink().get(0).getLinkProperties().toString());
	}
	
	@Test
	public void checkPropertiesRole() {
		assertEquals("role=secretaire", 
				graph.getVertex("BigCO").getLink().get(1).getLinkProperties().get(0).toString());
	}
	
	@Test
	public void checkPropertiesHired() {
		assertEquals("hired=2000", 
				graph.getVertex("BigCO").getLink().get(1).getLinkProperties().get(1).toString());
	}
	
	@Test
	public void addPropertiesWhichExists() throws ParseException {
		graph.getVertex("BigCO").getLink().get(1).setLinkProperties(new Hired("2000"));
		int count = (int) graph.getVertex("BigCO").getLink().get(1).getLinkProperties()
				.stream()
				.filter(e -> "hired=2000".equals(e.toString())).count();
		assertThat(count, is(1));
	}
	
	@Test
	public void checkPropertiesShared() {
		assertEquals("[shared=[books+ music]]",
				graph.getVertex("Dawn").getLink().get(0).getLinkProperties().toString());
	}
	
	@Test
	public void addPropertyToNode() throws ParseException {
		graph.getVertex("BigCO").getLink().get(0).setLinkProperties(new Hired("2000"));
		assertEquals("[hired=2000]",graph.getVertex("BigCO").getLink().get(0).getLinkProperties().toString());
	}
	
	@Test
	public void addPropertiesToNode() {
		graph.getVertex("Jack").getLink().get(0).setLinkProperties(new Hired("2000"));
		graph.getVertex("Jack").getLink().get(0).setLinkProperties(new Role("Bricoleur"));
		graph.getVertex("Jack").getLink().get(0).setLinkProperties(new Since("2000"));
		Set<String> set = new HashSet<String>();
		set.add("Sport");
		set.add("Bouffe");
		graph.getVertex("Jack").getLink().get(0).setLinkProperties(new Shared(set));
		assertEquals("shared=[Sport+ Bouffe]", (graph.getVertex("Jack").getLink().get(0).getLinkProperties().get(3).toString()));
	}
}
