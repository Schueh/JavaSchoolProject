package schoolmngmt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import com.thoughtworks.xstream.XStream;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Dialogs;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import schoolmngmt.model.SchoolClass;
import schoolmngmt.util.FileUtil;
import schoolmngmt.MainApp;
import schoolmngmt.ClassOverviewController;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	
	private ObservableList<SchoolClass> classData = FXCollections.observableArrayList();
	
	public ObservableList<SchoolClass> getClassData() {
		return classData;
	}
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("SchoolManagementApp");
		// TODO: Set icon
		
		initialize();
		
		showClassOverview();
		
		loadClassData();
	}
	
	private void initialize() {
		try {
			FXMLLoader loader = getLoaderForView("view/RootLayout.fxml");
			rootLayout = (BorderPane) loader.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			
			RootLayoutController controller = loader.getController();
			controller.setMainApp(this);
			
			primaryStage.show();
			
		} catch (IOException e) {
			// Root layout couldn't be loaded.
			e.printStackTrace();
		}
	}
	
	private void showClassOverview() {
		try {
			// Load the FXML file and set into the center of the main layout
			FXMLLoader loader = getLoaderForView("view/ClassOverview.fxml");
			AnchorPane overviewPage = (AnchorPane) loader.load();
			rootLayout.setCenter(overviewPage);
			
			// Give the controller access to the main application
			ClassOverviewController controller = loader.getController();
			controller.setMainApp(this);
			
		} catch (IOException e) {
			// Class overview couldn't be loaded.
			e.printStackTrace();
		}
	}

	private void loadClassData() {
		File file = getClassFilePath();
		if (file != null) {
			loadClassDataFromFile(file);
		}
	}

	private FXMLLoader getLoaderForView(String view) throws IOException {
		return new FXMLLoader(MainApp.class.getResource(view));
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public Stage getPrimaryStage() {
		return this.primaryStage;
	}
	
	public File getClassFilePath() {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		String filePath = prefs.get("filePath", null);
		if (filePath != null) {
			return new File(filePath);
		} else {
			return null;
		}
	}
	
	public void setClassFilePath(File file) {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		if (file != null) {
			prefs.put("filePath", file.getPath());
			
			primaryStage.setTitle("AddressApp - " + file.getName());
		} else {
			prefs.remove("filePath");
			primaryStage.setTitle("AddressApp");
		}
	}
	
	@SuppressWarnings("unchecked")
	public void loadClassDataFromFile(File file) {
		XStream xstream = new XStream();
		xstream.alias("class", SchoolClass.class);
		
		try {
			String xml = FileUtil.readFile(file);
			
			ArrayList<SchoolClass> personList = (ArrayList<SchoolClass>) xstream.fromXML(xml);
			
			classData.clear();
			classData.addAll(personList);
			
			setClassFilePath(file);
		} catch (Exception e) {
			Dialogs.showErrorDialog(primaryStage, 
					"Could not load data from file:\n" + file.getPath(),
					"Could not load data", "Error", e);
		}
	}
	
	public void saveClassDataToFile(File file) {
		XStream xstream = new XStream();
		xstream.alias("class", SchoolClass.class);
		
		ArrayList<SchoolClass> personList = new ArrayList<SchoolClass>(classData);
		
		String xml = xstream.toXML(personList);
		try {
			FileUtil.saveFile(xml, file);
			
			setClassFilePath(file);
		} catch (Exception e) {
			Dialogs.showErrorDialog(primaryStage, "Could not save data to file:\n" + file.getPath(),
					"Could not save data", "Error", e);
		}
	}
}
