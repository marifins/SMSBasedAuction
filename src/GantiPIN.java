/**
 * @(#)GantiPIN.java
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

public class GantiPIN extends JInternalFrame implements ActionListener{

	private JPanel pnlPIN = new JPanel(),
				pnlFilter = new JPanel();

	private JLabel lblNoKTP	= new JLabel("No. KTP"),
				lblNoPonsel	= new JLabel("No. Ponsel"),
				lblPIN = new JLabel("PIN"),
				lblRePIN = new JLabel("Ulangi PIN"),
				lblFilter = new JLabel("Filter", new ImageIcon("Gambar/search.png"),SwingConstants.LEFT);

	private JTextField txtNoKTP = new JTextField(),
					txtNoPonsel	= new JTextField();

	private JPasswordField txtPIN = new JPasswordField(),
					txtRePIN	= new JPasswordField();

	private JButton btnChange = new JButton("Change", new ImageIcon("Gambar/modify2.png")),
					btnClear = new JButton("Clear", new ImageIcon("Gambar/refresh2.png"));

	private JScrollPane spTable = new JScrollPane();

	private Connection conn;
  	private Statement stmt;

  	JTable tbl = new JTable();
  	DefaultTableModel model;

	Vector<Object> cols = new Vector<Object>();
    Vector<Object> row;

	GantiPIN(){
		super("Ganti PIN Bidder", false, true, false, false);
		ImageIcon icon = new ImageIcon("Gambar/ponsel.png");
  		this.setFrameIcon(icon);
		setSize (712, 530);

		btnChange.setFont(new Font("Arial",Font.PLAIN,9));
		btnClear.setFont(new Font("Arial",Font.PLAIN,9));

		pnlPIN.setLayout(null);
		pnlFilter.setLayout(null);

		pnlPIN.setBorder(BorderFactory.createCompoundBorder(
	      BorderFactory.createEmptyBorder(0,0,0,0),
	      BorderFactory.createEtchedBorder()));

	    pnlFilter.setBorder(BorderFactory.createCompoundBorder(
	      BorderFactory.createEmptyBorder(0,0,0,0),
	      BorderFactory.createEtchedBorder()));

		String[] r = {"NO. KTP","NO. PONSEL","NAMA","ALAMAT"};
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

		txtNoKTP.setEditable(false);
		txtNoPonsel.setEditable(false);

		lblNoKTP.setBounds(20, 20, 115, 25);
	    lblNoPonsel.setBounds(20, 50, 115, 25);
		lblPIN.setBounds(20, 80, 115, 25);
		lblRePIN.setBounds(20, 110, 115, 25);

		txtNoKTP.setBounds (125, 20, 155, 25);
		txtNoPonsel.setBounds (125, 50, 155, 25);
		txtPIN.setBounds (125, 80, 125, 25);
		txtRePIN.setBounds (125, 110, 125, 25);

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

		pnlPIN.add (lblNoKTP);
		pnlPIN.add (txtNoKTP);
		pnlPIN.add (lblNoPonsel);
		pnlPIN.add (txtNoPonsel);
		pnlPIN.add (lblPIN);
		pnlPIN.add (txtPIN);
		pnlPIN.add (lblRePIN);
		pnlPIN.add (txtRePIN);
		pnlPIN.add (btnChange);
		pnlPIN.add (btnClear);

		pnlFilter.add (lblFilter);
		pnlFilter.add (txtFilter);
		pnlFilter.add (spTable);

		getContentPane().setLayout(null);
		getContentPane().add(pnlPIN);
		pnlPIN.setBounds (15, 15, 675, 160);
		getContentPane().add(pnlFilter);
		pnlFilter.setBounds (15, 185, 675, 295);

		setVisible(true);
	}

	void showTable(){
		try {
	      stmt = Database.con.createStatement();
	      ResultSet rs = stmt.executeQuery(
	        "SELECT no_ktp, no_ponsel, nama, alamat FROM bidder");
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

            txtNoKTP.setText(tbl.getValueAt(row,0).toString());
            txtNoPonsel.setText(tbl.getValueAt(row,1).toString());
        }
        catch(Exception se) {System.err.println(se);}
    }

    private void focusTable(FocusEvent fe){
			btnChange.setEnabled(true);
			txtPIN.requestFocus();
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
		String noKTP = txtNoKTP.getText();
		String noPonsel = txtNoPonsel.getText();
		String pin = txtPIN.getText();
		String rePin = txtRePIN.getText();
		if(noKTP.equals("") || noPonsel.equals("") || pin.equals("") || rePin.equals("")){
			JOptionPane.showMessageDialog(null, "Field empty..");
		}else{
			if(pin.equals(rePin)){
				int respond = JOptionPane.showConfirmDialog(null, "Yakin akan ganti PIN Peserta?\nNo. KTP = " +noKTP +"\nNoPonsel = " +noPonsel, "Konfirmasi", JOptionPane.YES_NO_OPTION);
				if(respond == 0){
					Bidder.changePIN(noKTP,noPonsel, pin);
					refresh();
				}
			}else
				JOptionPane.showMessageDialog(null, "PIN did not match..");
		}
	}

	/* Fungsi Kosongkan Text */
	void clear(){
		txtNoKTP.setText("");
		txtNoPonsel.setText("");
		txtPIN.setText("");
		txtRePIN.setText("");
		txtPIN.requestFocus();
	}
}