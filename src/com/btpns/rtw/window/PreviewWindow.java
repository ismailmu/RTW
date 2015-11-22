package com.btpns.rtw.window;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.btpns.rtw.Settings;


public class PreviewWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Settings setting;
	/**
	 * Create the application.
	 */
	public PreviewWindow(Settings setting) {
		this.setSetting(setting);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		JTextArea txtPreview = new JTextArea();
		scrollPane.setViewportView(txtPreview);

		this.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
		this.setTitle("Preview File");
		this.setSize(400, 300);
		this.setModal(true);
		
		//txtPreview.setText(txtPreview.getText())
	}

	public Settings getSetting() {
		return setting;
	}

	public void setSetting(Settings setting) {
		this.setting = setting;
	}

}
