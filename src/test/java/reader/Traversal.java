package reader;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import miage.graph.model.Graph;
import miage.graph.utils.Reader;

public class Traversal {

	private static Graph graph;
	String[] params = {"friend >"};

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
		Set<String> sommetsVisites = new HashSet<String>();
		assertEquals(true, graph.breadthFirstTraversal("Barbara", sommetsVisites, params, 1, null).contains("Carol"));
	}

	@Test
	public void DescendantFriendshipLevel2() {
		Set<String> sommetsVisites = new HashSet<String>();
		assertEquals(true, graph.breadthFirstTraversal("Barbara",sommetsVisites,params, 2, null).contains("Dawn"));
		sommetsVisites.clear();
		assertEquals(true, graph.breadthFirstTraversal("Barbara",sommetsVisites, params, 2, null).contains("Jill"));
		sommetsVisites.clear();
		assertEquals(true, graph.depthFirstTraversal("Barbara", sommetsVisites, params, 2, 0, null).contains("Jill"));
		sommetsVisites.clear();
		assertEquals(false, graph.depthFirstTraversal("Barbara", sommetsVisites, params, 2, 0, null).contains("Jack"));
	}
 
	@Test
	public void DescendantFriendshipLevel3() {
		Set<String> sommetsVisites = new HashSet<String>();
		assertEquals(true, graph.breadthFirstTraversal("Barbara", sommetsVisites, params, 3, null).contains("Jill"));
		sommetsVisites.clear();
		assertEquals(true, graph.depthFirstTraversal("Barbara", sommetsVisites, params, 3, 0, null).contains("Jack"));
	}
	
	@Test
	public void NotDescendantFriendship() {
		Set<String> sommetsVisites = new HashSet<String>();
		assertEquals(false, graph.breadthFirstTraversal("Barbara",sommetsVisites, params, 2, null).contains("testPerson"));
	}
}
