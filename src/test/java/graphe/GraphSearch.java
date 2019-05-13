package graphe;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import reader.Reader;

class GraphSearch {

	public Graph graph;
	
	@BeforeAll
	public static void setUp() throws Exception {
		
	}
		
	@Test
	void SearchDepthTraversal() throws IOException {
		
		Reader reader = new Reader("src/main/resources/facebook.txt");
		graph = reader.read();
		
		System.out.println(graph);

		System.out.println(graph.search("Databases","mode=profondeur,liens=(category <,author,author <,likes <),niveau=4"));
		
//		assertEquals("[Databases, Pramod, Barbara, Martin, DatabaseRefactoring, NoSQLDistilled, Refactoring, Anna]",
//				graph.search("Databases","mode=profondeur,liens=(category <,author,author <,likes <),niveau=4"));
		
//		System.out.println(graph.search("NoSQLDistilled","mode=p,liens=(likes <,friend),niveau=2", s));
		//System.out.println(g.breadthFirstTraversal("Barbara", 2, "friend"));
	}

}
