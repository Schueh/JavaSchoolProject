package schoolmngmt;

import javafx.fxml.FXML;
import javafx.scene.control.Dialogs;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import schoolmngmt.model.SchoolClass;

public class ClassEditDialogController {
	@FXML
	private TextField nameField;
	@FXML
	private TextField courseField;
	@FXML
	private ListView<String> teachersList;
	@FXML
	private ListView<String> studentsList;
	@FXML
	private TextField addTeacherField;
	@FXML
	private TextField addStudentField;
	
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
		teachersList.getItems().addAll(schoolClass.getTeachers());
		studentsList.getItems().addAll(schoolClass.getStudents());
	}
	
	public boolean isOkClicked() {
		return okClicked;
	}
	
	/**
	 * Saves the edited or new school class if the given input is valid.
	 */
	@FXML
	private void handleOk() {
		if (isInputValid()) {
			schoolClass.setName(nameField.getText());
	        schoolClass.setCourse(courseField.getText());
	        schoolClass.getStudents().clear();
	        schoolClass.getStudents().addAll(studentsList.getItems());
	        schoolClass.getTeachers().clear();
	        schoolClass.getTeachers().addAll(teachersList.getItems());
	        	          
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
	      if (teachersList.getItems().isEmpty()) {
	    	  errorMessage += "No teachers added!";
	      }
	      if (studentsList.getItems().isEmpty()) {
	    	  errorMessage += "No students added!";
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
	
	@FXML
	private void handleAddTeacher() {
		String teacherToAdd = addTeacherField.getText();
		if (teacherToAdd.length() > 0) {
			teachersList.getItems().add(teacherToAdd);
			addTeacherField.setText("");
		}
	}
	
	@FXML
	private void handleDeleteTeacher() {
		int selectedTeacher = teachersList.getSelectionModel().getSelectedIndex();
		if (selectedTeacher >= 0) {
			teachersList.getItems().remove(selectedTeacher);
		}
	}
	
	@FXML
	private void handleAddStudent() {
		String studentToAdd = addStudentField.getText();
		if (studentToAdd.length() > 0) {
			studentsList.getItems().add(studentToAdd);
			addStudentField.setText("");
		}
	}
	
	@FXML
	private void handleDeleteStudent() {
		int selectedStudent = studentsList.getSelectionModel().getSelectedIndex();
		if (selectedStudent >= 0) {
			studentsList.getItems().remove(selectedStudent);
		}
	}
}
