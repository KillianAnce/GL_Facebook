package graphe;

import java.nio.file.LinkPermission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Graphe {
	
	private Set<Vertex> vertices;

    public Graphe (){
        this.vertices = new HashSet<Vertex>();
    }
    
    public Vertex addVertex(Vertex v) {
        Boolean contains = false;
        for (Vertex vertex : vertices){
            if (vertex.getLabel().equals(v.getLabel())){
                contains = true;
                return vertex;
            }
        }
        if (!contains){
            vertices.add(v);
        }
        return null;
    }
    
    public void addSingleEdge(Vertex v1, Vertex v2, String direction, ArrayList<LinkProperties> l, String name) {
        // children.get(v1).add(v2);
        v1.setChildren(v2);
        v2.setParents(v1);
        v2.setLink(new Link(">", l, "Friend"));
    }
    
    public void addMutualEdge(Vertex v1, Vertex v2) {
    	// children.get(l1).add(l2);
        // children.get(l2).add(l1);
        v1.setChildren(v2);
        v2.setChildren(v1);
        v1.setParents(v2);
        v2.setParents(v1);
        Link l = new Link(">", null, "Friend");
        l.setLinkProperties(null); 
        v1.setLink(l);
        v2.setLink(new Link(">", null, "Friend"));
    }
    
    public Set<String> depthFirstTraversal(Vertex root, int l) {
        Set<String> visited = new LinkedHashSet<String>();
        Stack<Vertex> stack = new Stack<Vertex>();
        stack.push(root);
        int i = 0;
        while (!stack.isEmpty() && i<l) {
            Vertex vertex = stack.pop();
            if (!visited.contains(vertex.getLabel())) {
                visited.add(vertex.getLabel());
                for (Vertex v : this.getAdjVerticesOfVertex(vertex)) {              
                    stack.push(v);
                }
            }
            i++;
        }
        return visited;
    }
    
    public Set<String> breadthFirstTraversal(Vertex root) {
        Set<String> visited = new LinkedHashSet<String>();
        Queue<Vertex> queue = new LinkedList<Vertex>();
        queue.add(root);
        visited.add(root.getLabel());
        while (!queue.isEmpty()) {
            Vertex vertex = queue.poll();
            for (Vertex v : this.getAdjVerticesOfVertex(vertex)) {
                if (!visited.contains(v.getLabel())) {
                    visited.add(v.getLabel());
                    queue.add(v);
                }
            }
        }
        return visited;
    }
    
    public Set<Vertex> getAdjVerticesOfVertex(Vertex v) {
        return v.getChildren();
    }

    public Set<Vertex> getAdjVertices(){
        return this.vertices;
    }

    @Override
    public String toString(){
        return "Graphe : " + vertices;
    }
}