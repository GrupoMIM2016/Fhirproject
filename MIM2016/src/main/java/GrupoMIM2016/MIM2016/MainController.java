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
	
	MakingTransaction mktr = new MakingTransaction( );
	
	public static final LocalDate NowLocalDate (){
    String date = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate localDate = LocalDate.parse(date, formatter);
    return localDate;
}
	
	//Seleccionar el Resultado
	@FXML public RadioButton PositiveRdbtn;
	@FXML public RadioButton NegativeRdbtn;
	
	public void ResultListen(ActionEvent event){
		if (PositiveRdbtn.isSelected()){
			mktr.setResult(PositiveRdbtn.getText());
			GramST.setDisable(false);
			Morpho.setDisable(false);
			atbButton.setDisable(false);
			MOidentification.setDisable(false);
		}
		if (NegativeRdbtn.isSelected()){
			mktr.setResult(NegativeRdbtn.getText());
			GramST.setDisable(true);
			Morpho.setDisable(true);
			atbButton.setDisable(true);
			MOidentification.setDisable(true);
		}	
	}
	
	//Selecionar tipo de bacteria
	@FXML public ComboBox<String> GramST;
	ObservableList<String> GramList = FXCollections.observableArrayList("Gram Negative", "Gram Positive");
	@FXML public ComboBox<String> Morpho;
	ObservableList<String> MorphoList = FXCollections.observableArrayList("Coccus", "Baccillus (Rod)");
	
	//Botón para abrir ventana de antibiograma
	@FXML public Button atbButton;
	
	//Seleccionar la fecha de toma de muestra
	@FXML public DatePicker testDatePicker;
	
	//Decision si crear o asignar paciente
	@FXML public RadioButton CreateRdbtn;
	@FXML public RadioButton AssignRdbtn;
	
	//Seleccionar el microorganismo identificado
	@FXML public ComboBox<String> MOidentification;
	ObservableList<String> MOList = FXCollections.observableArrayList("Pseudomonas aeruginosa", "Escherichia coli", "Neisseria meningitidis", "Clostridium difficile", "Staphylococcus aureus");
	
	//Determinar tincion Gram y morfologia
	public void gramStainListen(ActionEvent event) {
		mktr.setgramStain(GramST.getValue());
		MOList.clear();
		if (GramST.getValue() != null && GramST.getValue().equals("Gram Positive") && Morpho.getValue() != null && Morpho.getValue().equals("Coccus")) {
			MOList.add("Staphylococcus aureus");
		}
		if (GramST.getValue() != null && GramST.getValue().equals("Gram Positive") && Morpho.getValue() != null && Morpho.getValue().equals("Baccillus (Rod)")) {
			MOList.add("Clostridium difficile");
		}
		if (GramST.getValue() != null && GramST.getValue().equals("Gram Negative") && Morpho.getValue() != null && Morpho.getValue().equals("Coccus")) {
			MOList.add("Neisseria meningitidis");
		}
		if (GramST.getValue() != null && GramST.getValue().equals("Gram Negative") && Morpho.getValue() != null && Morpho.getValue().equals("Baccillus (Rod)")) {
			MOList.add("Pseudomonas aeruginosa");
			MOList.add("Escherichia coli");
		}
	}
	
	public void morphoListen(ActionEvent event) {
		mktr.setmorpho(Morpho.getValue());
		MOList.clear();
		if (GramST.getValue() != null && GramST.getValue().equals("Gram Positive") && Morpho.getValue() != null && Morpho.getValue().equals("Coccus")) {
			MOList.add("Staphylococcus aureus");
		}
		if (GramST.getValue() != null && GramST.getValue().equals("Gram Positive") && Morpho.getValue() != null && Morpho.getValue().equals("Baccillus (Rod)")) {
			MOList.add("Clostridium difficile");
		}
		if (GramST.getValue() != null && GramST.getValue().equals("Gram Negative") && Morpho.getValue() != null && Morpho.getValue().equals("Coccus")) {
			MOList.add("Neisseria meningitidis");
		}
		if (GramST.getValue() != null && GramST.getValue().equals("Gram Negative") && Morpho.getValue() != null && Morpho.getValue().equals("Baccillus (Rod)")) {
			MOList.add("Pseudomonas aeruginosa");
			MOList.add("Escherichia coli");
		}
	}
	
	public void PatientListen(ActionEvent event){
		if (CreateRdbtn.isSelected()){
			mktr.setPatient(CreateRdbtn.getText());
			nameTxtField.setDisable(false);
			lastNameTxtField.setDisable(false);
		}
		if (AssignRdbtn.isSelected()){
			mktr.setPatient(AssignRdbtn.getText());
			nameTxtField.setDisable(true);
			lastNameTxtField.setDisable(true);
		}
	}
	
	//Seleccionar el tipo de identificación
	@FXML public ComboBox<String> IdType;
	ObservableList<String> IdTypeList = FXCollections.observableArrayList("RUT", "Furore", "ACME");
	
	public void initialize(URL location, ResourceBundle resources) {
		IdType.setItems(IdTypeList);
		GramST.setItems(GramList);
		Morpho.setItems(MorphoList);
		MOidentification.setItems(MOList);
		testDatePicker.setValue(NowLocalDate());
	}
	
//Escribir textos
	@FXML public TextField testIdentifierTxtField;
	@FXML public TextField patientIdentifierTxtField;
	@FXML public TextField commentsTxtField;
	@FXML public TextField nameTxtField;
	@FXML public TextField lastNameTxtField;
	
	//Seleccionar el status de la muestra
	@FXML public RadioButton FinalRdbtn;
	@FXML public RadioButton PreliminaryRdbtn;
	@FXML public RadioButton RegisteredRdbtn;
	
	public void StatusListen(ActionEvent event){
		if (FinalRdbtn.isSelected()){
			mktr.setStatus(FinalRdbtn.getText());
		}
		if (PreliminaryRdbtn.isSelected()){
			mktr.setStatus(PreliminaryRdbtn.getText());
		}
		if (RegisteredRdbtn.isSelected()){
			mktr.setStatus(RegisteredRdbtn.getText());
		}
	}
	
//Seleccionar el tipo de muestra
	@FXML public RadioButton BloodRdbtn;
	@FXML public RadioButton UrineRdbtn;
	
	public void TypeListen(ActionEvent event){
		if (BloodRdbtn.isSelected()){
			mktr.setSampleType(BloodRdbtn.getText());
		}
		if (UrineRdbtn.isSelected()){
			mktr.setSampleType(UrineRdbtn.getText());
		}
	}
		
	//Obtener resultados
	public void IdListen(ActionEvent event) {
		mktr.setIdType(IdType.getValue());
	}
	public void MOListen(ActionEvent event) {
		mktr.setMOidentification(MOidentification.getValue());
	}
	public void DateListen(ActionEvent event) {
		mktr.setTestDate(testDatePicker.getValue().toString());
	}

	public void selectedAntibiogram(ActionEvent event) throws IOException{
		
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

		mktr.setComments(commentsTxtField.getText());
		mktr.setPatientId(patientIdentifierTxtField.getText());
		mktr.setTestId(testIdentifierTxtField.getText());
		mktr.setname(nameTxtField.getText());
		mktr.setlastName(lastNameTxtField.getText());
		mktr.setTestDate(testDatePicker.getValue().toString());
		
		try {
			mktr.main();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}