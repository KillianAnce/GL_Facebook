package miage.graph;

import java.io.IOException;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	private static final Logger LOGGER = Logger.getLogger(App.class.getName());
	
	@Override
	public void start(Stage primaryStage) {
		Parent root = null;
        try 
        {
        	root = FXMLLoader.load(getClass().getResource("./view/Graph.fxml"));
        }
        catch(IOException e)
        {
        	LOGGER.severe("Impossible d'ouvir l'interface du graphique");
        }
        Scene scene = new Scene(root);
        primaryStage.setTitle("GL_Facebook");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}	
}