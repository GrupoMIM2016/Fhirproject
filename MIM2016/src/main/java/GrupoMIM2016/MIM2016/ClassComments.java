package GrupoMIM2016.MIM2016;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClassComments {
	private SimpleStringProperty Comments;

	public final String getComments() {
		if (Comments != null)
			return Comments.get();
			return "No Comments";
	}

	public final void setComments(String Comments) {
		this.CommentsProperty().set(Comments);
	}
	
	public final StringProperty CommentsProperty() {
		if (Comments == null) {
			Comments = new SimpleStringProperty("No Comments");
		}
		return Comments;
	}
}
