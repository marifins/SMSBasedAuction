/**
 * @(#)User.java
 *
 *
 * @author
 * @version 1.00 2010/4/16
 */
import java.sql.ResultSet;
import javax.swing.JOptionPane;

class User extends Karyawan{
	private String username;
	private String password;
	private int level;
	Database d;

	public User(String username, String password){
		this.username = username;
		this.password = password;
		d = new Database();
	}

	public User(){
		d = new Database();
	}

	public String getUsername(){
		return this.username;
	}

	public static void changePass(String username, String pass){
        String q = "UPDATE user SET password = SUBSTRING(MD5('"+pass.trim()+"'),1,25) WHERE username = '"+username+"'";
        try{
            Database.pSt = Database.con.prepareStatement(q);
            Database.pSt.executeUpdate();
            Database.pSt.close();
            JOptionPane.showMessageDialog(null, "Password berhasil diganti..");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error changePass: \n" +e.getMessage());
        }
    }

	public int getLevel(){
		return this.level;
	}

    public boolean isValidUser(){
     	String u = this.username.trim();
     	String p = this.password.trim();
     	String res = "";
        String q = "SELECT COUNT(*) AS jlh FROM user WHERE password=SUBSTRING(MD5('"+p+"'),1,25) AND username = '"+u+"'";
        try{
            d.st = d.con.createStatement();
            d.rs = d.st.executeQuery(q);
            d.rs.next();
            res = d.rs.getString("jlh");
            d.st.close();
            return Integer.valueOf(res) > 0;
        }catch(Exception e){
            System.out.println("Error isValidUser:" + e.getMessage());
            return false;
        }
    }

    public boolean isValidAdmin(){
     	String u = this.username.trim();
     	String p = this.password.trim();
     	String res = "";
        String q = "SELECT COUNT(*) AS jlh FROM user WHERE password=SUBSTRING(MD5('"+p+"'),1,25) AND username = '"+u+"' AND level = 2";
        try{
            d.st = d.con.createStatement();
            d.rs = d.st.executeQuery(q);
            d.rs.next();
            res = d.rs.getString("jlh");
            d.st.close();
            return Integer.valueOf(res) > 0;
        }catch(Exception e){
            System.out.println("Error isValidUser:" + e.getMessage());
            return false;
        }
    }

    public boolean cekNIP(String nip){
     	String str = nip.trim();
     	String res = "";
        String q = "SELECT COUNT(*) AS jlh FROM user WHERE nip = '"+str+"'";
        try{
            d.st = d.con.createStatement();
            d.rs = d.st.executeQuery(q);
            d.rs.next();
            res = d.rs.getString("jlh");
            d.st.close();
            return Integer.valueOf(res) > 0;
        }catch(Exception e){
            System.out.println("Error cekNIP:" + e.getMessage());
            return false;
        }
    }

     public static boolean cekUsername(String username){
     	String str = username.trim();
     	String res = "";
     	ResultSet rSet = null;
        String q = "SELECT COUNT(*) AS jlh FROM user WHERE username = '"+username+"'";
        try{
            rSet = Database.getResult(q, "cekUsername");
            rSet.next();
            res = rSet.getString("jlh");
            return Integer.valueOf(res) > 0;
        }catch(Exception e){
            System.out.println("Error cekUsername:" + e.getMessage());
            return false;
        }
    }

    public static int getLevelUser(String username){
    	int level = 0;
    	ResultSet rSet = null;
		String query = "SELECT level FROM user WHERE username = '"+username+"'";
         try{
           rSet = Database.getResult(query, "getLevelUser");
           rSet.next();
           level = Integer.parseInt(rSet.getString("level"));
        }catch(Exception e){
            System.out.println("Error getLevelUser, " + e.getMessage());
        }
        return level;
    }

    protected void getDetails(String nip){
         String query = "SELECT nip, username, password, nama_lengkap, level FROM user WHERE nip = '"+nip+"'";
         try{
            d.st = d.con.createStatement();
            d.rs = d.st.executeQuery(query);
            while (d.rs.next()){
            	this.setNIP(d.rs.getString("nip"));
            	this.setNama(d.rs.getString("nama_lengkap"));
                this.username = d.rs.getString("username");
                this.password = d.rs.getString("password");
                String l = d.rs.getString("level");
                this.level = Integer.parseInt(l);
            }
            d.st.close();
        }catch(Exception e){
            System.out.println("Error getDetails User, " + e.getMessage());
        }
     }

     public static void tambahUser(String NIP, String nama, String username, String level){
    	String p = "1234";
        String q = "INSERT INTO user(nip, nama_lengkap, username, password, level) VALUES ('"+NIP.trim()+"','"+nama.trim()+"','"+username.trim()+"',SUBSTRING(MD5('"+p+"'),1,25),'"+level.trim()+"')";
        try{
            Database.pSt = Database.con.prepareStatement(q);
            Database.pSt.executeUpdate();
            Database.pSt.close();
            JOptionPane.showMessageDialog(null, "Data berhasil tersimpan..");
        }catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error tambahUser: \n" +e.getMessage());
        }
    }

    public static void updateUser(String NIP, String nama, String username, String level){
        String q = "UPDATE user SET nama_lengkap = '"+nama.trim()+"', username = '"+username.trim()+"', level = '"+level.trim()+"' WHERE nip = '"+NIP+"'";
        try{
            Database.pSt = Database.con.prepareStatement(q);
            Database.pSt.executeUpdate();
            Database.pSt.close();
            JOptionPane.showMessageDialog(null, "Data berhasil ter-update..");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error updateUser: \n" +e.getMessage());
        }
    }

    public static void deleteUser(String NIP){
        String q = "DELETE FROM user WHERE nip = '"+NIP+"'";
        try{
            Database.pSt = Database.con.prepareStatement(q);
            Database.pSt.executeUpdate();
            Database.pSt.close();
            JOptionPane.showMessageDialog(null, "Data berhasil terhapus..");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error deleteUser: \n" +e.getMessage());
        }
    }

    public static ResultSet getData(){
    	 ResultSet rSet = null;
         String query = "SELECT nip, nama_lengkap, username, level FROM user";
         try{
            Database.st = Database.con.createStatement();
            rSet = Database.st.executeQuery(query);
        }catch(Exception e){
            System.out.println("Error getData User, " + e.getMessage());
        }
        return rSet;
   	}
}