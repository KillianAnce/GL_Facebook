package graphe;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import reader.*;

public class Main {

	public static void main(String[] args) throws IOException {
		Reader r = new Reader("src/main/resources/facebook.txt");
		Graph g = r.read();
		g.export("export");
	}
}