package GrupoMIM2016.MIM2016;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClassStatus {
	private SimpleStringProperty Status;

	public final String getStatus() {
		if (Status != null)
			return Status.get();
			return "registered";
	}

	public final void setStatus(String Status) {
		this.StatusProperty().set(Status);
	}
	
	public final StringProperty StatusProperty() {
		if (Status == null) {
			Status = new SimpleStringProperty("registered");
		}
		return Status;
	}
}
