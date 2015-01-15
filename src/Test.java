public class Test{
	static String noPengirim = "";
 	static String pesanTerima = "";
	public static void main(String[]args){
		String s = koreksiNo("0102030405");
		System.out.println(s);

		String d = decToHexa(125);
		System.out.println(d);

		System.out.println(Integer.parseInt("B2",16));

		String a = hexaToASCII("D4B23CDD0E8396E1791A0D");
		System.out.println(a);

		String b = ASCIIToHexa("Terima Kasih");
		System.out.println(b);

		String pduKirim = PDUKirim("6281265888055","Terima Kasih");
		System.out.println(pduKirim);

		PDUTerima("0011000E91268862515386F50000AB0DD3F49C5E6E83986576D87D06");
		System.out.println(noPengirim);
		System.out.println(pesanTerima);
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
	      // geser posisi semua bit ke kiri sebanyak tmpLen bit
	      pair <<= tmpLen;
	      pair |= tmp; // berikan sisa bit dari pair ke tmp
	      octet += tmpLen;
	      ch = (char)(pair & 127); // get nilai pair menjadi 7 bit
	      pair >>>= 7; // geser posisi bit ke kanan sebanyak 7 bit
	      octet -= 7;
	      tmp = pair; // menaruh sisa bit dari pair ke tmp.
	      tmpLen = octet;
	      //ch = gsmToAsciiMap[ch];
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

  //*****************************************************************************

  // Awal method ASCIIToHexa
  public static String ASCIIToHexa(String pesan) {
    String encPesan = "";
    char octet = 0;
    char ch = 0;
    int i = 0; int bt = 0; int btLen = 0;
    while(i < pesan.length() || btLen >= 8){
      if (i < pesan.length()) {
        ch = pesan.charAt(i);
        //tc = asciiToGsmMap[ch];
        //ch = tc;
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

  	public static void PDUTerima(String pesanPDU) {
	  	int lenSMSC = 0;
	  	int lenNoPengirim = 0;
	  	int lenPesan = 0;
	    int i = 0;
	    try {
	      String strLenSMSC = pesanPDU.substring(i, 2);
	      lenSMSC = Integer.parseInt(strLenSMSC, 16);
	      i = i + 4 + (lenSMSC * 2) - 2 + 2; //format no. SMSC, PDU Type diabaikan
	      String strLenNoPengirim = pesanPDU.substring(i, i + 2); // get pjg no. pengirim
	      lenNoPengirim = Integer.parseInt(strLenNoPengirim, 16); //format nomor pengirim diabaikan
	      i = i + 4;
	      int nilaiNotlp = i + lenNoPengirim + lenNoPengirim % 2;
	      String PDUNoPonsel = pesanPDU.substring(i, nilaiNotlp); //get no. pengirim
	      noPengirim = koreksiNo(PDUNoPonsel);
	      i = nilaiNotlp; //nilai PID, DCS, dan SCTS diabaikan
	      i = i + 18;
	      String strLenPesan = pesanPDU.substring(i, i + 2); //get pjg pesan yang diterima
	      lenPesan = Integer.parseInt(strLenPesan, 16);
	      i = i + 2;
	      String pesanPDUTerima = pesanPDU.substring(i, pesanPDU.length());
	      pesanTerima = hexaToASCII(pesanPDUTerima);
	    }
	    catch (Exception e) {
	    	System.out.println("Error PDUTerima: "+ e);
	    }
  	}

}