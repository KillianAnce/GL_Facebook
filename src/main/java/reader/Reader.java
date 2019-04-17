package reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import graphe.Graphe;
import graphe.LinkProperties;
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
            if (Pattern.matches("^[A-Z][a-z]+\\:[A-Za-z_]+\\:((([A-Za-z]+(\\=([A-Za-z]+|[0-9]+|[A-Za-z]+ ?[0-9]+|([A-Za-z0-9]+\\,?)+))?)(\\;)?)+)?-->\\:[A-Z][a-z]+", line)) {
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
                Shared o = null;
                Since a = null;
                String[] LinkProperties = Link[0].split(";");
                ArrayList<LinkProperties> LP = new ArrayList<LinkProperties>();
                for (String s : LinkProperties){
                    String[] el = s.split("=");
                    if (el[0].equals("shared") || el[0].equals("Shared")){
                        o = new Shared();
                        String[] values = el[1].split(",");
                        for (String value : values){
                            o.setShared(value);
                        }
                    }
                    if (el[0].equals("since") || el[0].equals("Since")){
                        a = new Since(Integer.parseInt(el[1]));
                    }
                    LP.add(o);
                    LP.add(a);
                }
                for (LinkProperties k : LP){
                    System.out.println(k);
                }
                g.addSingleEdge(v, v1, direction, LP, name);
                System.out.println(g.toString());
            } else {
                System.out.println("non oke");
            }
        }
        br.close();
    }
}