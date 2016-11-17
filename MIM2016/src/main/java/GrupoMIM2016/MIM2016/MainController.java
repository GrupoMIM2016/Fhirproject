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
import javafx.scene.control.CheckBox;
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
	
	//Selecionar tipo de bacteria
	@FXML public ComboBox<String> GramST;
	ObservableList<String> GramList = FXCollections.observableArrayList("Gram Negative", "Gram Positive");
	@FXML public ComboBox<String> Morpho;
	ObservableList<String> MorphoList = FXCollections.observableArrayList("Coccus", "Baccillus (Rod)");
	
	//Botón para abrir ventana de antibiograma
	@FXML public Button atbButton;
	
	//Seleccionar la fecha de toma de muestra
	@FXML
	public DatePicker testDatePicker;
	
	//Seleccionar el tipo de identificación
	@FXML public ComboBox<String> IdType;
	ObservableList<String> IdTypeList = FXCollections.observableArrayList("RUT", "Furore", "ACME");
	
	public void initialize(URL location, ResourceBundle resources) {
		IdType.setItems(IdTypeList);
		GramST.setItems(GramList);
		Morpho.setItems(MorphoList);
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
	@FXML public TextField nameTxtField;
	@FXML public TextField lastNameTxtField;
	
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
			mktr.setSampleType(BloodRdbtn.getText());
			mktr.setSampleType(UrineRdbtn.getText());
		}
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
		}
		if (NegativeRdbtn.isSelected()){
			mktr.setResult(NegativeRdbtn.getText());
			GramST.setDisable(true);
			Morpho.setDisable(true);
			atbButton.setDisable(true);
		}
	}
	
//Seleccionar el Germen específico
	@FXML public CheckBox pAeruginosa;
	@FXML public CheckBox sAureus;
	@FXML public CheckBox nMeningitidis;
	@FXML public CheckBox cDifficile;
	@FXML public CheckBox eColi;
	
	public void gramStainListen(ActionEvent event) {
		mktr.setgramStain(GramST.getValue());
		if (GramST.getValue().equals("Gram Positive")){
			pAeruginosa.setDisable(true);
			nMeningitidis.setDisable(true);
			eColi.setDisable(true);}
		if (GramST.getValue().equals("Gram Negative")){
			sAureus.setDisable(true);
			cDifficile.setDisable(true);}
	}
	public void morphoListen(ActionEvent event) {
		mktr.setmorpho(Morpho.getValue());
		if (GramST.getValue().equals("Coccus")){
			pAeruginosa.setDisable(true);
			cDifficile.setDisable(true);
			eColi.setDisable(true);}
		if (GramST.getValue().equals("Baccillus (Rod)")){
			sAureus.setDisable(true);
			nMeningitidis.setDisable(true);}
	}
		
	//Obtener valores de tipo de Id y Date
	public void IdListen(ActionEvent event) {
		mktr.setIdType(IdType.getValue());
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