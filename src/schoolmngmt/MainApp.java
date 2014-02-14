package schoolmngmt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.google.inject.Guice;
import com.google.inject.Injector;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Dialogs;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import schoolmngmt.ClassEditDialogController;
import schoolmngmt.model.SchoolClass;
import schoolmngmt.model.User;
import schoolmngmt.repository.IDataRepository;
import schoolmngmt.repository.IUserPreferences;
import schoolmngmt.MainApp;
import schoolmngmt.ClassOverviewController;

public class MainApp extends Application {

	private final IDataRepository dataRepository;
	private final IUserPreferences userPreferences;
	
	private final Injector injector = Guice.createInjector(new DependencyModule());
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	private User currentUser;
	
	public User getCurrentUser() {
		return currentUser;
	}
	
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
	
	private ObservableList<SchoolClass> classData = FXCollections.observableArrayList();
	
	public ObservableList<SchoolClass> getClassData() {
		return classData;
	}
	
	public MainApp() {

		dataRepository = injector.getInstance(IDataRepository.class);
		userPreferences = injector.getInstance(IUserPreferences.class);
		
		SchoolClass testClass1 = new SchoolClass("PABIT", "Bsc BIT");
		testClass1.getTeachers().add("Michael Stoll");
		testClass1.getStudents().add("Lisa Simpson");
		classData.add(testClass1);
		
		SchoolClass testClass2 = new SchoolClass("BIT", "Bsc BIT");
		testClass2.getTeachers().add("Max Mustermann");
		testClass2.getStudents().add("Kevin Buhlmann");
		classData.add(testClass2);
	}
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("SchoolManagementApp");
		// TODO: Set icon
		
		initialize();
		
		showLogin();
				
		loadClassData();
	}
	
	private void initialize() {
		try {
			FXMLLoader loader = getLoaderForView("view/RootLayout.fxml");
			rootLayout = (BorderPane) loader.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);		
			primaryStage.show();			
		} catch (IOException e) {
			// Root layout couldn't be loaded.
			e.printStackTrace();
		}
	}
	
	private void showLogin() {
		try {
			FXMLLoader loader = getLoaderForView("view/LoginView.fxml");
			AnchorPane loginPage = (AnchorPane) loader.load();
			rootLayout.setCenter(loginPage);
			
			LoginController loginController = loader.getController();
			loginController.setMainApp(this);
		} catch (IOException e) {
			// Login view couldn't be loaded.
			e.printStackTrace();
		}
	}
	
	public void showClassOverview() {
		try {
			// Load the FXML file and set into the center of the main layout
			FXMLLoader loader = getLoaderForView("view/ClassOverview.fxml");
			AnchorPane overviewPage = (AnchorPane) loader.load();
			rootLayout.setCenter(overviewPage);
			
			ClassOverviewController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			// Class overview couldn't be loaded.
			e.printStackTrace();
		}
	}
	
	public boolean showClassEditDialog(SchoolClass schoolClass) {
		try {
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/ClassEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Class");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			
			ClassEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setClass(schoolClass);
			
			dialogStage.showAndWait();
			
			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}		
	}

	private void loadClassData() {
		File file = getClassFilePath();
		if (file != null) {
			loadClassDataFromFile(file);
		}
	}

	private FXMLLoader getLoaderForView(String view) throws IOException {
		FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(view));
		loader.setControllerFactory(new GuiceControllerFactory(injector));
		return loader;
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public Stage getPrimaryStage() {
		return this.primaryStage;
	}
	
	public File getClassFilePath() {
		String filePath = userPreferences.getPreference(MainApp.class, "filePath");
		if (filePath != null) {
			return new File(filePath);
		} else {
			return null;
		}
	}
	
	public void setClassFilePath(File file) {
		if (file != null) {
			userPreferences.savePreference(MainApp.class, "filePath", file.getPath());
		} else {
			userPreferences.removePreference(MainApp.class, "filePath");
		}
	}
	
	public void loadClassDataFromFile(File file) {
		try {
			ArrayList<SchoolClass> personList = dataRepository.loadData(file);
			
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
		try {
			dataRepository.saveData(file, new ArrayList<SchoolClass>(classData));
			
			setClassFilePath(file);
		} catch (Exception e) {
			Dialogs.showErrorDialog(primaryStage, "Could not save data to file:\n" + file.getPath(),
					"Could not save data", "Error", e);
		}
	}
}
