/**
 * @(#)Bidder.java
 *
 *
 * @author
 * @version 1.00 2010/4/16
 */

import java.util.Date;
import javax.swing.JOptionPane;
import java.sql.ResultSet;

class Bidder{
	protected static String nama;
	protected static String alamat;
	protected static String noKTP;
	protected static String noPonsel;
	private int PIN;
	protected int bid;
	Database db;

	public Bidder(){
		db = new Database();
	}

	public String getNama(){
		return this.nama;
	}

	public String getAlamat(){
		return this.alamat;
	}

	public String getNoKTP(){
		return this.noKTP;
	}

	public String getNoPonsel(){
		return this.noPonsel;
	}

	public int getPIN(){
		return PIN;
	}

	public static void changePIN(String noKTP,String noPonsel, String pin){
    	String pesan = "PIN anda telah diganti,\nPIN anda sekarang : " +pin +"\nsegera simpan PIN anda\ndan hapus pesan ini.";
        String q = "UPDATE bidder SET pin = SUBSTRING(MD5('"+pin.trim()+"'),1,20) WHERE no_ktp = '"+noKTP+"'";
        try{
            Database.pSt = Database.con.prepareStatement(q);
            Database.pSt.executeUpdate();
            Database.pSt.close();
            JOptionPane.showMessageDialog(null, "PIN berhasil diganti..");
            Outbox.insertOutbox(noPonsel, pesan);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error changePIN: \n" +e.getMessage());
        }
    }


    public int getBid(){
		return this.bid;
	}

    public void setBid(String noPonsel, String kodeBarang, String bid){
        String q = "INSERT INTO penawaran(no_ponsel, kode_barang, harga, date, time) VALUES ('"+noPonsel.trim()+"','"+kodeBarang.trim()+"','"+bid.trim()+"',CURDATE(),CURTIME())";
        try{
            db.pSt = db.con.prepareStatement(q);
            db.pSt.executeUpdate();
            db.pSt.close();
        }catch(Exception e) {
            System.out.println ("Error setPenawaran: " + e.getMessage());
        }
    }

     public String getCurBid(String kodeBarang){
     	 String res = "";
         String query = "SELECT MAX(harga) as cur_bid FROM penawaran WHERE kode_barang = '"+kodeBarang+"'";
         try{
            db.st = db.con.createStatement();
            db.rs = db.st.executeQuery(query);
            while (db.rs.next()){
                res = db.rs.getString("cur_bid");
            }
            db.st.close();
        }catch(Exception e){
            System.out.println("Error getCurBid, " + e.getMessage());
        }
        if(res == null)
        	res = Barang.getHargaAwal(kodeBarang);

        return res;
     }

     public void getPemenang(String kodeBarang){
         String query = "SELECT a.no_ponsel, a.harga, b.nama FROM penawaran AS a, bidder AS b WHERE a.kode_barang='"+kodeBarang+"' AND a.no_ponsel = b.no_ponsel";
         try{
            db.st = db.con.createStatement();
            db.rs = db.st.executeQuery(query);
            while (db.rs.next()){
                this.nama = db.rs.getString("b.nama");
                String hp = db.rs.getString("a.no_ponsel");
                this.noPonsel = hp.substring(0,9) +"XXX";
                String str = db.rs.getString("a.harga");
                this.bid = Integer.parseInt(str);
            }
            db.st.close();
        }catch(Exception e){
            System.out.println("Error cariPemenang, " + e.getMessage());
        }
     }

    public boolean isValidBidder(String noPengirim, String pin){
     	String p = pin.trim();
     	String res = "";
        String q = "SELECT COUNT(*) AS jlh FROM bidder WHERE pin=SUBSTRING(MD5("+p+"),1,20) AND no_ponsel = "+noPengirim+"";
        try{
            db.st = db.con.createStatement();
            db.rs = db.st.executeQuery(q);
            db.rs.next();
            res = db.rs.getString("jlh");
            db.st.close();
            return Integer.valueOf(res) > 0;
        }catch(Exception e){
            System.out.println("Error cekValidBidder:" + e.getMessage());
            return false;
        }
    }

    public static boolean cekNoKTP(String noKTP){
     	String ktp = noKTP.trim();
     	String res = "";
        String q = "SELECT COUNT(*) AS jlh FROM bidder WHERE no_ktp = "+ktp+"";
        try{
            Database.st = Database.con.createStatement();
            Database.rs = Database.st.executeQuery(q);
            Database.rs.next();
            res = Database.rs.getString("jlh");
            Database.st.close();
            return Integer.valueOf(res) > 0;
        }catch(Exception e){
            System.out.println("Error cekNoKTP:" + e.getMessage());
            return false;
        }
    }

	public void getDetails(String noKTP){
         String query = "SELECT no_ktp, no_ponsel, nama, alamat FROM bidder WHERE no_ktp = '"+noKTP+"'";
         try{
            db.st = db.con.createStatement();
            db.rs = db.st.executeQuery(query);
            while (db.rs.next()){
            	this.noKTP = db.rs.getString("no_ktp");
                this.noPonsel = db.rs.getString("no_ponsel");
                this.nama = db.rs.getString("nama");
	            this.alamat = db.rs.getString("alamat");
            }
            db.st.close();
        }catch(Exception e){
            System.out.println("Error getDetails, " + e.getMessage());
        }
     }

    public static void tambahBidder(String noKTP, String noPonsel, String nama, String alamat){
    	String pin = String.valueOf(rand());
        String q = "INSERT INTO bidder(no_ktp, no_ponsel, nama, alamat, pin) VALUES ('"+noKTP.trim()+"','"+noPonsel.trim()+"','"+nama.trim()+"','"+alamat.trim()+"','"+pin+"')";
        try{
            Database.pSt = Database.con.prepareStatement(q);
            Database.pSt.executeUpdate();
            Database.pSt.close();
            JOptionPane.showMessageDialog(null, "Data berhasil tersimpan..");
        }catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error tambahBidder: \n" +e.getMessage());
        }
    }

    public static void updateBidder(String noKTP, String noPonsel, String nama, String alamat){
        String q = "UPDATE bidder SET no_ponsel = '"+noPonsel.trim()+"', nama = '"+nama.trim()+"', alamat = '"+alamat.trim()+"' WHERE no_ktp = '"+noKTP+"'";
        try{
            Database.pSt = Database.con.prepareStatement(q);
            Database.pSt.executeUpdate();
            Database.pSt.close();
            JOptionPane.showMessageDialog(null, "Data berhasil ter-update..");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error updateBidder: \n" +e.getMessage());
        }
    }

    public static void deleteBidder(String noKTP){
    	String pin = String.valueOf(rand());
        String q = "DELETE FROM bidder WHERE no_ktp = '"+noKTP+"'";
        try{
            Database.pSt = Database.con.prepareStatement(q);
            Database.pSt.executeUpdate();
            Database.pSt.close();
            JOptionPane.showMessageDialog(null, "Data berhasil terhapus..");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error deleteBidder: \n" +e.getMessage());
        }
    }

    public static ResultSet getData(){
    	 ResultSet rSet = null;
         String query = "SELECT no_ktp, no_ponsel, nama, alamat FROM bidder";
         try{
            Database.st = Database.con.createStatement();
            rSet = Database.st.executeQuery(query);
        }catch(Exception e){
            System.out.println("Error getData Bidder, " + e.getMessage());
        }
        return rSet;
     }

    public static int rand(){
		return (int) (Math.floor(Math.random() * 9999) + 1);
    }
}