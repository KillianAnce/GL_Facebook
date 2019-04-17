package reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import graphe.Graphe;
import graphe.Hired;
import graphe.LinkProperties;
import graphe.Role;
import graphe.Shared;
import graphe.Since;
import graphe.Vertex;

public class Reader {
    File f;

    public Reader(String f) {
        this.f = new File(f);
    }

    public void read() throws IOException {
        Graphe g = new Graphe();
        BufferedReader br = new BufferedReader(new FileReader(this.f));
        String line;
        while ((line = br.readLine()) != null) {
            if (Pattern.matches("^[A-Z][a-z]+\\:[A-Za-z_]+\\:((([A-Za-z]+(\\=([A-Za-z]+|[0-9]+|[A-Za-z]+ ?[0-9]+|([A-Za-z0-9]+\\,?)+))?)(\\;)?)+)?-->\\:[A-Za-z]+", line)) {
                // Step 1 : vertices creation
                String[] vertices = line.split(":");
                Vertex v = new Vertex(vertices[0]); 
                Vertex v1 = new Vertex(vertices[vertices.length-1]);
                Vertex tmp = g.addVertex(v);
                Vertex tmp2 = g.addVertex(v1);
                v = (tmp == null) ? v : tmp;
                v1 = (tmp2 == null) ? v1 : tmp2;

                // Step 2.a : Link creation
                String name = vertices[1];
                String[] Link = vertices[vertices.length-2].split("--");
                String direction = Link[1];
                LinkProperties o = null;
                String[] LinkProperties = Link[0].split(";");
                ArrayList<LinkProperties> LP = new ArrayList<LinkProperties>();
                for (String s : LinkProperties){
                    String[] el = s.split("=");
                    switch (el[0]){
                        case "shared":
                            o = new Shared();
                            String[] values = el[1].split(",");
                            for (String value : values){
                                ((Shared) o).setShared(value);
                            }
                            break;
                        case "since":
                            o = new Since(Integer.parseInt(el[1]));
                            break;
                        case "role":
                            o = new Role(el[1]);
                            break;
                        case "hired":
                            o = new Hired(el[1]);
                            break;
                        default:
                            // Nothing
                    }
                    LP.add(o);
                }

                switch (direction) {
                    case ">":
                        g.addSingleEdge(v, v1, direction, LP, name);
                        break;
                    case "<":
                        g.addSingleEdge(v1, v, direction, LP, name);
                        break;
                    case "<>":
                        g.addMutualEdge(v, v1, direction, LP, name);
                        break;
                    default:
                        // nothing
                }
                
                System.out.println(g.toString());
            } else {
                // nothing
            }
        }
        br.close();
    }
}