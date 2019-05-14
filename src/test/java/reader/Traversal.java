package reader;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import graphe.Graph;

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
		assertEquals(graph.breadthFirstTraversal("Barbara", sommetsVisites, params, 1).contains("Carol"), true);
	}

	@Test
	public void DescendantFriendshipLevel2() {
		Set<String> sommetsVisites = new HashSet<String>();
		assertEquals(graph.breadthFirstTraversal("Barbara",sommetsVisites,params, 2).contains("Dawn"), true);
		sommetsVisites.clear();
		assertEquals(graph.breadthFirstTraversal("Barbara",sommetsVisites, params, 2).contains("Jill"), true);
		sommetsVisites.clear();
		assertEquals(graph.depthFirstTraversal("Barbara", sommetsVisites, params, 2, 0).contains("Jill"), true);
		sommetsVisites.clear();
		assertEquals(graph.depthFirstTraversal("Barbara", sommetsVisites, params, 2, 0).contains("Jack"), false);
	}
 
	@Test
	public void DescendantFriendshipLevel3() {
		Set<String> sommetsVisites = new HashSet<String>();
		assertEquals(graph.breadthFirstTraversal("Barbara", sommetsVisites, params, 3).contains("Jill"), true);
		sommetsVisites.clear();
		assertEquals(graph.depthFirstTraversal("Barbara", sommetsVisites, params, 3, 0).contains("Jack"), true);
	}
	
	@Test
	public void NotDescendantFriendship() {
		Set<String> sommetsVisites = new HashSet<String>();
		assertEquals(graph.breadthFirstTraversal("Barbara",sommetsVisites, params, 2).contains("testPerson"), false);
	}
}
