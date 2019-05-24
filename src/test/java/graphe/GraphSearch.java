package graphe;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import miage.graph.model.Graph;
import miage.graph.utils.Reader;

class GraphSearch {

	private static Graph graph;
	
	@BeforeAll
	public static void setUp() throws Exception {
		graph = Reader.read("src/main/resources/facebook.txt");
	}
		
	@Test
	void SearchDepthTraversal1() throws IOException {
		
		String[] strArray = {"Databases", "Pramod", "Barbara", "Dawn", "Elisabeth", "Martin", "DatabaseRefactoring", "Carol", "NoSQLDistilled", "Refactoring", "Anna"};
		Set<String> strSet = Arrays.stream(strArray).collect(Collectors.toSet());
		
		assertEquals(strSet,
				graph.search("Databases","mode=profondeur,liens=(category <,author,author <,likes <),niveau=4", null));
	}
	
	@Test
	void SearchDepthTraversal2() throws IOException {
		
		String[] strArray = {"Barbara", "Dawn", "Elisabeth", "Carol", "Jill", "Jack", "NoSQLDistilled", "Anna"};
		Set<String> strSet = Arrays.stream(strArray).collect(Collectors.toSet());
		
		assertEquals(strSet, 
				graph.search("NoSQLDistilled","mode=profondeur,liens=(likes <,friend),niveau=4", null));
	}
	
	@Test
	void SearchDepthTraversal3() throws IOException {
		
		String[] strArray = {"Barbara", "Dawn", "Elisabeth", "Carol", "Jill", "Jack", "Anna"};
		Set<String> strSet = Arrays.stream(strArray).collect(Collectors.toSet());
		
		assertEquals(strSet, 
				graph.search("Carol","mode=profondeur,liens=(friend)", null));
	
	}
	
	@Test
	void SearchDepthTraversal4() throws IOException {
		
		String[] strArray = {"Barbara", "Dawn", "Carol"};
		Set<String> strSet = Arrays.stream(strArray).collect(Collectors.toSet());
		
		assertEquals(strSet, 
				graph.search("Carol","mode=profondeur,liens=(friend),niveau=1", null));
	
	}
	
	@Test
	void SearchDepthTraversal5() throws IOException {
		
		String[] strArray = {"Dawn", "Carol", "Jill"};
		Set<String> strSet = Arrays.stream(strArray).collect(Collectors.toSet());
		
		assertEquals(strSet, 
				graph.search("Carol","mode=profondeur,liens=(friend >),niveau=2", null));
		
	}
	
	@Test
	void SearchBreadthTraversal1() throws IOException {
		
		String[] strArray = {"Databases", "Pramod", "Barbara", "Dawn", "Elisabeth", "Martin", "DatabaseRefactoring", "Carol", "NoSQLDistilled", "Refactoring", "Anna"};
		Set<String> strSet = Arrays.stream(strArray).collect(Collectors.toSet());
		
		assertEquals(strSet,
				graph.search("Databases","mode=largeur,liens=(category <,author,author <,likes <),niveau=4", null));
	}
	
	@Test
	void SearchBreadthTraversal2() throws IOException {
		
		String[] strArray = {"Barbara", "Dawn", "Elisabeth", "Carol", "Jill", "Jack", "NoSQLDistilled", "Anna"};
		Set<String> strSet = Arrays.stream(strArray).collect(Collectors.toSet());
		
		assertEquals(strSet, 
				graph.search("NoSQLDistilled","mode=largeur,liens=(likes <,friend),niveau=4", null));
	}
	
	@Test
	void SearchBreadthTraversal3() throws IOException {
		
		String[] strArray = {"Barbara", "Dawn", "Elisabeth", "Carol", "Jill", "Jack", "Anna"};
		Set<String> strSet = Arrays.stream(strArray).collect(Collectors.toSet());
		
		assertEquals(strSet, 
				graph.search("Carol","mode=largeur,liens=(friend)", null));
	
	}
	
	@Test
	void SearchBreadthTraversal4() throws IOException {
		
		String[] strArray = {"Barbara", "Dawn", "Carol"};
		Set<String> strSet = Arrays.stream(strArray).collect(Collectors.toSet());
		
		assertEquals(strSet, 
				graph.search("Carol","mode=largeur,liens=(friend),niveau=1", null));
	
	}
	
	@Test
	void SearchBreadthTraversal5() throws IOException {
		
		String[] strArray = {"Dawn", "Carol", "Jill"};
		Set<String> strSet = Arrays.stream(strArray).collect(Collectors.toSet());
		
		assertEquals(strSet, 
				graph.search("Carol","mode=largeur,liens=(friend >),niveau=2", null));
		
	}
	
	@Test
	void SearchDepthTraversalWithFilter() throws IOException {
		
		String[] strArray = {"Barbara", "Anna"};
		Set<String> strSet = Arrays.stream(strArray).collect(Collectors.toSet());
		
		Set<String> filters = new HashSet<String>(); 
		filters.add("since=2000");
		assertEquals(strSet,
				graph.search("Barbara","mode=profondeur,liens=(friend),niveau=4", filters));
	}
	
	@Test
	void SearchDepthTraversalWithFiltersWithHiredAndRole() throws IOException {
		
		String[] strArray = {"Barbara", "BigCO"};
		Set<String> strSet = Arrays.stream(strArray).collect(Collectors.toSet());
		
		Set<String> filters = new HashSet<String>(); 
		filters.add("role=secretaire");
		filters.add("hired=2000");
		assertEquals(strSet,
				graph.search("Barbara","mode=profondeur,liens=(employeof),niveau=4", filters));
	}
	
	@Test
	void SearchDepthTraversalWithFiltersOnMultipleLevelWithSince() throws IOException {
		
		String[] strArray = {"Barbara", "Jill", "Elisabeth"};
		Set<String> strSet = Arrays.stream(strArray).collect(Collectors.toSet());
		
		Set<String> filters = new HashSet<String>(); 
		filters.add("since=2001");
		assertEquals(strSet,
				graph.search("Barbara","mode=profondeur,liens=(friend),niveau=4", filters));
	}
	
	@Test
	void SearchDepthTraversalWithFilterwithMultipleShared() throws IOException {
		
		String[] strArray = {"Carol", "Dawn"};
		Set<String> strSet = Arrays.stream(strArray).collect(Collectors.toSet());
		
		Set<String> filters = new HashSet<String>(); 
		filters.add("shared=books,music");
		assertEquals(strSet,
				graph.search("Carol","mode=profondeur,liens=(friend),niveau=4", filters));
	}
	
	@Test
	void SearchDepthTraversalWithFilterwithSingleShared() throws IOException {
		
		String[] strArray = {"Carol", "Dawn"};
		Set<String> strSet = Arrays.stream(strArray).collect(Collectors.toSet());
		
		Set<String> filters = new HashSet<String>(); 
		filters.add("shared=music");
		assertEquals(strSet,
				graph.search("Carol","mode=profondeur,liens=(friend),niveau=4", filters));
	}
	
	@Test
	void SearchDepthTraversalWithFilterDifferent() throws IOException {
		
		String[] strArray = {"Carol"};
		Set<String> strSet = Arrays.stream(strArray).collect(Collectors.toSet());
		
		Set<String> filters = new HashSet<String>(); 
		filters.add("shared=foot");
		assertEquals(strSet,
				graph.search("Carol","mode=profondeur,liens=(friend),niveau=4", filters));
	}
	
	@Test
	void SearchDepthTraversalWithFilterNotPresent() throws IOException {
		
		String[] strArray = {"Carol"};
		Set<String> strSet = Arrays.stream(strArray).collect(Collectors.toSet());
		
		Set<String> filters = new HashSet<String>(); 
		filters.add("since=2000");
		assertEquals(strSet,
				graph.search("Carol","mode=profondeur,liens=(friend >),niveau=4", filters));
	}
	
	@Test
	void SearchDepthTraversalWithMultipleFilter() throws IOException {
		String[] strArray = {"Elisabeth", "Jill"};
		Set<String> strSet = Arrays.stream(strArray).collect(Collectors.toSet());
		
		Set<String> filters = new HashSet<String>(); 
		filters.add("since=2001");
		filters.add("shared=pizza");
		assertEquals(strSet,
				graph.search("Elisabeth","mode=profondeur,liens=(friend >),niveau=4", filters));
	}
}
