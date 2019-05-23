package miage.graph.model;

import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import javafx.embed.swing.SwingNode;
import javafx.scene.layout.AnchorPane;

import java.util.Random;

public class GraphUI {

	private mxGraph mxGraph = new mxGraph();
	private mxGraphComponent mxGraphComponent;

	public GraphUI() {
		mxGraphComponent = new mxGraphComponent(mxGraph);
	}

	public void createUI(SwingNode swingNode, Graph graph, AnchorPane anchorPane) {

		graph.getVertices().forEach(vertex -> mxGraph.insertVertex(null, vertex.getLabel(), vertex.getLabel(),
				randomNumber(graph.getVertices().size()), randomNumber(graph.getVertices().size()), 35, 35));

		graph.getVertices().stream().forEach(vertex -> 
			vertex.getLink().forEach(relation -> {
				Object vertex1 = ((mxGraphModel) mxGraph.getModel()).getCell(relation.getSource().getLabel());
				Object vertex2 = ((mxGraphModel) mxGraph.getModel()).getCell(relation.getDestination().getLabel());
				mxGraph.insertEdge(null, relation.getSource().getLabel(), relation.getRelation(), vertex1,
						vertex2);
			})
		);
		swingNode.setContent(mxGraphComponent);
		
	}
	
	
	public void addVertex(Graph graph,String Name, SwingNode swingNode) {
		graph.addVertex(new Vertex(Name));
		System.out.println("blabla");
		mxGraph.insertVertex(null, graph.getVertex(Name).getLabel(), graph.getVertex(Name).getLabel(),
				randomNumber(graph.getVertices().size()), randomNumber(graph.getVertices().size()), 35, 35)	;

		graph.getVertex(Name).getLink().forEach(vertex -> {
				Object vertex1 = ((mxGraphModel) mxGraph.getModel()).getCell(vertex.getSource().getLabel());
				Object vertex2 = ((mxGraphModel) mxGraph.getModel()).getCell(vertex.getDestination().getLabel());
				mxGraph.insertEdge(null, vertex.getSource().getLabel(), vertex.getRelation(), vertex1,
						vertex2);
		});
		swingNode.setContent(mxGraphComponent);
	}
	
	

	private int randomNumber(int bound) {
		return 60 * new Random().nextInt(bound);
	}
}