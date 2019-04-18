package graphe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Graphe {

    private Set<Vertex> vertices;

    public Graphe() {
        this.vertices = new HashSet<Vertex>();
    }

    public Vertex addVertex(Vertex v) {
        Vertex contenu = getVertex(v.getLabel());
        if (contenu == null) {
            vertices.add(v);
        }
        return contenu;
    }

    public Vertex getVertex(String label){
        for (Vertex vertex : vertices) {
            if (vertex.getLabel().equals(label)) {
                return vertex;
            }
        }
        return null;
    }

    public void addSingleEdge(Vertex v1, Vertex v2, String direction, ArrayList<LinkProperties> l, String name) {
        // children.get(v1).add(v2);
        v1.setChildren(v2);
        v2.setParents(v1);
        v2.setLink(new Link(v1, direction, l, name, v2));
    }

    public void addMutualEdge(Vertex v1, Vertex v2, String direction, ArrayList<LinkProperties> l, String name) {
        v1.setChildren(v2);
        v2.setChildren(v1);
        v1.setParents(v2);
        v2.setParents(v1);
        v1.setLink(new Link(v1, direction, l, name, v2));
        v2.setLink(new Link(v2, direction, l, name, v1));
    }

    public Set<String> depthFirstTraversal(String root, int l) {
        Set<String> visited = new LinkedHashSet<String>();
        Stack<String> stack = new Stack<String>();
        stack.push(root);
        int i = 0;
        while (!stack.isEmpty() && i < l) {
            String vertex = stack.pop();
            if (!visited.contains(this.getVertex(vertex).getLabel())) {
                visited.add(this.getVertex(vertex).getLabel());
                for (Vertex v : this.getAdjVerticesOfVertex(this.getVertex(vertex))) {
                    stack.push(v.getLabel());
                }
            }
            i++;
        }
        return visited;
    }

    public Set<String> breadthFirstTraversal(String root, int l, String relation) {
        Set<String> visited = new LinkedHashSet<String>();
        Queue<String> queue = new LinkedList<String>();
        queue.add(root);
        visited.add(root);
        int i = 0;
        while (!queue.isEmpty() && i<l) {
            String vertex = queue.poll();
            for (Vertex v : this.getAdjVerticesOfVertex(this.getVertex(vertex))) {
                for (Link link : v.getLink()) {
                    if (link.getSource() == this.getVertex(vertex) && link.getRelation().equals(relation)) {
                        if (!visited.contains(v.getLabel())) {
                            visited.add(v.getLabel());
                            queue.add(v.getLabel());
                        }
                    }
                }
            }
            i++;
        }
        return visited;
    }

    public Set<String> depthFirstTraversal(String root, int l, String relation) {
        Set<String> visited = new LinkedHashSet<String>();
        Stack<String> stack = new Stack<String>();
        stack.push(root);
        int i = 0;
        while (!stack.isEmpty() && i<l) {
            String vertex = stack.pop();
            if (!visited.contains(vertex)) {
                visited.add(vertex);
                for (Vertex v : this.getAdjVerticesOfVertex(getVertex(vertex))) {              
                    for (Link link : v.getLink()) {
                        if (link.getSource() == this.getVertex(vertex) && link.getRelation().equals(relation)) {
                            stack.push(v.getLabel());
                        }
                    }
                }
            }
            i++;
        }
        
        return visited;
    }

    public Set<Vertex> getAdjVerticesOfVertex(Vertex v) {
        return v.getChildren();
    }

    public Set<Vertex> getAdjVertices() {
        return this.vertices;
    }

    @Override
    public String toString() {
        return " " + vertices;
    }
}