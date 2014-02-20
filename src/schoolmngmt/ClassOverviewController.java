package schoolmngmt;

import java.io.File;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import schoolmngmt.model.SchoolClass;
import schoolmngmt.model.User;
import schoolmngmt.util.PDFUtil;

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
	
	private MainApp mainApp;
	
	public ClassOverviewController() {
		
	}
	
	/**
	  * Initializes the controller class. This method is automatically called
	  * after the fxml file has been loaded.
	  */
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
	
	/**
	  * Is called by the main application to give a reference back to itself.
	  * 
	  * @param mainApp
	  */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		
		// Add observable list data to the table.
		classTable.setItems(mainApp.getClassData());
	}
	
	/**
	 * Fills all text fields to show details about the school class.
	 * If the specified school class is null, all text fields are cleared.
	 * If the current user is a student or teacher and not in this school class
	 * an access denied error will be shown.
	 * 
	 * @param schoolClass the school class or null
	 */
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
	
	/**
	 * Checks whether the current user has access to view the details of the selected school class.
	 * @param schoolClass
	 * @return true if the current user has access to the class details.
	 */
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
	
	/**
	 * Starts a pdf export if the current user has access to the selected school class.
	 * The export contains all necessary information (Name, course, teachers and students)
	 * about the selected school class.
	 */
	@FXML
	private void handleExportPdf() {
		SchoolClass selectedSchoolClass = classTable.getSelectionModel().getSelectedItem();
		if (canUserViewClassDetails(selectedSchoolClass)) {
			FileChooser fileChooser = new FileChooser();
			
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
			fileChooser.getExtensionFilters().add(extFilter);
			
			File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
			
			if (file != null) {
				if (!file.getPath().endsWith(".pdf")) {
					file = new File(file.getPath());
				}
				
				try {
					PDFUtil.exportPdf(file, selectedSchoolClass);
				} catch (Exception e) {
					e.printStackTrace();
					Dialogs.showErrorDialog(mainApp.getPrimaryStage(), "An error occurred while trying to export the PDF file.", "Export error", "Export error");
				}
			}
		} else {
			Dialogs.showErrorDialog(mainApp.getPrimaryStage(), "You don't have enough privileges to export this class.", "Access denied", "Access denied");
		}
	}
}
