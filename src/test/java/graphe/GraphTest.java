package graphe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileFilter;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
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
	public void setUp() throws IOException {
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
	public void addPropertiesWhichExists() {
		Set<String> set = new HashSet<>();
		set.add("hired=2000");
		graph.addProperties("Barbara", "BigCO", set);
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
	public void addPropertyToNode() {
		Set<String> set = new HashSet<String>();
		set.add("hired=2000");
		graph.addProperties("Barbara", "BigCO", set);
		int count = (int) graph.getVertex("BigCO").getParents().stream()
				.filter(parents -> Filter.checkFilters(parents.getLinkVertex("BigCO"), set)).count();
		assertThat(count, is(1));
	}

	@Test
	public void addPropertiesToNode() {
		
		Set<String> set = new HashSet<String>();
		set.add("shared=Bouffe,Sport");	
		set.add("hired=2000");
		set.add("role=Bricoleur");
		set.add("since=2000");
		
		graph.addProperties("Jill", "Jack", set);
		
		int count = (int) graph.getVertex("Jack").getParents().stream()
				.filter(parents -> Filter.checkFilters(parents.getLinkVertex("Jack"), set)).count();
		assertThat(count, is(1));
	}
	
	@Test
	public void LinkNotPresent() {
		Set<String> set = new HashSet<String>();
		set.add("shared=Bouffe,Sport");
		
		Assertions.assertThrows(NullPointerException.class, () -> {
			graph.addProperties("Refactoring", "Jack", set);
		  });
	}
	
	@Test
	public void VertexSourceNotPresent() {
		Set<String> set = new HashSet<String>();
		set.add("shared=Bouffe,Sport");
		
		Assertions.assertThrows(NullPointerException.class, () -> {
			graph.addProperties("Joseph", "Jack", set);
		  });
	}
	
	@Test
	public void VertexDestinationNotPresent() {
		Set<String> set = new HashSet<String>();
		set.add("shared=Bouffe,Sport");
		
		Assertions.assertThrows(NullPointerException.class, () -> {
			graph.addProperties("Refactoring", "Rene", set);
		  });
	}
	
	@Test
	public void RemoveLink() {
		graph.removeLink("Barbara", "BigCO");
		assertEquals(null, graph.getVertex("Barbara").getLinkVertex("BigCO"));
	}
	
	@Test
	public void RemoveVertex() {
		graph.removeVertex("Barbara");
		assertEquals(null, graph.getVertex("Barbara"));
		Assertions.assertThrows(NullPointerException.class, () -> {
			graph.getVertex("Barbara").getParents();
		  });
		Assertions.assertThrows(NullPointerException.class, () -> {
			graph.getVertex("Barbara").getLink();
		  });
	}
	
	@Test
	public void RemoveVertexWithChildVerification() {
		graph.removeVertex("Carol");
		assertEquals(null, graph.getVertex("Carol"));
		assertEquals(null, graph.getVertex("Barbara").getLinkVertex("Carol"));
		Assertions.assertThrows(NullPointerException.class, () -> {
			graph.getVertex("Carol").getLink();
		  });
	}
	
	@Test
	public void NodeRenamingByExistentName() {
		graph.renameVertex("NoSQLDistilled", "Dawn");
		assertEquals(graph.getVertices().contains(graph.getVertex("NoSQLDistilled")), true);
	}
	
	@Test
	public void NodeRenamingByNonExistentName() {
		graph.renameVertex("NoSQLDistilled", "Toto");
		assertEquals(null, graph.getVertex("NoSQLDistilled"));
		assertEquals(graph.getVertices().contains(graph.getVertex("Toto")), true);
	}
	
	@Test
	public void renameRelation() {
		graph.modifyRelation("Barbara", "Anna", "coloc");
		assertEquals(graph.getVertex("Barbara").getLinkVertex("Anna").getRelation(), "coloc");
		assertEquals(graph.getVertex("Barbara").getLinkVertex("Anna").getRelation().equals("friend"),false);
	}
}
