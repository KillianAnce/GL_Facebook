package miage.graph.controller;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import miage.graph.model.Graph;
import miage.graph.utils.Reader;

public class FileLoaderController {

	private static Graph graph;
	@FXML
	private Button explorer;
	@FXML
	private Button showGraph;
	@FXML
	private Label file;

	/**
	 * Permet d'ouvrir le fichier 
	 * @param event
	 */
	@FXML
	void browseFile(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choisir un fichier");
		File file = fileChooser.showOpenDialog(new Stage());
		if (file == null)
			return;
		try {
			graph = Reader.read(file.getAbsolutePath());

		} catch (Exception e) {
			String message = String.format("Impossible d'ouvrir le fichier \"%s\".", file.getAbsolutePath());
			Alert alert = new Alert(Alert.AlertType.ERROR, message);
			alert.showAndWait();
		}
	}

	public static Graph getGraph() {
		return graph;
	}

}
