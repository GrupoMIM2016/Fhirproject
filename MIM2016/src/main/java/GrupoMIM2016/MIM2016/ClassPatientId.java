package GrupoMIM2016.MIM2016;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClassPatientId {
	private SimpleStringProperty PatientId;

	public final String getpatientId() {
		if (PatientId != null)
			return PatientId.get();
			return "000";
	}

	public final void setPatientId(String PatientId) {
		this.PatientIdProperty().set(PatientId);
	}
	
	public final StringProperty PatientIdProperty() {
		if (PatientId == null) {
			PatientId = new SimpleStringProperty("000");
		}
		return PatientId;
	}
}