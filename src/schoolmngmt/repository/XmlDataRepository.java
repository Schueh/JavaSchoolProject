package schoolmngmt.repository;

import java.io.File;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;

import schoolmngmt.model.SchoolClass;
import schoolmngmt.util.FileUtil;

public class XmlDataRepository implements IDataRepository {

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<SchoolClass> loadData(File file) throws Exception {
		ArrayList<SchoolClass> data = new ArrayList<SchoolClass>();

		XStream xstream = new XStream();
		xstream.alias("class", SchoolClass.class);
		String xml = FileUtil.readFile(file);
		data.addAll((ArrayList<SchoolClass>) xstream.fromXML(xml));
				
		return data;
	}

	@Override
	public void saveData(File file, ArrayList<SchoolClass> classData) throws Exception {
		XStream xstream = new XStream();
		xstream.alias("class", SchoolClass.class);
		
		ArrayList<SchoolClass> personList = new ArrayList<SchoolClass>(classData);
		
		String xml = xstream.toXML(personList);
		FileUtil.saveFile(xml, file);
	}
}
