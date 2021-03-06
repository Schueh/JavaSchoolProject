package schoolmngmt;

import schoolmngmt.login.ILoginManager;
import schoolmngmt.login.LoginManager;
import schoolmngmt.repository.IDataRepository;
import schoolmngmt.repository.IUserPreferences;
import schoolmngmt.repository.UserPreferences;
import schoolmngmt.repository.XmlDataRepository;

import com.google.inject.AbstractModule;

public class DependencyModule extends AbstractModule {

	/**
	 * Defines the specific implementation for all dependencies.
	 */
	@Override
	protected void configure() {
		bind(IDataRepository.class).to(XmlDataRepository.class);
		bind(IUserPreferences.class).to(UserPreferences.class);
		bind(ILoginManager.class).to(LoginManager.class);
	}
}
