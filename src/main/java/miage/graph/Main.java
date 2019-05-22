package miage.graph;


import java.io.IOException;
import miage.graph.model.Graph;
import miage.graph.utils.*;

public class Main {

	public static void main(String[] args) throws IOException {
		Reader r = new Reader("src/main/resources/facebook.txt");
		Graph g = r.read();
		System.out.println(g);
		
		System.out.println("=========================================");
		System.out.println(g.getVertex("Jill"));
		g.export("export");
	}
}