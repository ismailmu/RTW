package com.btpns.rtw;

import java.util.List;

public class Settings {
	private String outputType="";
	private boolean singleOutput=false;
	private String pathOutput="";
	private String fileNameOutput="";
	private boolean includeDate=false;
	private String includeDateFormat="";
	private String query="";
	private String pathKettle="";
	private String pathPentahoLog="";
	private String hostDb="";
	private String dbName="";
	private String userNameDb="";
	private String passwordDb="";
	private String portDb="";
	private String delimiter="";
	private List<String> schema=null;
	
	public String getOutputType() {
		return outputType;
	}
	public boolean isSingleOutput() {
		return singleOutput;
	}
	public void setSingleOutput(boolean singleOutput) {
		this.singleOutput = singleOutput;
	}
	public void setOutputType(String outputType) {
		this.outputType = outputType;
	}
	public String getPathOutput() {
		return pathOutput;
	}
	public void setPathOutput(String pathOutput) {
		this.pathOutput = pathOutput;
	}
	public String getPathPentahoLog() {
		return pathPentahoLog;
	}
	public void setPathPentahoLog(String pathPentahoLog) {
		this.pathPentahoLog = pathPentahoLog;
	}
	public String getHostDb() {
		return hostDb;
	}
	public void setHostDb(String hostDb) {
		this.hostDb = hostDb;
	}
	public String getUserNameDb() {
		return userNameDb;
	}
	public void setUserNameDb(String userNameDb) {
		this.userNameDb = userNameDb;
	}
	public String getPasswordDb() {
		return passwordDb;
	}
	public void setPasswordDb(String passwordDb) {
		this.passwordDb = passwordDb;
	}
	public String getPortDb() {
		return portDb;
	}
	public void setPortDb(String portDb) {
		this.portDb = portDb;
	}
	public String getDelimiter() {
		return delimiter;
	}
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}
	public String getFileNameOutput() {
		return fileNameOutput;
	}
	public void setFileNameOutput(String fileNameOutput) {
		this.fileNameOutput = fileNameOutput;
	}
	public boolean isIncludeDate() {
		return includeDate;
	}
	public void setIncludeDate(boolean includeDate) {
		this.includeDate = includeDate;
	}
	public String getIncludeDateFormat() {
		return includeDateFormat;
	}
	public void setIncludeDateFormat(String includeDateFormat) {
		this.includeDateFormat = includeDateFormat;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public List<String> getSchema() {
		return schema;
	}
	public void setSchema(List<String> schema) {
		this.schema = schema;
	}
	public String getPathKettle() {
		return pathKettle;
	}
	public void setPathKettle(String pathKettle) {
		this.pathKettle = pathKettle;
	}	
}