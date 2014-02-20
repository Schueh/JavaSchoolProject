package schoolmngmt.repository;

import java.util.prefs.Preferences;

/**
 * Wrapper for accessing the user preferences.
 * @author ManuelSidler
 *
 */
public class UserPreferences implements IUserPreferences {

	@Override
	public String getPreference(Class<?> type, String key) {
		Preferences prefs = Preferences.userNodeForPackage(type);
		return prefs.get(key, null);
	}

	@Override
	public void savePreference(Class<?> type, String key, String value) {
		Preferences prefs = Preferences.userNodeForPackage(type);
		prefs.put(key, value);
	}

	@Override
	public void removePreference(Class<?> type, String key) {
		Preferences prefs = Preferences.userNodeForPackage(type);
		prefs.remove(key);
	}

}
