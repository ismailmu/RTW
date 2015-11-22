package com.btpns.rtw;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import net.miginfocom.swing.MigLayout;

import com.btpns.rtw.utils.DbUtil;
import com.btpns.rtw.utils.FileUtil;
import com.btpns.rtw.utils.FolderUtil;
import com.btpns.rtw.utils.TextUtil;
import com.btpns.rtw.window.RunningWindow;

public class Application implements ActionListener, KeyListener {

	private JFrame frmMain;
	private JTextField txtDelimiter;
	private JTextField txtPathOutput;
	private JTextField txtFileNameOutput;
	private JTextField txtHost;
	private JTextField txtUserName;
	private JTextField txtPort;
	private JPasswordField txtPassword;
	private JTextField txtPathKettle;
	private JTextField txtPathPentahoLog;
	private JComboBox cboOutputType;
	private JComboBox cboDateFormat;
	private JCheckBox chkSingleOutput;
	private JCheckBox chkIncludeDate;
	private JButton btnTestConnection;
	private JButton btnClearSelected;
	private JButton btnBrowsePathOutput;
	private JButton btnBrowsePathPentahoLog;
	private JButton btnBrowsePathSpoon;
	private JScrollPane scrollPane;
	private JButton btnSearch;
	private JTextField txtSearch;
	private JTable tblWisma;
	private JButton btnRun;
	private JButton btnSelectAll;
	private JButton btnPreviewFile;
	private JButton btnSaveSettings;
	private JButton btnLoadSettings;
	
	private static Settings setting;

	private DbUtil dbUtil = new DbUtil();
	private TextUtil txtUtil = new TextUtil();
	private FolderUtil folderUtil = new FolderUtil();
	private FileUtil fileUtil = new FileUtil();

	private static List<String> schemaSelected = new ArrayList<String>();

	private JScrollPane scrollPane_1;
	private JTextArea txtQuery;

	private SimpleDateFormat format = new SimpleDateFormat();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application app = new Application();
					app.frmMain.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws IOException
	 */
	public Application() throws IOException {
		initialize();
		initializeSettings(true);

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMain = new JFrame();
		frmMain.setTitle("Rekon Taman Siswa");
		frmMain.setBounds(100, 100, 553, 406);
		frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMain.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		frmMain.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		JPanel pnlFile = new JPanel();
		tabbedPane.add("File", pnlFile);
		pnlFile.setLayout(new MigLayout("", "[11px][][186px,grow][]",
				"[20px][][][][][][][][][][][]"));

		JLabel lblNewLabel = new JLabel("Output :");
		pnlFile.add(lblNewLabel, "cell 1 0,alignx trailing,aligny center");

		cboOutputType = new JComboBox();
		cboOutputType.addActionListener(this);
		cboOutputType.setModel(new DefaultComboBoxModel(new String[] {
				"Excel 2003", "Excel 2007", "CSV" }));
		pnlFile.add(cboOutputType, "cell 2 0,growx");

		chkSingleOutput = new JCheckBox("Single Output");
		pnlFile.add(chkSingleOutput, "cell 3 0");

		JLabel lblDelimiter = new JLabel("Delimiter :");
		pnlFile.add(lblDelimiter, "cell 1 1,alignx trailing");

		txtDelimiter = new JTextField();
		txtDelimiter.setEditable(false);
		pnlFile.add(txtDelimiter, "cell 2 1,growx");
		txtDelimiter.setColumns(10);

		JLabel lblPathOutput = new JLabel("Path output :");
		pnlFile.add(lblPathOutput, "cell 1 2,alignx trailing");

		txtPathOutput = new JTextField();
		pnlFile.add(txtPathOutput, "flowx,cell 2 2,growx");
		txtPathOutput.setColumns(10);

		btnBrowsePathOutput = new JButton("...");
		btnBrowsePathOutput.addActionListener(this);
		pnlFile.add(btnBrowsePathOutput, "cell 3 2");

		JLabel lblFileNameOutput = new JLabel("File name output :");
		pnlFile.add(lblFileNameOutput, "cell 1 3,alignx trailing");

		txtFileNameOutput = new JTextField();
		pnlFile.add(txtFileNameOutput, "cell 2 3,growx");
		txtFileNameOutput.setColumns(10);

		JLabel lblIncludeDate = new JLabel("Include date :");
		pnlFile.add(lblIncludeDate, "cell 1 4,alignx right");

		chkIncludeDate = new JCheckBox("");
		chkIncludeDate.addActionListener(this);
		pnlFile.add(chkIncludeDate, "cell 2 4");

		JLabel lblDateFormat = new JLabel("Date format :");
		pnlFile.add(lblDateFormat, "cell 1 5,alignx trailing");

		cboDateFormat = new JComboBox();
		cboDateFormat.setModel(new DefaultComboBoxModel(new String[] {
				"yyyyMMdd", "ddMMyyyy", "MMddyyyy" }));
		pnlFile.add(cboDateFormat, "cell 2 5,growx");

		JLabel lblPathLogPentaho_1 = new JLabel("Path kettle :");
		pnlFile.add(lblPathLogPentaho_1, "cell 1 8,alignx trailing");

		txtPathKettle = new JTextField();
		txtPathKettle.setColumns(10);
		pnlFile.add(txtPathKettle, "cell 2 8,growx");

		btnBrowsePathSpoon = new JButton("...");
		btnBrowsePathSpoon.addActionListener(this);
		pnlFile.add(btnBrowsePathSpoon, "cell 3 8");

		JLabel label = new JLabel("Path pentaho log :");
		pnlFile.add(label, "cell 1 9,alignx trailing");

		txtPathPentahoLog = new JTextField();
		txtPathPentahoLog.setToolTipText("");
		txtPathPentahoLog.setColumns(10);
		pnlFile.add(txtPathPentahoLog, "cell 2 9,growx");

		btnBrowsePathPentahoLog = new JButton("...");
		btnBrowsePathPentahoLog.addActionListener(this);
		pnlFile.add(btnBrowsePathPentahoLog, "cell 3 9");

		btnPreviewFile = new JButton("Preview");
		btnPreviewFile.setVisible(false);
		btnPreviewFile.addActionListener(this);
		pnlFile.add(btnPreviewFile, "cell 2 11");
		panel.add(tabbedPane);

		JPanel pnlDb = new JPanel();
		tabbedPane.addTab("Db", null, pnlDb, null);
		pnlDb.setLayout(new MigLayout("", "[11px][][186px,grow][]",
				"[20px][][][][][]"));

		JLabel lblHost = new JLabel("Host :");
		pnlDb.add(lblHost, "cell 1 0,alignx trailing");

		txtHost = new JTextField();
		pnlDb.add(txtHost, "cell 2 0,growx");
		txtHost.setColumns(10);

		JLabel lblUsername = new JLabel("Username :");
		pnlDb.add(lblUsername, "cell 1 1,alignx trailing");

		txtUserName = new JTextField();
		txtUserName.setColumns(10);
		pnlDb.add(txtUserName, "cell 2 1,growx");

		JLabel lblPassword = new JLabel("Password :");
		pnlDb.add(lblPassword, "cell 1 2,alignx trailing");

		txtPassword = new JPasswordField();
		pnlDb.add(txtPassword, "cell 2 2,growx");

		JLabel lblPort = new JLabel("Port :");
		pnlDb.add(lblPort, "cell 1 3,alignx trailing");

		txtPort = new JTextField();
		txtPort.setColumns(10);
		pnlDb.add(txtPort, "cell 2 3,growx");

		btnTestConnection = new JButton("Test Connection");
		btnTestConnection.addActionListener(this);
		pnlDb.add(btnTestConnection, "cell 2 5");

		JPanel pnlWisma = new JPanel();
		tabbedPane.add("Wisma", pnlWisma);
		pnlWisma.setLayout(new BorderLayout(0, 0));

		JPanel panel_2 = new JPanel();
		pnlWisma.add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(new BorderLayout(0, 0));

		txtSearch = new JTextField();
		panel_2.add(txtSearch, BorderLayout.CENTER);
		txtSearch.setColumns(10);

		btnSearch = new JButton("Search");
		btnSearch.addActionListener(this);
		panel_2.add(btnSearch, BorderLayout.EAST);

		JPanel panel_6 = new JPanel();
		pnlWisma.add(panel_6, BorderLayout.SOUTH);

		btnClearSelected = new JButton("Clear Selected");
		btnClearSelected.addActionListener(this);

		btnSelectAll = new JButton("Select All");
		btnSelectAll.addActionListener(this);
		panel_6.add(btnSelectAll);

		panel_6.add(btnClearSelected);

		scrollPane = new JScrollPane();
		pnlWisma.add(scrollPane, BorderLayout.CENTER);

		tblWisma = new JTable();
		scrollPane.setViewportView(tblWisma);

		JPanel pnlQuery = new JPanel();
		tabbedPane.add("Query", pnlQuery);
		pnlQuery.setLayout(new BorderLayout(0, 0));

		scrollPane_1 = new JScrollPane();
		pnlQuery.add(scrollPane_1, BorderLayout.CENTER);

		txtQuery = new JTextArea();
		txtQuery.addKeyListener(this);
		scrollPane_1.setViewportView(txtQuery);

		JPanel panel_1 = new JPanel();
		frmMain.getContentPane().add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		btnSaveSettings = new JButton("Save Settings");
		btnSaveSettings.addActionListener(this);
		panel_1.add(btnSaveSettings);

		btnLoadSettings = new JButton("Load Settings");
		btnLoadSettings.addActionListener(this);
		panel_1.add(btnLoadSettings);

		btnRun = new JButton("Run");
		btnRun.addActionListener(this);
		panel_1.add(btnRun);
	}

	private void initializeSettings(boolean isNew) throws IOException {
		if (!isNew) {
			try {
				frmMain.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				getWisma("");
				schemaSelected.clear();
				for (String str : setting.getSchema()) {
					for (int i = 0; i < tblWisma.getRowCount(); i++) {
						if (tblWisma.getValueAt(i, 1).toString().equalsIgnoreCase(str)) {
							tblWisma.setValueAt(true, i, 0);
							schemaSelected.add(str);
						}
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage(),
						"Error get wisma", JOptionPane.ERROR_MESSAGE);
			}finally {
				frmMain.setCursor(Cursor.getDefaultCursor());
			}
			
		}else {
			setting = fileUtil.getSettings();
		}
		cboOutputType.setSelectedItem(setting.getOutputType());
		stateOutputType(setting.getOutputType());

		txtPathOutput.setText(setting.getPathOutput());

		chkSingleOutput.setSelected(setting.isSingleOutput());
		txtPathOutput.setText(setting.getPathOutput());
		if (setting.isIncludeDate()) {
			txtFileNameOutput.setText(setting.getFileNameOutput().substring(9));
		}else {
			txtFileNameOutput.setText(setting.getFileNameOutput());
		}
		
		stateIncludeDate(setting.isIncludeDate());
		chkIncludeDate.setSelected(setting.isIncludeDate());
		txtPathKettle.setText(setting.getPathKettle());
		int i = setting.getPathPentahoLog().lastIndexOf("/");
		if (i>1) {
			String str = setting.getPathPentahoLog().substring(0,i);
			setting.setPathPentahoLog(str);
			txtPathPentahoLog.setText(str);
		}else {
			txtPathPentahoLog.setText(setting.getPathPentahoLog());
		}
		

		txtHost.setText(setting.getHostDb());
		txtUserName.setText(setting.getUserNameDb());
		txtPassword.setText(txtUtil.decryptd(setting.getPasswordDb()));
		txtPort.setText(setting.getPortDb());
		
		txtQuery.setText(setting.getQuery());
		
		
		
	}

	private void stateIncludeDate(boolean state) {
		if (state) {
			cboDateFormat.setSelectedItem(setting.getIncludeDateFormat());
			cboDateFormat.setEnabled(true);
		} else {
			cboDateFormat.setSelectedIndex(-1);
			cboDateFormat.setEnabled(false);
		}
	}

	private void stateOutputType(String type) {
		if (type.equalsIgnoreCase("csv")) {
			txtDelimiter.setText(setting.getDelimiter());
			txtDelimiter.setEditable(true);
		} else {
			txtDelimiter.setText("");
			txtDelimiter.setEditable(false);
		}
	}

	@SuppressWarnings("deprecation")
	private void getWisma(String search) throws Exception {
		final String[] columnNames = { "Select ?", "Schema Name",
				"Office Code", "Office Name", "File Date" };
		final List<Wisma> wismas = dbUtil.getWisma(txtHost.getText(),
				txtPort.getText(), setting.getDbName(), txtUserName.getText(),
				txtPassword.getText(), search,schemaSelected);

		tblWisma = new JTable(new FinalTableModel(columnNames, wismas));
		tblWisma.setPreferredScrollableViewportSize(tblWisma.getPreferredSize());
		scrollPane.setViewportView(tblWisma);

		tblWisma.setRowSelectionAllowed(true);
		tblWisma.setColumnSelectionAllowed(false);

		tblWisma.setDefaultEditor(Boolean.class, new DefaultCellEditor(
				new JCheckBox()) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellEditorComponent(JTable table,
					Object value, boolean isSelected, int row, int column) {
				// System.out.println("value = " + value + ", isSelected = " +
				// isSelected);
				String schemaCodeCurrent = tblWisma.getValueAt(row, 1)
						.toString();
				if (!(Boolean) value) {
					if (!schemaSelected.contains(schemaCodeCurrent)) {
						schemaSelected.add(schemaCodeCurrent);
					}
				} else {
					schemaSelected.remove(schemaCodeCurrent);
				}
				return super.getTableCellEditorComponent(table, value,
						isSelected, row, column);
			}

		});
	}
	
	@SuppressWarnings("deprecation")
	private void setSetting() throws IOException {
		format.applyPattern("yyyyMMdd");
		
		setting.setOutputType(cboOutputType.getSelectedItem().toString());
		setting.setSingleOutput(chkSingleOutput.isSelected());
		setting.setDelimiter(txtDelimiter.getText());
		
		String pathOutput = "";
		if (txtPathOutput.getText().equalsIgnoreCase(
				setting.getPathOutput())) {
			pathOutput = fileUtil.getPathSystem("." + txtPathOutput.getText(), txtPathOutput.getText());
		} else {
			pathOutput = txtPathOutput.getText();
		}
		setting.setPathOutput(pathOutput);
		
		setting.setIncludeDate(chkIncludeDate.isSelected());
		
		if (setting.isIncludeDate()) {
			setting.setIncludeDateFormat(cboDateFormat.getSelectedItem().toString());
			format.applyPattern(setting.getIncludeDateFormat());
			setting.setFileNameOutput(format.format(new Date()) + "_"
					+ txtFileNameOutput.getText());
		}else {
			setting.setIncludeDateFormat("");
			format.applyPattern(setting.getIncludeDateFormat());
			setting.setFileNameOutput(txtFileNameOutput.getText());
		}
		
		if (setting.getPathKettle().equalsIgnoreCase(txtPathKettle.getText())) {
			setting.setPathKettle(fileUtil.getPathSystem("." + txtPathKettle.getText(),txtPathKettle.getText()));
		}else {
			setting.setPathKettle(txtPathKettle.getText());
		}
			
		String outputPentahoLog = format.format(new Date())
				+ "_output.txt";
		if (txtPathPentahoLog.getText().equalsIgnoreCase(
				setting.getPathPentahoLog())) {
			outputPentahoLog = fileUtil.getPathSystem("." + txtPathPentahoLog.getText(),txtPathPentahoLog.getText())
					+ "/" + outputPentahoLog;
		} else {
			outputPentahoLog = txtPathPentahoLog.getText() + "/"
					+ outputPentahoLog;
		}
		setting.setPathPentahoLog(outputPentahoLog);
		setting.setHostDb(txtHost.getText());
		setting.setUserNameDb(txtUserName.getText());
		setting.setPasswordDb(txtUtil.encrypt(txtPassword.getText()));
		setting.setPortDb(txtPort.getText());
		setting.setSchema(schemaSelected);
		setting.setQuery(txtQuery.getText());
	}

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent arg) {
		if (arg.getSource() == btnTestConnection) {
			if (dbUtil.isConnect(txtHost.getText(), txtPort.getText(),
					setting.getDbName(), txtUserName.getText(),
					txtPassword.getText())) {
				JOptionPane.showMessageDialog(null, "Connection success",
						"Test Connection", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Connection failure",
						"Test Connection", JOptionPane.ERROR_MESSAGE);
			}
		} else if (arg.getSource() == chkIncludeDate) {
			stateIncludeDate(chkIncludeDate.isSelected());
		} else if (arg.getSource() == cboOutputType) {
			stateOutputType(cboOutputType.getSelectedItem().toString());
		} else if (arg.getSource() == btnSelectAll) {
			schemaSelected.clear();
			for (int i = 0; i < tblWisma.getRowCount(); i++) {
				tblWisma.setValueAt(true, i, 0);
				schemaSelected.add(tblWisma.getValueAt(i, 1).toString());
			}
		} else if (arg.getSource() == btnClearSelected) {
			for (int i = 0; i < tblWisma.getRowCount(); i++) {
				tblWisma.setValueAt(false, i, 0);
			}
			schemaSelected.clear();
		} else if (arg.getSource() == btnBrowsePathPentahoLog) {
			txtPathPentahoLog.setText(folderUtil.getFolder(txtPathPentahoLog
					.getText()));
		} else if (arg.getSource() == btnBrowsePathOutput) {
			txtPathOutput
					.setText(folderUtil.getFolder(txtPathOutput.getText()));
		} else if (arg.getSource() == btnBrowsePathSpoon) {
			txtPathKettle.setText(folderUtil.getFolder(txtPathKettle.getText()));
		} else if (arg.getSource() == btnSearch) {
			try {
				getWisma(txtSearch.getText());
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage(),
						"Error get wisma", JOptionPane.ERROR_MESSAGE);
			}
		} else if (arg.getSource() == btnPreviewFile) {
			
		} else if (arg.getSource() == btnSaveSettings) {
			try {
				setSetting();
				int select = folderUtil.saveFileSetting(setting);
				if (select == JFileChooser.APPROVE_OPTION)
					JOptionPane.showMessageDialog(null, "Success Save",
						"Save Setting", JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(),
						"Error Save Setting", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		} else if (arg.getSource() == btnLoadSettings) {
			try {
				setting = folderUtil.loadFileSetting();
				if (setting != null) {
					initializeSettings(false);
					JOptionPane.showMessageDialog(null, "Success Load",
							"Load Setting", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage(),
						"Error Load Setting", JOptionPane.ERROR_MESSAGE);
			}
		} else if (arg.getSource() == btnRun) {
			try {
				setSetting();
				if (setting.getSchema().size() > 0 && setting.getQuery().trim().length() > 0) {
					txtUtil.createXmlSetting(setting,null);
					
					final String execComd = setting.getPathKettle()
							+ "/kitchen.bat /file:"
							+ fileUtil.getPathSystem("./pentaho","./pentaho")
							+ "/main_rtw.kjb -param:XML_PATH=\"" + fileUtil.getPathSystem("./files/rtw_setting.xml"
									,"./files/rtw_setting.xml") + "\""
							+ " /level:Detailed /logfile:" + setting.getPathPentahoLog();
					
					fileUtil.deleteFile(setting.getPathOutput() + "/" + setting.getFileNameOutput());
					
					SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							RunningWindow prevWin;
							try {
								prevWin = new RunningWindow(execComd,"Run...");
								prevWin.process();
								prevWin.setVisible(true);
							} catch (IOException e) {
								JOptionPane.showMessageDialog(null, e.getMessage(),
										"Error Run", JOptionPane.ERROR_MESSAGE);
								e.printStackTrace();
							}
						}
					});
				}else {
					JOptionPane.showMessageDialog(null, "Must be selected wisma and input query",
							"Error Run", JOptionPane.ERROR_MESSAGE);
				}
				}catch(Exception e) {
					e.printStackTrace();
				}
				
		}
	}

	@Override
	public void keyPressed(KeyEvent event) {
		System.out.println(event.getKeyCode());
		if (event.getSource() == txtQuery && event.getKeyCode() == 121) {
			txtQuery.setText(txtUtil.encrypt(txtQuery.getText()));
		}
		
		if (event.getSource() == txtQuery && event.getKeyCode() == 122) {
			txtQuery.setText(txtUtil.decryptd(txtQuery.getText()));
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

}

class FinalTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String[] columnNames;
	List<Wisma> wismas;

	public FinalTableModel(String[] columnNames, List<Wisma> wismas) {
		this.columnNames = columnNames;
		this.wismas = wismas;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return wismas.size();
	}

	@Override
	public String getColumnName(int i) {
		return columnNames[i];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Wisma wisma = wismas.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return wisma.isSelected();
		case 1:
			return wisma.getSchemaName();
		case 2:
			return wisma.getOfficeCode();
		case 3:
			return wisma.getOfficeName();
		case 4:
			return wisma.getFileDate();
		default:
			return null;
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return Boolean.class;
		case 1:
			return String.class;
		case 2:
			return String.class;
		case 3:
			return String.class;
		case 4:
			return Date.class;
		default:
			return super.getColumnClass(columnIndex);
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return (columnIndex == 0);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Wisma wisma = wismas.get(rowIndex);
		if (columnIndex == 0) {
			wisma.setSelected((Boolean) aValue);
			fireTableCellUpdated(rowIndex, columnIndex);
		}
	}
}