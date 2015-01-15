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
import javax.swing.border.TitledBorder;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.util.Vector;
import java.util.Date;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.text.MaskFormatter;

import com.toedter.calendar.JDateChooser;

public class ManBarang extends JInternalFrame implements ActionListener, FocusListener{

	private JPanel pnlBarang = new JPanel(),
				pnlImg = new JPanel(),
				pnlFilter = new JPanel();

	private JLabel lblKodeBarang = new JLabel("Kode Barang"),
				lblNamaBarang	= new JLabel("Nama Barang"),
				lblHargaAwal	= new JLabel("Harga Awal"),
				lblTanggalLelang = new JLabel("Tanggal Lelang"),
				lblImg = new JLabel(""),
				lblFilter = new JLabel("Filter", new ImageIcon("Gambar/search.png"),SwingConstants.LEFT);

	private JTextField txtKodeBarang	= new JTextField(),
					txtNamaBarang	= new JTextField(),
					txtHargaAwal	= new JTextField(),
					txtFilter = new JTextField();

	private JButton btnAdd = new JButton("Add", new ImageIcon("Gambar/add2.png")),
					btnEdit = new JButton("Edit", new ImageIcon("Gambar/modify2.png")),
					btnDelete = new JButton("Delete", new ImageIcon("Gambar/delete2.png")),
					btnClear = new JButton("Clear", new ImageIcon("Gambar/refresh2.png"));

	private JDateChooser dateTanggalLelang = new JDateChooser();

	private JScrollPane spTable = new JScrollPane();

	private Connection conn;
  	private Statement stmt;

  	JTable tbl = new JTable();
  	DefaultTableModel model;

	Vector<Object> cols = new Vector<Object>();
    Vector<Object> row;

    String imgSource;

	ManBarang(){
		super("Form Barang", false, true, false, false);//title, resizable, closable, maximizable, iconifiable
		ImageIcon icon = new ImageIcon("Gambar/item.png");
  		this.setFrameIcon(icon);
		setSize (712, 530);

		lblImg.setIcon(new ImageIcon("img_barang/mipa.jpg"));

		btnAdd.setFont(new Font("Arial",Font.PLAIN,9));
		btnEdit.setFont(new Font("Arial",Font.PLAIN,9));
		btnDelete.setFont(new Font("Arial",Font.PLAIN,9));
		btnClear.setFont(new Font("Arial",Font.PLAIN,9));

		pnlBarang.setLayout(null);
		pnlFilter.setLayout(null);
		pnlImg.setLayout(null);

		pnlBarang.setBorder(BorderFactory.createCompoundBorder(
	      BorderFactory.createEmptyBorder(0,0,0,0),
	      BorderFactory.createEtchedBorder()));

	    pnlFilter.setBorder(BorderFactory.createCompoundBorder(
	      BorderFactory.createEmptyBorder(0,0,0,0),
	      BorderFactory.createEtchedBorder()));

	    pnlImg.setBorder(new TitledBorder(""));

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

		lblKodeBarang.setBounds (20, 20, 115, 25);
		lblNamaBarang.setBounds (20, 50, 110, 25);
	    lblHargaAwal.setBounds (20, 80, 110, 25);
		lblTanggalLelang.setBounds (20, 110, 115, 25);

		dateTanggalLelang.setBounds (125, 110, 125, 25);
		txtKodeBarang.setBounds (125, 20, 80, 25);
		txtNamaBarang.setBounds (125, 50, 245, 25);
		txtHargaAwal.setBounds (125, 80, 125, 25);

		lblFilter.setBounds (20, 15, 115, 25);
		txtFilter.setBounds (125, 15, 270, 25);
		spTable.setBounds (15, 50, 650, 180);

		btnAdd.setBounds (25, 155, 90, 35);
		btnEdit.setBounds (125, 155, 90, 35);
		btnDelete.setBounds (225, 155, 90, 35);
		btnClear.setBounds (325, 155, 90, 35);

		txtHargaAwal.addKeyListener (new KeyAdapter(){
			public void keyTyped (KeyEvent ke){
				char c = ke.getKeyChar ();
				if (!((Character.isDigit (c) || (c == KeyEvent.VK_BACK_SPACE)))){
					getToolkit().beep (); ke.consume ();
      				}
    			}
  		 } );

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

		txtKodeBarang.addFocusListener (this);
		btnAdd.addActionListener (this);
		btnEdit.addActionListener (this);
		btnDelete.addActionListener (this);
		btnClear.addActionListener (this);

		pnlBarang.add(lblKodeBarang);
		pnlBarang.add(txtKodeBarang);
		pnlBarang.add(lblNamaBarang);
		pnlBarang.add(txtNamaBarang);
		pnlBarang.add(lblHargaAwal);
		pnlBarang.add(txtHargaAwal);
		pnlBarang.add(lblTanggalLelang);
		pnlBarang.add(dateTanggalLelang);
		pnlBarang.add(btnAdd);
		pnlBarang.add(btnEdit);
		pnlBarang.add(btnDelete);
		pnlBarang.add(btnClear);

		pnlBarang.add(lblImg);
		lblImg.setBounds (455, 15, 180, 180);

		lblImg.add(pnlImg);
		pnlImg.setBounds (0, 0, 180, 180);


		pnlFilter.add(lblFilter);
		pnlFilter.add(txtFilter);
		pnlFilter.add(spTable);

		getContentPane().setLayout(null);
		getContentPane().add(pnlBarang);
		pnlBarang.setBounds (15, 15, 675, 210);
		getContentPane().add(pnlFilter);
		pnlFilter.setBounds (15, 235, 675, 245);

		setVisible(true);
	}

	void showTable(){
		try {
	      stmt = Database.con.createStatement();
	      ResultSet rs = stmt.executeQuery(
	        "SELECT b.kode_barang,nama_barang, harga_awal, tanggal_lelang, status_barang FROM barang AS b, detail_barang AS d WHERE b.kode_barang = d.kode_barang");
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
		if (txtKodeBarang.getText().equals("")){
		}
		else{
			Cari();
		}
	}

	private void TableMouseClick(MouseEvent me){
      try {
            int row = tbl.getSelectedRow();
            int col = tbl.getSelectedColumn();

            txtKodeBarang.setText(tbl.getValueAt(row,0).toString());
            txtNamaBarang.setText(tbl.getValueAt(row,1).toString());
            txtHargaAwal.setText(tbl.getValueAt(row,2).toString());

			dateTanggalLelang.setDate((Date)tbl.getValueAt(row,3));

			imgSource = "img_barang/print.png";
            lblImg.setIcon(new ImageIcon(imgSource));
        }
        catch(Exception se) {System.err.println(se);}
    }

    private void focusTable(FocusEvent fe){
	   	btnAdd.setEnabled(false);
		btnEdit.setEnabled(true);
		btnDelete.setEnabled(true);
		txtKodeBarang.requestFocus();
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
		String kode = txtKodeBarang.getText();
		Barang item = new Barang();
		boolean cek = item.cekKodeBarang(kode);
		if(cek){
			//Jika kode barang ada
			item.getDetails(kode);
			txtKodeBarang.setText(item.getKodeBarang());
			txtNamaBarang.setText(item.getNamaBarang());
			String s = String.valueOf(item.getHargaAwal());
			txtHargaAwal.setText(s);
			dateTanggalLelang.setDate(item.getTglLelang());
			btnAdd.setEnabled(false);
			btnEdit.setEnabled(true);
			btnDelete.setEnabled(true);
			txtNamaBarang.requestFocus();
		}else{
			//Jika kode barang tidak ada
			btnAdd.setEnabled(true);
			btnEdit.setEnabled(false);
			btnDelete.setEnabled(false);
			txtHargaAwal.requestFocus();
		}

	}

	/* method tambah data peserta*/
	void tambah(){
		String kodeBarang = txtKodeBarang.getText();
		String namaBarang = txtNamaBarang.getText();
		String hargaAwal = txtHargaAwal.getText();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strTgl = sdf.format(dateTanggalLelang.getDate()).toString();

		if(kodeBarang.equals("") || namaBarang.equals("") || hargaAwal.equals("") || dateTanggalLelang.getDate() == null){
			JOptionPane.showMessageDialog(null, "Field empty..");
		}else{
			Barang.tambahBarang(kodeBarang, namaBarang, hargaAwal, strTgl);
			hapusTable();
			showTable();
		}
    }

	/* method update data peserta* */
	void ubah(){
		String kodeBarang = txtKodeBarang.getText();
		String namaBarang = txtNamaBarang.getText();
		String hargaAwal = txtHargaAwal.getText();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strTgl = sdf.format(dateTanggalLelang.getDate()).toString();

		if(kodeBarang.equals("") || namaBarang.equals("") || hargaAwal.equals("") || dateTanggalLelang.getDate() == null){
			JOptionPane.showMessageDialog(null, "Field empty..");
		}else{
			Barang.updateBarang(kodeBarang, namaBarang, hargaAwal, strTgl);
			hapusTable();
			showTable();
		}
	}

	/* method hapus data peserta* */
	void hapus(){
		String kodeBarang = txtKodeBarang.getText();
		String namaBarang = txtNamaBarang.getText();
		int respond = JOptionPane.showConfirmDialog(null, "Yakin akan dihapus?\nKode barang = " +kodeBarang +"\nNama Barang = " +namaBarang, "Konfirmasi", JOptionPane.YES_NO_OPTION);
		if(respond == 0){
			Barang.deleteBarang(kodeBarang);
			hapusTable();
			showTable();
		}
	}

	/* Fungsi Kosongkan Text */
	void clear(){
		txtKodeBarang.setText("");
		txtNamaBarang.setText("");
		txtHargaAwal.setText("");
		dateTanggalLelang.setDate(null);
		btnAdd.setEnabled(false);
		btnEdit.setEnabled(false);
		btnDelete.setEnabled(false);
		txtKodeBarang.requestFocus();
	}
}