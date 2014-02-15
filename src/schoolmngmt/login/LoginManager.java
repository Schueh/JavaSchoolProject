package schoolmngmt.login;

import java.util.HashMap;
import java.util.Map;

import schoolmngmt.model.Secretary;
import schoolmngmt.model.Student;
import schoolmngmt.model.Teacher;
import schoolmngmt.model.User;

public class LoginManager implements ILoginManager {

	Map<String, String> users;
	
	public LoginManager() {
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
	
	@Override
	public Boolean tryLogin(String username, String password) {
		if (username.length() > 0 && password.length() > 0) {
			if (users.containsKey(username)) {
				if (users.get(username).equals(password)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public User getUserByUsername(String username) {
		switch (username) {
		case ("Sandra Studer") : return new Secretary(username);
		case ("Michael Stoll") : return new Teacher(username);
		case ("Kevin Buhlmann") : return new Student(username);
		default : return null;
		}
	}

}
