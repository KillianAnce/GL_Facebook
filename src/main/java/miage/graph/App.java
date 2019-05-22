package miage.graph;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	private static Stage stage;
	
	@Override
	public void start(Stage primaryStage) {
		Parent root = null;
        try 
        {
        	root = FXMLLoader.load(getClass().getResource("./view/FileLoader.fxml"));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        stage = primaryStage;        
        Scene scene = new Scene(root);
        stage.setTitle("GL_Facebook");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static Stage getStage() {
		return stage;
	}
	
	
}