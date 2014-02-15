package schoolmngmt;

import com.google.inject.Inject;

import schoolmngmt.login.ILoginManager;
import schoolmngmt.model.User;

import javafx.fxml.FXML;
import javafx.scene.control.Dialogs;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	
	private final ILoginManager loginManager;
	
	private MainApp mainApp;

	@Inject public LoginController(ILoginManager loginManager) {
		this.loginManager = loginManager;
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	@FXML
	private void handleLogin() {
		Boolean loginSuccessful = loginManager.tryLogin(usernameField.getText(), passwordField.getText());
		if (loginSuccessful) {
			setCurrentUser();
			mainApp.showClassOverview();
		} else {
			Dialogs.showErrorDialog(mainApp.getPrimaryStage(), "Login failed.", "Username and/or password are not correct.", "Login failed");
		}
	}
	
	private void setCurrentUser() {
		String username = usernameField.getText();
		User currentUser = loginManager.getUserByUsername(username);
		
		mainApp.getPrimaryStage().setTitle("SchoolManagementApp - " + username);
		mainApp.setCurrentUser(currentUser);
	}
}
