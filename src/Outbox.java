/**
 * @(#)Outbox.java
 *
 *
 * @author
 * @version 1.00 2010/5/13
 */


import java.util.regex.Pattern;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Outbox{
  private String id = null;
  private String pesan = null;
  private String noTujuan = null;
  private int status = 0;

  static PreparedStatement pStatement = null;
  static String sql = null;

  public static int i = 0;

  public Outbox(){}

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

  public static ResultSet showOutbox(){
    ResultSet rSet = null;
    String query = "SELECT id_outbox, no_tujuan, pesan, status_outbox FROM outbox";
    rSet = Database.getResult(query,"showOutbox");
    return rSet;
  }

  // Awal method getOutbox
  public void getOutbox() {
    try {
      String sql =
          "SELECT id_outbox, no_tujuan, pesan FROM outbox WHERE status_outbox = 0";
      pStatement = Database.con.prepareStatement(sql);
      try {
        ResultSet rSet = pStatement.executeQuery();
        while (rSet.next()) {
          id = rSet.getString("id_outbox");
          noTujuan = rSet.getString("no_tujuan");
          pesan = rSet.getString("pesan");
          ServerLelang.sendSMS(id, noTujuan, pesan);
        }
        pStatement.close();
      }
      catch (Exception ie) {}
    }
    catch (Exception e) {}
  }
  // Akhir method getOutbox

  // Awal method insertOutbox
  public static void insertOutbox(String noTujuan, String reply) {
  	String query = "INSERT INTO outbox(no_tujuan, pesan) VALUES ('"+noTujuan+"','"+reply+"')";
  	try {
  		Database.exec(query);
	}catch (Exception e) {
	    MainServer.proses.add("Error insertOutbox: "+e);}

  }
  // Akhir method insertOutbox

  public static ArrayList<Object> Results2Array(ResultSet rs) throws SQLException {
		ResultSetMetaData metaData = rs.getMetaData();
        int columns = metaData.getColumnCount();
		ArrayList<Object> record = new ArrayList<Object>();
        while (rs.next()) {
            for (int i = 1; i <= columns; i++) {
                Object value = rs.getObject(i);
                record.add(value);
            }
        }
        return record;
  }

  public static void sendAll(String pesan) {
  	ArrayList<Object> list = new ArrayList<Object>();
  	ResultSet rSet = Database.getResult("SELECT no_ponsel from bidder","getAll");
  	try{
  		list = Results2Array(rSet);
  	}catch(Exception e){}
  	int a = list.toString().lastIndexOf("]");
	String  s = list.toString().substring(1,a);
	String strAr[] = s.split(",");
  	try {
		for (int i = 0; i < strAr.length; i++) {
	        String query = "INSERT INTO outbox(no_tujuan, pesan) VALUES ('"+strAr[i]+"','"+pesan+"')";
	        Database.exec(query);
	    }
    }catch (Exception e) {}
  }
}