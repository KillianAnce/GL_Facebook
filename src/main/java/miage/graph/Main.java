package miage.graph;


import java.io.IOException;
import miage.graph.model.Graph;
import miage.graph.utils.*;

public class Main {

	public static void main(String[] args) throws IOException {
		Graph g = Reader.read("src/main/resources/facebook.txt");
		System.out.println(g);	
		System.out.println("=========================================");
		System.out.println(g.getVertex("Jill"));
		g.export("export");
	}
}