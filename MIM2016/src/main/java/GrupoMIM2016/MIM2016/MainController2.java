package GrupoMIM2016.MIM2016;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainController2 {
	
	@FXML
	private Label lblStatus;

	@FXML
	private TextField txtUserName;
	
	@FXML
	private TextField txtPassword;
	
	public void login(ActionEvent event) throws Exception {
		
		if(txtUserName.getText().equals("user") && txtPassword.getText().equals("pass")){
			lblStatus.setText("Login Successs");
			
			((Node)(event.getSource())).getScene().getWindow().hide();
			Parent root = FXMLLoader.load(getClass().getResource("Fhir.fxml"));
			Stage stage = new Stage();
			Scene scene2 = new Scene(root);
			//scene2.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			stage.setScene(scene2);
			stage.setTitle("Culture Club");
			stage.show();
			
			
			
		
		}else {
			lblStatus.setText("Login Failed");
		}
	}
		
}