/**
 * @(#)Inbox.java
 *
 *
 * @author
 * @version 1.00 2010/5/13
 */


import java.util.regex.Pattern;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Inbox implements SMS{

	private int status = 0;

	public static int i = 0;

    public Inbox(){}

    public String getID(){
		return this.id;
    }

	public void setID(String id){
		this.id = id;
	}

	public String getPesan(){
		return this.pesan;
	}

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public int getStatus(){
		return this.status;
	}
	public void setStatus(int status){
		this.status = status;
	}

  // Deklarasi variabel
  String str1 = null;
  String str2 = null;
  String str3 = null;
  String str1Salah = null;
  String formatStr1 = null;
  String warnStr1 = null;

  // Awal method prosesPesan
  public void prosesPesan(String id, String noPengirim, String pesan) {
	String h[];
    Pattern pattern = Pattern.compile(" ");
    h = pattern.split(pesan.trim());

	if(h.length == 2){
		System.out.println("2");
		str1 = h[0].trim().toString().toUpperCase();
		str2 = h[1].trim().toString().toUpperCase();

		if(str1.equals("LELANG")){
			queryLelang2(noPengirim, str2);
		}else{
			str1Salah = "Format pesan " +str1 +" tidak terdaftar.";
			formatStr1 = "\nFormat yg benar: \nLELANG AKTIF";
			warnStr1 = str1Salah + formatStr1;
			System.out.println(noPengirim +" " +warnStr1);
			Outbox.insertOutbox(noPengirim,warnStr1);
		}
	}else if(h.length == 3){
		System.out.println("3");
		str1 = h[0].trim().toString().toUpperCase();
		str2 = h[1].trim().toString().toUpperCase();
		str3 = h[2].trim().toString().toUpperCase();

		if(str1.equals("LELANG")){
			queryLelang3(noPengirim, str2, str3);
		}else if(isInt(str1)){
			queryPenawaran(noPengirim, str1, str2, str3);
		}else{
			str1Salah = "Format pesan " +str1 +" tidak terdaftar.";
			formatStr1 = "\nFormat yg benar: \n1. LELANG MENANG KODE_BARANG \n2. PIN KODE_BARANG HARGA";
			warnStr1 = str1Salah + formatStr1;
			Outbox.insertOutbox(noPengirim,warnStr1);
		}
	}else{
		System.out.println("format salah");
		warnStr1 = "Format pesan salah. \nCoba periksa kembali format pesan yg anda kirim";
		Outbox.insertOutbox(noPengirim,warnStr1);
		Database.updateStatus(id, "1");

	}

	Database.updateStatus(id, "1");
  }
  // Akhir method prosesPesan

  //*****************************************************************************
  String bid = null;
  String str2TawarSalah = null;
  String replyTawar = null;

  // Awal method queryPenawaran
  public void queryPenawaran(String noPengirim, String str1, String str2, String str3){
	bid = str3.substring(0,3).trim();
	Bidder b = new Bidder();
	boolean cek = b.isValidBidder(noPengirim, str1);
	if(cek){
		System.out.println("Validasi Bidder Sukses..");
		Barang item = new Barang();
		if(item.isBarangAktif(str2)){
			String cb = b.getCurBid(str2);
			int x = Integer.parseInt(str3);
			int y = Integer.parseInt(cb);
			if(x > y){
				b.setBid(noPengirim, str2, str3);
				replyTawar = "Penawaran anda untuk barang " +str2 +"\nsebesar " +str3 +" telah diproses.";
			}else{
				replyTawar = "Penawaran " +str3 +" utk barang "+str2 +"\ntidak berhasil. \nSilahkan ajukan penawaran \nyg lebih tinggi.";
			}
		}else{
		str2TawarSalah = "Kode barang " +str2 +" tidak ditemukan";
		str2TawarSalah += "\nSilahkan cek lelang yg akftif: LELANG AKTIF";
		replyTawar = str2TawarSalah;
		System.out.println("Kode Barang Salah..");
		}
	}else{
		System.out.println("PIN anda salah..");
		replyTawar = "PIN " +str1 +" tidak valid. \nPeriksa kembali PIN anda.";
	}

	Outbox.insertOutbox(noPengirim,replyTawar);
  }
  // Akhir method queryPenawaran

  //*****************************************************************************

  String kodeBarang = null;
  String str2Salah = null;
  String formatStr2 = null;
  String replyLelang = null;

  // Awal method queryLelang3
  public void queryLelang3(String noPengirim, String str2, String str3){
 	kodeBarang = str3.substring(0,3).trim();
	if(str2.equals("MENANG")){
		Bidder b = new Bidder();
		b.getPemenang(kodeBarang);
		String noPonsel = b.getNoPonsel();
		int bid = b.getBid();
		replyLelang = "Pemenang Lelang " +kodeBarang +":\nPonsel: " +noPonsel +"\nNama: " +b.getNama() +"\nTerjual: " +bid;
	}else{
		str2Salah = "Pesan yg anda kirim salah, Format: LELANG " +str2 +"\n tidak terdaftar";
		formatStr2 = "\nFormat yang benar: LELANG MENANG KODE_BARANG";
		replyLelang = str2Salah + formatStr2;
	}
	if(replyLelang == null){
		replyLelang = "Keyword LELANG " +str2 +" kode " +str3 +" Tidak ditemukan \nPeriksa format pesan yang anda kirim.";
	}
	Outbox.insertOutbox(noPengirim,replyLelang);
  }
  // Akhir method queryLelang3


// *****************************************************************************

  String replyLelang2 = null;

  // Awal method queryLelang2
  public void queryLelang2(String noPengirim, String str2){
  	if(str2.equals("AKTIF")){
		Barang brg = new Barang();
		brg.lelangAktif();
		replyLelang2 = "Lelang yg aktif: \n";
		String s = "";
		int i = 0;
		while(brg.al.size() > i ){
			s += "" +brg.al.get(i) +", " +brg.al.get(i+1) +"(" +brg.al.get(i+2) +")\n";
			i += 3;
		}
		replyLelang2 = s;
	}else if(str2.equals("AKHIR")){
		String s = "";
		Penawaran tawar = new Penawaran();
		s = tawar.penawaranTerakhir();
		replyLelang2 = "Penawaran terakhir :" + s;
	}else if(str2.equals("MENANG")){
		String s = "";
		Pemenang menang = new Pemenang();
		s = menang.lihatPemenang();
		replyLelang2 = "Pemenang lelang :" + s;
	}else if(str2.equals("NEXT")){
		String s = "";
		Barang b = new Barang();
		s = b.getNext();
		replyLelang2 = "Lelang berikutnya :" + s;
	}else{
		replyLelang2 = "Keyword LELANG " +str2 +" Tidak ditemukan \nPeriksa format pesan yang anda kirim.";
	}
	Outbox.insertOutbox(noPengirim,replyLelang2);
  }
  // Akhir method queryLelang2


  // *****************************************************************************
  public static ResultSet showInbox(){
    ResultSet rSet = null;
    String query = "SELECT id_inbox, no_pengirim, pesan, status_inbox FROM inbox";
	rSet = Database.getResult(query,"showInbox");
  	return rSet;
  }


  // *****************************************************************************
  String id = null;
  String noPengirim = null;
  String pesan = null;
  static PreparedStatement pStatement = null;
  static String sql = null;

  // Awal method getInbox
  public void getInbox() {
    try {
      sql ="SELECT id_inbox, no_pengirim, pesan FROM inbox WHERE status_inbox = 0";
      pStatement = Database.con.prepareStatement(sql);
      try {
        ResultSet rSet = pStatement.executeQuery();
        while (rSet.next()) {

          id = rSet.getString("id_inbox");
          noPengirim = rSet.getString("no_pengirim");
          pesan = rSet.getString("pesan");

          prosesPesan(id, noPengirim, pesan);
        }
        pStatement.close();
      }
      catch (Exception ie) {}
    }
    catch (Exception e) {}
  }
  // Akhir method getInbox

  // Awal method insertInbox
  public static void insertInbox(String notlp, String pesan) {
  	String query = "INSERT INTO inbox(no_pengirim,pesan) VALUES ('"+notlp+"','"+pesan+"')";
    try {
    	Database.exec(query);
	}catch (Exception ei) {
	    MainServer.proses.add("Error pengisisan data pada kotak masuk.");
	    MainServer.proses.add("Kesalahan :" + ei);
	}
   }
   // Akhir method insertInbox


  //*****************************************************************************
  public boolean isInt(String str){
	try{
		Integer.parseInt(str);
		return true;
	}catch(Exception e){
		return false;
	}
  }
  //*****************************************************************************

}