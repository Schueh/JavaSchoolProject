package schoolmngmt;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
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
			
		}
	}
	
	private void clearLabels() {
		this.nameLabel.setText("");
		this.courseLabel.setText("");
		this.teachersList.getItems().clear();
		this.studentsList.getItems().clear();
	}
}
