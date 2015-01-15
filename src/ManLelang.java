/**
 * @(#)ManLelang.java
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

public class ManLelang extends JInternalFrame implements ActionListener{

	private JPanel pnlManLelang = new JPanel(),
				pnlFilter = new JPanel();

	private JLabel lblKodeBarang	= new JLabel("Kode Barang"),
				lblNoPonsel	= new JLabel("No. Ponsel"),
				lblFilter = new JLabel("Filter", new ImageIcon("Gambar/search.png"),SwingConstants.LEFT);

	private JTextField txtKodeBarang = new JTextField();

	private JButton btnAktf = new JButton("Aktif", new ImageIcon("Gambar/aktif.png")),
					btnNonAktf = new JButton("Non-Aktif", new ImageIcon("Gambar/nonAktif.png")),
					btnClear = new JButton("Clear", new ImageIcon("Gambar/refresh2.png"));

	private JScrollPane spTable = new JScrollPane();

	private Connection conn;
  	private Statement stmt;

  	JTable tbl = new JTable();
  	DefaultTableModel model;

	Vector<Object> cols = new Vector<Object>();
    Vector<Object> row;

	ManLelang(){
		super("Manajemen Lelang", false, true, false, false);
		ImageIcon icon = new ImageIcon("Gambar/lelang.png");
  		this.setFrameIcon(icon);
		setSize (712, 530);

		btnAktf.setFont(new Font("Arial",Font.PLAIN,9));
		btnNonAktf.setFont(new Font("Arial",Font.PLAIN,9));
		btnClear.setFont(new Font("Arial",Font.PLAIN,9));

		pnlManLelang.setLayout(null);
		pnlFilter.setLayout(null);

		pnlManLelang.setBorder(BorderFactory.createCompoundBorder(
	      BorderFactory.createEmptyBorder(0,0,0,0),
	      BorderFactory.createEtchedBorder()));

	    pnlFilter.setBorder(BorderFactory.createCompoundBorder(
	      BorderFactory.createEmptyBorder(0,0,0,0),
	      BorderFactory.createEtchedBorder()));

		String[] r = {"KODE BARANG","NAMA BARANG","HARGA AWAL","TGL. LELANG", "STATUS"};
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

		txtKodeBarang.setEditable(false);

		lblFilter.setBounds (20, 15, 115, 25);
		txtFilter.setBounds (125, 15, 270, 25);
		spTable.setBounds(15, 50, 650, 225);

		btnAktf.setBounds(180, 20, 95, 36);
		btnNonAktf.setBounds(280, 20, 95, 36);
		btnClear.setBounds(380, 20, 95, 36);

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

		btnAktf.setEnabled(true);
		btnNonAktf.setEnabled(true);
		btnClear.setEnabled(true);

		btnAktf.addActionListener(this);
		btnNonAktf.addActionListener(this);
		btnClear.addActionListener(this);

		pnlManLelang.add(btnAktf);
		pnlManLelang.add(btnNonAktf);
		pnlManLelang.add(btnClear);

		pnlFilter.add (lblFilter);
		pnlFilter.add (txtFilter);
		pnlFilter.add (spTable);

		getContentPane().setLayout(null);
		getContentPane().add(pnlManLelang);
		pnlManLelang.setBounds (15, 15, 675, 80);
		getContentPane().add(pnlFilter);
		pnlFilter.setBounds (15, 100, 675, 380);

		setVisible(true);
	}

void showTable(){
		try {
	      stmt = Database.con.createStatement();
	      ResultSet rs = stmt.executeQuery(
	        "SELECT b.kode_barang,nama_barang, harga_awal, tanggal_lelang, status_barang FROM barang AS b, detail_barang AS d WHERE b.kode_barang = d.kode_barang AND b.status_barang != 2");
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

            txtKodeBarang.setText(tbl.getValueAt(row,0).toString());
            //txtNoPonsel.setText(tbl.getValueAt(row,1).toString());
        }
        catch(Exception se) {System.err.println(se);}
    }

    private void focusTable(FocusEvent fe){
			btnAktf.setEnabled(true);
			txtKodeBarang.requestFocus();
    }

	public void actionPerformed (ActionEvent ae){
		Object obj = ae.getSource();
		if (obj == btnAktf){
			btnAktif();
		}
		if (obj == btnNonAktf){
			btnNonAktif();
		}
		clear();
	}

	void btnAktif(){
		String kodeBarang = txtKodeBarang.getText();
		if(kodeBarang.equals("")){
			JOptionPane.showMessageDialog(null, "Data belum dipilih!");
		}else{
			int respond = JOptionPane.showConfirmDialog(null, "Yakin akan aktifkan lelang?\nKode Barang = " +kodeBarang, "Konfirmasi", JOptionPane.YES_NO_OPTION);
			if(respond == 0){
				Barang.aktifkanLelang(kodeBarang);
				refresh();
			}
		}
	}

	void btnNonAktif(){
		String kodeBarang = txtKodeBarang.getText();
		if(kodeBarang.equals("")){
			JOptionPane.showMessageDialog(null, "Data belum dipilih!");
		}else{
			int respond = JOptionPane.showConfirmDialog(null, "Yakin akan non-aktifkan lelang?\nKode Barang = " +kodeBarang, "Konfirmasi", JOptionPane.YES_NO_OPTION);
			if(respond == 0){
				Barang.nonAktifkanLelang(kodeBarang);
				refresh();
			}
		}
	}

	/* Fungsi Kosongkan Text */
	void clear(){
		txtKodeBarang.setText("");
		txtKodeBarang.requestFocus();
		refresh();
	}
}