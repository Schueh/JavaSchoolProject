package schoolmngmt.repository;

import java.io.File;
import java.util.ArrayList;

import schoolmngmt.model.SchoolClass;

public interface IDataRepository {
	ArrayList<SchoolClass> loadData(File file) throws Exception;
	void saveData(File file, ArrayList<SchoolClass> classData) throws Exception;
}
