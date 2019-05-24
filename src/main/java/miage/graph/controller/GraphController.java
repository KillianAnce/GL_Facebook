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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
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
	@FXML
	private Circle circleFilterLink;

	private List<LinkProperties> linkProperties = new ArrayList<>();

	// remove node
	@FXML
	private Button removeNode;

	// remove link
	@FXML
	private TextField firstVertex;
	@FXML
	private TextField secondVertex;

	// search
	@FXML
	private ChoiceBox searchNode;
	@FXML
	private ChoiceBox parcoursSearch;
	@FXML
	private ChoiceBox niveauSearch;
	@FXML
	private ComboBox relationSearch;
	@FXML
	private ComboBox directionSearch;
	@FXML
	private ComboBox filterNameSearch;
	@FXML
	private TextField filterValueSearch;
	@FXML
	private TextArea searchLabel;
	@FXML
	private Circle circleFilterSearch;
	@FXML
	private Circle circleLinkSearch;
	private int countNumberClick = 0;
	private String searchLinks;
	private Set<String> filtersSearch = new HashSet<>();

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

		directionSearch.getItems().add("<>");
		directionSearch.getItems().add(">");
		directionSearch.getItems().add("<");
		directionSearch.getSelectionModel().selectFirst();

		relationSearch.getItems().add("friend");
		relationSearch.getItems().add("employeof");
		relationSearch.getItems().add("likes");
		relationSearch.getItems().add("author");
		relationSearch.getItems().add("category");
		relationSearch.getSelectionModel().selectFirst();

		filterName.getItems().add("hired");
		filterName.getItems().add("since");
		filterName.getItems().add("role");
		filterName.getItems().add("Shared");

		filterNameSearch.getItems().add("hired");
		filterNameSearch.getItems().add("since");
		filterNameSearch.getItems().add("role");
		filterNameSearch.getItems().add("shared");

		parcoursSearch.getItems().add("largeur");
		parcoursSearch.getItems().add("profondeur");
		parcoursSearch.getSelectionModel().selectFirst();

		graph = FileLoaderController.getGraph();

		circleFilterLink.setFill(javafx.scene.paint.Color.RED);
		circleFilterSearch.setFill(javafx.scene.paint.Color.RED);
		circleLinkSearch.setFill(javafx.scene.paint.Color.RED);

		for (Vertex vertex : graph.getVertices()) {
			searchNode.getItems().add(vertex.getLabel());
		}

		for (int i = 0; i < graph.getVertices().size(); i++) {
			niveauSearch.getItems().add(i);
		}
		niveauSearch.getSelectionModel().selectNext();

		userInterface = new GraphUI();
		userInterface.createUI(swingNode, graph);
	}

	@FXML
	void createVertex(ActionEvent event) {
		userInterface.addVertex(graph, vertexName.getText(), swingNode);

	}

	@FXML
	void addLink(ActionEvent event) {
		userInterface.addLink(graph, startingNode.getText(), destNode.getText(),
				relation.getSelectionModel().getSelectedItem().toString(),
				direction.getSelectionModel().getSelectedItem().toString(), linkProperties, swingNode);
		linkProperties.clear();
		circleFilterLink.setFill(javafx.scene.paint.Color.RED);
	}

	@FXML
	void addFilter(ActionEvent event) {
		try {
			if (filterName.getSelectionModel().getSelectedItem().toString().equals("hired")) {
				linkProperties.add(new Hired(filterValue.getText()));
				circleFilterLink.setFill(javafx.scene.paint.Color.GREEN);
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
			circleFilterLink.setFill(javafx.scene.paint.Color.GREEN);
		} catch (Exception e) {
			circleFilterLink.setFill(javafx.scene.paint.Color.RED);
		}
	}

	@FXML
	void removeNode(ActionEvent event) {
		userInterface.removeVertex(graph, swingNode);
	}

	@FXML
	void removeLink(ActionEvent event) {
		userInterface.removeLink(graph, firstVertex.getText(), secondVertex.getText(), swingNode);
	}

	@FXML
	void addLinkSearch(ActionEvent event) {
		try {
			if (countNumberClick >= 1) {
				searchLinks += "," + relationSearch.getSelectionModel().getSelectedItem().toString() + " "
						+ directionSearch.getSelectionModel().getSelectedItem().toString();
			} else {
				searchLinks = "liens=(" + relationSearch.getSelectionModel().getSelectedItem().toString() + " "
						+ directionSearch.getSelectionModel().getSelectedItem().toString();
				countNumberClick++;
			}
		} catch (Exception e) {
			
		}
		circleLinkSearch.setFill(javafx.scene.paint.Color.GREEN);
	}

	@FXML
	void addFilterSearch(ActionEvent event) {
		try {
			if (filterNameSearch.getSelectionModel().getSelectedItem().toString().equals("hired")) {
				filtersSearch.add("hired=" + filterValueSearch.getText());
			} else if (filterNameSearch.getSelectionModel().getSelectedItem().toString().equals("since")) {
				filtersSearch.add("since=" + filterValueSearch.getText());
			} else if (filterNameSearch.getSelectionModel().getSelectedItem().toString().equals("role")) {
				filtersSearch.add("role=" + filterValueSearch.getText());
			} else {
				filtersSearch.add("shared=" + filterValueSearch.getText());
			}
			circleFilterSearch.setFill(javafx.scene.paint.Color.GREEN);
		} catch (Exception e) {
			circleFilterSearch.setFill(javafx.scene.paint.Color.RED);
		}
	}

	@FXML
	void search(ActionEvent event) {
		String searchString = "mode=" + parcoursSearch.getSelectionModel().getSelectedItem().toString() + ","
				+ searchLinks + ")," + "niveau=" + niveauSearch.getSelectionModel().getSelectedItem().toString();
		Set<String> vertices = graph.search(searchNode.getSelectionModel().getSelectedItem().toString(), searchString,
				filtersSearch);
		searchLabel.setText(vertices.toString());
		vertices.clear();
		filtersSearch.clear();
		countNumberClick = 0;
		circleLinkSearch.setFill(javafx.scene.paint.Color.RED);
		circleFilterSearch.setFill(javafx.scene.paint.Color.RED);
	}
}