package graphe;

import java.io.IOException;

import reader.*;

public class Principale {

	public static void main(String[] args) throws IOException {
		Reader r = new Reader("src/test/java/reader/facebook.txt");
		Graphe g = r.read();
		System.out.println(g.depthFirstTraversal("Barbara", 2, "friend"));
	}
}