/**
 * @(#)Karyawan.java
 *
 *
 * @author
 * @version 1.00 2010/5/10
 */

class Karyawan{
	private String NIP;
	private static String nama;
	private int usia;
	private String alamat;
	private String jabatan;

	public Karyawan(){
	}

	public String getNIP(){
		return this.NIP;
	}

	public void setNIP(String NIP){
		this.NIP = NIP;
	}

	public String getNama(){
		return this.nama;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public int getUsia(){
		return this.usia;
	}

	public void setUsia(int usia){
		this.usia = usia;
	}

	public String getAlamat(){
		return this.alamat;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getJabatan(){
		return this.jabatan;
	}

	public void setJabatan(String jabatan){
		this.jabatan = jabatan;
	}
}