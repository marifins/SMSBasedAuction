import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;

public class Coba{

	public static void showReport(String fileReport){
		Map<String, String> param = new HashMap<String, String>();
		param.put("ReportTitle", "Laporan Data Anggota");
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/sms", "anca","4158260");

			JasperDesign design = JRXmlLoader.load(fileReport);
			JasperReport report = JasperCompileManager.compileReport(design);
			JasperPrint print = JasperFillManager.fillReport(report, param, conn);
			JasperViewer.viewReport(print);
		}catch(ClassNotFoundException cnfe){
			System.err.println(cnfe.getMessage());
		}catch(Exception ex){
			System.err.println(ex);
		}
	}

	public static void main(String[]args){
		showReport("C:/Documents and Settings/marifins.MASCODE/reportPeserta.jrxml");
	}
}