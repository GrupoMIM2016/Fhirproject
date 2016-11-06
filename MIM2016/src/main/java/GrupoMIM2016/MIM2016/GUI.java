package GrupoMIM2016.MIM2016;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUI extends Application{
		public void start(Stage stage) throws Exception {
			
			Parent root = FXMLLoader.load(getClass().getResource("Fhir.fxml"));
			Scene scene = new Scene(root);
			//scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			stage.setScene(scene);
			stage.setTitle("Culture Club");
			stage.show();
		}
	}