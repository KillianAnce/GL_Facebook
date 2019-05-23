package miage.graph.controller;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import miage.graph.App;
import miage.graph.model.Graph;
import miage.graph.utils.Reader;

public class FileLoaderController {

	private static final Logger LOGGER = Logger.getLogger(App.class.getName());
	private static Graph graph;
	@FXML
	private Button explorer;
	@FXML
	private Button showGraph;
	@FXML
	private Label filePath;

	/**
	 * Permet d'ouvrir le fichier 
	 * @param event
	 */
	@FXML
	public void browseFile(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choisir un fichier");
		File file = fileChooser.showOpenDialog(new Stage());
	
		if (file == null)
			return;
		try {
			graph = Reader.read(file.getAbsolutePath());
			openGraphController();
			
		} catch (Exception e) {
			String message = String.format("Impossible d'ouvrir le fichier \"%s\".", file.getAbsolutePath());
			Alert alert = new Alert(Alert.AlertType.ERROR, message);
			alert.showAndWait();
		}
	}
	
	public void openGraphController() {
		Parent root = null;
        try 
        {
        	root = FXMLLoader.load(getClass().getResource("../view/Graph.fxml"));
        	Scene scene = new Scene(root);
            App.getStage().close();
            App.getStage().setTitle("Indicateurs du cours");
            App.getStage().setScene(scene);
            App.getStage().show();
        }
        catch(IOException e)
        {
        	LOGGER.severe("Impossible d'ouvrir la vue Graph.fxml");
        }
	}


	public static Graph getGraph() {
		return graph;
	}

}
