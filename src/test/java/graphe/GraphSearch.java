package graphe;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import reader.Reader;

class GraphSearch {

	private static Graph graph;
	private static Graph graphSimple;
	private Set<String> set;
	
	@BeforeAll
	public static void setUp() throws Exception {
		Reader reader = new Reader("src/main/resources/facebook.txt");
		graph = reader.read();
		
		Reader simple = new Reader("src/main/resources/SimpleTest.txt");
		graphSimple = simple.read();
	}
		
	@Test
	void SearchDepthTraversal1() throws IOException {
		
		String[] strArray = {"Databases", "Pramod", "Barbara", "Dawn", "Elisabeth", "Martin", "DatabaseRefactoring", "Carol", "NoSQLDistilled", "Refactoring", "Anna"};
		Set<String> strSet = Arrays.stream(strArray).collect(Collectors.toSet());
		
		assertEquals(strSet,
				graph.search("Databases","mode=profondeur,liens=(category <,author,author <,likes <),niveau=4"));
	}
	
	@Test
	void SearchDepthTraversal2() throws IOException {
		
		String[] strArray = {"Barbara", "Dawn", "Elisabeth", "Carol", "Jill", "Jack", "NoSQLDistilled", "Anna"};
		Set<String> strSet = Arrays.stream(strArray).collect(Collectors.toSet());
		
		assertEquals(strSet, 
				graph.search("NoSQLDistilled","mode=profondeur,liens=(likes <,friend),niveau=4"));
	}
	
	@Test
	void SearchDepthTraversal3() throws IOException {
		
		String[] strArray = {"Barbara", "Dawn", "Elisabeth", "Carol", "Jill", "Jack", "Anna"};
		Set<String> strSet = Arrays.stream(strArray).collect(Collectors.toSet());
		
		assertEquals(strSet, 
				graph.search("Carol","mode=profondeur,liens=(friend)"));
	
	}
	
	@Test
	void SearchDepthTraversal4() throws IOException {
		
		String[] strArray = {"Barbara", "Dawn", "Carol"};
		Set<String> strSet = Arrays.stream(strArray).collect(Collectors.toSet());
		
		assertEquals(strSet, 
				graph.search("Carol","mode=profondeur,liens=(friend),niveau=1"));
	
	}
	
	@Test
	void SearchDepthTraversal5() throws IOException {
		
		String[] strArray = {"Dawn", "Carol", "Jill"};
		Set<String> strSet = Arrays.stream(strArray).collect(Collectors.toSet());
		
		assertEquals(strSet, 
				graph.search("Carol","mode=profondeur,liens=(friend >),niveau=2"));
		
	}
	
	@Test
	void SearchBreadthTraversal1() throws IOException {
		
		String[] strArray = {"Databases", "Pramod", "Barbara", "Dawn", "Elisabeth", "Martin", "DatabaseRefactoring", "Carol", "NoSQLDistilled", "Refactoring", "Anna"};
		Set<String> strSet = Arrays.stream(strArray).collect(Collectors.toSet());
		
		assertEquals(strSet,
				graph.search("Databases","mode=largeur,liens=(category <,author,author <,likes <),niveau=4"));
	}
	
	@Test
	void SearchBreadthTraversal2() throws IOException {
		
		String[] strArray = {"Barbara", "Dawn", "Elisabeth", "Carol", "Jill", "Jack", "NoSQLDistilled", "Anna"};
		Set<String> strSet = Arrays.stream(strArray).collect(Collectors.toSet());
		
		assertEquals(strSet, 
				graph.search("NoSQLDistilled","mode=largeur,liens=(likes <,friend),niveau=4"));
	}
	
	@Test
	void SearchBreadthTraversal3() throws IOException {
		
		String[] strArray = {"Barbara", "Dawn", "Elisabeth", "Carol", "Jill", "Jack", "Anna"};
		Set<String> strSet = Arrays.stream(strArray).collect(Collectors.toSet());
		
		assertEquals(strSet, 
				graph.search("Carol","mode=largeur,liens=(friend)"));
	
	}
	
	@Test
	void SearchBreadthTraversal4() throws IOException {
		
		String[] strArray = {"Barbara", "Dawn", "Carol"};
		Set<String> strSet = Arrays.stream(strArray).collect(Collectors.toSet());
		
		assertEquals(strSet, 
				graph.search("Carol","mode=largeur,liens=(friend),niveau=1"));
	
	}
	
	@Test
	void SearchBreadthTraversal5() throws IOException {
		
		String[] strArray = {"Dawn", "Carol", "Jill"};
		Set<String> strSet = Arrays.stream(strArray).collect(Collectors.toSet());
		
		assertEquals(strSet, 
				graph.search("Carol","mode=largeur,liens=(friend >),niveau=2"));
		
	}
	

}
