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
import javax.swing.JOptionPane;
import java.util.ArrayList;

class Pemenang extends Bidder{
	private static String kodeBarang;
  	private static int hargaTerjual;
	static Database db;
	static ArrayList al = new ArrayList();

	public Pemenang(){
		db = new Database();
	}

	public int getHarga(){
		return this.hargaTerjual;
	}

	public String lihatPemenang(){
		 String hasil = ""; String temp = "";
		 getStatusSelesai();
		 String s = al.toString();
		 int a = s.lastIndexOf("]");
		 String str = s.substring(1,a);
		 String arr[] = str.split(", ");
		 if(str.equals("")){
		 	hasil += " \nPemenang belum ditentukan.";
		 }else{
			 for(int i = 0; i < arr.length; i++){
		         String query = "SELECT a.no_ponsel AS ponsel, a.kode_barang as kode, a.harga_terjual AS harga FROM pemenang AS a JOIN detail_barang AS b WHERE a.kode_barang = b.kode_barang AND b.tanggal_lelang = CURDATE() AND b.kode_barang = '"+arr[i]+"'";
		         try{
		            db.st = db.con.createStatement();
		            db.rs = db.st.executeQuery(query);
		            hasil += "\n";
		            while (db.rs.next()){
		            	hasil += db.rs.getString("kode") +"-";
		                temp = db.rs.getString("ponsel").substring(0,9) +"XXX";
		                hasil += temp +"-";
		                hasil += db.rs.getString("harga");
		            }
		            db.st.close();
		        	}catch(Exception e){
		            JOptionPane.showMessageDialog(null,"Error lihatPemenang, " + e.getMessage());
		        	}
				 }
		 }
		 if(hasil.equals("\n"))
		 	hasil += "Pemenang belum ditentukan.";
		 return hasil;
     }

	public static void getWinner(){
         String query = "SELECT a.no_ponsel AS ponsel, a.kode_barang as kode, MAX(a.harga) AS max , b.nama FROM penawaran AS a JOIN bidder AS b WHERE a.date = CURDATE()";
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
            JOptionPane.showMessageDialog(null,"Error getWinner, " + e.getMessage());
        }
     }

    public static void insertPemenang(String noPonsel, String kodeBarang, String hargaTerjual){
        String q = "INSERT INTO pemenang(no_ponsel, kode_barang, harga_terjual) VALUES ('"+noPonsel.trim()+"','"+kodeBarang.trim()+"','"+hargaTerjual.trim()+"')";
        try{
            db.pSt = db.con.prepareStatement(q);
            db.pSt.executeUpdate();
            db.pSt.close();
            JOptionPane.showMessageDialog(null, "Pemenang sudah ditentukan!\nNo. Ponsel : " +noPonsel +"\nKode Barang : " +kodeBarang +"\nTerjual : Rp." +hargaTerjual);
        }catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error insertPemenang: " + e.getMessage());
        }
    }

    public void getDetails(String noPonsel){
         String query = "SELECT no_ponsel, kode_barang, harga_terjual FROM pemenang WHERE no_ponsel = '"+noPonsel+"'";
         try{
            db.st = db.con.createStatement();
            db.rs = db.st.executeQuery(query);
            while (db.rs.next()){
                this.noPonsel = db.rs.getString("no_ponsel");
                this.kodeBarang = db.rs.getString("kode_barang");
	            String s = db.rs.getString("harga_terjual");
	            this.hargaTerjual = Integer.parseInt(s);
            }
            db.st.close();
        }catch(Exception e){
            System.out.println("Error getDetails, " + e.getMessage());
        }
     }

     public static void setPemenang(){
	    getWinner();
	    String harga = String.valueOf(hargaTerjual);
		insertPemenang(noPonsel, kodeBarang, harga);
     }

     public void getStatusSelesai(){
         String q = "SELECT kode_barang FROM barang WHERE status_barang = 2";
         try{
            db.st = db.con.createStatement();
            db.rs = db.st.executeQuery(q);
            while(db.rs.next()){
               	al.add(db.rs.getString("kode_barang"));
            }
            db.st.close();
        }catch(Exception e){
            System.out.println("Error getStatusSelesai, " + e.getMessage());
        }
     }

     public static void main(String[]args){
		Pemenang p = new Pemenang();
		String str = p.lihatPemenang().substring(1);
		String arr[] = str.split("/");
     	System.out.println(str);
	}
}