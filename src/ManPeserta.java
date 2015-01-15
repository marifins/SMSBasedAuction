/**
 * @(#)ManPeserta.java
 *
 *
 * @author
 * @version 1.00 2010/4/26
 */

import javax.swing.JPanel;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import javax.swing.event.InternalFrameListener;

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

public class ManPeserta extends JInternalFrame implements ActionListener, FocusListener{

	private JPanel pnlPeserta = new JPanel(),
				pnlFilter = new JPanel();

	private JLabel lblNoKtp = new JLabel("No. KTP"),
				lblNoPonsel	= new JLabel("No. Ponsel"),
				lblNamaPeserta	= new JLabel("Nama Peserta"),
				lblAlamat = new JLabel("Alamat Peserta"),
				lblFilter = new JLabel("Filter", new ImageIcon("Gambar/search.png"),SwingConstants.LEFT);

	private JTextField txtNoKtp	= new JTextField(),
					txtNoPonsel	= new JTextField(),
					txtNamaPeserta	= new JTextField(),
					txtFilter = new JTextField();
	private JTextArea taAlamat = new JTextArea();

	private JButton btnAdd = new JButton("Add", new ImageIcon("Gambar/add2.png")),
					btnEdit = new JButton("Edit", new ImageIcon("Gambar/modify2.png")),
					btnDelete = new JButton("Delete", new ImageIcon("Gambar/delete2.png")),
					btnClear = new JButton("Clear", new ImageIcon("Gambar/refresh2.png"));

	private JScrollPane spAlamat = new JScrollPane();
	private JScrollPane spTable = new JScrollPane();

	private Connection conn;
  	private Statement stmt;

  	JTable tbl = new JTable();
  	DefaultTableModel model;

	Vector<Object> cols = new Vector<Object>();
    Vector<Object> row;

	ManPeserta (){
		super ("Form Peserta", false, true, false, false);
		ImageIcon icon = new ImageIcon("Gambar/bidder.png");
  		this.setFrameIcon(icon);
		setSize (712, 530);

		btnAdd.setFont(new Font("Arial",Font.PLAIN,9));
		btnEdit.setFont(new Font("Arial",Font.PLAIN,9));
		btnDelete.setFont(new Font("Arial",Font.PLAIN,9));
		btnClear.setFont(new Font("Arial",Font.PLAIN,9));

		pnlPeserta.setLayout(null);
		pnlFilter.setLayout(null);

		pnlPeserta.setBorder(BorderFactory.createCompoundBorder(
	      BorderFactory.createEmptyBorder(0,0,0,0),
	      BorderFactory.createEtchedBorder()));

	    pnlFilter.setBorder(BorderFactory.createCompoundBorder(
	      BorderFactory.createEmptyBorder(0,0,0,0),
	      BorderFactory.createEtchedBorder()));

        spAlamat.setViewportView(taAlamat);

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

		lblNoKtp.setBounds (20, 20, 115, 25);
		lblNoPonsel.setBounds (20, 50, 110, 25);
	    lblNamaPeserta.setBounds (20, 80, 110, 25);
		lblAlamat.setBounds (20, 110, 115, 25);

		txtNoKtp.setBounds (125, 20, 190, 25);
		txtNoPonsel.setBounds (125, 50, 145, 25);
		txtNamaPeserta.setBounds (125, 80, 270, 25);
		spAlamat.setBounds (125, 110, 270, 80);

		lblFilter.setBounds (20, 15, 115, 25);
		txtFilter.setBounds (125, 15, 270, 25);
		spTable.setBounds (15, 50, 650, 180);

		btnAdd.setBounds (500, 25, 90, 35);
		btnEdit.setBounds (500, 65, 90, 35);
		btnDelete.setBounds (500, 105, 90, 35);
		btnClear.setBounds (500, 145, 90, 35);


		txtNoKtp.addKeyListener (new KeyAdapter(){
			public void keyTyped (KeyEvent ke){
				char c = ke.getKeyChar ();
				if (!((Character.isDigit (c) || (c == KeyEvent.VK_BACK_SPACE)))){
					getToolkit().beep (); ke.consume ();
      				}
    			}
  		});

  		txtNoPonsel.addKeyListener (new KeyAdapter(){
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

		txtNoKtp.addFocusListener (this);
		btnAdd.addActionListener (this);
		btnEdit.addActionListener (this);
		btnDelete.addActionListener (this);
		btnClear.addActionListener (this);

		pnlPeserta.add (lblNoKtp);
		pnlPeserta.add (txtNoKtp);
		pnlPeserta.add (lblNoPonsel);
		pnlPeserta.add (txtNoPonsel);
		pnlPeserta.add (lblNamaPeserta);
		pnlPeserta.add (txtNamaPeserta);
		pnlPeserta.add (lblAlamat);
		pnlPeserta.add (spAlamat);
		pnlPeserta.add (btnAdd);
		pnlPeserta.add (btnEdit);
		pnlPeserta.add (btnDelete);
		pnlPeserta.add (btnClear);

		pnlFilter.add (lblFilter);
		pnlFilter.add (txtFilter);
		pnlFilter.add (spTable);

		getContentPane().setLayout(null);
		getContentPane().add(pnlPeserta);
		pnlPeserta.setBounds (15, 15, 675, 210);
		getContentPane().add(pnlFilter);
		pnlFilter.setBounds (15, 235, 675, 245);

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

	public void focusGained (FocusEvent fe){}
	public void focusLost (FocusEvent fe){
		if (txtNoKtp.getText().equals("")){
		}
		else{
			Cari();
		}
	}

	private void TableMouseClick(MouseEvent me){
      try {
            int row = tbl.getSelectedRow();
            int col = tbl.getSelectedColumn();

            txtNoKtp.setText(tbl.getValueAt(row,0).toString());
            txtNoPonsel.setText(tbl.getValueAt(row,1).toString());
            txtNamaPeserta.setText(tbl.getValueAt(row,2).toString());
            taAlamat.setText(tbl.getValueAt(row,3).toString());
        }
        catch(Exception se) {System.err.println(se);}
    }

    private void focusTable(FocusEvent fe){
    		btnAdd.setEnabled(false);
			btnEdit.setEnabled(true);
			btnDelete.setEnabled(true);
			txtNoPonsel.requestFocus();
    }


	public void actionPerformed (ActionEvent ae){
		Object obj = ae.getSource();
		if (obj == btnAdd){
			tambah();
			}
		if (obj == btnEdit){
			ubah();
			}
		if (obj == btnDelete){
			hapus();
			}
		clear();
	}

	void Cari(){
		String ktp = txtNoKtp.getText();
		Bidder b = new Bidder();
		boolean cek = b.cekNoKTP(ktp);
		if(cek){
			//Jika No KTP sudah terdaftar
			b.getDetails(ktp);
			txtNoKtp.setText(b.getNoKTP());
			txtNoPonsel.setText(b.getNoPonsel());
			txtNamaPeserta.setText(b.getNama());
			taAlamat.setText(b.getAlamat());
			btnAdd.setEnabled(false);
			btnEdit.setEnabled(true);
			btnDelete.setEnabled(true);
			txtNoPonsel.requestFocus();
		}else{
			//Jika No KTP belum terdaftar
			btnAdd.setEnabled(true);
			btnEdit.setEnabled(false);
			btnDelete.setEnabled(false);
			txtNamaPeserta.requestFocus();
		}

	}

	/* method tambah data peserta*/
	void tambah(){
		String noKtp = txtNoKtp.getText();
		String noPonsel = txtNoPonsel.getText();
		String nama = txtNamaPeserta.getText();
		String alamat = taAlamat.getText();
		if(noKtp.equals("") || noPonsel.equals("") || nama.equals("") || alamat.equals("")){
			JOptionPane.showMessageDialog(null, "Field empty..");
		}else{
			Bidder.tambahBidder(noKtp, noPonsel, nama, alamat);
			refresh();
		}
    }

	/* method update data peserta* */
	void ubah(){
		String noKtp = txtNoKtp.getText();
		String noPonsel = txtNoPonsel.getText();
		String nama = txtNamaPeserta.getText();
		String alamat = taAlamat.getText();
		if(noKtp.equals("") || noPonsel.equals("") || nama.equals("") || alamat.equals("")){
			JOptionPane.showMessageDialog(null, "Field empty..");
		}else{
			Bidder.updateBidder(noKtp, noPonsel, nama, alamat);
			refresh();
		}
	}

	/* method hapus data peserta* */
	void hapus(){
		String noKtp = txtNoKtp.getText();
		String nama = txtNamaPeserta.getText();
		int respond = JOptionPane.showConfirmDialog(null, "Yakin akan hapus data peserta?\nNo. KTP = " +noKtp +"\nNama = " +nama, "Konfirmasi", JOptionPane.YES_NO_OPTION);
		if(respond == 0){
			Bidder.deleteBidder(noKtp);
			refresh();
		}
	}

	/* Fungsi Kosongkan Text */
	void clear(){
		txtNoKtp.setText ("");
		txtNoPonsel.setText("");
		txtNamaPeserta.setText ("");
		taAlamat.setText ("");
		btnAdd.setEnabled(false);
		btnEdit.setEnabled(false);
		btnDelete.setEnabled(false);
		txtNoKtp.requestFocus();
	}
}