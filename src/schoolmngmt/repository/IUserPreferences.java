package schoolmngmt.repository;

public interface IUserPreferences {
	String getPreference(Class<?> type, String key);
	void savePreference(Class<?> type, String key, String value);
	void removePreference(Class<?> type, String key);
}
