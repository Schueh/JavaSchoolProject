package schoolmngmt.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import schoolmngmt.login.LoginManager;
import schoolmngmt.model.Secretary;
import schoolmngmt.model.Student;
import schoolmngmt.model.Teacher;
import schoolmngmt.model.User;

@RunWith(JUnit4.class)
public class LoginTest {
	
	@Test
	public void tryLogin_withSecretaryCredentials_returnsTrue() {
		LoginManager sut = new LoginManager();
		
		Boolean actual = sut.tryLogin("Sandra Studer", "sekretariat");
		
		assertTrue(actual);
	}
	
	@Test
	public void tryLogin_withTeacherCredentials_returnsTrue() {
		LoginManager sut = new LoginManager();
		
		Boolean actual = sut.tryLogin("Michael Stoll", "dozent");
		
		assertTrue(actual);
	}
	
	@Test
	public void tryLogin_withStudentCredentials_returnsTrue() {
		LoginManager sut = new LoginManager();
		
		Boolean actual = sut.tryLogin("Kevin Buhlmann", "student");
		
		assertTrue(actual);
	}
	
	@Test
	public void tryLogin_withWrongCredentials_returnsFalse() {
		LoginManager sut = new LoginManager();
		
		Boolean actual = sut.tryLogin("xyz", "abc");
		
		assertFalse(actual);
	}
	
	@Test
	public void getUserByUsername_withSecretaryUsername_returnsSecretary() {
		LoginManager sut = new LoginManager();
		
		User actual = sut.getUserByUsername("Sandra Studer");
		
		assertTrue(actual instanceof Secretary);
		assertEquals(actual.getUsername(), "Sandra Studer");
	}
	
	@Test
	public void getUserByUsername_withTeacherUsername_returnsTeacher() {
		LoginManager sut = new LoginManager();
		
		User actual = sut.getUserByUsername("Michael Stoll");
		
		assertTrue(actual instanceof Teacher);
		assertEquals(actual.getUsername(), "Michael Stoll");
	}
	
	@Test
	public void getUserByUsername_withStudentUsername_returnsStudent() {
		LoginManager sut = new LoginManager();
		
		User actual = sut.getUserByUsername("Kevin Buhlmann");
		
		assertTrue(actual instanceof Student);
		assertEquals(actual.getUsername(), "Kevin Buhlmann");
	}
	
	@Test
	public void getUserByUsername_withUnknownUsername_returnsNull() {
		LoginManager sut = new LoginManager();
		
		User actual = sut.getUserByUsername("xyz");
		
		assertNull(actual);
	}
}
