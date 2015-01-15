/**
 * @(#)GantiPass.java
 *
 *
 * @author
 * @version 1.00 2010/4/26
 */

import javax.swing.JPanel;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.util.Vector;

import java.awt.Font;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class GantiPass extends JInternalFrame implements ActionListener{

	private JPanel pnlPass = new JPanel(),
				pnlFilter = new JPanel();

	private JLabel lblNIP	= new JLabel("NIP"),
				lblUsername	= new JLabel("Username"),
				lblPass = new JLabel("Password"),
				lblRePass = new JLabel("Ulangi Password"),
				lblFilter = new JLabel("Filter", new ImageIcon("Gambar/search.png"),SwingConstants.LEFT);

	private JTextField txtNIP = new JTextField(),
					txtUsername	= new JTextField();

	private JPasswordField txtPass = new JPasswordField(),
					txtRePass	= new JPasswordField();

	private JButton btnChange = new JButton("Change", new ImageIcon("Gambar/modify2.png")),
					btnClear = new JButton("Clear", new ImageIcon("Gambar/refresh2.png"));

	private JScrollPane spTable = new JScrollPane();

	private Connection conn;
  	private Statement stmt;

  	JTable tbl = new JTable();
  	DefaultTableModel model;

	Vector<Object> cols = new Vector<Object>();
    Vector<Object> row;

	GantiPass(){
		super("Ganti Password User", false, true, false, false);
		ImageIcon icon = new ImageIcon("Gambar/c_pass.png");
  		this.setFrameIcon(icon);
		setSize (712, 530);

		btnChange.setFont(new Font("Arial",Font.PLAIN,9));
		btnClear.setFont(new Font("Arial",Font.PLAIN,9));

		pnlPass.setLayout(null);
		pnlFilter.setLayout(null);

		pnlPass.setBorder(BorderFactory.createCompoundBorder(
	      BorderFactory.createEmptyBorder(0,0,0,0),
	      BorderFactory.createEtchedBorder()));

	    pnlFilter.setBorder(BorderFactory.createCompoundBorder(
	      BorderFactory.createEmptyBorder(0,0,0,0),
	      BorderFactory.createEtchedBorder()));

		String[] r = {"NIP","NAMA LENGKAP","USERNAME","LEVEL"};
		for (int i=0; i<r.length; i++){
	        cols.addElement("<html><b>" +r[i] +"</br><html>");
	    }
		model = new DefaultTableModel(null, cols);
    	tbl.setModel(model);
    	tbl.setFont(new Font("Tahoma",0,12));
        showTable();

    	final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
    	tbl.setRowSorter(sorter);
    	spTable.setViewportView(tbl);

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

		txtNIP.setEditable(false);
		txtUsername.setEditable(false);

		lblNIP.setBounds(20, 20, 115, 25);
	    lblUsername.setBounds(20, 50, 115, 25);
		lblPass.setBounds(20, 80, 115, 25);
		lblRePass.setBounds(20, 110, 115, 25);

		txtNIP.setBounds (125, 20, 155, 25);
		txtUsername.setBounds (125, 50, 155, 25);
		txtPass.setBounds (125, 80, 125, 25);
		txtRePass.setBounds (125, 110, 125, 25);

		lblFilter.setBounds (20, 15, 115, 25);
		txtFilter.setBounds (125, 15, 270, 25);
		spTable.setBounds (15, 50, 650, 225);

		btnChange.setBounds (450, 40, 95, 35);
		btnClear.setBounds (450, 80, 95, 35);


  		tbl.addMouseListener(
        	new java.awt.event.MouseAdapter() {
	        	@Override
				public void mouseClicked(
				java.awt.event.MouseEvent evt) {
				TableMouseClick(evt);
			}
		});

		tbl.addFocusListener(
        	new java.awt.event.FocusAdapter() {
			@Override
			public void focusGained(
			java.awt.event.FocusEvent evt) {
				focusTable(evt);
			}
        });

		btnChange.setEnabled(true);
		btnClear.setEnabled(true);

		btnChange.addActionListener (this);
		btnClear.addActionListener (this);

		pnlPass.add (lblNIP);
		pnlPass.add (txtNIP);
		pnlPass.add (lblUsername);
		pnlPass.add (txtUsername);
		pnlPass.add (lblPass);
		pnlPass.add (txtPass);
		pnlPass.add (lblRePass);
		pnlPass.add (txtRePass);
		pnlPass.add (btnChange);
		pnlPass.add (btnClear);

		pnlFilter.add (lblFilter);
		pnlFilter.add (txtFilter);
		pnlFilter.add (spTable);

		getContentPane().setLayout(null);
		getContentPane().add(pnlPass);
		pnlPass.setBounds (15, 15, 675, 160);
		getContentPane().add(pnlFilter);
		pnlFilter.setBounds (15, 185, 675, 295);

		setVisible(true);
	}

	void showTable(){
		try {
	      stmt = Database.con.createStatement();
	      ResultSet rs = stmt.executeQuery(
	        "SELECT nip, nama_lengkap, username, level FROM user");
	      ResultSetMetaData rsmd = rs.getMetaData();
	      int intNum = rsmd.getColumnCount();
	      while(rs.next()){
	        row = new Vector<Object>();
	        for (int i=1; i<=intNum; i++){
	          row.addElement(rs.getObject(i));
	        }
	        model.addRow(row);
	      }
	    } catch (SQLException ex) {
	      JOptionPane.showMessageDialog(null, ex.getMessage());
	    }
	}

	void hapusTable(){
		int row = model.getRowCount();
		for(int i=0; i<row;i++)
			model.removeRow(0);
	}
	void refresh(){
		hapusTable();
		showTable();
	}

	private void TableMouseClick(MouseEvent me){
      try {
            int row = tbl.getSelectedRow();
            int col = tbl.getSelectedColumn();

            txtNIP.setText(tbl.getValueAt(row,0).toString());
            txtUsername.setText(tbl.getValueAt(row,2).toString());
        }
        catch(Exception se) {System.err.println(se);}
    }

    private void focusTable(FocusEvent fe){
			btnChange.setEnabled(true);
			txtPass.requestFocus();
    }

	public void actionPerformed (ActionEvent ae){
		Object obj = ae.getSource();
		if (obj == btnChange){
			change();
			}
		clear();
	}

	/* method change password user* */
	void change(){
		String nip = txtNIP.getText();
		String username = txtUsername.getText();
		String pass = txtPass.getText();
		String repass = txtRePass.getText();
		if(nip.equals("") || username.equals("") || pass.equals("") || repass.equals("")){
			JOptionPane.showMessageDialog(null, "Field empty..");
		}else{
			if(pass.equals(repass)){
				int respond = JOptionPane.showConfirmDialog(null, "Yakin akan ganti Password User?\nNIP = " +nip +"\nUsername = " +username, "Konfirmasi", JOptionPane.YES_NO_OPTION);
				if(respond == 0){
					User.changePass(username, pass);
					refresh();
				}
			}else
				JOptionPane.showMessageDialog(null, "Password did not match..");
		}
	}

	/* Fungsi Kosongkan Text */
	void clear(){
		txtNIP.setText("");
		txtUsername.setText("");
		txtPass.setText("");
		txtRePass.setText("");
		txtPass.requestFocus();
	}
}