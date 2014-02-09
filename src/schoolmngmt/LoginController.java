package schoolmngmt;

import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Dialogs;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	
	private MainApp mainApp;
	
	Map<String, String> users;
	
	public LoginController() {
		/* Because the whole user management is out of scope in this version
		 * we setup some example login for the provided user roles.
		 */
		SetupExampleLoginData();
	}
	
	private void SetupExampleLoginData() {
		users = new HashMap<String, String>();
		users.put("Sandra Studer", "sekretariat"); // sample login for secretary role
		users.put("Michael Stoll", "dozent"); // sample login for teacher role
		users.put("Kevin Buhlmann", "student"); // sample login for student role
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	@FXML
	private void handleLogin() {
		Boolean loginSuccessful = false;
		
		if (usernameField.getText().length() > 0 && passwordField.getText().length() > 0) {
			if (users.containsKey(usernameField.getText())) {
				if (users.get(usernameField.getText()).equals(passwordField.getText())) {
					loginSuccessful = true;
				}
			}
		}
		
		if (loginSuccessful) {
			// TODO: set current user on main app
			mainApp.showClassOverview();
		} else {
			Dialogs.showErrorDialog(mainApp.getPrimaryStage(), "Login failed.", "Username and/or password are not correct.", "Login failed");
		}
	}
}
