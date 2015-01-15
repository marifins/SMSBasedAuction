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

 	public static String hexaToASCII(String pesan) {
		String ascii = "";
		String strPair;
		char ch;
	    int pair;
	    int len = pesan.length();
	    int udl = len / 2 + 1;
	    int i = 0; int ctr = 0; int tmp = 0; int tmpLen = 0;
	    while(((i + 1) < len) && (ctr < udl)){
	      strPair = pesan.substring(i, i + 2);
	      pair = Integer.parseInt(strPair, 16);
	      int octet = 8;
	      pair <<= tmpLen;
	      pair |= tmp;
	      octet += tmpLen;
	      ch = (char)(pair & 127);
	      pair >>>= 7;
	      octet -= 7;
	      tmp = pair;
	      tmpLen = octet;
	      ascii += ch;
	      ctr++;

	      if (tmpLen >= 7) {
	        ch = (char) (tmp & 127);
	        tmp >>>= 7;
	        tmpLen -= 7;
	        ascii += ch;
	        ctr++;
	      }
	      i += 2;
	    }
	    if ((tmpLen > 0) && (ctr < udl)) {
	      ascii += ((char)tmp);
	    }
	    return ascii;
  	}
  // Akhir method hexaToASCII

  // Awal method ASCIIToHexa
  public static String ASCIIToHexa(String pesan) {
    String encPesan = "";
    char octet = 0;
    char ch = 0;
    int i = 0; int bt = 0; int btLen = 0;
    while(i < pesan.length() || btLen >= 8){
      if (i < pesan.length()) {
        ch = pesan.charAt(i);
        ch &= ~ (1 << 7);
        bt |= (ch << btLen);
        btLen += 7;
      }
      while (btLen >= 8) {
        octet = (char) (bt & 255);
        encPesan += decToHexa(octet);
        bt >>>= 8;
        btLen -= 8;
      }
      i++;
    }
    if ((btLen > 0)) {
      encPesan += decToHexa(bt);
    }
    return encPesan;
  }
  // Akhir method ASCIIToHexa

  static String noPengirim = null;
  static String pesanTerima = null;

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
      pesanTerima = hexaToASCII(pesanPDUTerima);
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
		    lenPesanKirim = pesan.length() + port5000.length()/2;
		    hexaLenPesan = decToHexa(lenPesanKirim);
		    PDUPesan = ASCIIToHexa(pesan);
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
      MainServer.proses.add(respons, ++i);
      MainServer.proses.select(i);
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
    MainServer.aPesan.setText("");
    MainServer.tfPengirim.setText(" +" +noPengirim);
    MainServer.tfPesanMasuk.setText(" " +pesanTerima);
    sendAT("AT+CMGD=" + Index + "\15", 2000);
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
      String pesanPDUKirim = PDUKirim(noTujuan.trim(),reply.trim());
      //MainServer.proses.add("PDUnya: "+pesanPDUKirim, ++i);
      sendAT("AT+CMGS=" +(pesanPDUKirim.length()/2) + "\15", 500);
      sendAT("00" +pesanPDUKirim, 2500);
      sendAT("\032", 100); // Ctrl + Z
      MainServer.tfPengirim.setText(" +" +noTujuan);
      MainServer.tfPesanMasuk.setText(" " +pesanTerima);
      MainServer.aPesan.setText(reply);
      MainServer.proses.add("kirim ke : +" + noTujuan, ++i);
      MainServer.proses.select(i);
      MainServer.proses.add("loading..", ++i);
      MainServer.proses.select(i);

      Thread.currentThread().sleep(10000);
      MainServer.proses.add("status : sudah dikirim", ++i);
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
		JOptionPane.showMessageDialog(null,"Terminal belum ready!!");
		System.exit(0);
      }
      if (statusServer == 2) {
        ThreadGetOutbox outbox = new ThreadGetOutbox();
        ThreadGetInbox inbox = new ThreadGetInbox();
        outbox.start();
        inbox.start();
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

    // Awal method setServer
  public void setServer() {
    namaPort = "COM9";

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
