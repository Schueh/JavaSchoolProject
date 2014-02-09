package schoolmngmt.model;

public class Secretary extends User {

	public Secretary(String username) {
		super(username);
		AddRole("create");
		AddRole("edit");
		AddRole("delete");
		AddRole("view");
	}
}