package GrupoMIM2016.MIM2016;

import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainController implements Initializable {
	
	final ClassIdType idType = new ClassIdType();
	final ClassTestDate testDate = new ClassTestDate();
	final ClassStatus status = new ClassStatus();
	final ClassSampleType sampleType = new ClassSampleType();
	final ClassResult result = new ClassResult();
	final ClassPatientId patientId = new ClassPatientId();
	final ClassComments comments = new ClassComments();
	
	//private String selectedIdType;
	//private String selectedExamDate;
	//private String selectedStatus;
	//private String selectedSampleType;
	//private String selectedResult;
	private String selectedTestId;
	//private String selectedPatientIdentifier;
	//private String selectedComments;
	private String selectedAntibiogram;
	
	public static final LocalDate NowLocalDate (){
    String date = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate localDate = LocalDate.parse(date, formatter);
    return localDate;
}
	
	//Selecionar tipo de bacteria
	@FXML
	public ComboBox<String> GramST;
	ObservableList<String> GramList = FXCollections.observableArrayList("Negative", "Positive");
	@FXML public ComboBox<String> Microorganism;
	ObservableList<String> MicroorganismList = FXCollections.observableArrayList("Cocci", "Baccillus (Rod)");
	
	//Botón para abrir ventana de antibiograma
	@FXML public Button atbButton;
	
	//Seleccionar la fecha de toma de muestra
	@FXML
	public DatePicker testDatePicker;
	
	//Seleccionar el tipo de identificación
	@FXML 
	public ComboBox<String> IdType;
	ObservableList<String> IdTypeList = FXCollections.observableArrayList("RUT", "ACME", "Social Number");
	
	public void initialize(URL location, ResourceBundle resources) {
		IdType.setItems(IdTypeList);
		GramST.setItems(GramList);
		Microorganism.setItems(MicroorganismList);
		testDatePicker.setValue(NowLocalDate());
	}
	
	//Seleccionar el status de la muestra
	@FXML public RadioButton FinalRdbtn;
	@FXML public RadioButton PreliminaryRdbtn;
	@FXML public RadioButton RegisteredRdbtn;
	
	//Escribir textos
	@FXML public TextField testIdentifierTxtField;
	@FXML public TextField patientIdentifierTxtField;
	@FXML public TextField commentsTxtField;
	
	public void StatusListen(ActionEvent event){
		if (FinalRdbtn.isSelected()){
			status.setStatus(FinalRdbtn.getText());
			//selectedStatus += FinalRdbtn.getText();
		}
		if (PreliminaryRdbtn.isSelected()){
			status.setStatus(PreliminaryRdbtn.getText());
			//selectedStatus += PreliminaryRdbtn.getText();
		}
		if (RegisteredRdbtn.isSelected()){
			status.setStatus(RegisteredRdbtn.getText());
			//selectedStatus += RegisteredRdbtn.getText();
		}
	}
	
//Seleccionar el tipo de muestra
	@FXML public RadioButton BloodRdbtn;
	@FXML public RadioButton UrineRdbtn;
	
	public void TypeListen(ActionEvent event){
		if (BloodRdbtn.isSelected()){
			sampleType.setSampleType(BloodRdbtn.getText());
			//selectedSampleType += BloodRdbtn.getText();
		}
		if (UrineRdbtn.isSelected()){
			sampleType.setSampleType(UrineRdbtn.getText());
			//selectedSampleType += UrineRdbtn.getText();
		}
	}
	
//Seleccionar el Resultado
	@FXML public RadioButton PositiveRdbtn;
	@FXML public RadioButton NegativeRdbtn;
	
	public void ResultListen(ActionEvent event){
		if (PositiveRdbtn.isSelected()){
			result.setResult(PositiveRdbtn.getText());
			//selectedResult += PositiveRdbtn.getText();
			GramST.setDisable(false);
			Microorganism.setDisable(false);
			atbButton.setDisable(false);
		}
		if (NegativeRdbtn.isSelected()){
			result.setResult(NegativeRdbtn.getText());
			//selectedResult += NegativeRdbtn.getText();
			GramST.setDisable(true);
			Microorganism.setDisable(true);
			atbButton.setDisable(true);
		}
	}
		
	//Obtener resultados
	public void IdListen(ActionEvent event) {
		idType.setIdType(IdType.getValue());
		//this.selectedIdType = IdType.getValue();
	}
	public void DateListen(ActionEvent event) {
		testDate.setTestDate(testDatePicker.getValue().toString());
		//this.selectedExamDate = testDatePicker.getValue().toString();
	}
	
	public void selectedAntibiogram(ActionEvent event) throws IOException{
		this.selectedAntibiogram = "";
		
		if (atbButton.isPickOnBounds()){
			Stage secondaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("Antibiograma.fxml"));
			Scene scene = new Scene(root,500,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			secondaryStage.setScene(scene);
			secondaryStage.show();
		}
	}

	// Imprimir resultados al apretar el botón Send
	public void SendAction(ActionEvent event) {
		
		testDate.setTestDate(testDatePicker.getValue().toString());
		//selectedExamDate = testDatePicker.getValue().toString();
		selectedTestId = testIdentifierTxtField.getText();
		patientId.setPatientId(patientIdentifierTxtField.getText());
		//selectedPatientIdentifier = patientIdentifier.getText();
		comments.setComments(commentsTxtField.getText());
		//selectedComments = comments.getTextTxtField();
		
		System.out.println("Test code: " + this.selectedTestId);
		
		System.out.println("Report status: " + status.getStatus());
		//System.out.println("Estado del informe: " + this.selectedStatus);
		System.out.println("Sample: " + sampleType.getSampleType());
		//System.out.println(this.selectedSampleType);
		System.out.println("Type of Patient Id: " + idType.getIdType());
		//System.out.println(this.selectedIdType);
		System.out.println("Number of Patient Id: " + patientIdentifierTxtField.getText());
		//System.out.println(this.selectedPatientIdentifier);
		System.out.println("Date of Sample Withdrawal: " + testDate.getTestDate());
		//System.out.println(this.selectedExamDate);
		System.out.println("Test Result: " + result.getResult());
		//System.out.println(this.selectedResult);
		System.out.println("Comments: " + comments.getComments());
		//System.out.println(this.selectedComments);
	}
}