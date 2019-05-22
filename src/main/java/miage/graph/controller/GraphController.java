package miage.graph.controller;

import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import miage.graph.model.Graph;
import miage.graph.model.GraphUI;

public class GraphController {
	@FXML
	private SwingNode swingNode;
	private Graph graph;
	private GraphUI UI;

	@FXML
	public void initialize() {
		graph = FileLoaderController.getGraph();
		UI = new GraphUI();
		UI.createUI(swingNode, graph);
	}

}