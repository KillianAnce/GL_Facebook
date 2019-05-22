package graphe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileFilter;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import miage.graph.model.*;
import miage.graph.utils.Reader;

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
		assertEquals(true, g.getAdjVerticesOfVertex(v, "<>", "friend", null).contains(v1));
		assertEquals(true, g.getAdjVerticesOfVertex(v1, "<>", "friend", null).contains(v));
	}

	@Test
	public void checkPropertiesSince() {

		int count = (int) graph.getVertex("Anna").getParents().stream()
				.filter(parents -> parents.getLinkVertex("Anna").getLinkProperties().toString().equals("[since=2000]"))
				.count();
		assertThat(count, is(1));
	}

	@Test
	public void checkPropertiesRole() {

		Set<String> set = new HashSet<String>();
		set.add("role=secretaire");
		
		int count = (int) graph.getVertex("Barbara").getLink().stream()
				.filter(parents -> Filter.checkFilters(parents, set)).count();
		assertThat(count, is(1));
		
		count = (int) graph.getVertex("BigCO").getParents().stream().filter(
				parents -> Filter.checkFilters(parents.getLinkVertex("BigCO"), set))
				.count();
		assertThat(count, is(1));
	}

	@Test
	public void checkPropertiesHired() {
		Set<String> set = new HashSet<String>();
		set.add("hired=2000");
		int count = (int) graph.getVertex("BigCO").getParents().stream()
				.filter(parents -> Filter.checkFilters(parents.getLinkVertex("BigCO"), set)).count();
		assertThat(count, is(1));
	}

	@Test
	public void addPropertiesWhichExists() throws ParseException {
		graph.getVertex("Barbara").getLinkVertex("BigCO").setLinkProperties(new Hired("2000"));
		
		Set<String> set = new HashSet<String>();
		set.add("hired=2000");
		int count = (int) graph.getVertex("BigCO").getParents().stream()
				.filter(parents -> Filter.checkFilters(parents.getLinkVertex("BigCO"), set)).count();
		assertThat(count, is(1));
	}

	@Test
	public void checkPropertiesShared() {
		assertEquals("[shared=[books+ music]]",
				graph.getVertex("Carol").getLinkVertex("Dawn").getLinkProperties().toString());
	}

	@Test
	public void addPropertyToNode() throws ParseException {
		graph.getVertex("Barbara").getLinkVertex("BigCO").setLinkProperties(new Hired("2000"));
		Set<String> set = new HashSet<String>();
		set.add("hired=2000");
		int count = (int) graph.getVertex("BigCO").getParents().stream()
				.filter(parents -> Filter.checkFilters(parents.getLinkVertex("BigCO"), set)).count();
		assertThat(count, is(1));
	}

	@Test
	public void addPropertiesToNode() {
		graph.getVertex("Jill").getLinkVertex("Jack").setLinkProperties(new Hired("2000"));
		graph.getVertex("Jill").getLinkVertex("Jack").setLinkProperties(new Role("Bricoleur"));
		graph.getVertex("Jill").getLinkVertex("Jack").setLinkProperties(new Since("2000"));
		Set<String> set = new HashSet<String>();
		set.add("shared=Bouffe,sport");	
		graph.getVertex("Jill").getLinkVertex("Jack").setLinkProperties(new Shared(set));
		set.add("hired=2000");
		set.add("role=Bricoleur");
		set.add("since=2000");
		
		int count = (int) graph.getVertex("Jack").getParents().stream()
				.filter(parents -> Filter.checkFilters(parents.getLinkVertex("Jack"), set)).count();
		assertThat(count, is(1));
	}
}
