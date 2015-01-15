/**
 * @(#)Tes.java
 *
 *
 * @author
 * @version 1.00 2010/5/27
 */

public class Tes {

  static String noPengirim = "";
  static String pesanTerima = "";

    /**
     * Creates a new instance of <code>Tes</code>.
     */
     public static void main(String[] args) {
        PDUTerima("0011000E91268862515386F50000AB0DD3F49C5E6E83986576D87D06");
		System.out.println(noPengirim);
		System.out.println(pesanTerima);
    }
    public static String balikKarakter(String karakter) {
  	int lenKarakter = 0;
    StringBuffer sb = null;
    lenKarakter = karakter.length();
    sb = new StringBuffer(lenKarakter);
    for(int i = 0; (i + 1) < lenKarakter; i += 2) {
      sb.append(karakter.charAt(i + 1));
      sb.append(karakter.charAt(i));
    }
    return new String(sb);
  }
  // Akhir method balikKarakter

  //*****************************************************************************

  // Awal method decToHexa
  public static String decToHexa(int dec) {
    char[] hexa = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D','E', 'F'};
    char[] digit = new char[2];
    dec = dec & 255; //get hanya 8 bit 255d = 11111111 b
    digit[0] = hexa[dec / 16];
    digit[1] = hexa[dec % 16];
    return new String(digit);
  }
  // Akhir method decToHexa

  //*****************************************************************************
  static char[] gsmToAsciiMap;

  // Awal method hexaToASCII
  public static String hexaToASCII(String pesan, int msglen) {
    int i, o, r = 0, rlen = 0, olen = 0, charcnt = 0;
    StringBuffer msg = new StringBuffer(160);
    int pesanlen = pesan.length();
    String ostr;
    char c;

    for (i = 0; ( (i + 1) < pesanlen) && (charcnt < msglen); i = i + 2) {
      ostr = pesan.substring(i, i + 2);
      o = Integer.parseInt(ostr, 16);
      // berikan nilai olen = 8
      olen = 8;
      // geser posisi semua bit ke kiri sebanyak rlen bit
      o <<= rlen;
      o |= r; // berikan sisa bit dari o ke r
      olen += rlen;
      c = (char) (o & 127); // get nilai o menjadi 7 bit
      o >>>= 7; // geser posisi bit ke kanan sebanyak 7 bit
      olen -= 7;
      r = o; // menaruh sisa bit dari o ke r.
      rlen = olen;
      c = gsmToAsciiMap[c];
      msg.append(c);
      charcnt++;

      if (rlen >= 7) {
        c = (char) (r & 127);
        r >>>= 7;
        rlen -= 7;
        msg.append(c);
        charcnt++;
      }
    }
    if ( (rlen > 0) && (charcnt < msglen)) {
      msg.append( (char) r);
    }
    return msg.toString();
  }
  // Akhir method hexaToASCII

  //*****************************************************************************
  static char[] asciiToGsmMap;

  // Awal method ASCIIToHexa
  public static String ASCIIToHexa(String pesan) {
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
        encmsg.append(decToHexa(o));
        bb >>>= 8;
        bblen -= 8;
      }
    }
    if ((bblen > 0)) {
      encmsg.append(decToHexa(bb));
    }
    return encmsg.toString();
  }
  // Akhir method ASCIIToHexa

  static {
    final int lastindex = 255;
    gsmToAsciiMap = new char[lastindex + 1];
    asciiToGsmMap = new char[lastindex + 1];
    int i;
    for (i = 0; i <= lastindex; i++) {
      gsmToAsciiMap[i] = asciiToGsmMap[i] = (char) i;
    }
  }

  //*****************************************************************************



  // Awal method PDUTerima
  public static String PDUTerima(String pesanPDU) {
  	int lenSMSC = 0;
  	int lenNoPengirim = 0;
  	int lenPesan = 0;
    int i = 0;
    try {
      String strLenSMSC = pesanPDU.substring(i, 2);
      lenSMSC = Integer.parseInt(strLenSMSC, 16);
      i = i + 4; //format no. SMSC diabaikan
      i = i + (lenSMSC * 2) - 2; //no. SMSC diabaikan
      i = i + 2; //PDU Type diabaiakan
      String strLenNoPengirim = pesanPDU.substring(i, i + 2); // get pjg no. pengirim
      lenNoPengirim = Integer.parseInt(strLenNoPengirim, 16); //format nomor pengirim diabaikan
      i = i + 4;
      int nilaiNotlp = i + lenNoPengirim + lenNoPengirim % 2;
      String PDUNoPonsel = pesanPDU.substring(i, nilaiNotlp); //get no. pengirim
      noPengirim = balikKarakter(PDUNoPonsel);
      i = nilaiNotlp; //nilai PID, DCS, dan SCTS diabaikan
      i = i + 18;
      String strLenPesan = pesanPDU.substring(i, i + 2); //get pjg pesan yang diterima
      lenPesan = Integer.parseInt(strLenPesan, 16);
      i = i + 2;
      String pesanPDUTerima = pesanPDU.substring(i, pesanPDU.length());
      pesanTerima = hexaToASCII(pesanPDUTerima, lenPesan);
      return pesanTerima;
    }
    catch (Exception e) {
    	//MainServer.proses.add("Error PDUTerima: "+ e);
    }return pesanTerima;
  }

  //*****************************************************************************
  // Awal method PDUKirim
  public static String PDUKirim(String noPonsel, String pesan) {
  	StringBuffer strPDU = null;
    int lenNoTujuan = 0;
    int lenPesanKirim = 0;
    String PDUPesan = null;
  	try{
	    //MainServer.proses.add("pduKirirm sms dijalankan!", ++i);
	    //MainServer.proses.select(i);
	    strPDU = new StringBuffer(320); // 320 = 160 * 2 (panjang max)
	    strPDU.append("11"); //nilai default PDU Type = 11
	    strPDU.append("00"); //nilai default MR = 00
	    lenNoTujuan = noPonsel.length();
	    strPDU.append(decToHexa(lenNoTujuan)); //nilai hexa no. tujuan = 11
	    strPDU.append("91"); //format no. internasional
	    // Jika pjg no. ponsel ganjil
	    if ( (noPonsel.length() % 2) == 1) {
	      noPonsel = balikKarakter(noPonsel + "F");
	    }
	    // Jika panjang noPonsel adalah genap
	    else {
	      noPonsel = balikKarakter(noPonsel);
	    }
	    strPDU.append(noPonsel);
	    strPDU.append("00"); //nilai default PID = 00
	    strPDU.append("00"); //nilai default DCS = 00
	    strPDU.append("AB"); //nilai VP = 5 hari
	    lenPesanKirim = pesan.length();
	    PDUPesan = ASCIIToHexa(pesan);
	    strPDU.append(decToHexa(lenPesanKirim));
	    strPDU.append(PDUPesan);
    }catch (Exception ex){
   		//MainServer.proses.add("Error PDUKirim: "+ ex);
    }
    return new String(strPDU);
  }
  // Akhir method PDUKirim

}
