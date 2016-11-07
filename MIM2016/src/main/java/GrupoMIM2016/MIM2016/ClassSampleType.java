package GrupoMIM2016.MIM2016;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClassSampleType {
	private SimpleStringProperty SampleType;

	public final String getSampleType() {
		if (SampleType != null)
			return SampleType.get();
			return "Blood";
	}

	public final void setSampleType(String SampleType) {
		this.SampleTypeProperty().set(SampleType);
	}
	
	public final StringProperty SampleTypeProperty() {
		if (SampleType == null) {
			SampleType = new SimpleStringProperty("Blood");
		}
		return SampleType;
	}
}
