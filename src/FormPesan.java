/**
 * @(#)FormPesan.java
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
import javax.swing.JCheckBox;
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
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
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

public class FormPesan extends JInternalFrame implements ActionListener, FocusListener, ItemListener{

	private JPanel pnlPeserta = new JPanel(),
				pnlFilter = new JPanel();

	private JLabel lblNoPonsel	= new JLabel("No. Ponsel"),
				lblPesan = new JLabel("Pesan"),
				lblFilter = new JLabel("Filter", new ImageIcon("Gambar/search.png"),SwingConstants.LEFT);

	private JTextField 	txtNoPonsel	= new JTextField(),
					txtFilter = new JTextField();
	private JTextArea taPesan = new JTextArea();

	private JButton btnKirim = new JButton("Kirim", new ImageIcon("Gambar/pesan2.png")),
					btnClear = new JButton("Clear", new ImageIcon("Gambar/refresh2.png"));

	private JCheckBox cb = new JCheckBox("Send All");

	private JScrollPane spPesan = new JScrollPane();
	private JScrollPane spTable = new JScrollPane();

	private Connection conn;
  	private Statement stmt;

  	JTable tbl = new JTable();
  	DefaultTableModel model;

	Vector<Object> cols = new Vector<Object>();
    Vector<Object> row;

	FormPesan (){
		super ("Form SMS", false, true, false, false);
		ImageIcon icon = new ImageIcon("Gambar/pesan2.png");
  		this.setFrameIcon(icon);
		setSize (712, 530);

		btnKirim.setFont(new Font("Arial",Font.PLAIN,9));
		btnClear.setFont(new Font("Arial",Font.PLAIN,9));

		pnlPeserta.setLayout(null);
		pnlFilter.setLayout(null);

		pnlPeserta.setBorder(BorderFactory.createCompoundBorder(
	      BorderFactory.createEmptyBorder(0,0,0,0),
	      BorderFactory.createEtchedBorder()));

	    pnlFilter.setBorder(BorderFactory.createCompoundBorder(
	      BorderFactory.createEmptyBorder(0,0,0,0),
	      BorderFactory.createEtchedBorder()));

        spPesan.setViewportView(taPesan);

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

		lblNoPonsel.setBounds (20, 20, 110, 25);
		lblPesan.setBounds (20, 62, 115, 25);

		txtNoPonsel.setBounds (125, 20, 190, 25);
		cb.setBounds (325, 20, 80, 25);
		spPesan.setBounds (125, 62, 270, 125);

		lblFilter.setBounds (20, 15, 115, 25);
		txtFilter.setBounds (125, 15, 270, 25);
		spTable.setBounds (15, 50, 650, 180);

		btnKirim.setBounds (500, 25, 90, 35);
		btnClear.setBounds (500, 65, 90, 35);

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


		btnKirim.setEnabled(true);
		btnClear.setEnabled(true);

		btnKirim.addActionListener (this);
		btnClear.addActionListener (this);
		cb.addItemListener (this);

		pnlPeserta.add (lblNoPonsel);
		pnlPeserta.add (txtNoPonsel);
		pnlPeserta.add (cb);
		pnlPeserta.add (lblPesan);
		pnlPeserta.add (spPesan);
		pnlPeserta.add (btnKirim);
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
		if (txtNoPonsel.getText().equals("")){
		}
		else{
			Cari();
		}
	}

	private void TableMouseClick(MouseEvent me){
      try {
            int row = tbl.getSelectedRow();
            int col = tbl.getSelectedColumn();
            txtNoPonsel.setText(tbl.getValueAt(row,1).toString());
        }
        catch(Exception se) {System.err.println(se);}
    }

    private void focusTable(FocusEvent fe){
			txtNoPonsel.requestFocus();
    }


	public void actionPerformed (ActionEvent ae){
		Object obj = ae.getSource();
		if (obj == btnKirim){
			btnKirim();
			}
		clear();
	}

	void Cari(){
		String ktp = txtNoPonsel.getText();
		Bidder b = new Bidder();
		boolean cek = b.cekNoKTP(ktp);
		if(cek){
			//Jika No KTP sudah terdaftar
			b.getDetails(ktp);
			txtNoPonsel.setText(b.getNoPonsel());
			taPesan.setText(b.getAlamat());
			txtNoPonsel.requestFocus();
		}else{
			//Jika No KTP belum terdaftar
			txtNoPonsel.requestFocus();
		}

	}

	/* method kirim pesan*/
	void btnKirim(){
		String noPonsel = txtNoPonsel.getText();
		String pesan = taPesan.getText();
		if((noPonsel.equals("") && !cb.isSelected())|| pesan.equals("")){
			JOptionPane.showMessageDialog(null, "Field empty..");
		}else{
			int respond = JOptionPane.showConfirmDialog(null, "Yakin akan kirim pesan?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
				if(respond == 0){
					if(cb.isSelected()){
						Outbox.sendAll(pesan);
					}else{
						if(pesan.length() > 160){
							kirim2(noPonsel, pesan);
						}else Outbox.insertOutbox(noPonsel, pesan);
					}
				}
			refresh();
		}
    }

    private void kirim2(String noPonsel, String pesan){
    	String p1 = pesan.substring(0,160);
		String p2 = pesan.substring(160);
		Outbox.insertOutbox(noPonsel, p1);
		Outbox.insertOutbox(noPonsel, p2);
    }
    public void itemStateChanged(ItemEvent ie){
    	if(ie.getSource() == cb){
    		if(cb.isSelected()) {
    			txtNoPonsel.setText("");
    			txtNoPonsel.setEnabled(false);
    		}else txtNoPonsel.setEnabled(true);
    	}
    }

	/* Fungsi clear text */
	void clear(){
		txtNoPonsel.setText("");
		taPesan.setText ("");
		txtNoPonsel.requestFocus();
	}
}