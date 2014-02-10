package schoolmngmt;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Dialogs;
import javafx.stage.FileChooser;
import schoolmngmt.MainApp;

public class RootLayoutController {
	
	private MainApp mainApp;
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	@FXML
	private void handleNew() {
		
		mainApp.getClassData().clear();
		mainApp.setClassFilePath(null);
	}
	
	@FXML
	private void handleOpen() {
		FileChooser fileChooser = new FileChooser();
		
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		
		File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
		
		if (file != null) {
			mainApp.loadClassDataFromFile(file);
		}
	}
	
	@FXML
	private void handleSave() {
		File classFile = mainApp.getClassFilePath();
		if (classFile != null) {
			mainApp.saveClassDataToFile(classFile);
		} else {
			handleSaveAs();
		}
	}
	
	@FXML
	private void handleSaveAs() {
		FileChooser fileChooser = new FileChooser();
		
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		
		File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
		
		if (file != null) {
			if (!file.getPath().endsWith(".xml")) {
				file = new File(file.getPath());
			}
			
			mainApp.saveClassDataToFile(file);
		}
	}
	
	@FXML
	private void handleAbout() {
		Dialogs.showInformationDialog(mainApp.getPrimaryStage(), "Author: Manuel Sidler", "SchoolManagementApp", "About");
		
	}
	
	@FXML
	private void handleExit() {
		System.exit(0);
	}
}
