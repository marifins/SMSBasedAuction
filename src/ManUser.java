/**
 * @(#)ManUser.java
 *
 *
 * @author
 * @version 1.00 2010/4/26
 */

import javax.swing.JPanel;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
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

import java.util.Enumeration;
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

public class ManUser extends JInternalFrame implements ActionListener, FocusListener{

	private JPanel pnlUser = new JPanel(),
				pnlFilter = new JPanel();

	private JLabel lblNIP = new JLabel("NIP"),
				lblNama = new JLabel("Nama Lengkap"),
				lblUsername	= new JLabel("Username"),
				lblFilter = new JLabel("Filter", new ImageIcon("Gambar/search.png"),SwingConstants.LEFT),
				lblLevel = new JLabel("Level");

	private JTextField txtNIP = new JTextField(),
					txtNama = new JTextField(),
					txtUsername	= new JTextField();

	private JRadioButton rb1 = new JRadioButton("Operator");
	private JRadioButton rb2 = new JRadioButton("Administrator");
	private ButtonGroup g = new ButtonGroup();

	private JButton btnAdd = new JButton("Add", new ImageIcon("Gambar/add2.png")),
					btnEdit = new JButton("Edit", new ImageIcon("Gambar/modify2.png")),
					btnDelete = new JButton("Delete", new ImageIcon("Gambar/delete2.png")),
					btnClear = new JButton("Clear", new ImageIcon("Gambar/refresh2.png"));

	private JScrollPane spTable = new JScrollPane();

	private Connection conn;
  	private Statement stmt;

  	JTable tbl = new JTable();
  	DefaultTableModel model;

	Vector<Object> cols = new Vector<Object>();
    Vector<Object> row;

	ManUser(){
		super("Form User", false, true, false, false);
		ImageIcon icon = new ImageIcon("Gambar/user.png");
  		this.setFrameIcon(icon);
		setSize (712, 530);

		btnAdd.setFont(new Font("Arial",Font.PLAIN,9));
		btnEdit.setFont(new Font("Arial",Font.PLAIN,9));
		btnDelete.setFont(new Font("Arial",Font.PLAIN,9));
		btnClear.setFont(new Font("Arial",Font.PLAIN,9));

		pnlUser.setLayout(null);
		pnlFilter.setLayout(null);

		pnlUser.setBorder(BorderFactory.createCompoundBorder(
	      BorderFactory.createEmptyBorder(0,0,0,0),
	      BorderFactory.createEtchedBorder()));

	    pnlFilter.setBorder(BorderFactory.createCompoundBorder(
	      BorderFactory.createEmptyBorder(0,0,0,0),
	      BorderFactory.createEtchedBorder()));

		rb1.setActionCommand("1");
		rb2.setActionCommand("2");
        g.add(rb1);
        g.add(rb2);

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

		lblNIP.setBounds(20, 20, 115, 25);
		lblNama.setBounds(20, 50, 110, 25);
	    lblUsername.setBounds(20, 80, 110, 25);
		lblLevel.setBounds(20, 110, 115, 25);

		txtNIP.setBounds (125, 20, 125, 25);
		txtNama.setBounds (125, 50, 245, 25);
		txtUsername.setBounds (125, 80, 125, 25);
		rb1.setBounds (125, 110, 80, 25);
		rb2.setBounds (215, 110, 125, 25);

		lblFilter.setBounds (20, 15, 115, 25);
		txtFilter.setBounds (125, 15, 270, 25);
		spTable.setBounds (15, 50, 650, 180);

		btnAdd.setBounds (500, 25, 90, 35);
		btnEdit.setBounds (500, 65, 90, 35);
		btnDelete.setBounds (500, 105, 90, 35);
		btnClear.setBounds (500, 145, 90, 35);

	    txtNIP.addKeyListener (new KeyAdapter(){
			public void keyTyped (KeyEvent ke){
				char c = ke.getKeyChar ();
				if (!((Character.isDigit (c) || (c == KeyEvent.VK_BACK_SPACE)))){
					getToolkit().beep (); ke.consume ();
      				}
    			}
  		});

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

		btnAdd.setEnabled(false);
		btnEdit.setEnabled(false);
		btnDelete.setEnabled(false);
		btnClear.setEnabled(true);

		txtNIP.addFocusListener (this);
		btnAdd.addActionListener (this);
		btnEdit.addActionListener (this);
		btnDelete.addActionListener (this);
		btnClear.addActionListener (this);

		pnlUser.add (lblNIP);
		pnlUser.add (txtNIP);
		pnlUser.add (lblNama);
		pnlUser.add (txtNama);
		pnlUser.add (lblUsername);
		pnlUser.add (txtUsername);
		pnlUser.add (lblLevel);
		pnlUser.add (rb1);
		pnlUser.add (rb2);
		pnlUser.add (btnAdd);
		pnlUser.add (btnEdit);
		pnlUser.add (btnDelete);
		pnlUser.add (btnClear);

		pnlFilter.add (lblFilter);
		pnlFilter.add (txtFilter);
		pnlFilter.add (spTable);

		getContentPane().setLayout(null);
		getContentPane().add(pnlUser);
		pnlUser.setBounds (15, 15, 675, 210);
		getContentPane().add(pnlFilter);
		pnlFilter.setBounds (15, 235, 675, 245);

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

	public void focusGained(FocusEvent fe){}
	public void focusLost(FocusEvent fe){
		if (txtNIP.getText().equals("")){
			}
		else{
			Cari();
		    }
	}

	private void TableMouseClick(MouseEvent me){
      try {
            int row = tbl.getSelectedRow();
            int col = tbl.getSelectedColumn();

            txtNIP.setText(tbl.getValueAt(row,0).toString());
            txtNama.setText(tbl.getValueAt(row,1).toString());
            txtUsername.setText(tbl.getValueAt(row,2).toString());
            //taAlamat.setText(tbl.getValueAt(row,3).toString());
        }
        catch(Exception se) {System.err.println(se);}
    }

    private void focusTable(FocusEvent fe){
    		btnAdd.setEnabled(false);
			btnEdit.setEnabled(true);
			btnDelete.setEnabled(true);
			txtNIP.requestFocus();
    }

	String cekRadio(){
		return g.getSelection().getActionCommand();
	}

	public void actionPerformed(ActionEvent ae){
		Object obj = ae.getSource();
		if (obj == btnAdd){
			tambah();
			}
		if (obj == btnEdit){
			Ubah();
			}
		if (obj == btnDelete){
			hapus();
			}
		clear();
	}

	void Cari(){
		String nip = txtNIP.getText();
		User u = new User();
		boolean cek = u.cekNIP(nip);
		if(cek){
			//Jika nip ada
			u.getDetails(nip);
			txtNIP.setText(u.getNIP());
			txtNama.setText(u.getNama());
			txtUsername.setText(u.getUsername());
			btnAdd.setEnabled(false);
			btnEdit.setEnabled(true);
			btnDelete.setEnabled(true);
			txtNama.requestFocus();
		}else{
			//Jika nip belum terdaftar
			btnAdd.setEnabled(true);
			btnEdit.setEnabled(false);
			btnDelete.setEnabled(false);
			txtUsername.requestFocus();
		}
	}

	/* method tambah data user*/
	void tambah(){
		String NIP = txtNIP.getText();
		String nama = txtNama.getText();
		String username = txtUsername.getText();
		String level = cekRadio();
		boolean cek = User.cekUsername(username);
		if(NIP.equals("") || nama.equals("") || username.equals("") || level.equals("")){
			JOptionPane.showMessageDialog(null, "Field empty..");
		}else if(cek){
			JOptionPane.showMessageDialog(null, "Username sudah terdaftar, pilih username lain");
		}else{
			User.tambahUser(NIP, nama, username, level);
			refresh();
		}
    }

	/* method update data user* */
	void Ubah(){
		String NIP = txtNIP.getText();
		String nama = txtNama.getText();
		String username = txtUsername.getText();
		String level = cekRadio();
		if(NIP.equals("") || nama.equals("") || username.equals("")){
			JOptionPane.showMessageDialog(null, "Field empty..");
		}else{
			User.updateUser(NIP, nama, username, level);
			refresh();
		}
	}

	/* method hapus data user* */
	void hapus(){
		String NIP = txtNIP.getText();
		String nama = txtNama.getText();
		int respond = JOptionPane.showConfirmDialog(null, "Yakin akan hapus data peserta?\nNIP = " +NIP +"\nNama = " +nama, "Konfirmasi", JOptionPane.YES_NO_OPTION);
		if(respond == 0){
			User.deleteUser(NIP);
			refresh();
		}
	}

	/* Fungsi Kosongkan Text */
	void clear(){
		txtNIP.setText("");
		txtNama.setText("");
		txtUsername.setText("");
		btnAdd.setEnabled(false);
		btnEdit.setEnabled(false);
		btnDelete.setEnabled(false);
		txtNIP.requestFocus();
	}
}