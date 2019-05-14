package graphe;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import reader.Reader;

class GraphSearch {

	private static Graph graph;
	
	@BeforeAll
	public static void setUp() throws Exception {
		Reader reader = new Reader("src/main/resources/facebook.txt");
		graph = reader.read();
	}
		
	@Test
	void SearchDepthTraversal() throws IOException {
		
		assertEquals("[Databases, Pramod, Barbara, Dawn, Elisabeth, Martin, DatabaseRefactoring, Carol, NoSQLDistilled, Refactoring, Anna]",
				graph.search("Databases","mode=profondeur,liens=(category <,author,author <,likes <),niveau=4").toString());
		
		assertEquals("[Barbara, Dawn, Elisabeth, Carol, Jill, Jack, NoSQLDistilled, Anna]", 
				graph.search("NoSQLDistilled","mode=profondeur,liens=(likes <,friend),niveau=4").toString());
		
		assertEquals("[Barbara, Dawn, Elisabeth, Carol, Jill, Jack, Anna]", 
				graph.search("Carol","mode=profondeur,liens=(friend)").toString());
		
		assertEquals("[Barbara, Dawn, Carol]", 
				graph.search("Carol","mode=profondeur,liens=(friend),niveau=1").toString());
		
		assertEquals("[Dawn, Carol, Jill]", 
				graph.search("Carol","mode=profondeur,liens=(friend >),niveau=2").toString());
		
	}
	
	@Test
	void SearchBreadthTraversal() throws IOException {
		
		assertEquals("[Databases, Pramod, Barbara, Dawn, Elisabeth, Martin, DatabaseRefactoring, Carol, NoSQLDistilled, Refactoring, Anna]",
				graph.search("Databases","mode=largeur,liens=(category <,author,author <,likes <),niveau=4").toString());
		
		assertEquals("[Barbara, Dawn, Elisabeth, Carol, Jill, Jack, NoSQLDistilled, Anna]", 
				graph.search("NoSQLDistilled","mode=largeur,liens=(likes <,friend),niveau=4").toString());
		
		assertEquals("[Barbara, Dawn, Elisabeth, Carol, Jill, Jack, Anna]", 
				graph.search("Carol","mode=largeur,liens=(friend)").toString());
		
		assertEquals("[Barbara, Dawn, Carol]", 
				graph.search("Carol","mode=largeur,liens=(friend),niveau=1").toString());
		
		assertEquals("[Dawn, Carol, Jill]", 
				graph.search("Carol","mode=largeur,liens=(friend >),niveau=2").toString());
		
	}

}
