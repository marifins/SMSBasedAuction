/**
 * @(#)Penawaran.java
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

class Penawaran{
	private int idPenawaran;
	private String namaBarang;
	private int bid;
	private Date tglPenawaran;
	private Time waktuPenawaran;
	private Database dbase;
	static ArrayList al = new ArrayList();

	public Penawaran(){
		dbase = new Database();
	}

	public int getIdPenawaran(){
		return this.idPenawaran;
	}

	public int getNamaBarang(){
		return this.idPenawaran;
	}

	public int getBid(){
		return this.bid;
	}

	public Date getTglPenawaran(){
		return this.tglPenawaran;
	}

	public Time getWaktuPenawaran(){
		return this.waktuPenawaran;
	}

    public static ResultSet getData(){
    	 ResultSet rSet = null;
         String query = "SELECT id_penawaran, no_ponsel, kode_barang, harga, date, time FROM Penawaran";
         try{
            Database.st = Database.con.createStatement();
            rSet = Database.st.executeQuery(query);
        }catch(Exception e){
            System.out.println("Error getData, " + e.getMessage());
        }
        return rSet;
     }

     public String penawaranTerakhir(){
     	 String hasil = ""; String harga = "";
     	 getAktif();
		 String s = al.toString();
		 int a = s.lastIndexOf("]");
		 String str = s.substring(1,a);
		 String arr[] = str.split(", ");
		 for(int i = 0; i < arr.length; i++){
	         String q = "SELECT b.kode_barang AS kode, b.nama_barang AS nama, MAX(d.harga) AS harga FROM barang AS b JOIN penawaran AS d WHERE b.kode_barang = d.kode_barang AND b.kode_barang = '"+arr[i]+"'";
	         try{
	            dbase.st = dbase.con.createStatement();
	            dbase.rs = dbase.st.executeQuery(q);
	            hasil += "\n";
	            while(dbase.rs.next()){
	            	hasil += dbase.rs.getString("kode") +"(";
	            	hasil += dbase.rs.getString("nama") +", ";
	            	harga = dbase.rs.getString("harga");
	            }
	            dbase.st.close();
	            if(harga == null)
	            	harga = Barang.getHargaAwal(arr[i]);
	            hasil += harga +")";
	         }catch(Exception e){
	            System.out.println("Error penawaranTerakhir, " + e.getMessage());
	         }
		 }
        return hasil;
     }

     public void getAktif(){
         String q = "SELECT kode_barang FROM barang WHERE status_barang = 1";
         try{
            dbase.st = dbase.con.createStatement();
            dbase.rs = dbase.st.executeQuery(q);
            while(dbase.rs.next()){
               	al.add(dbase.rs.getString("kode_barang"));
            }
            dbase.st.close();
        }catch(Exception e){
            System.out.println("Error getAktif, " + e.getMessage());
        }
     }



}