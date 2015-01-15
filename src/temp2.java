public class MainServer extends JFrame {
	
	// inisialisasi variabel
	protected static JTextField tfWaktu;
	String strTanggal = "", strJam = "", strMenit = "", strDetik = "";
	Date dt = null;
	int hari, tanggal, bulan, tahun, jam, menit, detik;

	String []hariIndo = {"Minggu", "Senin", "Selasa", "Rabu", "Kamis",
	"Jumat", "Sabtu"};
	String []bulanIndo = {"Januari", "Februari", "Maret", "April",
	"Mei", "Juni", "Juli", "Agustus", "September", "Oktober",
	"November", "Desember"};

	// method untuk menangani waktu
	public void jam(){
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
		tfWaktu.setText("" +hariIndo[hari] +", " +strTanggal +" " 
		+bulanIndo[bulan] +" " +tahun +"  |  " +strJam  +":" 
		+strMenit +":" +strDetik);
	}

	
	// method untuk setting lookAndFeel jTattoo.
	public static void setTheme(){
		// Gunakan exception handling (try and catch) 
		// untuk menangani kesalahan jika jTattoo tidak ditemukan
		try{
			LookAndFeel lf = new com.jtattoo.plaf.acryl.AcrylLookAndFeel();
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
    
    // class threadJam untuk menampilkan waktu (refresh tiap detik)
	class ThreadJam extends Thread{
		public ThreadJam(){}
		public void run(){
			while(true){
				jam(); // panggil method jam()
				try{
					Thread.sleep(1000); //sleep 1 detik
				}catch(InterruptedException ie){}
			}
		}
	}
	
	// method verifikasi login (digunakan pada saat menjalankan server lelang)
	private boolean verifikasi(){
    	txtUser.setText("");
    	txtPass.setText("");
    	boolean b = false;
    	int res = JOptionPane.showOptionDialog(null, new Object[] {lblUser,
    	txtUser, lblPass, txtPass}, "Input Username dan Password",
    	JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, icon,
    	null, null);

    	if(res == JOptionPane.OK_OPTION){
			String u = txtUser.getText();
			String p = txtPass.getText();
			User o = new User(u, p);
			b = o.isValidUser();
			if(!b) JOptionPane.showMessageDialog(this,
			"Username dan Password tidak valid!");
    	}
    	return b;
    }
    
    // method untuk menjalankan server lelang
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
}