package schoolmngmt;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import schoolmngmt.model.SchoolClass;
import schoolmngmt.model.User;

public class ClassOverviewController {
	@FXML
	private TableView<SchoolClass> classTable;
	@FXML
	private TableColumn<SchoolClass, String> nameColumn;
	@FXML
	private TableColumn<SchoolClass, String> courseColumn;
	
	@FXML
	private Label nameLabel;
	@FXML
	private Label courseLabel;
	@FXML
	private ListView<String> teachersList;
	@FXML
	private ListView<String> studentsList;
	
	// Reference to the main application.
	private MainApp mainApp; // TODO: DI does not work here.
	
	public ClassOverviewController() {
		
	}
	
	@FXML
	private void initialize() {
		initializeClassTable();
	}

	private void initializeClassTable() {
		nameColumn.setCellValueFactory(new PropertyValueFactory<SchoolClass, String>("name"));
		courseColumn.setCellValueFactory(new PropertyValueFactory<SchoolClass, String>("course"));
		
		classTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		clearLabels();
		
		classTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SchoolClass>(){
	    	   
	    	   @Override
	    	   public void changed(ObservableValue<? extends SchoolClass> observable, SchoolClass oldValue, SchoolClass newValue) {
	    		   showClassDetails(newValue);
	    	   }
	       });
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		
		// Add observable list data to the table.
		classTable.setItems(mainApp.getClassData());
	}
	
	private void showClassDetails(SchoolClass schoolClass) {
		if (canUserViewClassDetails(schoolClass)) {
			if (schoolClass == null) {
				clearLabels();
			} else {
				this.nameLabel.setText(schoolClass.getName());
				this.courseLabel.setText(schoolClass.getCourse());
				this.teachersList.getItems().clear();
				this.teachersList.getItems().addAll(schoolClass.getTeachers());
				this.studentsList.getItems().clear();
				this.studentsList.getItems().addAll(schoolClass.getStudents());	
			}
		} else {
			Dialogs.showErrorDialog(mainApp.getPrimaryStage(), "You don't have enough privileges to view this class details.", "Access denied", "Access denied");
			clearLabels();
		}
	}
	
	private Boolean canUserViewClassDetails(SchoolClass schoolClass) {
		Boolean canViewClassDetails = false;
		
		User currentUser = mainApp.getCurrentUser();
		if (currentUser.hasRole("viewAll")) {
			canViewClassDetails = true;
		} else if (currentUser.hasRole("viewStudent") && schoolClass.getStudents().contains(currentUser.getUsername())) {
			canViewClassDetails = true;
		} else if (currentUser.hasRole("viewTeacher") && schoolClass.getTeachers().contains(currentUser.getUsername())) {
			canViewClassDetails = true;
		}
		
		return canViewClassDetails;
	}
	
	private void clearLabels() {
		this.nameLabel.setText("");
		this.courseLabel.setText("");
		this.teachersList.getItems().clear();
		this.studentsList.getItems().clear();
	}
	
	@FXML
	private void handleDeleteClass() {
		if (mainApp.getCurrentUser().hasRole("delete")) {
			int selectedIndex = classTable.getSelectionModel().getSelectedIndex();
			if (selectedIndex >= 0) {
				classTable.getItems().remove(selectedIndex);
			} else {
				Dialogs.showWarningDialog(mainApp.getPrimaryStage(), "Please select a class in the table.", "No class selected", "No selection");
			}
		} else {
			Dialogs.showErrorDialog(mainApp.getPrimaryStage(), "You don't have enough privileges to delete a class.", "Access denied", "Access denied");
		}
	}
	
	@FXML
	private void handleNewClass() {
		if (mainApp.getCurrentUser().hasRole("create")) {
			SchoolClass tempClass = new SchoolClass();
			boolean okClicked = mainApp.showClassEditDialog(tempClass);
			if (okClicked) {
				mainApp.getClassData().add(tempClass);
			}
		} else {
			Dialogs.showErrorDialog(mainApp.getPrimaryStage(), "You don't have enough privileges to create a new class.", "Access denied", "Access denied");
		}
	}
	
	@FXML
	private void handleEditClass() {
		if (mainApp.getCurrentUser().hasRole("edit")) {
			SchoolClass selectedClass = classTable.getSelectionModel().getSelectedItem();
			if (selectedClass != null) {
				boolean okClicked = mainApp.showClassEditDialog(selectedClass);
				if (okClicked) {
					refreshClassTable();
					showClassDetails(selectedClass);
				}
			}
		} else {
			Dialogs.showErrorDialog(mainApp.getPrimaryStage(), "You don't have enough privileges to edit a class.", "Access denied", "Access denied");
		}
	}

	private void refreshClassTable() {
		int selectedIndex = classTable.getSelectionModel().getSelectedIndex();
		classTable.setItems(null);
		classTable.layout();
		classTable.setItems(mainApp.getClassData());
		classTable.getSelectionModel().select(selectedIndex);
	}
}
