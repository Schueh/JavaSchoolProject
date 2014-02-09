package schoolmngmt.model;

public class Teacher extends User {

	public Teacher(String username) {
		super(username);
		AddRole("viewTeacher");
	}
}
