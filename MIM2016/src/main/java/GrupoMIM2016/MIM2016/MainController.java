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
	
	MakingDR mkdr=new MakingDR( );
	
	private String selectedTestId;
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
	ObservableList<String> GramList = FXCollections.observableArrayList("Gram Negative", "Gram Positive");
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
	ObservableList<String> IdTypeList = FXCollections.observableArrayList("RUT", "Furore", "ACME");
	
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
			mkdr.setStatus(FinalRdbtn.getText());
		}
		if (PreliminaryRdbtn.isSelected()){
			mkdr.setStatus(PreliminaryRdbtn.getText());
		}
		if (RegisteredRdbtn.isSelected()){
			mkdr.setStatus(RegisteredRdbtn.getText());
		}
	}
	
//Seleccionar el tipo de muestra
	@FXML public RadioButton BloodRdbtn;
	@FXML public RadioButton UrineRdbtn;
	
	public void TypeListen(ActionEvent event){
		if (BloodRdbtn.isSelected()){
			mkdr.setSampleType(BloodRdbtn.getText());
		}
		if (UrineRdbtn.isSelected()){
			mkdr.setSampleType(BloodRdbtn.getText());
			mkdr.setSampleType(UrineRdbtn.getText());
		}
	}
	
//Seleccionar el Resultado
	@FXML public RadioButton PositiveRdbtn;
	@FXML public RadioButton NegativeRdbtn;
	
	public void ResultListen(ActionEvent event){
		if (PositiveRdbtn.isSelected()){
			mkdr.setResult(PositiveRdbtn.getText());
			GramST.setDisable(false);
			Microorganism.setDisable(false);
			atbButton.setDisable(false);
		}
		if (NegativeRdbtn.isSelected()){
			mkdr.setResult(NegativeRdbtn.getText());
			//selectedResult += NegativeRdbtn.getText();
			GramST.setDisable(true);
			Microorganism.setDisable(true);
			atbButton.setDisable(true);
		}
	}
		
	//Obtener resultados
	public void IdListen(ActionEvent event) {
		mkdr.setIdType(IdType.getValue());
	}
	public void DateListen(ActionEvent event) {
		mkdr.setTestDate(testDatePicker.getValue().toString());
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

		mkdr.setComments(commentsTxtField.getText());
		mkdr.setPatientId(patientIdentifierTxtField.getText());
		mkdr.setTestDate(testDatePicker.getValue().toString());
		
		try {
			mkdr.main();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}