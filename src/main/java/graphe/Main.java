package graphe;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import reader.*;

public class Main {

	public static void main(String[] args) throws IOException {
		Reader r = new Reader("src/main/resources/facebook.txt");
		Graphe g = r.read();
		//System.out.println(g.depthFirstTraversal("Barbara", 2, "friend").contains("Carol"));
		//Set<String> visited = new HashSet<String>();
		
		Set<String> s = new HashSet<String>();
		System.out.println(g.search("NoSQLDistilled","mode=profondeur,liens=(friend <),niveau=2", s));
		System.out.println(g.search("NoSQLDistilled","mode=p,liens=(likes <,friend),niveau=2", s));
		//System.out.println(g.breadthFirstTraversal("Barbara", 2, "friend"));
	}
}