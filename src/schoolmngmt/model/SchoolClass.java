package schoolmngmt.model;

import java.util.ArrayList;
import java.util.List;

public class SchoolClass {
	private String name;
	private String course;
	private List<String> teachers = new ArrayList<String>();
	private List<String> students = new ArrayList<String>();
	
	public SchoolClass() {
		
	}
	
	public SchoolClass(String name, String course) {
		this.name = name;
		this.course = course;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCourse() {
		return this.course;
	}
	
	public void setCourse(String course) {
		this.course = course;
	}
	
	public List<String> getTeachers() {
		return this.teachers;
	}
	
	public List<String> getStudents() {
		return this.students;
	}
}
