package GrupoMIM2016.MIM2016;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClassResult {
	private SimpleStringProperty Result;

	public final String getResult() {
		if (Result != null)
			return Result.get();
			return "Negative";
	}

	public final void setResult(String Result) {
		this.ResultProperty().set(Result);
	}
	
	public final StringProperty ResultProperty() {
		if (Result == null) {
			Result = new SimpleStringProperty("Negative");
		}
		return Result;
	}
}
