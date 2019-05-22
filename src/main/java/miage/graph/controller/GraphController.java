package miage.graph.controller;

import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import miage.graph.model.Graph;
import miage.graph.model.GraphUI;

public class GraphController {
	@FXML
	private SwingNode swingNode;
	@FXML
	private AnchorPane anchorPane;
	private Graph graph;
	private GraphUI userInterface;

	@FXML
	public void initialize() {
		graph = FileLoaderController.getGraph();
		userInterface = new GraphUI();
		userInterface.createUI(swingNode, graph, anchorPane);
	}

}