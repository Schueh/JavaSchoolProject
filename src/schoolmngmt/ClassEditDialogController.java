package schoolmngmt;

import javafx.fxml.FXML;
import javafx.scene.control.Dialogs;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import schoolmngmt.model.SchoolClass;

public class ClassEditDialogController {
	@FXML
	private TextField nameField;
	@FXML
	private TextField courseField;
	// TODO: edit fields for teachers and students
	
	private Stage dialogStage;
	private SchoolClass schoolClass;
	private boolean okClicked = false;
	
	@FXML
	private void initialize() {
		
	}
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public void setClass(SchoolClass schoolClass) {
		this.schoolClass = schoolClass;
		
		nameField.setText(schoolClass.getName());
		courseField.setText(schoolClass.getCourse());
		// TODO: set students and teachers
	}
	
	public boolean isOkClicked() {
		return okClicked;
	}
	
	@FXML
	private void handleOk() {
		if (isInputValid()) {
			schoolClass.setName(nameField.getText());
	        schoolClass.setCourse(courseField.getText());
	        // TODO: set teachers and students
	          
	        okClicked = true;
	        dialogStage.close();
		}
	}

	private boolean isInputValid() {
		String errorMessage = "";

	      if (nameField.getText() == null || nameField.getText().length() == 0) {
	          errorMessage += "No valid first name!\n";
	      }
	      if (courseField.getText() == null || courseField.getText().length() == 0) {
	          errorMessage += "No valid last name!\n";
	      }
	      
	      if (errorMessage.length() == 0) {
	          return true;
	      } else {
	          // Show the error message
	          Dialogs.showErrorDialog(dialogStage, errorMessage,
	                  "Please correct invalid fields", "Invalid Fields");
	          return false;
	      }
	}
	
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}
}
