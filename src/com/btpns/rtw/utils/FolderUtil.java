package com.btpns.rtw.utils;

import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.btpns.rtw.Settings;

public class FolderUtil {
	final FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter("xml files (*.xml)", "xml");
	private TextUtil textUtil = new TextUtil();
	
	public String getFolder(String original) {
		JFileChooser folderBrowse = new JFileChooser();
		folderBrowse.setAcceptAllFileFilterUsed(false);
		folderBrowse.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (folderBrowse.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			return folderBrowse.getCurrentDirectory().getAbsolutePath().replace("\\", "/");
		} else {
			return original;
		}
	}
	
	public Integer saveFileSetting(Settings setting) throws IOException {
		JFileChooser fileSave = new JFileChooser();
		fileSave.setDialogTitle("Save Setting");
		fileSave.setAcceptAllFileFilterUsed(false);
		fileSave.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileSave.setFileFilter(xmlfilter);
        fileSave.showSaveDialog(null);
        int select = JFileChooser.APPROVE_OPTION;
        
		if (select == JFileChooser.APPROVE_OPTION) {
		 	textUtil.createXmlSetting(setting,fileSave.getSelectedFile().getCanonicalPath().replace("\\","/"));
		}
		
		return select;
	}
	
	public Settings loadFileSetting() throws SAXException, IOException, ParserConfigurationException {
		Settings setting = null;
		JFileChooser fileBrowse = new JFileChooser();
		fileBrowse.setDialogTitle("Load Setting");
		fileBrowse.setAcceptAllFileFilterUsed(false);
		fileBrowse.setFileFilter(xmlfilter);
		fileBrowse.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (fileBrowse.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			setting = textUtil.readXmlSetting(fileBrowse.getSelectedFile().getCanonicalPath().replace("\\","/"));
		}
		return setting;
	}
}
