package com.btpns.rtw.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jasypt.util.text.BasicTextEncryptor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.btpns.rtw.Settings;

public class TextUtil {
	private final static String MY_SECRET_PASSWORD = "W03K3r#n";
	
	private String getXmlWisma(List<String> schema, String query) {
		StringBuilder strBuild = new StringBuilder();
		for (String str : schema) {
			strBuild.append("<WISMA>");
			strBuild.append("<SCHEMA_NAME><VALUE>" + str
					+ "</VALUE></SCHEMA_NAME>");
			strBuild.append("<QUERY_PATH><VALUE><![CDATA["
					+ query.replace("[WismaSchema]", str)
					+ "]]></VALUE></QUERY_PATH>");
			strBuild.append("</WISMA>");
		}

		strBuild.trimToSize();
		return strBuild.toString();
	}

	public void createXmlSetting(Settings setting, String fileName)
			throws IOException {
		if (fileName == null) {
			fileName = "./files/rtw_setting.xml";
		}
		File file = new File(fileName);
		// System.out.println("Path = " + file.getAbsolutePath() + "  " +
		// file.getCanonicalPath());
		BufferedWriter output;
		StringBuilder strBuild = new StringBuilder();
		output = new BufferedWriter(new FileWriter(file));

		strBuild.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><SETTINGS_LIST>");
		strBuild.append("<SETTING>");
		strBuild.append("<OUTPUT_TYPE>" + setting.getOutputType()
				+ "</OUTPUT_TYPE>");
		strBuild.append("<SINGLE_OUTPUT>" + setting.isSingleOutput()
				+ "</SINGLE_OUTPUT>");
		strBuild.append("<PATH_OUTPUT>" + setting.getPathOutput()
				+ "</PATH_OUTPUT>");
		strBuild.append("<FILE_NAME_OUTPUT>" + setting.getFileNameOutput()
				+ "</FILE_NAME_OUTPUT>");
		strBuild.append("<INCLUDE_DATE>" + setting.isIncludeDate()
				+ "</INCLUDE_DATE>");
		strBuild.append("<INCLUDE_DATE_FORMAT>"
				+ setting.getIncludeDateFormat() + "</INCLUDE_DATE_FORMAT>");
		strBuild.append("<PATH_KETTLE>" + setting.getPathKettle()
				+ "</PATH_KETTLE>");
		strBuild.append("<PATH_PENTAHO_LOG>" + setting.getPathPentahoLog()
				+ "</PATH_PENTAHO_LOG>");
		strBuild.append("<DELIMITER>" + setting.getDelimiter() + "</DELIMITER>");
		strBuild.append("<HOST_DB>" + setting.getHostDb() + "</HOST_DB>");
		strBuild.append("<USERNAME_DB>" + setting.getUserNameDb() + "</USERNAME_DB>");
		strBuild.append("<PASSWORD_DB>" + setting.getPasswordDb() + "</PASSWORD_DB>");
		strBuild.append("<PORT_DB>" + setting.getPortDb() + "</PORT_DB>");
		strBuild.append("<DB_NAME>" + setting.getDbName() + "</DB_NAME>");
		strBuild.append("<QUERY><![CDATA[" + setting.getQuery() + "]]></QUERY>");
		strBuild.append("</SETTING>");
		strBuild.append(getXmlWisma(setting.getSchema(), setting.getQuery()));
		strBuild.append("</SETTINGS_LIST>");
		output.write(strBuild.toString());
		output.close();
	}

	public Settings readXmlSetting(String fileName) throws SAXException,
			IOException, ParserConfigurationException {
		Settings setting = null;

		File fXmlFile = new File(fileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);

		// optional, but recommended
		// read this -
		// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		doc.getDocumentElement().normalize();

		NodeList nList = doc.getElementsByTagName("SETTING");

		setting = new Settings();

		// Node nNode = nList.item(0);
		//
		// System.out.println("\nCurrent Element :" + nNode.getNodeName());
		//
		// if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		//
		// Element element = (Element) nNode;
		//
		// setting.setOutputType(element.getAttribute("OUTPUT_TYPE"));
		// setting.setSingleOutput(Boolean.getBoolean(element.getAttribute("SINGLE_OUTPUT")));
		// setting.setPathOutput(element.getAttribute("PATH_OUTPUT"));
		// setting.setFileNameOutput(element.getAttribute("FILE_NAME_OUTPUT"));
		// setting.setIncludeDate(Boolean.getBoolean(element.getAttribute("INCLUDE_DATE")));
		// setting.setIncludeDateFormat(element.getAttribute("INCLUDE_DATE_FORMAT"));
		// }

		Node nNode = nList.item(0);

		if (nNode.getNodeType() == Node.ELEMENT_NODE) {

			Element element = (Element) nNode;
			setting.setOutputType(element.getElementsByTagName("OUTPUT_TYPE").item(0).getTextContent());
			if (element.getElementsByTagName("SINGLE_OUTPUT").item(0).getTextContent().equalsIgnoreCase("true"))
				setting.setSingleOutput(true);
			else
				setting.setSingleOutput(false);
			setting.setPathOutput(element.getElementsByTagName("PATH_OUTPUT").item(0).getTextContent());
			setting.setFileNameOutput(element.getElementsByTagName("FILE_NAME_OUTPUT").item(0).getTextContent());
			if (element.getElementsByTagName("INCLUDE_DATE").item(0).getTextContent().equalsIgnoreCase("true"))
				setting.setIncludeDate(true);
			else
				setting.setIncludeDate(false);
			setting.setIncludeDateFormat(element.getElementsByTagName("INCLUDE_DATE_FORMAT").item(0).getTextContent());
			setting.setQuery(element.getElementsByTagName("QUERY").item(0).getTextContent());
			setting.setPathKettle(element.getElementsByTagName("PATH_KETTLE").item(0).getTextContent());
			setting.setPathPentahoLog(element.getElementsByTagName("PATH_PENTAHO_LOG").item(0).getTextContent());
			setting.setHostDb(element.getElementsByTagName("HOST_DB").item(0).getTextContent());
			setting.setUserNameDb(element.getElementsByTagName("USERNAME_DB").item(0).getTextContent());
			setting.setPasswordDb(element.getElementsByTagName("PASSWORD_DB").item(0).getTextContent());
			setting.setPortDb(element.getElementsByTagName("PORT_DB").item(0).getTextContent());
			setting.setDbName(element.getElementsByTagName("DB_NAME").item(0).getTextContent());
			setting.setDelimiter(element.getElementsByTagName("DELIMITER").item(0).getTextContent());
		}
		
		nList = doc.getElementsByTagName("WISMA");
		List<String> schemas = new ArrayList<String>();
		
		for (int temp = 0; temp < nList.getLength(); temp++) {

			nNode = nList.item(temp);

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element element = (Element) nNode;
				schemas.add(element.getElementsByTagName("SCHEMA_NAME").item(0).getTextContent());
			}

		}
		setting.setSchema(schemas);
		return setting;
	}
	
	public String encrypt(String plainText) {
		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
		textEncryptor.setPassword(MY_SECRET_PASSWORD);
		return textEncryptor.encrypt(plainText);
	}
	
	public String decryptd(String encryptedText) {
		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
		textEncryptor.setPassword(MY_SECRET_PASSWORD);
		return textEncryptor.decrypt(encryptedText);
	}
}