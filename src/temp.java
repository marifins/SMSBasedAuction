// import library java yang diperlukan
import javax.swing.JOptionPane;
import java.sql.ResultSet;

class Pemenang extends Bidder{	

    /**
	method getWinner digunakan untuk menentukan pemenang lelang
    **/
    public static void getWinner(){
        String query = "SELECT a.no_ponsel AS ponsel, a.kode_barang as kode," 
        MAX(a.harga) AS max , b.nama FROM penawaran AS a JOIN bidder AS b
        WHERE a.date = CURDATE()";
        try{
            db.st = db.con.createStatement();
            db.rs = db.st.executeQuery(query);
            while (db.rs.next()){
                noPonsel = db.rs.getString("ponsel");
                kodeBarang = db.rs.getString("kode");
                String str = db.rs.getString("max");
                hargaTerjual = Integer.parseInt(str);
            }
            db.st.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error getWinner, "
            + e.getMessage());
        }
    }

    public static void setPemenang(){
        getWinner();
        String harga = String.valueOf(hargaTerjual);
        insertPemenang(noPonsel, kodeBarang, harga);
    }

    /**
	method insertPemenang digunakan untuk menyimpan data pemenang lelang
	ke tabel pemenang database server 
    **/
    public static void insertPemenang(String noPonsel, String kodeBarang,
    String hargaTerjual){
        String q = "INSERT INTO pemenang(no_ponsel, kode_barang,
        harga_terjual) VALUES ('"+noPonsel.trim()+"',
        '"+kodeBarang.trim()+"','"+hargaTerjual.trim()+"')";
        try{
            db.pSt = db.con.prepareStatement(q);
            db.pSt.executeUpdate();
            db.pSt.close();
            JOptionPane.showMessageDialog(null, 
            "Pemenang sudah ditentukan!\nNo. Ponsel : " +noPonsel
            +"\nKode Barang : " +kodeBarang +"\nTerjual : Rp." +hargaTerjual);
        }catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error insertPemenang: " 
            + e.getMessage());
        }
    }
}

/** 
Class ThreadSetPemenang digunakan untuk mengecek waktu 
dan menjalankan proses untuk menentukan pemenang lelang
**/
class ThreadSetPemenang extends Thread{
    String waktu = ""; String waktuSelesai = "";
    int i = 0;
    public ThreadSetPemenang(){}
    public void run() {
        waktuSelesai = Database.getWaktuSelesai();
        while (true) {
            String s = MainServer.getWaktu();
            i = s.lastIndexOf("|  ") + 3;
            waktu = s.substring(i);
            if(waktu.startsWith(waktuSelesai)){
                Pemenang winner = new Pemenang();
                winner.setPemenang();
                setWinner.stop();
            }
            System.out.println(waktu);
            try {
                Thread.sleep(2000);
            }
            catch (InterruptedException e) {}
        }
    }
}


// import library java yang diperlukan
import java.awt.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class MainServer extends JFrame {

	private JButton btnStart;
    public static List proses = null;
    
    JTextField txtUser = new JTextField();
    JPasswordField txtPass = new JPasswordField();
    JLabel lblUser = new JLabel("Username");
    JLabel lblPass = new JLabel("Password");
    
    // inisialisasi komponen
    private void initComponents() {
    	btnStart = new JButton(null, new ImageIcon("images/start.png"));
    	proses = new List();
    	
    	btnStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
               	start(ae);
            }
        });
    }
}
