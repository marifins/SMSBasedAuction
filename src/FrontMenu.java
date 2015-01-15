import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import java.util.Vector;

import java.awt.Font;

import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class FrontMenu extends JInternalFrame{
	public static int HEIGHT = 530;
	public static int WIDHT = 715;
	public static String TITLE = "Swing Start";

	JTabbedPane tabbedPane = new JTabbedPane();
	JPanel mainPanel = new JPanel();
	JPanel pnlPeserta = new JPanel();
	JPanel pnlBarang = new JPanel();
	JPanel pnlUser = new JPanel();
	JPanel pnlPenawaran = new JPanel();
	JPanel pnlInbox = new JPanel();
	JPanel pnlOutbox = new JPanel();
	JPanel[] panels = {mainPanel,pnlPeserta,pnlBarang,pnlUser,pnlPenawaran,pnlInbox,pnlOutbox};

	private JScrollPane spTbPeserta = new JScrollPane();
	private JScrollPane spTbBarang = new JScrollPane();
	private JScrollPane spTbUser = new JScrollPane();
	private JScrollPane spTbPenawaran = new JScrollPane();
	private JScrollPane spInbox = new JScrollPane();
	private JScrollPane spOutbox = new JScrollPane();

	JLabel lblFilter = new JLabel("Filter");
	ImageIcon icon = new ImageIcon("Gambar/search.png");
  	JTable tbPeserta = new JTable();
  	JTable tbBarang = new JTable();
  	JTable tbUser = new JTable();
  	JTable tbPenawaran = new JTable();
  	JTable tbInbox = new JTable();
  	JTable tbOutbox = new JTable();

  	DefaultTableModel mdlPeserta;
  	DefaultTableModel mdlBarang;
  	DefaultTableModel mdlUser;
  	DefaultTableModel mdlPenawaran;
  	DefaultTableModel mdlInbox;
  	DefaultTableModel mdlOutbox;

	public FrontMenu(){
		super(TITLE);
		buildGUI();
		setSize(WIDHT,HEIGHT);
		((javax.swing.plaf.basic.BasicInternalFrameUI)
      	this.getUI()).setNorthPane(null);
		show();
		//ThreadRefresh r = new ThreadRefresh();
       	//r.start();
	}
	void buildGUI(){
		String[] tabs = {"", "Peserta", "Barang", "User", "Penawaran", "Inbox", "Outbox"};
		String[] tabsTips = {"Sistem Pelelangan Barang Berbasis SMS", "Data Peserta Lelang", "Data Barang Lelang", "Data User", "Data Penawaran Lelang", "Data Tabel Inbox", "Data Tabel Outbox"};
		for(int i=0; i<tabs.length; i++){
			panels[i].setBorder(new TitledBorder(tabsTips[i]));
			tabbedPane.addTab(tabs[i],null,panels[i],tabsTips[i]);
		}
		addComponentsToTabs();
		add("Center",tabbedPane);
	}
	void addComponentsToTabs(){
		setupMainPanel();
		setupPnlPeserta();
		setupPnlBarang();
		setupPnlUser();
		setupPnlPenawaran();
		setupPnlInbox();
		setupPnlOutbox();
	}
	void setupMainPanel(){

	}
	void setupPnlPeserta(){
		try {
			mdlPeserta = createTable(Bidder.getData());
	     	tbPeserta = new JTable(mdlPeserta);
     	} catch (SQLException ex) {
     		JOptionPane.showMessageDialog(null, ex.getMessage());
    	}

    	final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(mdlPeserta);
    	tbPeserta.setRowSorter(sorter);
    	spTbPeserta.setViewportView(tbPeserta);
    	final JTextField txtFilter = new JTextField(20);
    	txtFilter.addKeyListener(new KeyAdapter() {
	      	public void keyReleased(KeyEvent e) {
	        	String str = txtFilter.getText().trim();
	        	if (str.isEmpty()) {
	          	sorter.setRowFilter(null);
	        	} else {
	          	sorter.setRowFilter(RowFilter.regexFilter(str));
	        	}
	      	}
		});

		pnlPeserta.setLayout(null);
		pnlPeserta.add(new JLabel("Filter",icon,SwingConstants.LEFT)).setBounds(170, 28, 115, 20);
		txtFilter.setBounds(235, 28, 270, 20);
		spTbPeserta.setBounds(17, 56, 675, 415);
		pnlPeserta.add(txtFilter);
		pnlPeserta.add(spTbPeserta);
	}
	void setupPnlBarang(){
		try {
			mdlBarang = createTable(Barang.getData());
	     	tbBarang = new JTable(mdlBarang);
     	} catch (SQLException ex) {
     		JOptionPane.showMessageDialog(null, ex.getMessage());
    	}

    	final TableRowSorter<TableModel> sorter2 = new TableRowSorter<TableModel>(mdlBarang);
    	tbBarang.setRowSorter(sorter2);
    	spTbBarang.setViewportView(tbBarang);

    	final JTextField txtFilter2 = new JTextField(20);
    	txtFilter2.addKeyListener(new KeyAdapter() {
	      	public void keyReleased(KeyEvent e) {
	        	String str2 = txtFilter2.getText().trim();
	        	if (str2.isEmpty()) {
	          	sorter2.setRowFilter(null);
	        	} else {
	          	sorter2.setRowFilter(RowFilter.regexFilter(str2));
	        	}
	      	}
		});

		pnlBarang.setLayout(null);
		pnlBarang.add(new JLabel("Filter",icon,SwingConstants.LEFT)).setBounds(170, 28, 115, 20);
		txtFilter2.setBounds(235, 28, 270, 20);
		spTbBarang.setBounds(17, 56, 675, 415);
		pnlBarang.add(txtFilter2);
		pnlBarang.add(spTbBarang);
	}

	void setupPnlUser(){
		try {
			mdlUser = createTable(User.getData());
	     	tbUser = new JTable(mdlUser);
     	} catch (SQLException ex) {
     		JOptionPane.showMessageDialog(null, ex.getMessage());
    	}

    	final TableRowSorter<TableModel> sorterU = new TableRowSorter<TableModel>(mdlUser);
    	tbUser.setRowSorter(sorterU);
    	spTbUser.setViewportView(tbUser);

    	final JTextField txtFilterU = new JTextField(20);
    	txtFilterU.addKeyListener(new KeyAdapter() {
	      	public void keyReleased(KeyEvent e) {
	        	String strU = txtFilterU.getText().trim();
	        	if (strU.isEmpty()) {
	          	sorterU.setRowFilter(null);
	        	} else {
	          	sorterU.setRowFilter(RowFilter.regexFilter(strU));
	        	}
	      	}
		});

		pnlUser.setLayout(null);
		pnlUser.add(new JLabel("Filter",icon,SwingConstants.LEFT)).setBounds(170, 28, 115, 20);
		txtFilterU.setBounds(235, 28, 270, 20);
		spTbUser.setBounds(17, 56, 675, 415);
		pnlUser.add(txtFilterU);
		pnlUser.add(spTbUser);
	}

	void setupPnlPenawaran(){
		try {
			mdlPenawaran = createTable(Penawaran.getData());
	     	tbPenawaran = new JTable(mdlPenawaran);
     	} catch (SQLException ex) {
     		JOptionPane.showMessageDialog(null, ex.getMessage());
    	}

    	final TableRowSorter<TableModel> sorter3 = new TableRowSorter<TableModel>(mdlPenawaran);
    	tbPenawaran.setRowSorter(sorter3);
    	spTbPenawaran.setViewportView(tbPenawaran);
    	final JTextField txtFilter3 = new JTextField(20);
    	txtFilter3.addKeyListener(new KeyAdapter() {
	      	public void keyReleased(KeyEvent e) {
	        	String str3 = txtFilter3.getText().trim();
	        	if (str3.isEmpty()) {
	          	sorter3.setRowFilter(null);
	        	} else {
	          	sorter3.setRowFilter(RowFilter.regexFilter(str3));
	        	}
	      	}
		});

		pnlPenawaran.setLayout(null);
		pnlPenawaran.add(new JLabel("Filter",icon,SwingConstants.LEFT)).setBounds(170, 28, 115, 20);
		txtFilter3.setBounds(235, 28, 270, 20);
		spTbPenawaran.setBounds(17, 56, 675, 415);
		pnlPenawaran.add(txtFilter3);
		pnlPenawaran.add(spTbPenawaran);
	}

	void setupPnlInbox(){
		try {
			mdlInbox = createTable(Inbox.showInbox());
	     	tbInbox = new JTable(mdlInbox);
     	} catch (SQLException ex) {
     		JOptionPane.showMessageDialog(null, ex.getMessage());
    	}

    	final TableRowSorter<TableModel> sorter4 = new TableRowSorter<TableModel>(mdlInbox);
    	tbInbox.setRowSorter(sorter4);
    	spInbox.setViewportView(tbInbox);
    	final JTextField txtFilter4 = new JTextField(20);
    	txtFilter4.addKeyListener(new KeyAdapter() {
	      	public void keyReleased(KeyEvent e) {
	        	String str4 = txtFilter4.getText().trim();
	        	if (str4.isEmpty()) {
	          	sorter4.setRowFilter(null);
	        	} else {
	          	sorter4.setRowFilter(RowFilter.regexFilter(str4));
	        	}
	      	}
		});

		pnlInbox.setLayout(null);
		pnlInbox.add(new JLabel("Filter",icon,SwingConstants.LEFT)).setBounds(170, 28, 115, 20);
		txtFilter4.setBounds(235, 28, 270, 20);
		spInbox.setBounds(17, 56, 675, 415);
		pnlInbox.add(txtFilter4);
		pnlInbox.add(spInbox);
	}

	void setupPnlOutbox(){
		try {
			mdlOutbox = createTable(Outbox.showOutbox());
	     	tbOutbox = new JTable(mdlOutbox);
     	} catch (SQLException ex) {
     		JOptionPane.showMessageDialog(null, ex.getMessage());
    	}

    	final TableRowSorter<TableModel> sorter5= new TableRowSorter<TableModel>(mdlOutbox);
    	tbOutbox.setRowSorter(sorter5);
    	spOutbox.setViewportView(tbOutbox);
    	final JTextField txtFilter5 = new JTextField(20);
    	txtFilter5.addKeyListener(new KeyAdapter() {
	      	public void keyReleased(KeyEvent e) {
	        	String str5 = txtFilter5.getText().trim();
	        	if (str5.isEmpty()) {
	          	sorter5.setRowFilter(null);
	        	} else {
	          	sorter5.setRowFilter(RowFilter.regexFilter(str5));
	        	}
	      	}
		});

		pnlOutbox.setLayout(null);
		pnlOutbox.add(new JLabel("Filter",icon,SwingConstants.LEFT)).setBounds(170, 28, 115, 20);
		txtFilter5.setBounds(235, 28, 270, 20);
		spOutbox.setBounds(17, 56, 675, 415);
		pnlOutbox.add(txtFilter5);
		pnlOutbox.add(spOutbox);
	}

	private DefaultTableModel createTable(ResultSet rs) throws SQLException {
	    DefaultTableModel dtm = new DefaultTableModel();

	    ResultSetMetaData rsmd = rs.getMetaData();

	    int intNum = rsmd.getColumnCount();
	    Vector<Object> cols = new Vector<Object>();
	    for (int i=1; i<=intNum; i++) {
	      cols.addElement("<html><b>" +rsmd.getColumnName(i).toUpperCase() +"</b></html>");
	    }
	    dtm.setColumnIdentifiers(cols);
	    while(rs.next()){
	      String[] data = new String[intNum];
	      for (int i=0; i<intNum; i++){
	        data[i] = rs.getString(i+1);
	      }
	      dtm.addRow(data);
	    }
	    return dtm;
 	}

 	void showTable(){
 		try{
			setupPnlPeserta();
			setupPnlBarang();
			setupPnlUser();
			setupPnlPenawaran();
			setupPnlInbox();
			setupPnlOutbox();
	    } catch (Exception e) {
	      JOptionPane.showMessageDialog(null, e.getMessage());
	    }
	}

 	void hapusTable(){
		int r1 = mdlPeserta.getRowCount();
		for(int i=0; i<r1;i++)
			mdlPeserta.removeRow(0);

		int r2 = mdlBarang.getRowCount();
		for(int i=0; i<r2;i++)
			mdlBarang.removeRow(0);

		int r3 = mdlUser.getRowCount();
		for(int i=0; i<r3;i++)
			mdlUser.removeRow(0);

		int r4 = mdlPenawaran.getRowCount();
		for(int i=0; i<r4;i++)
			mdlPenawaran.removeRow(0);

		int r5 = mdlInbox.getRowCount();
		for(int i=0; i<r5;i++)
			mdlInbox.removeRow(0);

		int r6 = mdlOutbox.getRowCount();
		for(int i=0; i<r6;i++)
			mdlOutbox.removeRow(0);
	}
	void refresh(){
		hapusTable();
		showTable();
	}

	class ThreadRefresh extends Thread{
		public ThreadRefresh(){}
		public void run(){
			while(true){
				refresh();
				try{
					Thread.sleep(9000);
				}catch(InterruptedException ie){}
			}
		}
	}
}
