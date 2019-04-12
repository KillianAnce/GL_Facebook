package graphe;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Graphe {
	
	private Map<Vertex, Set<Vertex>> children;

    public Graphe (){
        this.children = new HashMap<Vertex, Set<Vertex>>();
    }
    
    public void addVertex(Vertex v) {
    	children.putIfAbsent(v, new HashSet<Vertex>());
    }
    
    public void addSingleEdge(Vertex v1, Vertex v2) {
    	children.get(v1).add(v2);
    	v2.setParents(v1);
    }
    
    public void addMutualEdge(Vertex l1, Vertex l2) {
    	children.get(l1).add(l2);
    	children.get(l2).add(l1);
    }
    
    public Set<String> depthFirstTraversal(Vertex root) {
        Set<String> visited = new LinkedHashSet<String>();
        Stack<Vertex> stack = new Stack<Vertex>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Vertex vertex = stack.pop();
            if (!visited.contains(vertex.getLabel())) {
                visited.add(vertex.getLabel());
                for (Vertex v : this.getAdjVerticesOfVertex(vertex)) {              
                    stack.push(v);
                }
            }
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
    
    Set<Vertex> getAdjVerticesOfVertex(Vertex v) {
        return children.get(v);
    }

	public Map<Vertex, Set<Vertex>> getAdjVertices() {
		return this.children;
	}
}