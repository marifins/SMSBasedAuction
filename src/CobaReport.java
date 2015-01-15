/**
 * @(#)CobaReport.java
 *
 *
 * @author
 * @version 1.00 2010/6/1
 */
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.engine.design.JasperDesign;
import java.io.File;
import java.util.HashMap;

public class CobaReport extends JFrame implements ActionListener {

    JButton btn;
    public CobaReport() {
    	super("Coba Call Report");
    	btn = new JButton("Call Report");
    	btn.addActionListener(this);

    	this.add(btn,"Center");
    	this.pack();
    	this.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae){
		if(ae.getSource() == btn)
			cetak();
    }

    void cetak(){
    	try{
	    	String nm_file = "report/simple.jasper";
	    	String driver = "com.mysql.jdbc.Driver";
	    	String konek = "jdbc:mysql://localhost/sms";
	    	String user = "anca";
	    	String password = "4158260";

	    	HashMap parameter = new HashMap();
	    	Class.forName(driver);
	    	Connection conn = DriverManager.getConnection(konek, user, password);
	    	File report_file = new File(nm_file);
	    	JasperReport jasperReport = (JasperReport)JRLoader.loadObject(report_file.getPath());
	    	JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameter,conn);
	    	JasperViewer.viewReport(jasperPrint,false);
	    	JasperViewer.setDefaultLookAndFeelDecorated(true);
    	}catch(Exception e){
    		System.err.println(e);
    		}
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
		new CobaReport();
    }
}
