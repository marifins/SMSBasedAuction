/**
 * @(#)ServerLelang.java
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

import javax.comm.SerialPort;
import javax.comm.CommPortIdentifier;
import javax.comm.SerialPortEventListener;
import javax.comm.SerialPortEvent;
import javax.comm.PortInUseException;
import javax.comm.UnsupportedCommOperationException;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class ServerLelang implements SerialPortEventListener{

   	public ServerLelang() {}
   	int statusServer = 1;
   	public static int i = 0;

	private static String ASCIIToSeptetString(char ch){
     	 int desimal = (int)ch;
         int displayMask = 1 << 6;
         String bit = "";
         for ( int c = 1; c <= 7; c++ ){
             bit += (desimal & displayMask) == 0 ? '0' : '1';
             desimal <<= 1;
         }
         return bit;
     }

	private static String bitStringToHexa(String val){
		int desimal = Integer.parseInt(val, 2);
		String hexa = Integer.toHexString(desimal);
		if(hexa.length() == 1) hexa = "0" + hexa;
		return hexa.toUpperCase();
     }

	private static String bitStringToASCII(String val){
		int desimal = Integer.parseInt(val, 2);
		return String.valueOf((char)desimal);
    }

    private static String hexaToOctetString(String str){
    	int hexa = Integer.parseInt(str, 16);
        int displayMask = 1 << 7;
        String bit = "";
        for ( int c = 1; c <= 8; c++ ){
            bit += (hexa & displayMask) == 0 ? '0' : '1';
            hexa <<= 1;
        }
        return bit;
    }

   	public static String decToHexa(int dec) {
   		String hex = "";
		String digits = "0123456789ABCDEF";
    	if (dec == 0) return "0";
		while (dec > 0) {
      		int digit = dec % 16;
        	hex = digits.charAt(digit) + hex;
        	dec = dec / 16;
    	}
   		if(hex.length() == 1) hex = "0" + hex;
   		return hex;
   	}

  	public static String koreksiNo(String no) {
  		String s = "";
  		int len = no.length();
	  	int i = 0;
	    while((i+1) < len){
	      s += no.charAt(i + 1);
	      s += no.charAt(i);
	      i += 2;
	    }
	    return s;
  	}

	// Awal method hexaToString
 	private static String hexaToString(String val){
     	int len = val.length()/2;
		String bitOctet[] = new String[len];
		String ambil = "";
		String sisa = "";
		String hexa = "";
		String h = "";
		for(int i=0; i<len; i++){
			hexa = val.substring(i*2, 2+i*2);
			bitOctet[i] = hexaToOctetString(hexa);
		}
		int i = 0; int ctr = 0;
		while(i < len){
			ambil = bitOctet[i].substring(ctr+1);
			if(ctr == 0) sisa = "";
			else sisa = bitOctet[i-1].substring(0,ctr);
			h += bitStringToASCII(ambil + sisa);
			if(ambil.length() == 0){
				ctr = 0;
				sisa = "";
				ambil = bitOctet[i].substring(ctr+1);
				h += bitStringToASCII(ambil + sisa);
			}
			i++;
			ctr++;
		}
		return h;
    }
    // Akhir method hexaToString

    // Awal method stringToHexa
	private static String stringToHexa(String pesan){
     	int len = pesan.length();
     	String ambil = "";
		String sisa = "";
		int ctr = 0;
		String bitSeptet[] = new String[len];
		String hexa = "";
		for(int i = 0; i < len; i++){
			bitSeptet[i] = ASCIIToSeptetString(pesan.charAt(i));
		}
		int i = 0;
		while(i < len){
			int lenSeptet = 7;
		 	if (ambil.length() == 1){
		 		ctr = 0;
				i++;
		 	}
		 	ambil = bitSeptet[i].substring(0,lenSeptet-ctr);
		 	if(i == pesan.length()-1){
		 		sisa = "";
		 		for(int k=0; k < (8 - ambil.length()); k++) sisa += "0";
		 	}else sisa = bitSeptet[i+1].substring(lenSeptet-ctr-1);
			hexa += bitStringToHexa(sisa + ambil);
			i++;
			ctr++;
		}
		return hexa;
    }
    // Akhir method stringToHexa

  static String noPengirim = null;
  static String pesanTerima = "";

  // Awal method PDUTerima
  public static void PDUTerima(String pesanPDU) {
  	int lenSMSC = 0;
  	int lenNoPengirim = 0;
  	int lenPesan = 0;
    int i = 0;
    try {
      String strLenSMSC = pesanPDU.substring(i, 2);
      lenSMSC = Integer.parseInt(strLenSMSC, 16);
      i = i + 4 + ((lenSMSC * 2) - 2) + 2; //format no. SMSC dan PDU Type diabaikan
      String strLenNoPengirim = pesanPDU.substring(i, i + 2); // get pjg no. pengirim
      lenNoPengirim = Integer.parseInt(strLenNoPengirim, 16); //format nomor pengirim diabaikan
      i = i + 4;
      int nilaiNotlp = i + lenNoPengirim + lenNoPengirim % 2;
      String PDUNoPonsel = pesanPDU.substring(i, nilaiNotlp); //get no. pengirim
      noPengirim = koreksiNo(PDUNoPonsel);
      i = nilaiNotlp + 18; //nilai PID, DCS, dan SCTS diabaikan
      String strLenPesan = pesanPDU.substring(i, i + 2); //get pjg pesan yang diterima
      lenPesan = Integer.parseInt(strLenPesan, 16);
      i = i + 2;
      String pesanPDUTerima = pesanPDU.substring(i, pesanPDU.length());
      //pesanTerima = hexaToASCII(pesanPDUTerima, lenPesan);
    }
    catch (Exception e) {
    	MainServer.proses.add("Error PDUTerima: "+ e);
    }
  }

  // Awal method PDUKirim

  public static String PDUKirim(String noPonsel, String pesan) {
	  	String strPDU = "";
	  	String PDUPesan = "";
	  	String hexaLenNo = "";
	  	String hexaLenPesan = "";
	    int lenNoTujuan = 0;
	    int lenPesanKirim = 0;
	    final String port5000 = "060504C3500000";
	  	try{
	  		lenNoTujuan = noPonsel.length();
		    hexaLenNo = decToHexa(lenNoTujuan);
		    if((lenNoTujuan % 2) == 1){
		      noPonsel = koreksiNo(noPonsel + "F");
		    }else{
		      noPonsel = koreksiNo(noPonsel);
		    }
		    lenPesanKirim = pesan.length() + port5000.length()/2 + 1;
		    hexaLenPesan = decToHexa(lenPesanKirim);
		    PDUPesan = stringToHexa(pesan);
		    strPDU += "5100" + hexaLenNo + "91" + noPonsel + "0000AB" + hexaLenPesan + port5000 + PDUPesan;
	    }catch (Exception ex){
	   		System.err.println(ex);
	    }
	    return strPDU;
  }
  // Akhir method PDUKirim


  // Awal method sendAT
  public static void sendAT(String ATCommand, int delay) {
    Boolean tungguDelay = new Boolean(true);
    boolean getDelay = false;
    synchronized (tungguDelay) {
      try {
        output.write( (ATCommand).getBytes()); //write AT Commmand
        output.flush(); //hapus OutputStream
      }
      catch (IOException e) {}
      try {
        tungguDelay.wait(delay);
      }
      catch (InterruptedException ie) {
        getDelay = true;
      }
    }
  }
  // Akhir method sendAT


  static String[] hasil;
  static int Index;
  static int panjangPDU;
  static int PDU = 0;
  static String respons;
  static StringTokenizer st;

  // Awal method getAT
  public static void getAT(String buffer) {
    st = new StringTokenizer(buffer, "\r\n"); //uraikan buffer berdasarkan karakter CRLF
    while (st.hasMoreTokens()) {
      respons = st.nextToken(); //get token yang ada pada obyek
      //MainServer.proses.add(respons, ++i);
      //MainServer.proses.select(i);
      try {
        if (respons.startsWith("RING")) {// jika ada telp. masuk diputuskan
          sendAT("ATH0" + "\15", 100);
        }else if (respons.startsWith("+CMTI:")) {// jika ada pesan baru yg masuk
          Pattern pattern = Pattern.compile(",");
          hasil = pattern.split(respons.trim());
          Index = Integer.parseInt(hasil[1].trim());
          sendAT("AT+CMGR=" + Index + "\15", 1250); // baca pesan baru yg masuk
        }else if (respons.startsWith("+CMGR:")) {// jika ada pesan baru yg dibaca
          PDU = 1;
        }else if (respons.startsWith("+CMGL")) { // jika ada pesan inbox yg blm dibaca
          Pattern pattern = Pattern.compile(":");
          hasil = pattern.split(respons.trim());
          pattern = Pattern.compile(",");
          hasil = pattern.split(hasil[1].trim());
          Index = Integer.parseInt(hasil[0].trim());
          PDU = 1;
        }else if (PDU == 1) {
          getSMS(Index, respons.trim());
          PDU = 0;
        }else {}
      }
      catch (Exception e) {}
    }
  }
  // Akhir method getAT

  // Awal method getSMS
  public static void getSMS(int Index, String Pdu) {
    try {
      PDUTerima(Pdu);
    }catch (Exception e) {}
    if (noPengirim.endsWith("F")) {
      noPengirim = noPengirim.substring(0, noPengirim.length() - 1);
    }
    MainServer.proses.add("penerimaan pesan..", ++i);
    MainServer.proses.select(i);
    MainServer.aPesanReply.setText("");
    MainServer.tfNoPonsel.setText(" +" +noPengirim);
    MainServer.aPesanMasuk.setText(" " +pesanTerima);
    sendAT("AT+CMGD=" + Index + "\15", 2000);
    MainServer.proses.add("pesan pada ponsel server dihapus..", ++i);
    MainServer.proses.select(i);
    Inbox.insertInbox(noPengirim, pesanTerima);
    MainServer.proses.add("pengirim : " + noPengirim, ++i);
    MainServer.proses.add("pesan : " + pesanTerima, ++i);
    MainServer.proses.select(i);
  }
  // Akhir method getSMS

  // Awal method sendSMS
  public static void sendSMS(String id_outbox, String noTujuan, String reply) {
    try {
      Database.updateStatus(id_outbox, "2");
      MainServer.proses.select(i);
      MainServer.proses.add("pengiriman pesan..", ++i);
      MainServer.proses.select(i);
      String pesanPDUKirim = PDUKirim(noTujuan.trim(),reply.trim());
      sendAT("AT+CMGS=" +(pesanPDUKirim.length()/2) + "\15", 500);
      sendAT("00" +pesanPDUKirim, 2500);
      sendAT("\032", 100); // Ctrl + Z
      MainServer.tfNoPonsel.setText(" +" +noTujuan);
      MainServer.aPesanMasuk.setText(" " +pesanTerima);
      MainServer.aPesanReply.setText(reply);
      MainServer.proses.add("sedang dikirim ke : +" + noTujuan, ++i);
      MainServer.proses.select(i);
      MainServer.proses.add("loading pengiriman pesan..", ++i);
      MainServer.proses.select(i);

      Thread.currentThread().sleep(10000);
      MainServer.proses.add("status : sudah dikirim", ++i);
      MainServer.proses.select(i);
      Database.updateStatus(id_outbox, "2");
    }catch (Exception e) {}
  }
  // Akhir method sendSMS

  // Awal method startServer
  public void startServer() {
    if (statusServer == 1) {
      try{
      	setServer();
      }catch(Exception e){
		JOptionPane.showMessageDialog(null,"Ponsel server belum tersambung!!");
		System.exit(0);
      }
      if (statusServer == 2) {
        ThreadGetOutbox outbox = new ThreadGetOutbox();
        ThreadGetInbox inbox = new ThreadGetInbox();
        inbox.start();
        outbox.start();
        setWinner.start();
      }
    }
  }
  // Akhir method startServer

  class ThreadGetInbox extends Thread{
    public ThreadGetInbox(){}
    public void run() {
      while (true) {
      	Inbox in = new Inbox();
        in.getInbox();
        try {
          Thread.sleep(1000);
        }
        catch (InterruptedException e) {}
      }
    }
  }

  class ThreadGetOutbox extends Thread{
    public ThreadGetOutbox(){}
    public void run() {
      while (true) {
       Outbox out = new Outbox();
       out.getOutbox();
        try {
          Thread.sleep(2000);
        }
        catch (InterruptedException e) {}
      }
    }
  }

  ThreadSetPemenang setWinner = new ThreadSetPemenang();
  class ThreadSetPemenang extends Thread{
  	String waktu = ""; String waktuSelesai = "";
  	int i = 0;
    public ThreadSetPemenang(){}
    public void run() {
    	waktuSelesai = Database.getWaktuSelesai();
      	while (true) {
	       String s = MainServer.getWaktu();
	       i = s.lastIndexOf("|  ") + 3;
	       waktu = s.substring(i);
	       if(waktu.startsWith(waktuSelesai)){
		       Pemenang winner = new Pemenang();
		       winner.setPemenang();
		       setWinner.stop();
	       }
	       System.out.println(waktu);
	       try {
	         Thread.sleep(2000);
	       }
	       catch (InterruptedException e) {}
      }
    }
  }

    // Awal method setServer
  public void setServer() {
    namaPort = "COM23";

    MainServer.proses.add("mencari port yang aktif..", ++i);
    try{
	    Enumeration portList = CommPortIdentifier.getPortIdentifiers();
	    while(portList.hasMoreElements()){
	      CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();
	      if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
	        if (portId.getName().equals(namaPort)) {
	          try{
	            port = (SerialPort) portId.open("SMS", 5000);
	            MainServer.proses.select(i);
	          }catch(PortInUseException piue) {
	            MainServer.proses.add("port : " + namaPort + " sedang digunakan", ++i);
	            MainServer.proses.add("koneksi ke terminal gagal..", ++i);
	            MainServer.proses.add("Error : " + piue, ++i);
	            MainServer.proses.select(i);
	          }
	        }
	      }
	    }
    }catch(Exception ex){
        MainServer.proses.add("port tidak ditemukan!!!!", ++i);
        MainServer.proses.add("Error :"+ ex, ++i);
    }
    try{
      output = port.getOutputStream();
      input = port.getInputStream();
    }catch (IOException ioe) {
      MainServer.proses.add("gagal membuka IOStream", ++i);
      MainServer.proses.add("Error : " + ioe, ++i);
      MainServer.proses.select(i);
    }
    try{
      port.setSerialPortParams(nilaiBaud, nilaiData, nilaiStop, nilaiParity);
      port.setFlowControlMode(nilaiFlow);
      port.notifyOnDataAvailable(true);// notifikasi jika ada data pada terminal

      MainServer.proses.add("berhasil tehubung ke port : " + namaPort, ++i);
      MainServer.proses.add("pengaturan ponsel server..", ++i);
      MainServer.proses.add("loading...", ++i);
      MainServer.proses.select(i);

      sendAT("AT" + "\15", 1250);
      sendAT("AT+CMGF=0" + "\15", 1250);
      sendAT("AT+CSCS=GSM" + "\15", 1250);
      //sendAT("AT+CPMS=MT,MT,MT"+"\15",1250);
      sendAT("AT+CNMI=1,1,2,2,1" + "\15", 1250);
      sendAT("AT+CMGL=0" + "\15", 1250);

      MainServer.proses.add("pengaturan selesai..", ++i);
      MainServer.proses.add("server ready..", ++i);
      MainServer.proses.select(i);

      statusServer = 2;//terminal tersambung

    }catch (UnsupportedCommOperationException ucoe) {
     MainServer.proses.add("pengaturan data Serial Port gagal", ++i);
     MainServer.proses.add("Error : " + ucoe, ++i);
     MainServer.proses.select(i);
    }

    try {
      port.addEventListener(this);
    }catch (TooManyListenersException tmle) {
      MainServer.proses.add("Error : " + tmle, ++i);
      MainServer.proses.select(i);
    }

  }
  // Akhir method setServer

  int bufferOffset = 0;
  byte[] bacaBuffer = new byte[100000];
  int n;
  public void serialEvent(SerialPortEvent event) {
    try {
      while ( (n = input.available()) > 0) {
        n = input.read(bacaBuffer, bufferOffset, n);
        bufferOffset += n;
        // Jika ada respons "\15" (Line Feed Carriage Return),
        if ( (bacaBuffer[bufferOffset - 1] == 10) &&
            (bacaBuffer[bufferOffset - 2] == 13)) {
          String buffer = new String(bacaBuffer, 0, bufferOffset - 2);
          getAT(buffer);
          bufferOffset = 0;
        }
      }
    }
    catch (IOException e) {
        MainServer.proses.add(""+e, ++i);
    }
  }

  SerialPort port = null;
  Enumeration portList = null;
  CommPortIdentifier portId = null;
  InputStream input;
  static OutputStream output;
  String namaPort = null;

  int nilaiBaud = 19200;
  int nilaiData = SerialPort.DATABITS_8;
  int nilaiStop = SerialPort.STOPBITS_1;
  int nilaiParity = SerialPort.PARITY_NONE;
  int nilaiFlow = SerialPort.FLOWCONTROL_NONE;

}
