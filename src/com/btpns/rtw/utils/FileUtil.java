package com.btpns.rtw.utils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import com.btpns.rtw.Settings;

public class FileUtil {
	final static Properties prop=new Properties();
	public FileUtil() throws IOException {
		 prop.load(getClass().getResourceAsStream("/Application.properties"));
	}
	public Settings getSettings() throws IOException {
		
		 Settings setting = new Settings();
		 setting.setPathKettle(prop.getProperty("path.kettle"));
		 setting.setPathPentahoLog(prop.getProperty("path.pentaho.log"));
		 setting.setHostDb(prop.getProperty("host"));
		 setting.setUserNameDb(prop.getProperty("username"));
		 setting.setPasswordDb(prop.getProperty("password"));
		 setting.setPortDb(prop.getProperty("port"));
		 setting.setDelimiter(prop.getProperty("delimiter"));
		 setting.setIncludeDateFormat(prop.getProperty("date.format"));
		 setting.setOutputType(prop.getProperty("output.type"));
		 setting.setDbName(prop.getProperty("db.name"));
		 setting.setPathOutput(prop.getProperty("path.output"));
		 
		 return setting;
	}
	
	public String getPathSystem(String path,String original) {
		File file = new File(path);
		try {
			return file.getCanonicalPath();
		} catch (IOException e) {
			return original;
		}
	}
	
	public void deleteFile(String path) {
		new File(path).delete();
	}
}
