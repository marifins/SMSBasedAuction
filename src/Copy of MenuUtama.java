/**
 * @(#)MenuUtama.java
 *
 *
 * @author
 * @version 1.00 2010/4/25
 */
import java.awt.Font;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.Event;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.Timer;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;


public class MenuUtama extends JFrame implements ActionListener {
	private JDesktopPane desktop;

	private JMenuBar mBar;
	private JMenu mFile;
	private JMenu mMan;
	private JMenu mLaporan;
	private JMenu mHelp;

	private JMenuItem mItLogout;
	//private JMenuItem mItExit;
	private JMenuItem mItChangePIN;
	private JMenuItem mItChangePass;

	private JMenuItem mItPeserta;
	private JMenuItem mItBarang;
	private JMenuItem mItUser;

	private JMenuItem mItLapPeserta;
	private JMenuItem mItLapBarang;
	private JMenuItem mItLapLelang;

    private JMenuItem mItAbout;

	/* Menu PopUp */
	private JPopupMenu popMenu;
	private JMenuItem mPopUpPeserta;
	private JMenuItem mPopUpBarang;
	private JMenuItem mPopUpUser;

	/* Menu ToolBar */
	private	JToolBar toolBar;
	private	JPanel ToolbarPanel1;
	private	JPanel ToolbarPanel2;
	private	JPanel ToolbarPanel3;
	private	JButton mBtnPeserta;
	private	JButton	mBtnBarang;
	private	JButton	mBtnUser;

	private	JButton	mBtnPIN;
	private	JButton	mBtnPass;

	private	JButton mBtnRep1;
	private	JButton	mBtnRep2;
	private	JButton	mBtnRep3;

	private JLabel lblwaktu1;

	/* Pengaturan Waktu dan Tanggal */
	private Date tglsekarang;
	private SimpleDateFormat smpdtfmt;
	private String tgl;

	/* Pengaturan Status Bar */
	private JPanel statusBar;
	private JLabel lblExit;
	private JLabel lblTgl;

	public void initComponent(){
		desktop = new JDesktopPane();
		mBar = new JMenuBar();
		mFile = new JMenu("File");
		mMan = new JMenu("Manajemen");
		mLaporan = new JMenu("Laporan");
		mHelp = new JMenu("Help");

		mItLogout = new JMenuItem("Logout", new ImageIcon ("Gambar/exit.png"));
		//mItExit = new JMenuItem("Exit", new ImageIcon ("Gambar/cancel.png"));
		mItChangePIN = new JMenuItem("Ganti PIN", new ImageIcon ("Gambar/ponsel.png"));
		mItChangePass = new JMenuItem("Ganti Password", new ImageIcon ("Gambar/c_pass.png"));
		mItPeserta = new JMenuItem("Peserta", new ImageIcon ("Gambar/bidder.png"));
		mItBarang = new JMenuItem("Barang", new ImageIcon ("Gambar/item.png"));
		mItUser = new JMenuItem("User", new ImageIcon ("Gambar/user.png"));
		mItLapPeserta = new JMenuItem("Report Peserta", new ImageIcon ("Gambar/report1.png"));
		mItLapBarang = new JMenuItem("Report Barang", new ImageIcon ("Gambar/report2.png"));
		mItLapLelang = new JMenuItem("Report Lelang", new ImageIcon ("Gambar/report3.png"));

		mItAbout = new JMenuItem("About", new ImageIcon ("Gambar/info.png"));

		popMenu = new JPopupMenu();

		mPopUpPeserta = new JMenuItem("Data Peserta", new ImageIcon ("Gambar/bintang.gif"));
		mPopUpBarang = new JMenuItem("Data Barang", new ImageIcon ("Gambar/add.gif"));
		mPopUpUser = new JMenuItem("Data Operator", new ImageIcon ("Gambar/ed.gif"));
		toolBar = new JToolBar();

		ToolbarPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		ToolbarPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		ToolbarPanel3 = new JPanel(new FlowLayout(FlowLayout.CENTER));

		mBtnPeserta = new JButton(new ImageIcon("Gambar/bidder2.png"));
		mBtnBarang = new JButton(new ImageIcon("Gambar/item2.png"));
		mBtnUser = new JButton(new ImageIcon("Gambar/user2.png"));
		mBtnPIN = new JButton(new ImageIcon("Gambar/ponsel2.png"));
		mBtnPass = new JButton(new ImageIcon("Gambar/c_pass2.png"));
		mBtnRep1 = new JButton(new ImageIcon("Gambar/report1_2.png"));
		mBtnRep2 = new JButton(new ImageIcon("Gambar/report2_2.png"));
		mBtnRep3 = new JButton(new ImageIcon("Gambar/report3_2.png"));
		lblwaktu1 = new JLabel();

		tglsekarang = new Date();
		smpdtfmt = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
		tgl = smpdtfmt.format(tglsekarang);
		statusBar = new JPanel();
		//lblExit = new JLabel(" " + "Copyright © marifins 2010", JLabel.LEFT);
		lblTgl = new JLabel(" " + tgl + " ", JLabel.RIGHT);
	}


	public MenuUtama (){

		super("Sistem Manajemen Lelang");
		initComponent();
		setIconImage (getToolkit().getImage("Gambar/connected_multiple.jpg"));

		setSize (720, 650);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width  - getWidth()) / 2,
			(Toolkit.getDefaultToolkit().getScreenSize().height - getHeight()) / 2);

		addWindowListener (new WindowAdapter () {
			public void windowClosing (WindowEvent we) {
				Keluar();
			}
		});

		mFile.setMnemonic ('F');
		mMan.setMnemonic ('D');
		mLaporan.setMnemonic ('L');
		mHelp.setMnemonic ('H');

		mItLogout.setMnemonic ('O');
		//mItExit.setMnemonic ('X');
		mItChangePIN.setMnemonic ('P');
		mItChangePass.setMnemonic ('W');

		mItPeserta.setMnemonic('P');
		mItBarang.setMnemonic('B');
		mItUser.setMnemonic('U');

		mItLapPeserta.setMnemonic('P');
		mItLapBarang.setMnemonic('B');
		mItLapLelang.setMnemonic('L');

		//mItExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK));
		mItLogout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));
		mItChangePIN.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, Event.CTRL_MASK));
		mItChangePass.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, Event.CTRL_MASK));

		mItPeserta.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, Event.CTRL_MASK));
	    mItBarang.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, Event.CTRL_MASK));
	    mItUser.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, Event.CTRL_MASK));

        mItLapPeserta.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_P, Event.CTRL_MASK));
		mItLapBarang.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_B, Event.CTRL_MASK));
		mItLapLelang.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_L, Event.CTRL_MASK));

		mItAbout.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_A, Event.CTRL_MASK));

		//mItExit.addActionListener (this);
		mItLogout.addActionListener (this);
		mItChangePIN.addActionListener (this);
		mItChangePass.addActionListener (this);
		mItPeserta.addActionListener (this);
		mItBarang.addActionListener (this);
		mItUser.addActionListener (this);
		mItLapPeserta.addActionListener (this);
		mItLapBarang.addActionListener (this);
		mItLapLelang.addActionListener (this);
		mItAbout.addActionListener (this);

		mFile.add(mItChangePIN);
		mFile.add(mItChangePass);
		mFile.addSeparator();
		//mFile.add(mItExit);
		mFile.add(mItLogout);
		mMan.add(mItPeserta);
	    mMan.add(mItBarang);
	    mMan.add(mItUser);
	    mLaporan.add(mItLapPeserta);
		mLaporan.add(mItLapBarang);
		mLaporan.add(mItLapLelang);
		mLaporan.addSeparator();
		mHelp.add(mItAbout);

		setJMenuBar (mBar);

		mBar.add(mFile);
		mBar.add(mMan);
		mBar.add(mLaporan);
		mBar.add(mHelp);

		mPopUpBarang.addActionListener (this);
		mPopUpPeserta.addActionListener (this);
		mPopUpUser.addActionListener (this);

		popMenu.add(mPopUpBarang);
		popMenu.add(mPopUpPeserta);
	    popMenu.add(mPopUpUser);

		addMouseListener(new MouseAdapter(){
			public void mousePressed (MouseEvent me){ checkMouseTrigger (me); }
			public void mouseReleased (MouseEvent me){ checkMouseTrigger (me); }
			private void checkMouseTrigger (MouseEvent me) {
				if (me.isPopupTrigger())
					popMenu.show (me.getComponent (), me.getX (), me.getY ());
			}
		});

		mBtnBarang.setToolTipText("Manajemen Barang");
		mBtnPeserta.setToolTipText("Manajemen Peserta");
		mBtnUser.setToolTipText("Manajemen User");
		mBtnPIN.setToolTipText("Ganti PIN Bidder");
		mBtnPass.setToolTipText("Ganti Password User");
		mBtnRep1.setToolTipText("Laporan Peserta");
		mBtnRep2.setToolTipText("Laporan Barang");
		mBtnRep3.setToolTipText("Laporan Lelang");

		mBtnBarang.addActionListener(this);
		mBtnPeserta.addActionListener(this);
		mBtnUser.addActionListener(this);
		mBtnPIN.addActionListener(this);
		mBtnPass.addActionListener(this);


		ToolbarPanel1.add(mBtnBarang);
		ToolbarPanel1.add(mBtnPeserta);
	    ToolbarPanel1.add(mBtnUser);

	    ToolbarPanel2.add(mBtnPIN);
	    ToolbarPanel2.add(mBtnPass);

		ToolbarPanel3.add(mBtnRep1);
		ToolbarPanel3.add(mBtnRep2);
		ToolbarPanel3.add(mBtnRep3);

		toolBar.add(ToolbarPanel1);
		toolBar.addSeparator();
		toolBar.add(ToolbarPanel2);
		toolBar.addSeparator();
		toolBar.add(ToolbarPanel3);

		statusBar.setLayout(new BorderLayout());
		statusBar.setFont(new Font("Arial",Font.BOLD,10));

		//statusBar.add(lblExit, BorderLayout.WEST);
		statusBar.add(lblTgl, BorderLayout.WEST);

		desktop.setBackground(Color.gray);

		FrontMenu mainInFrame = new FrontMenu();
		desktop.add(mainInFrame);
		mainInFrame.show();

		getContentPane().add(toolBar, BorderLayout.NORTH);
		getContentPane().add(desktop, BorderLayout.CENTER);
		getContentPane().add(statusBar, BorderLayout.SOUTH);

		setJam();
		statusBar.add(lblwaktu1, BorderLayout.EAST);

		show();
	}

	public void actionPerformed(ActionEvent ae){
		Object obj = ae.getSource();

		if(obj == mItPeserta || obj == mPopUpPeserta || obj == mBtnPeserta){
			ManPeserta manPeserta = new ManPeserta();
			setJIF(manPeserta);
		}

		if(obj == mItBarang || obj == mPopUpBarang || obj == mBtnBarang){
			ManBarang manBarang = new ManBarang();
			setJIF(manBarang);
		}

		if(obj == mItUser || obj == mPopUpUser || obj == mBtnUser){
			ManUser manUser = new ManUser();
			setJIF(manUser);
		}

	   	if(obj == mItPeserta){
			/*Faktur FMFaktur = new Faktur();
			desktop.add (FMFaktur);
			FMFaktur.show ();*/
		}

		if(obj == mItLapPeserta){
			/*ReportBarang RptBrg = new ReportBarang();
			desktop.add (RptBrg);
			RptBrg.show ();*/
		}

		if(obj == mItLapBarang){
			/*ReportPelanggan RptPlg = new ReportPelanggan();
			desktop.add (RptPlg);
			RptPlg.show ();*/
		}

		if(obj == mItLapLelang){
			/*ReportFaktur RptFkt = new ReportFaktur();
			desktop.add (RptFkt);
			RptFkt.show ();*/
		}

		if(obj==mItAbout){
			JOptionPane.showMessageDialog(this,"Sistem Manajemen Pelelangan \nBerbasis Short Message Service (SMS) \nMuhammad Arifin Siregar","Sistem Pelelangan Berbasis SMS",JOptionPane.PLAIN_MESSAGE);
		}

		if(obj == mItChangePIN || obj == mBtnPIN ){
			GantiPIN gantiPIN = new GantiPIN();
			setJIF(gantiPIN);
		}

		if(obj == mItChangePass || obj == mBtnPass){
			GantiPass gantiPass = new GantiPass();
			setJIF(gantiPass);
		}

		if(obj==mItLogout){
			Keluar();
		}

		/*if(obj==mItExit){
			Keluar();
		}*/
}

	private void setJIF(JInternalFrame jif){
		desktop.add(jif);
		jif.show();
		try {
				jif.setSelected(true);
			}catch (java.beans.PropertyVetoException e){}
	}

	/* Method Keluar Dari Sistem */
	private void Keluar(){
		try{
	    	confirm();
		}catch(Exception e){
		}
	}

	public void confirm(){
		int respond = JOptionPane.showConfirmDialog(null, "Anda yakin akan keluar?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
		if(respond == 0){
			setVisible (false);
			dispose();
			System.exit(0);
		}else setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	/* Methode Setting Waktu Sistem */
	public void setJam(){
		ActionListener taskPerformer = new ActionListener(){
    		public void actionPerformed(ActionEvent evt) {
        		String nol_jam = "", nol_menit = "", nol_detik = "";

        		Date dateTime = new Date();
        		int nilai_jam = dateTime.getHours();
        		int nilai_menit = dateTime.getMinutes();
	        	int nilai_detik = dateTime.getSeconds();

        		if (nilai_jam <= 9) nol_jam = "0";
        		if (nilai_menit <= 9) nol_menit = "0";
        		if (nilai_detik <= 9) nol_detik = "0";

	        	String jam = nol_jam + Integer.toString(nilai_jam);
    	    	String menit = nol_menit + Integer.toString(nilai_menit);
        		String detik = nol_detik + Integer.toString(nilai_detik);

        		lblwaktu1.setText(jam + ":" + menit + ":" + detik + " ");
      		}
   		};
    	new Timer(1000, taskPerformer).start();
  	}
}