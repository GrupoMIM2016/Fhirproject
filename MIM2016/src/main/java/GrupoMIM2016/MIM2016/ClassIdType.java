package GrupoMIM2016.MIM2016;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClassIdType {
	private SimpleStringProperty IdType;

	public final String getIdType() {
		if (IdType != null)
			return this.IdType.get();
			return "ACME";
	}

	public final void setIdType(String IdType) {
		this.IdTypeProperty().set(IdType);
	}
	
	public final StringProperty IdTypeProperty() {
		if (IdType == null) {
			IdType = new SimpleStringProperty("ACME");
		}
		return IdType;
	}
}