package schoolmngmt.login;

import schoolmngmt.model.User;

public interface ILoginManager {
	Boolean tryLogin(String username, String password);
	User getUserByUsername(String username);
}
