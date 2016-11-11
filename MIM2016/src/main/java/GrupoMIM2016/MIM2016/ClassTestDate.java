/*package GrupoMIM2016.MIM2016;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClassTestDate {
	
		private SimpleStringProperty TestDate;

		public final String getTestDate() {
			if (TestDate != null)
				return TestDate.get();
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date();
				return dateFormat.format(date);
		}

		public final void setTestDate(String TestDate) {
			this.TestDateProperty().set(TestDate);
		}
		
		public final StringProperty TestDateProperty() {
			if (TestDate == null) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date();
				TestDate = new SimpleStringProperty(dateFormat.format(date));
			}
			return TestDate;
		}
}
*/