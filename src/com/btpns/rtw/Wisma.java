package com.btpns.rtw;

import java.util.Date;

public class Wisma {
	private String schemaName;
	private String officeCode;
	private String officeName;
	private Date fileDate;
	private boolean selected;
	
	
	
	public Wisma(String schemaName, String officeCode, String officeName,
			Date fileDate, boolean selected) {
		super();
		this.schemaName = schemaName;
		this.officeCode = officeCode;
		this.officeName = officeName;
		this.fileDate = fileDate;
		this.selected = selected;
	}
	public String getSchemaName() {
		return schemaName;
	}
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	public String getOfficeCode() {
		return officeCode;
	}
	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	public Date getFileDate() {
		return fileDate;
	}
	public void setFileDate(Date fileDate) {
		this.fileDate = fileDate;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}