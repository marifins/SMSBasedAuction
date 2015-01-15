/**
 * @(#)MainServer.java
 *
 *
 * @author
 * @version 1.00 2010/4/16
 */

import java.util.Date;
import java.awt.Font;
import java.awt.Color;
import java.awt.List;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.LookAndFeel;
import javax.swing.border.TitledBorder;

public class MainServer extends JFrame {

	private JLabel lblNoPonsel;
    private JLabel lblPesanMasuk;
    private JLabel lblReply;
	private JButton btnStart;
    private JButton btnStop;
    protected static JTextField tfNoPonsel;
    protected static JTextField tfWaktu;
    protected static JTextArea aPesanReply;
    protected static JTextArea aPesanMasuk;
    private JPanel pnlWaktu;
    private JPanel pnlPengirim;
    private JPanel pnlReply;
    private JPanel pnlProses;
    private JScrollPane spPesanReply;
    private JScrollPane spPesanMasuk;
    public static List proses = null;
    
    String strTanggal = "", strJam = "", strMenit = "", strDetik = "";
	Date dt = null;
	int hari, tanggal, bulan, tahun, jam, menit, detik;

	String []hariIndo = {"Minggu","Senin","Selasa","Rabu","Kamis","Jumat","Sabtu"};
	String []bulanIndo = {"Januari","Februari","Maret","April","Mei","Juni","Juli","Agustus","September","Oktober","November","Desember"};

    ServerLelang serverLelang = new ServerLelang();

    JTextField txtUser = new JTextField();
    JPasswordField txtPass = new JPasswordField();
    JLabel lblUser = new JLabel("Username");
    JLabel lblPass = new JLabel("Password");

    ImageIcon icon = new ImageIcon("images/key.png");

    public MainServer() {
       initComponents();
       setTheme();
       ThreadJam jam = new ThreadJam();
       jam.start();
    }

    private void initComponents() {
    	this.setTitle("Server Produksi");
    	setIconImage (getToolkit().getImage("images/server.png"));
    	this.setResizable(false);
    	btnStart = new JButton(null, new ImageIcon("images/start.png"));
    	btnStop = new JButton(null, new ImageIcon("images/stop.png"));
		lblNoPonsel = new JLabel();
		lblPesanMasuk = new JLabel();
        lblReply = new JLabel();
        tfNoPonsel = new JTextField();
        tfWaktu = new JTextField();
        aPesanReply = new JTextArea();
        aPesanMasuk = new JTextArea();
        pnlWaktu = new JPanel();
        pnlPengirim = new JPanel();
        pnlReply = new JPanel();
        pnlProses = new JPanel();
        spPesanReply = new JScrollPane();
        spPesanMasuk = new JScrollPane();
        proses = new List();

		tfNoPonsel.setFont(new Font("Dialog",0,14));
		tfNoPonsel.setEditable(false);
		//aPesanMasuk.setEditable(false);
		aPesanReply.setEditable(false);
        btnStop.setEnabled(false);

        getContentPane().setLayout(null);

		//start panel detail
        pnlPengirim.setLayout(null);
        pnlPengirim.setBorder(BorderFactory.createTitledBorder(null, "Bidder", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 0, 11), new Color(125, 125, 125)));
        lblNoPonsel.setText("Ponsel");
        pnlPengirim.add(lblNoPonsel);
        lblNoPonsel.setBounds(10, 25, 80, 25);

        pnlPengirim.add(tfNoPonsel);
        tfNoPonsel.setBounds(56, 25, 215, 25);

        lblPesanMasuk.setText("<html>Pesan<br>Masuk</html>");
        pnlPengirim.add(lblPesanMasuk);
        lblPesanMasuk.setBounds(10, 65, 70, 25);

        spPesanMasuk.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        spPesanMasuk.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        aPesanMasuk.setColumns(20);
        aPesanMasuk.setRows(5);
        aPesanMasuk.setAutoscrolls(false);
        spPesanMasuk.setViewportView(aPesanMasuk);

        pnlPengirim.add(spPesanMasuk);
        spPesanMasuk.setBounds(56, 65, 215, 90);

        getContentPane().add(pnlPengirim);
        pnlPengirim.setBounds(10, 10, 290, 180);
		//end panel detail

		//start panel waktu
		pnlWaktu.setLayout(null);
		pnlWaktu.setBorder(BorderFactory.createTitledBorder(null, "Waktu Server", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 0, 11), new Color(125, 125, 125)));
        pnlWaktu.add(tfWaktu);

        tfWaktu.setBackground(this.getBackground());
        tfWaktu.setBorder(null);
        tfWaktu.setBounds(41, 15, 212, 20);
        tfWaktu.setHorizontalAlignment(SwingConstants.RIGHT);
        tfWaktu.setFont(new Font("Dialog",0,14));
		getContentPane().add(pnlWaktu);
		pnlWaktu.setBounds(310, 10, 295, 45);
		//end panel waktu

		//start panel proses
		pnlReply.setLayout(null);
        pnlReply.setBorder(BorderFactory.createTitledBorder(null, "Server", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 0, 11), new Color(125, 125, 125)));

        lblReply.setText("<html>Reply<br>Pesan</html>");
        pnlReply.add(lblReply);
        lblReply.setBounds(10, 20, 70, 25);

        spPesanReply.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        spPesanReply.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        aPesanReply.setColumns(20);
        aPesanReply.setRows(5);
        aPesanReply.setAutoscrolls(false);
        spPesanReply.setViewportView(aPesanReply);

        pnlReply.add(spPesanReply);
        spPesanReply.setBounds(56, 20, 224, 90);

        pnlProses.setLayout(null);
        pnlProses.setBorder(BorderFactory.createTitledBorder(null, "Proses", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 0, 11), new Color(125, 125, 125)));
        pnlProses.add(proses);

        proses.setBounds(15,20,395,55);
        pnlProses.add(btnStart);
        btnStart.setBounds(420, 22, 80, 50);
		pnlProses.add(btnStop);
        btnStop.setBounds(505, 22, 80, 50);
        getContentPane().add(pnlReply);
        pnlReply.setBounds(310, 55, 295, 135);
        getContentPane().add(pnlProses);
        pnlProses.setBounds(10, 190, 595, 90);
        //end panel proses


        btnStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
               	start(ae);
            }
        });

        btnStop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                stop(ae);
            }
        });
		try{
     		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
  		}catch(Exception e) {
  		    System.out.println("Error setting native LAF: " + e);
  		}

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-702)/2, (screenSize.height-350)/2, 620, 315);
    }

    protected void processWindowEvent(WindowEvent we) {
        if (we.getID() == WindowEvent.WINDOW_CLOSING) {
        	if(serverLelang.statusServer == 2)
            	JOptionPane.showMessageDialog(this, "Stop server untuk keluar!");
            if(serverLelang.statusServer == 1)
            	System.exit(0);
        }
    }
    
    // method untuk menangani waktu
  	public void Jam(){
		dt = new Date();
		tanggal = dt.getDate();
		hari = dt.getDay();
		bulan = dt.getMonth();
		tahun  = dt.getYear() + 1900;
		jam = dt.getHours();
		menit = dt.getMinutes();
		detik = dt.getSeconds();
		
		// konversi jam (integer) -> strJam (string)
		strJam = String.valueOf(jam);
		// jika strJam 1 digit,  maka tambahkan angka 0 di depan
		if (strJam.length() == 1) strJam = "0" + strJam;
		
		strMenit = String.valueOf(menit);
		if (strMenit.length() == 1) strMenit = "0" + strMenit;
		
		strDetik = String.valueOf(detik);
		if (strDetik.length() == 1) strDetik = "0" + strDetik;
		
		// tampilkan waktu pada text field tfWaktu
		tfWaktu.setText("" +hariIndo[hari] +", " +strTanggal +" " +bulanIndo[bulan] +" " +tahun +"  |  " +strJam  +":" +strMenit +":" +strDetik);
	}

	public static String getWaktu(){
		return tfWaktu.getText();
    }

    private void start(ActionEvent ae) {
    	Database.connectDb();
    	boolean cek = verifikasi();
		if(cek){
			Database.connectDb();
			proses.add("koneksi ke database berhasil..",1);
			serverLelang.startServer();
	       	btnStart.setEnabled(false);
	       	btnStop.setEnabled(true);
		}
    }

    private void stop(ActionEvent evt) {
		int cnfrm = JOptionPane.showConfirmDialog(this, "Anda yakin menutup aplikasi ini?","Exit", JOptionPane.YES_NO_OPTION);
		if(cnfrm == 0){
			boolean cek = verifikasi();
			if(cek){
				//Pemenang winner = new Pemenang();
	       		//winner.setPemenang();
	       		Database.closeConn();
				System.exit(0);
			}
		}
    }

    private boolean verifikasi(){
    	txtUser.setText("");
    	txtPass.setText("");
    	boolean b = false;
    	int res = JOptionPane.showOptionDialog(null, new Object[] {lblUser, txtUser, lblPass, txtPass},
    	"Input Username dan Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, icon, null, null);

    	if(res == JOptionPane.OK_OPTION){
			String u = txtUser.getText();
			String p = txtPass.getText();
			User o = new User(u, p);
			b = o.isValidUser();
			if(!b) JOptionPane.showMessageDialog(this, "Username dan Password tidak valid!");
    	}
    	return b;
    }
    
    // class threadJam untuk menampilkan waktu (refresh tiap detik)
    class ThreadJam extends Thread{
		public ThreadJam(){}
		public void run(){
			while(true){
				Jam(); // panggil method jam()
				try{
					Thread.sleep(1000); //sleep 1 detik
				}catch(InterruptedException ie){}
			}
		}
	}

	// method untuk setting lookAndFeel jTatoo
	public static void setTheme(){
		// Gunakan exception handling (try and catch) 
		// untuk menangani kesalahan jika jTattoo tidak ditemukan
		 try{
	   		LookAndFeel lf = new com.jtattoo.plaf.mcwin.McWinLookAndFeel();
			UIManager.setLookAndFeel(lf);
	     }catch(Exception e) {
	    	System.out.println("Error : " + e); // tampilkan pesan error
         }
	}

	// main method
	public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	// panggil method setTheme()
            	setTheme();
            	// ciptakan objek MainServer dan tampilkan
			   	new MainServer().setVisible(true);
            }
        });
    }
}
