package GrupoMIM2016.MIM2016;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class MainController implements Initializable {
	
	private String selectedIdType;
	private String selectedExamDate;
	private String selectedStatus;
	private String selectedSampleType;
	private String selectedResult;
	private String selectedTestId;
	private String selectedPatientIdentifier;
	private String selectedComments;
	
	public static final LocalDate NOW_LOCAL_DATE (){
    String date = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate localDate = LocalDate.parse(date, formatter);
    return localDate;
}
	
	//Seleccionar la fecha de toma de muestra
	@FXML
	public DatePicker examDate;
	
	//Seleccionar el tipo de identificación
	@FXML 
	public ComboBox<String> IdType;
	ObservableList<String> list = FXCollections.observableArrayList("RUT", "ACME", "Social Number");
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		IdType.setItems(list);
		examDate.setValue(NOW_LOCAL_DATE());
	}
	
	//Seleccionar el status de la muestra
	@FXML public RadioButton FinalRdbtn;
	@FXML public RadioButton PreliminaryRdbtn;
	@FXML public RadioButton RegisteredRdbtn;
	
	//Escribir textos
	@FXML public TextField testIdentifier;
	@FXML public TextField patientIdentifier;
	@FXML public TextField comments;
	
	public void StatusListen(ActionEvent event){
		this.selectedStatus = "";
		if (FinalRdbtn.isSelected()){
			selectedStatus += FinalRdbtn.getText();
		}
		if (PreliminaryRdbtn.isSelected()){
			selectedStatus += PreliminaryRdbtn.getText();
		}
		if (RegisteredRdbtn.isSelected()){
			selectedStatus += RegisteredRdbtn.getText();
		}
	}
	
//Seleccionar el tipo de muestra
	@FXML public RadioButton BloodRdbtn;
	@FXML public RadioButton UrineRdbtn;
	
	public void TypeListen(ActionEvent event){
		this.selectedSampleType = "";
		if (BloodRdbtn.isSelected()){
			selectedSampleType += BloodRdbtn.getText();
		}
		if (UrineRdbtn.isSelected()){
			selectedSampleType += UrineRdbtn.getText();
		}
	}
	
//Seleccionar el Resultado
	@FXML public RadioButton PositiveRdbtn;
	@FXML public RadioButton NegativeRdbtn;
	
	public void ResultListen(ActionEvent event){
		this.selectedResult = "";
		if (PositiveRdbtn.isSelected()){
			selectedResult += PositiveRdbtn.getText();
		}
		if (NegativeRdbtn.isSelected()){
			selectedResult += NegativeRdbtn.getText();
		}
	}
		
	//Obtener resultados
	public void IdListen(ActionEvent event) {
		this.selectedIdType = IdType.getValue();
	}
	public void DateListen(ActionEvent event) {
		this.selectedExamDate = examDate.getValue().toString();
	}
		
	// Imprimir resultados al apretar el botón Send
	public void SendAction(ActionEvent event) {
		
		selectedExamDate = examDate.getValue().toString();
		selectedTestId = testIdentifier.getText();
		selectedPatientIdentifier = patientIdentifier.getText();
		selectedComments = comments.getText();
		
		System.out.println(this.selectedTestId);
		System.out.println(this.selectedStatus);
		System.out.println(this.selectedSampleType);
		System.out.println(this.selectedIdType);
		System.out.println(this.selectedPatientIdentifier);
		System.out.println(this.selectedExamDate);
		System.out.println(this.selectedResult);
		System.out.println(this.selectedComments);
	}
}
