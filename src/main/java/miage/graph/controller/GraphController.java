package miage.graph.controller;



import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import miage.graph.model.*;

public class GraphController {
	@FXML
	private SwingNode swingNode;
	@FXML
	private AnchorPane anchorPane;
	@FXML
    private Button addVertex;
	
	// Partie ajout lien
	@FXML
	private TextField startingNode;
	@FXML
	private TextField destNode;
	@FXML
	private ComboBox direction; 
	@FXML
	private Button addLink;
	@FXML
	private ChoiceBox relation;
	@FXML
	private ComboBox filterName;
	@FXML
	private TextField filterValue;
	
	private List<LinkProperties> linkProperties = new ArrayList<>();
	
	//remove node
	@FXML
	private Button removeNode;
	
	//remove link
	@FXML
	private TextField firstVertex;
	@FXML
	private TextField secondVertex;
	
	@FXML
    private TextField vertexName;
	private Graph graph;
	private GraphUI userInterface;

	@FXML
	public void initialize() {
		direction.getItems().add(">");
		direction.getItems().add("<>");
		
		relation.getItems().add("friend");
		relation.getItems().add("employeof");
		relation.getItems().add("likes");
		relation.getItems().add("author");
		relation.getItems().add("category");
		
		filterName.getItems().add("hired");
		filterName.getItems().add("since");
		filterName.getItems().add("role");
		filterName.getItems().add("Shared");
		
		graph = FileLoaderController.getGraph();
		userInterface = new GraphUI();
		userInterface.createUI(swingNode, graph, anchorPane);
	}

	@FXML
    void createVertex(ActionEvent event) {
		userInterface.addVertex(graph, vertexName.getText(), swingNode);

    }
	
	@FXML
	void addLink(ActionEvent event) {
		userInterface.addLink(
				graph, 
				startingNode.getText(), 
				destNode.getText(), 
				relation.getSelectionModel().getSelectedItem().toString(), 
				direction.getSelectionModel().getSelectedItem().toString(), 
				linkProperties,
				swingNode);
	}
	
	@FXML
	void addFilter(ActionEvent event) {
		if (filterName.getSelectionModel().getSelectedItem().toString().equals("hired")) {
			linkProperties.add(new Hired(filterValue.getText()));
		} else if (filterName.getSelectionModel().getSelectedItem().toString().equals("since")) {
			linkProperties.add(new Since(filterValue.getText()));
		} else if (filterName.getSelectionModel().getSelectedItem().toString().equals("role")) {
			linkProperties.add(new Role(filterValue.getText()));
		} else {
			String[] value = filterValue.getText().split(",");
			Set<String> shared = new HashSet<>();
			for (String val : value) {
				shared.add(val);
			}
			linkProperties.add(new Shared(shared));
		}
	}
	
	@FXML
	void removeNode(ActionEvent event) {
		userInterface.removeVertex(graph, swingNode);
	}
	
	@FXML
	void removeLink(ActionEvent event) {
		userInterface.removeLink(graph, firstVertex.getText(),secondVertex.getText(), swingNode);
	}
}