/**
 * @(#)Database.java
 *
 *
 * @author
 * @version 1.00 2010/4/16
 */

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Database {

    private static String driver = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost/sms";
    private static String user  = "root";
    private static String pass  = "";

    public static Connection con;
    public static Statement st;
    public static PreparedStatement pSt;
    public static ResultSet rs;

    public static int i = 0;

    public static void Database() {
        connectDb();
    }

    public static void connectDb(){
         try {
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,pass);
            //if(MainServer.proses != null)
            	//MainServer.proses.add("Berhasil terkoneksi ke database..", ++i);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Cek koneksi database! ","Informasi",JOptionPane.ERROR_MESSAGE);
           System.exit(0);
        }
    }

    public static void closeConn() {
    	try {
            con.close();
        } catch (Exception ex) {}
    }

    public static void exec(String s){
    	try{
    		pSt = con.prepareStatement(s);
			pSt.executeUpdate();
			pSt.close();
    	}catch(Exception e){
    		JOptionPane.showMessageDialog(null,"Error! \n" +e,"Kesalahan",JOptionPane.ERROR_MESSAGE);
    	}
    }

    public static ResultSet getResult(String s, String err){
    	ResultSet rSet = null;
         try{
            st = con.createStatement();
            rSet = st.executeQuery(s);
        }catch(Exception e){
            System.out.println("Error "+err +", " + e.getMessage());
        }
        return rSet;
    }

    public static String getWaktuSelesai(){
    	String s = "";
    	String q = "SELECT waktu_selesai FROM pengaturan";
    	ResultSet rSet = getResult(q,"getWaktuSelesai");
    	try{
	    	rSet.next();
	    	s = rSet.getString("waktu_selesai");
    	}catch(Exception e){
            System.out.println("Error " +e.getMessage());
        }
		return s;
    }

    public static void updateWaktuSelesai(String waktu){
    	String q = "UPDATE pengaturan SET waktu_selesai = '"+waktu+"'";
    	exec(q);
    }

    // Awal method updateStatus
    public static void updateStatus(String id, String status) {
    	String updateStatus = null;
	    try {
	      if (status.equals("2")){
	        updateStatus = "UPDATE outbox SET status_outbox=2 WHERE id_outbox=?";
	      }
	      if (status.equals("1")){
	        updateStatus = "UPDATE inbox SET status_inbox=1 WHERE id_inbox=?";
	      }
	      pSt = con.prepareStatement(updateStatus);
	      try {
	        pSt.setString(1, id);
	        pSt.executeUpdate();
	      }
	      catch (Exception e) {}
	      pSt.close();
	    }
    catch (Exception e) {MainServer.proses.add("Error updateStatus:"+e, ++i);}
  	}
  	// Akhir method updateStatus

}
