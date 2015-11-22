package com.btpns.rtw.window;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

public class RunningWindow extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;

	private JTextArea txtPreview;
	private JButton btnClose;

	private MessageWorker worker;

	/**
	 * Create the application.
	 * 
	 * @throws IOException
	 */
	public RunningWindow(String execCommand, String title) throws IOException {
		this.title = title;

		initialize();
		
		worker = new MessageWorker(txtPreview, execCommand);
		worker.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (event.getNewValue().toString().equalsIgnoreCase("DONE")) {
					btnClose.setEnabled(true);
				}
			}
		});
	}

	public void process() {
		worker.execute();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws IOException
	 */
	private void initialize() {
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		txtPreview = new JTextArea();
		scrollPane.setViewportView(txtPreview);

		btnClose = new JButton("Close");
		btnClose.setEnabled(false);
		btnClose.addActionListener(this);
		getContentPane().add(btnClose, BorderLayout.SOUTH);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.setTitle(title);
		this.setSize(600, 400);
		this.setModal(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == btnClose) {
//			if (btnClose.getText().equalsIgnoreCase("Cancel")) {
//				btnClose.setText("Close");
//				//if (!worker.isCancelled())
//				worker.cancel(true);
//			} else {
				this.setVisible(false);
//			}
		}
	}
}

class MessageWorker extends SwingWorker<Integer, String> {
	private final JTextArea messagesTextArea;
	//private final String execCommand;
	private final Process proc;

	public MessageWorker(JTextArea messagesTextArea, String execCommand) throws IOException {
		this.messagesTextArea = messagesTextArea;
		//this.execCommand = execCommand;
		System.out.println(execCommand);
		proc = Runtime.getRuntime().exec(execCommand);
	}

	@Override
	protected Integer doInBackground() {
		String line;
		
		try {
			
			BufferedReader input = new BufferedReader(new InputStreamReader(
					proc.getInputStream()));

			while ((line = input.readLine()) != null) {
				if (isCancelled()) {
					proc.destroy();
					publish("CANCELED !!!");
					break;
				}
				System.out.println(line);
				publish(line);
			}
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			proc.waitFor();
		}catch(Exception e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
		return proc.exitValue();

	}

	@Override
	protected void process(List<String> chunks) {
		for (final String string : chunks) {
			messagesTextArea.append(string);
			messagesTextArea.append("\n");
		}
	}
}
