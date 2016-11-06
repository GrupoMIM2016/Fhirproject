package GrupoMIM2016.MIM2016;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

public class AntibiogramaController implements Initializable{
	
	
	@FXML Label Ampicilin;
	@FXML Label Gentamicin;
	@FXML Label Imipenem;
	@FXML Label Sensitive;
	@FXML Label Intermediate;
	@FXML Label Resistant;
	@FXML RadioButton A1;
	@FXML RadioButton A2;
	@FXML RadioButton A3;
	@FXML RadioButton B1;
	@FXML RadioButton B2;
	@FXML RadioButton B3;
	@FXML RadioButton C1;
	@FXML RadioButton C2;
	@FXML RadioButton C3;
	private String selectedAntib;

	public void initialize(URL location, ResourceBundle resources) {
	}	

	public void radioSelect(ActionEvent event) {
		
		this.selectedAntib = A1.getText();
		
		if(A1.isSelected()){
			selectedAntib += Sensitive.getText();
		}
	
	System.out.println("Ampiclin:" + this.selectedAntib);
	}
}