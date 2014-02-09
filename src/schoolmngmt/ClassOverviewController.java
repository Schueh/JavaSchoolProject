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
	private MainApp mainApp;
	
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
		if (schoolClass == null) {
			clearLabels();
		} else {
			this.nameLabel.setText(schoolClass.getName());
			this.courseLabel.setText(schoolClass.getCourse());
			this.teachersList.getItems().addAll(schoolClass.getTeachers());
			this.studentsList.getItems().addAll(schoolClass.getStudents());
			
		}
	}
	
	private void clearLabels() {
		this.nameLabel.setText("");
		this.courseLabel.setText("");
		this.teachersList.getItems().clear();
		this.studentsList.getItems().clear();
	}
	
	@FXML
	private void handleDeleteClass() {
		int selectedIndex = classTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			classTable.getItems().remove(selectedIndex);
		} else {
			Dialogs.showWarningDialog(mainApp.getPrimaryStage(), "Please select a class in the table.", "No class selected", "No selection");
		}
		
	}
	
	@FXML
	private void handleNewClass() {
		SchoolClass tempClass = new SchoolClass();
		boolean okClicked = mainApp.showClassEditDialog(tempClass);
		if (okClicked) {
			mainApp.getClassData().add(tempClass);
		}
	}
	
	@FXML
	private void handleEditClass() {
		SchoolClass selectedClass = classTable.getSelectionModel().getSelectedItem();
		if (selectedClass != null) {
			boolean okClicked = mainApp.showClassEditDialog(selectedClass);
			if (okClicked) {
				refreshClassTable();
				showClassDetails(selectedClass);
			}
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
