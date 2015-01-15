/**
 * @(#)SMS.java
 *
 *
 * @author
 * @version 1.00 2010/4/16
 */

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.TooManyListenersException;
import java.util.Date;
import java.util.regex.Pattern;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.comm.SerialPort;
import javax.comm.CommPortIdentifier;
import javax.comm.SerialPortEventListener;
import javax.comm.SerialPortEvent;
import javax.comm.PortInUseException;
import javax.comm.UnsupportedCommOperationException;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class SMS implements SerialPortEventListener {

    /** Creates a new instance of SMS */
    public SMS() {
       // Database.sambungDB();
        //setTerminal();
        //startServer();
    }

  SerialPort port = null;
  Enumeration portList = null;
  CommPortIdentifier portId = null;
  InputStream input;
  OutputStream output;
  String portName = null; // nama port
  int nilaiBaud = 19200; //  nilai Baudrate
  int nilaiData = SerialPort.DATABITS_8; // nilai Databits
  int nilaiStop = SerialPort.STOPBITS_1; // nilai Stopbits
  int nilaiParity = SerialPort.PARITY_NONE; // nilai parity
  int nilaiFlow = SerialPort.FLOWCONTROL_NONE; // nilai flowcontrol
  int statusServer = 1; // status server pada proses penyambungan
  public static int i=-1;

   // Awal method setTerminal
  public void setTerminal() {
    portName = "COM9";

    ServerLelang.proses.add("Server sedang mencari port yang aktif", ++i);
    try{
	    Enumeration portList = CommPortIdentifier.getPortIdentifiers();
	    while(portList.hasMoreElements()){
	      CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();
	      if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
	        if (portId.getName().equals(portName)) {
	          try{
	            port = (SerialPort) portId.open("SMS", 5000);
	            ServerLelang.proses.add("Server berhasil membuka port : " + portName, ++i);
	            ServerLelang.proses.select(i);
	          }catch(PortInUseException piue) {
	            ServerLelang.proses.add("Port : " + portName + " Sedang digunakan", ++i);
	            ServerLelang.proses.add("Penyambungan ke terminal gagal .........", ++i);
	            ServerLelang.proses.add("Terjadi kesalahan pada : " + piue, ++i);
	            ServerLelang.proses.select(i);
	          }
	        }
	      }
	    }
    }catch(Exception ex){
        ServerLelang.proses.add("Port tidak ditemukan!!!!", ++i);
        ServerLelang.proses.add("Kesalahan :"+ ex, ++i);
    }

    try{
      output = port.getOutputStream();
      input = port.getInputStream();
    }catch (IOException ioe) {
      ServerLelang.proses.add("Gagal membuka IOStream", ++i);
      ServerLelang.proses.add("Kesalahan pada : " + ioe, ++i);
      ServerLelang.proses.select(i);
    }
    // Setting konfigutasi serial port
    try{
      port.setSerialPortParams(nilaiBaud, nilaiData, nilaiStop, nilaiParity);
      port.setFlowControlMode(nilaiFlow);
      // notifikasi jika ada data pada terminal
      port.notifyOnDataAvailable(true);

      ServerLelang.proses.add("Server Melakukan Hubungan ke Port : " + portName, ++i);
      ServerLelang.proses.add("Server Berhasil Tehubung ke Port : " + portName, ++i);
      ServerLelang.proses.add("Server Sedang melakukan Pengaturan Terminal", ++i);
      ServerLelang.proses.add("Tunggu Sebentar .....", ++i);
      ServerLelang.proses.select(i);

      // Setting terminal
      kirimAT("AT" + "\15", 1250); // memeriksa apakah terminal sudah ready
      kirimAT("AT+CMGF=0" + "\15", 1250); // menetapkan mode PDU
      kirimAT("AT+CSCS=GSM" + "\15", 1250); // menetapkan encoding GSM
      //kirimAT("AT+CPMS=MT,MT,MT"+"\15",1250);// baca memory ponsel
      kirimAT("AT+CNMI=1,1,2,2,1" + "\15", 1250); // listen pesan secara otomatis
      kirimAT("AT+CMGL=0" + "\15", 1250); // baca pesan pada inbox yang belum dibaca

      statusServer = 2;// status terminal telah tersambung

    }catch (UnsupportedCommOperationException ucoe) {
     ServerLelang.proses.add("Setting data Serial Port gagal", ++i);
     ServerLelang.proses.add("Kesalahan pada : " + ucoe, ++i);
     ServerLelang.proses.select(i);
    }

    try {
      port.addEventListener(this);
    }catch (TooManyListenersException tmle) {
      ServerLelang.proses.add("Terjadi kesalahan pada : " + tmle, ++i);
      ServerLelang.proses.select(i);
    }

  } // Akhir Methode setTerminal

   /**
   * Methode balikKarakter
   */
  // Deklarasi variabel
  static int panjangKarakter = 0;
  static StringBuffer stringBuffer = null;

  // Awal methode balikKarakter
  public static String balikKarakter(String karakter) {
    panjangKarakter = karakter.length();
    stringBuffer = new StringBuffer(panjangKarakter);
    for(int i = 0; (i + 1) < panjangKarakter; i = i + 2) {
      stringBuffer.append(karakter.charAt(i + 1));
      stringBuffer.append(karakter.charAt(i));
    }
    return new String(stringBuffer);
  } // Akhir methode balikKarakter

  /**
   * Methode rubahKeHexa
   */
  // Deklarasi variabel
  static char[] hexa;
  static char[] karakter;

  // Awal method rubahKeHexa
  public static String rubahKeHexa(int a) {
    char[] hexa = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
        'E', 'F'};
    karakter = new char[2];
    // Mengambil hanya 8 bit 255d = 11111111 b
    a = a & 255;
    // hasil pembagian dengan 16
    karakter[0] = hexa[a / 16];
    // sisa hasil pembagian dengan 16
    karakter[1] = hexa[a % 16];

    return new String(karakter);
  } // Akhir Method rubahKeHexa

  /**
   * Method delapanKeTujuhBit
   */
  static char[] gsmToAsciiMap;

  // Awal method delapanKeTujuhBit
  public static String delapanKeTujuhBit(String pesan, int msglen) {
    int i, o, r = 0, rlen = 0, olen = 0, charcnt = 0;
    StringBuffer msg = new StringBuffer(160);
    int pesanlen = pesan.length();
    String ostr;
    char c;

    for (i = 0; ( (i + 1) < pesanlen) && (charcnt < msglen); i = i + 2) {
      // mengambil dua digit Hexadesimal
      ostr = pesan.substring(i, i + 2);
      o = Integer.parseInt(ostr, 16);
      // berikan nilai olen = 8
      olen = 8;
      // geser posisi semua bit ke kiri sebanyak rlen bit
      o <<= rlen;
      o |= r; // berikan sisa bit dari o ke r
      olen += rlen; // olen = olen + rlen

      c = (char) (o & 127); // mendapatkan nilai o menjadi 7 bit
      o >>>= 7; // geser posisi bit ke kanan sebanyak 7 bit
      olen -= 7;

      r = o; // menaruh sisa bit dari o ke r.
      rlen = olen;

      c = gsmToAsciiMap[c]; // convert ke Text (kode ASCII)
      msg.append(c); // tambahkan ke msg
      charcnt++; // nilai charcnt ditambahkan 1

      // jika rlen >= 7
      if (rlen >= 7) {
        c = (char) (r & 127);
        r >>>= 7;
        rlen -= 7;
        msg.append(c);
        charcnt++;
      }
    } // Akhir for

    if ( (rlen > 0) && (charcnt < msglen)) {
      msg.append( (char) r);
    }
    return msg.toString();
  } // Akhir method delapanKeTujuhBit

  /**
   * Method tujuhKeDelapanBit
   */
  // Deklarasi variabel
  static char[] asciiToGsmMap;

  // Awal method tujuhKeDelapanBit
  public static String tujuhKeDelapanBit(String pesan) {

    StringBuffer msg = new StringBuffer(pesan);

    StringBuffer encmsg = new StringBuffer(2 * 160);
    int bb = 0, bblen = 0, i;
    char o = 0, c = 0, tc;

    for (i = 0; i < msg.length() || bblen >= 8; i++) {
      if (i < msg.length()) {
        c = msg.charAt(i);
        tc = asciiToGsmMap[c];

        c = tc;

        c &= ~ (1 << 7);
        bb |= (c << bblen);
        bblen += 7;
      }

      while (bblen >= 8) {
        o = (char) (bb & 255);
        encmsg.append(rubahKeHexa(o));
        bb >>>= 8;
        bblen -= 8;
      }
    }
    if ((bblen > 0)) {
      encmsg.append(rubahKeHexa(bb));
    }
    return encmsg.toString();
  } // Akhir method tujuhKeDelapanBit

  static {
    final int lastindex = 255;
    gsmToAsciiMap = new char[lastindex + 1];
    asciiToGsmMap = new char[lastindex + 1];
    int i;

    for (i = 0; i <= lastindex; i++) {
      gsmToAsciiMap[i] = asciiToGsmMap[i] = (char) i;
    }
  } // Akhir static
 /**
   * Methode PduTerimaSms
   */
  // Deklarasi variabel
  String infoSmsc = null;
  int nilaiSmsc = 0;
  int nomorSmsc = 0;
  String panjangNotlp = null;
  int nilaiPanjangNotlp = 0;
  int nilaiNotlp = 0;
  String Notlp = null;
  String dapatNotlp = null;
  String panjangPesan = null;
  int nilaiPanjangPesan = 0;
  String pesanPDU = null;
  String pesan = null;

  // Awal method PduTerimaSms
  public void PduTerimaSms(String smspdu) {
    int i = 0;
    try {
      // Mengambil nilai panjang informasi SMSC
      infoSmsc = smspdu.substring(i, 2);
      nilaiSmsc = Integer.parseInt(infoSmsc, 16);
      // format nomor dan nomor MSC dibuang
      i = i + 4;
      nomorSmsc = i + (nilaiSmsc * 2) - 2;
      // Nilai PDU Type dibuang
      i = nomorSmsc + 2;
      // Mengambil Panjang Nomor Telepon Pengirim
      panjangNotlp = smspdu.substring(i, i + 2);
      nilaiPanjangNotlp = Integer.parseInt(panjangNotlp, 16);
      // format nomor pengirim dibuang
      i = i + 4;
      nilaiNotlp = i + nilaiPanjangNotlp + nilaiPanjangNotlp % 2;
      // Nomor telepon pengirim
      Notlp = smspdu.substring(i, nilaiNotlp);
      dapatNotlp = balikKarakter(Notlp);
      i = nilaiNotlp;
      // Nilai PID, DCS, dan SCTS dibuang
      i = i + 18;
      // Mengambil Panjang Pesan SMS
      panjangPesan = smspdu.substring(i, i + 2);
      nilaiPanjangPesan = Integer.parseInt(panjangPesan, 16);
      i = i + 2;
      pesanPDU = smspdu.substring(i, smspdu.length());
      pesan = delapanKeTujuhBit(pesanPDU, nilaiPanjangPesan);
    }
    catch (Exception e) {}
  }

 /**
   * Method PduKirimSms
   */
  // Deklarasi Variabel
  static StringBuffer pesanPDUKirim = null;
  static int panjangNotlpTujuan = 0;
  static int panjangPesanKirim = 0;
  static String PduPesan = null;

  // Awal method PduKirimSms
  public static String PduKirimSms(String notlp, String pesan) {
   try{
       ServerLelang.proses.add("pduKirirm sms dijalankan!", ++i);
       ServerLelang.proses.select(i);
    pesanPDUKirim = new StringBuffer(320); // 320 = 160 * 2 (panjang max)
    // Tambahkan nilai PDU Type ---> Default = 11
    pesanPDUKirim.append("11");
    // Tambahkan nilai MR ---> Default = 00
    pesanPDUKirim.append("00");
    // Tambahkan nilai panjang nomor pengirim
    panjangNotlpTujuan = notlp.length();
    pesanPDUKirim.append(rubahKeHexa(panjangNotlpTujuan));
    // Tambah nilai format no. telepon --> format internasional = 91
    pesanPDUKirim.append("81");
    //pesanPDUKirim.append("91");
    // Tambahkan nilai nomor telepon pengirim
    // Jika panjang notlp adalah ganjil
    if ( (notlp.length() % 2) == 1) {
      notlp = balikKarakter(notlp + "F");
    }
    // Jika panjang notlp adalah genap
    else {
      notlp = balikKarakter(notlp);
    }
    pesanPDUKirim.append(notlp);
    // tambahkan nilai PID ---> Default = 00
    pesanPDUKirim.append("00");
    // tambahkan nilai DCS ---> Default = 00
    pesanPDUKirim.append("00");
    // tambahkan nilai VP = 4 hari ---> AA h
    pesanPDUKirim.append("AA");
    panjangPesanKirim = pesan.length();
    PduPesan = tujuhKeDelapanBit(pesan);
    pesanPDUKirim.append(rubahKeHexa(panjangPesanKirim));
    pesanPDUKirim.append(PduPesan);


   }catch (Exception ex){
   ServerLelang.proses.add("Ada kesalahan saat ubah pdu:"+ ex);
   }
    return new String(pesanPDUKirim);
  } // Akhir Method PduKirimSms
/**
   * Method kirimAT
   */
  // Awal method kirimAT
  public void kirimAT(String atCmd, int delay) {
    Boolean tungguDelay = new Boolean(true);
    boolean getDelay = false;
    // Membuat antrian proses
    synchronized (tungguDelay) {
      try {
        // Menulis AT Commmand
        output.write( (atCmd).getBytes());
        // Hapus OutputStream
        output.flush();
      } //Akhir Try
      catch (IOException e) {}
      try {
        tungguDelay.wait(delay);
      } // Akhir try
      catch (InterruptedException ie) {
        getDelay = true;
      } // Akhir catch
    } // Akhir syncronized
  } // Akhir Methode kirim AT

  /**
   * Method terimaAT
   */
  // Deklarasi variabel
  String[] hasil;
  int Index;
  int panjangPDU;
  int PDU = 0;
  String respons;
  StringTokenizer st;

  // Awal method terimaAT
  public void terimaAT(String buffer) {
    // Menguraikan buffer berdasarkan karakter CRLF
    st = new StringTokenizer(buffer, "\r\n");

    while (st.hasMoreTokens()) {
      // mengambil token yang ada pada obyek
      respons = st.nextToken();
      // Cetak respon ke layar
      ServerLelang.proses.add(respons, ++i);
      ServerLelang.proses.select(i);
      // Memproses respon yang diterima
      try {
        // Jika Ada Telepon yang Masuk
        if (respons.startsWith("RING")) {
          kirimAT("ATH0" + "\15", 100); // Diputuskan
        } // Akhir if "RING"

        // Jika ada Pesan Baru yang Masuk
        else if (respons.startsWith("+CMTI:")) {
          Pattern pattern = Pattern.compile(",");
          hasil = pattern.split(respons.trim());
          Index = Integer.parseInt(hasil[1].trim());
          kirimAT("AT+CMGR=" + Index + "\15", 1250); // Baca Pesan Baru yang Masuk
        } // Akhir if "+CMTI:"

        // Jika ada Pesan Baru Yang dibaca
        else if (respons.startsWith("+CMGR:")) {
          PDU = 1;
        } // Akhir if "+CMGR:"

        // Membaca Pesan Indox yang belum dibaca
        else if (respons.startsWith("+CMGL")) {
          Pattern pattern = Pattern.compile(":");
          hasil = pattern.split(respons.trim());
          pattern = Pattern.compile(",");
          hasil = pattern.split(hasil[1].trim());
          Index = Integer.parseInt(hasil[0].trim());
          PDU = 1;

        } // Akhir if "+CMGL"
        else if (PDU == 1) {
          prosesTerimaSms(Index, respons.trim());
          PDU = 0;
        }
        else {}
      }
      catch (Exception e) {} // Akhir while
    }
  } // Akhir Method terimaAT

  /**
   * Methode prosesTerimaSms
   */
  // Awal method prosesTerimaSms
  public void prosesTerimaSms(int Index, String Pdu) {
    try {
      // Rubah dari format PDU menjadi Format Teks
      PduTerimaSms(Pdu);
    } // Akhir try
    catch (Exception e) {}
    // Jika nomor telepon pengirim diakhiri dengan "F"
    if (dapatNotlp.endsWith("F")) {
      // Buang karakter "F"
      dapatNotlp = dapatNotlp.substring(0, dapatNotlp.length() - 1);
    }
    ServerLelang.aPesan.setText("");
    ServerLelang.tfPengirim.setText(" +" +dapatNotlp);
    ServerLelang.tfPesanMasuk.setText(" " +pesan);
    // Hapus Pesan yang Telah dibaca
    kirimAT("AT+CMGD=" + Index + "\15", 2000);
    // Tulis ke tabel TERIMA
    ServerLelang.proses.add("Proses insert ke inbox server", ++i);
    ServerLelang.proses.select(i);
    Database.insertInbox(dapatNotlp, pesan);
    // Cetak ke Layar
    //dari.setText(" " + dapatNotlp);
    //isi.setText(" " + pesan);
    ServerLelang.proses.add("Pesan Dari Nomor : " + dapatNotlp, ++i);
    ServerLelang.proses.add("Isi Pesan : " + pesan, ++i);
    ServerLelang.proses.select(i);
  } // Akhir Method prosesTerimaSms


  /**
   * Method getInbox
   */
  // Deklarasi Variabel
  String idInbox = null; // ID Pesan
  String noPengirim = null; // Nomer Telepon Pengirim
  String isiPesan = null; // Isi Pesan
  static PreparedStatement pStatement = null;
  static String sql = null;

  // Awal methode getInbox
  public void getInbox() {
    try {
      sql ="SELECT id_inbox, no_pengirim, pesan FROM inbox WHERE status_inbox = 0";
      pStatement = Database.con.prepareStatement(sql);
      try {
        ResultSet rSet = pStatement.executeQuery();
        while (rSet.next()) {

          idInbox = rSet.getString("id_inbox");
          noPengirim = rSet.getString("no_pengirim");
          isiPesan = rSet.getString("pesan");

          prosesPesan(idInbox, noPengirim, isiPesan);
        }
        pStatement.close();
      }
      catch (Exception ie) {}
    }
    catch (Exception e) {}
  }

  /**
   * Method prosesPesan
   */
  // Deklarasi variabel
  String str1 = null;
  String str2 = null;
  String str3 = null;
  String str1Salah = null;
  String formatStr1 = null;
  String warnStr1 = null;

  public void prosesPesan(String idInbox, String noPengirim, String pesan) {
    Pattern pattern = Pattern.compile(" ");
    hasil = pattern.split(pesan.trim());

	if(hasil.length == 2){
		System.out.println("2");
		str1 = hasil[0].trim().toString().toUpperCase();
		str2 = hasil[1].trim().toString().toUpperCase();

		if(str1.equals("LELANG")){
			queryLelang2(noPengirim, str2);
		}else{
			str1Salah = "Format pesan " +str1 +" tidak terdaftar.";
			formatStr1 = "\nFormat yg benar: \n1. LELANG AKTIF";
			warnStr1 = str1Salah + formatStr1;
			System.out.println(noPengirim +" " +warnStr1);
			Database.insertOutbox(noPengirim,warnStr1);
		}
	}else if(hasil.length == 3){
		System.out.println("3");
		str1 = hasil[0].trim().toString().toUpperCase();
		str2 = hasil[1].trim().toString().toUpperCase();
		str3 = hasil[2].trim().toString().toUpperCase();

		if(str1.equals("LELANG")){
			queryLelang3(noPengirim, str2, str3);
		}else if(isInt(str1)){
			queryTawar(noPengirim, str1, str2, str3);
		}else{
			str1Salah = "Format pesan " +str1 +" tidak terdaftar.";
			formatStr1 = "\nFormat yg benar: \n1. LELANG MENANG KODE_BARANG \n2. PIN KODE_BARANG HARGA";
			warnStr1 = str1Salah + formatStr1;
			Database.insertOutbox(noPengirim,warnStr1);
		}
	}else{
		System.out.println("format salah");
		warnStr1 = "Format pesan salah. \nCoba periksa kembali format pesan yg anda kirim";
		Database.insertOutbox(noPengirim,warnStr1);
		Database.updateStatus(idInbox, "1");

	}

	Database.updateStatus(idInbox, "1");
  } // Akhir Method prosesTabelTerima

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
  /**
  *Method queryTawar
  */
  String bid = null;
  String str2TawarSalah = null;
  String replyTawar = null;

  public void queryTawar(String noPengirim, String str1, String str2, String str3){
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
				replyTawar = "Penawaran anda untuk barang " +str2 +" telah diproses.";
			}else{
				replyTawar = "Penawaran " +str3 +" utk barang "+str2 +" tidak berhasil. \nSilahkan ajukan penawaran yg lebih tinggi.";
			}
		}else{
		str2TawarSalah = "Kode barang " +str2 +" tidak ditemukan";
		str2TawarSalah += "\nSilahkan cek lelang yg akftif: LELANG AKTIF";
		replyTawar = str2TawarSalah;
		System.out.println("Kode Barang Salah..");
		}
	}else{
		System.out.println("PIN anda salah..");
		replyTawar = "PIN" +str1 +" tidak valid. \nPeriksa kembali PIN anda.";
	}

	Database.insertOutbox(noPengirim,replyTawar);
  }

//*****************************************************************************
  /**
  *Method queryLelang3
  */
  String kodeBarang = null;
  String str2Salah = null;
  String formatStr2 = null;
  String replyLelang = null;

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
		replyLelang = "Data LELANG " +str2 +" kode " +str3 +" Tidak ditemukan \nPeriksa format pesan yang anda kirim.";
	}
	Database.insertOutbox(noPengirim,replyLelang);
  }

// *****************************************************************************
  /**
  *Method queryLelang2
  */
  String replyLelang2 = null;

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
	}
	else{
		replyLelang2 = "Data LELANG " +str2 +" Tidak ditemukan \nPeriksa format pesan yang anda kirim.";
	}
	Database.insertOutbox(noPengirim,replyLelang2);
  }

// *****************************************************************************
  /**
   * Method getOutbox
   * Keterangan : Mengambil data pesan SMS pada tebel KIRIM yang ber-STATUS Belum Dikirim
   */
  // Deklarasi Variabel
  String id_outbox = null;
  String no_pengirim = null;
  String reply = null;

  // Awal methode getOutbox
  public void getOutbox() {
    try {
      // Statement SQL
      String sql =
          "SELECT id_outbox, no_tujuan, pesan FROM outbox WHERE status_outbox = 0";
      pStatement = Database.con.prepareStatement(sql);
      try {
        ResultSet rSet = pStatement.executeQuery();
        while (rSet.next()) {
          // Mengambil data dari tabel TERIMA
          id_outbox = rSet.getString("id_outbox"); // ID Pesan
          no_pengirim = rSet.getString("no_tujuan"); // Nomer Telepon Pengirim
          reply = rSet.getString("pesan"); // Isi Pesan
          ServerLelang.proses.add("Server Mengambil Data yang belum dikirim pada Tabel KIRIM",++i);
          ServerLelang.proses.select(i);
          // Berikan nilai ke methode prosesTabelTerima
          kirimSMS(id_outbox, no_pengirim, reply);
        } // Akhir while
        // Menutup Statement SQL
        pStatement.close();
      } // Akhir try ResultSet
      catch (Exception ie) {}
    } // Akhir try SQL
    catch (Exception e) {}
  } // Akhir Methode getOutbox

  /**
   * Methode kirimSMS
   */

  // Awal methode kirimSMS
  public void kirimSMS(String id_outbox, String noTujuan, String reply) {
    try {
      // Menulis status pesan Tebel KIRIM menjadi "Telah Dikirim"
      Database.updateStatus(id_outbox, "2");
      ServerLelang.proses.add("ProsesKirirm SMS dijalankan", ++i);
      // Merubah pesan menjadi Format PDU (Protocol Data Unit)
      String pesanPDUKirim = PduKirimSms(noTujuan.trim(),reply.trim());
      ServerLelang.proses.add("PDUnya: "+pesanPDUKirim, ++i);
      // Proses Mengirim Pesan
      kirimAT("AT+CMGS=" +(pesanPDUKirim.length()/2) + "\15", 500);
      kirimAT("00" +pesanPDUKirim, 2500); // Kirim Pesan Format PDU
      kirimAT("\032", 100); // Ctrl + Z

      // Menulis ke layar

      ServerLelang.tfPengirim.setText(" +" +noTujuan);
      ServerLelang.tfPesanMasuk.setText(" " +pesan);
      ServerLelang.aPesan.setText(reply);
      ServerLelang.proses.add("Kirim ke : +" + noTujuan, ++i);
      ServerLelang.proses.add("Isi Pesan Balasan : ", ++i);
      ServerLelang.proses.add(reply, ++i);
      ServerLelang.proses.add("Status : TELAH DIKIRIM", ++i);
      ServerLelang.proses.select(i);

      ServerLelang.proses.add("Tunggu sebentar HP belumsiap", ++i);
      ServerLelang.proses.select(i);

      // Berikan waktu sleep agar terminal siap kembali untuk mengirim pesan
      Thread.currentThread().sleep(10000);

      Database.updateStatus(id_outbox, "2");
    } // Akhir try

    catch (Exception e) {}
  } // Akhir Methode kirimSMS

// *****************************************************************************

  /**
  * Methode startServer
  */

  // Awal Methode startServer
  public void startServer() {
    //setDatabase();
    if (statusServer == 1) {
      try{
      	setTerminal();
      }catch(Exception e){
		JOptionPane.showMessageDialog(null,"Terminal belum siap!!");
		System.exit(0);
      }
      if (statusServer == 2) {
        ThreadGetOutbox outbox = new ThreadGetOutbox();
        ThreadGetInbox inbox = new ThreadGetInbox();
        outbox.start();
        inbox.start();
        //this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      }
    }
  } // Akhir Methode startServer

// Class Untuk selalu memanggil methode getInbox()
  class ThreadGetInbox extends Thread{
    public ThreadGetInbox(){}
    public void run() {
      while (true) {
        getInbox();
        try {
          Thread.sleep(1000);
        }
        catch (InterruptedException e) {}
      }
    }
  }

// **********************************************************************
// Class Untuk Selalu memanggil methode getOutbox()
  class ThreadGetOutbox extends Thread{
    public ThreadGetOutbox(){}
    public void run() {
      while (true) {
       getOutbox();
        try {
          Thread.sleep(2000);
        }
        catch (InterruptedException e) {}
      }
    }
  }

   // Deklarasi variabel
  int bufferOffset = 0;
  byte[] bacaBuffer = new byte[100000];
  int n;

  // Awal methode serialEvent
  public void serialEvent(SerialPortEvent event) {
    try {
      while ( (n = input.available()) > 0) {
        n = input.read(bacaBuffer, bufferOffset, n);
        bufferOffset += n;

        // Jika ada respons "\15" (Line Feed Carriage Return),
        if ( (bacaBuffer[bufferOffset - 1] == 10) &&
            (bacaBuffer[bufferOffset - 2] == 13)) {
          String buffer = new String(bacaBuffer, 0, bufferOffset - 2);
          // Berikan ke methode terimaAT
          terimaAT(buffer);

          bufferOffset = 0;
        } // Akhir if
      } // Akhir while
    } // Akhir try
    catch (IOException e) {
        ServerLelang.proses.add(""+e, ++i);
    }
  } // Akhir methode serialEvent


}
