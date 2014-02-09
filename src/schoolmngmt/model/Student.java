package schoolmngmt.model;

public class Student extends User {

	public Student(String username) {
		super(username);
		AddRole("viewStudent");
	}
}
