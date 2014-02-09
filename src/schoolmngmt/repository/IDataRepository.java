package schoolmngmt.repository;

import java.io.File;
import java.util.ArrayList;

import schoolmngmt.model.SchoolClass;

public interface IDataRepository {
	ArrayList<SchoolClass> LoadData(File file) throws Exception;
	void SaveData(File file, ArrayList<SchoolClass> classData) throws Exception;
}
