/**
 * @(#)Barang.java
 *
 *
 * @author
 * @version 1.00 2010/4/16
 */

import java.util.ArrayList;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import javax.swing.JOptionPane;
import java.sql.ResultSet;

class Barang{
	private String kodeBarang;
	private String namaBarang;
	private int hargaAwal;
	private int hargaAkhir;
	private Date tglLelang;
	private Database dbase;
	ArrayList al = new ArrayList();

	public Barang(){
		dbase = new Database();
	}

	public String getKodeBarang(){
		return this.kodeBarang;
	}

	public String getNamaBarang(){
		return this.namaBarang;
	}

	public int getHargaAwal(){
		return this.hargaAwal;
	}

	public static String getHargaAwal(String kodeBarang){
     	 String res = "";
         String q = "SELECT harga_awal FROM detail_barang WHERE kode_barang = '"+kodeBarang+"'";
         try{
            Database.st = Database.con.createStatement();
            Database.rs = Database.st.executeQuery(q);
            while(Database.rs.next()){
               	res = Database.rs.getString("harga_awal");
            }
            Database.st.close();
        }catch(Exception e){
            System.out.println("Error getHargaAwal, " + e.getMessage());
        }
        return res;
    }

    int getHargaAkhir(){
		return this.hargaAkhir;
	}

	public Date getTglLelang(){
		return this.tglLelang;
	}

	public boolean isBarangAktif(String kodeBarang){
     	String res = "";
        String q = "SELECT COUNT(*) AS jlh FROM barang WHERE kode_barang = '"+kodeBarang+"' AND status_barang = 1";
        try{
            dbase.st = dbase.con.createStatement();
            dbase.rs = dbase.st.executeQuery(q);
            dbase.rs.next();
            res = dbase.rs.getString("jlh");
            dbase.st.close();
            return Integer.valueOf(res) > 0;
        }catch(Exception e){
            System.out.println("Error isKodeBarang:" + e.getMessage());
            return false;
        }
	}

	public void lelangAktif(){
         String q = "SELECT b.kode_barang AS kode, b.nama_barang AS nama, d.harga_awal AS harga FROM barang AS b JOIN detail_barang AS d WHERE b.kode_barang = d.kode_barang AND b.status_barang = 1";
         try{
            dbase.st = dbase.con.createStatement();
            dbase.rs = dbase.st.executeQuery(q);
            while(dbase.rs.next()){
               	al.add(dbase.rs.getString("kode"));
               	al.add(dbase.rs.getString("nama"));
               	al.add(dbase.rs.getString("harga"));
            }
            dbase.st.close();
        }catch(Exception e){
            System.out.println("Error lelangAktif, " + e.getMessage());
        }
     }

     public static boolean cekKodeBarang(String kodeBarang){
     	String kode = kodeBarang.trim();
     	String res = "";
        String q = "SELECT COUNT(*) AS jlh FROM barang WHERE kode_barang = '"+kode+"'";
        try{
            Database.st = Database.con.createStatement();
            Database.rs = Database.st.executeQuery(q);
            Database.rs.next();
            res = Database.rs.getString("jlh");
            Database.st.close();
            return Integer.valueOf(res) > 0;
        }catch(Exception e){
            System.out.println("Error cekKodeBarang:" + e.getMessage());
            return false;
        }
    }

    public static void aktifkanLelang(String kodeBarang){
        String q = "UPDATE barang SET status_barang = '"+1+"' WHERE kode_barang = '"+kodeBarang+"'";
        try{
            Database.exec(q);
            JOptionPane.showMessageDialog(null, "Lelang untuk barang " +kodeBarang +" berhasil diaktifkan..");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error aktifkanLelang: \n" +e.getMessage());
        }
    }

    public static void nonAktifkanLelang(String kodeBarang){
        String q = "UPDATE barang SET status_barang = '"+0+"' WHERE kode_barang = '"+kodeBarang+"'";
        try{
            Database.exec(q);
            JOptionPane.showMessageDialog(null, "Lelang untuk barang " +kodeBarang +" berhasil di-nonaktifkan..");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error nonAktifkanLelang: \n" +e.getMessage());
        }
    }

    protected void getDetails(String kodeBarang){
         String query = "SELECT b.kode_barang AS kode, b.nama_barang AS nama, d.harga_awal AS harga, d.tanggal_lelang AS tgl FROM barang AS b JOIN detail_barang AS d WHERE d.kode_barang = b.kode_barang AND d.kode_barang = '"+kodeBarang+"'";
         try{
            dbase.st = dbase.con.createStatement();
            dbase.rs = dbase.st.executeQuery(query);
            while(dbase.rs.next()){
            	this.kodeBarang = dbase.rs.getString("kode");
                this.namaBarang = dbase.rs.getString("nama");
                String s = dbase.rs.getString("harga");
                this.hargaAwal = Integer.parseInt(s);
	            this.tglLelang = dbase.rs.getDate("tgl");
            }
            dbase.st.close();
        }catch(Exception e){
            System.out.println("Error getDetails, " + e.getMessage());
        }
    }

    public static void tambahBarang(String kodeBarang, String namaBarang, String hargaAwal, String strTgl){
        String q1 = "INSERT INTO barang(kode_barang, nama_barang) VALUES ('"+kodeBarang.trim()+"','"+namaBarang.trim()+"')";
        String q2 = "INSERT INTO detail_barang(kode_barang, harga_awal, tanggal_lelang) VALUES ('"+kodeBarang.trim()+"','"+hargaAwal.trim()+"','"+strTgl+"')";
        try{
        	if(hargaAwal.isEmpty() && strTgl.isEmpty())
            	Database.exec(q1);
            else{
            	Database.exec(q1);
            	Database.exec(q2);
            }
            JOptionPane.showMessageDialog(null, "Data berhasil tersimpan..");
        }catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error tambahBarang: \n" +e.getMessage());
        }
    }

    public static void updateBarang(String kodeBarang, String namaBarang, String hargaAwal, String strTgl){
        String q1 = "UPDATE barang SET nama_barang = '"+namaBarang.trim()+"' WHERE kode_barang = '"+kodeBarang+"'";
        String q2 = "UPDATE detail_barang SET harga_awal = '"+hargaAwal.trim()+"', tanggal_lelang = '"+strTgl+"' WHERE kode_barang = '"+kodeBarang+"'";
        try{
            Database.exec(q1);
            Database.exec(q2);
            JOptionPane.showMessageDialog(null, "Data berhasil ter-update..");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error updateBidder: \n" +e.getMessage());
        }
    }

    public static void deleteBarang(String kodeBarang){
        String q1 = "DELETE FROM barang WHERE kode_barang = '"+kodeBarang+"'";
        String q2 = "DELETE FROM detail_barang WHERE kode_barang = '"+kodeBarang+"'";
        try{
            Database.exec(q1);
            Database.exec(q2);
            JOptionPane.showMessageDialog(null, "Data berhasil terhapus..");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error deleteBarang: \n" +e.getMessage());
        }
    }

    public static ResultSet getData(){
    	 ResultSet rSet = null;
         String query = "SELECT b.kode_barang AS kode, b.nama_barang AS nama, d.harga_awal AS harga, d.tanggal_lelang AS tgl FROM barang AS b JOIN detail_barang AS d WHERE d.kode_barang = b.kode_barang";
         try{
            Database.st = Database.con.createStatement();
            rSet = Database.st.executeQuery(query);
        }catch(Exception e){
            System.out.println("Error getData Barang, " + e.getMessage());
        }
        return rSet;
    }

    public String getNext(){
     	 String hasil = ""; String harga = "";
     	 getStatusNext();
		 String s = al.toString();
		 int a = s.lastIndexOf("]");
		 String str = s.substring(1,a);
		 String arr[] = str.split(", ");
		 if(str.equals("")){
		 	hasil += " Lelang berikutnya belum terdaftar.";
		 }else{
			 for(int i = 0; i < arr.length; i++){
		         String q = "SELECT b.kode_barang AS kode, b.nama_barang AS nama, d.tanggal_lelang AS tgl FROM barang AS b JOIN detail_barang AS d WHERE b.kode_barang = d.kode_barang AND b.kode_barang = '"+arr[i]+"'";
		         try{
		            dbase.st = dbase.con.createStatement();
		            dbase.rs = dbase.st.executeQuery(q);
		            hasil += "\n";
		            while(dbase.rs.next()){
		            	hasil += dbase.rs.getString("kode") +"(";
		            	hasil += dbase.rs.getString("nama") +"->";
		            	hasil += dbase.rs.getString("tgl")  +")";
		            }
		            dbase.st.close();
		         }catch(Exception e){
		            System.out.println("Error getNext, " + e.getMessage());
		         }
			 }
		 }
        return hasil;
     }

     public void getStatusNext(){
         String q = "SELECT b.kode_barang FROM barang AS b JOIN detail_barang AS d WHERE b.kode_barang = d.kode_barang AND b.status_barang = 0  AND d.tanggal_lelang > CURDATE() LIMIT 2";
         try{
            dbase.st = dbase.con.createStatement();
            dbase.rs = dbase.st.executeQuery(q);
            while(dbase.rs.next()){
               	al.add(dbase.rs.getString("kode_barang"));
            }
            dbase.st.close();
        }catch(Exception e){
            System.out.println("Error getStatusNext, " + e.getMessage());
        }
     }

     public static void main(String[]args){
		Barang b = new Barang();
		String str = b.getNext().substring(1);
		String arr[] = str.split("/");
     	System.out.println(str);
	}


}