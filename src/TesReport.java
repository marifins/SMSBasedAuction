/**
 * @(#)TesReport.java
 *
 *
 * @author
 * @version 1.00 2010/5/31
 */
    import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.*;
import net.sf.jasperreports.view.*;

import java.util.HashMap;

import java.io.File;

public class TesReport {


	private void cetak(){

	try{

		JasperReport jr;

		HashMap param=new HashMap();

		param.put("isi_dengan_parameter_iReport",variable_atau_isi_untuk_mengisi_parameter_tersebut);

		jr=(JasperReport)JRLoader.loadObject(new File("path_file_laporan.jasper"));

		JasperPrint jp=JasperFillManager.fillReport(jr,param,koneksi_sql.open()); //pada line ini membutuhkan open koneksi sql
		JasperViewer.viewReport(jp,false);
		JasperViewer.setDefaultLookAndFeelDecorated(true);

	}

	catch(Exception e){
		e.printStackTrace();
	}

	}
}
