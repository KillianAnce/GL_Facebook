package miage.graph.controller;



import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import miage.graph.model.Graph;
import miage.graph.model.GraphUI;

public class GraphController {
	@FXML
	private SwingNode swingNode;
	@FXML
	private AnchorPane anchorPane;
	@FXML
    private Button addVertex;
	@FXML
    private TextField vertexName;
	private Graph graph;
	private GraphUI userInterface;

	@FXML
	public void initialize() {
		graph = FileLoaderController.getGraph();
		userInterface = new GraphUI();
		userInterface.createUI(swingNode, graph, anchorPane);
	}

	@FXML
    void createVertex(ActionEvent event) {
		System.out.println(vertexName.getText());
		userInterface.addVertex(graph, vertexName.getText(), swingNode);

    }

	
	
	
}