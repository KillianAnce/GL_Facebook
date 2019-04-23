package graphe;

import java.io.IOException;

import reader.*;

public class Main {

	public static void main(String[] args) throws IOException {
		Reader r = new Reader("src/main/resources/facebook.txt");
		Graphe g = r.read();
//		System.out.println(g.depthFirstTraversal("Barbara", 4, "friend"));
//		System.out.println(g.breadthFirstTraversal("Barbara", 4, "friend"));
		System.out.println(g.depthFirstTraversal("Barbara", 2, "friend").contains("Carol"));
	}
}