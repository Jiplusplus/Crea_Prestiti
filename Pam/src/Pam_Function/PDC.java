package Pam_Function;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PDC {
	public static void pdc(Statement st)throws SQLException {
		controlloBanca(st);
		controlloInteressi(st);
		controlloCliente(st);
		
	}

	private static void controlloCliente(Statement st) throws SQLException {
		boolean inserisci=false;
		int rowCount = 0;
		
		ResultSet rs= st.executeQuery("SELECT * FROM jac_cg_pdc"
				+ " WHERE CG_PDC_GRUPPO=1"
				+ " AND CG_PDC_MASTRO=1"
				+ "	AND CG_PDC_SOTTOCONTO=0");
		
		 
         while (rs.next()) {
             rowCount++;
         }
		
		if(rowCount==0) {
			inserisci=true;
		}
		
		if(inserisci) {
			rs= st.executeQuery("INSERT INTO jac_cg_pdc(CG_PDC_GRUPPO,CG_PDC_MASTRO,CG_PDC_SOTTOCONTO,CG_PDC_TIPO_GRUPPO,CG_PDC_TIPO_MASTRO,CG_PDC_DESCRIZIONE)"
					+ "VALUES(1,1,0,'E','C','Cliente')");
		}
		
	}

	private static void controlloInteressi(Statement st) throws SQLException {
		boolean inserisci=false;
		int rowCount = 0;
		
		ResultSet rs= st.executeQuery("SELECT * FROM jac_cg_pdc"
				+ " WHERE CG_PDC_GRUPPO=1"
				+ " AND CG_PDC_MASTRO=2"
				+ "	AND CG_PDC_SOTTOCONTO=1");
		
		 
         while (rs.next()) {
             rowCount++;
         }
		
		if(rowCount==0) {
			inserisci=true;
		}
		
		if(inserisci) {
			rs= st.executeQuery("INSERT INTO jac_cg_pdc(CG_PDC_GRUPPO,CG_PDC_MASTRO,CG_PDC_SOTTOCONTO,CG_PDC_TIPO_GRUPPO,CG_PDC_TIPO_MASTRO,CG_PDC_DESCRIZIONE)"
					+ "VALUES(1,2,1,'E','I','Interessi')");
		}
		
	}

	private static void controlloBanca(Statement st) throws SQLException {
		boolean inserisci=false;
		int rowCount = 0;
		
		ResultSet rs= st.executeQuery("SELECT * FROM jac_cg_pdc"
				+ " WHERE CG_PDC_GRUPPO=2"
				+ " AND CG_PDC_MASTRO=1"
				+ "	AND CG_PDC_SOTTOCONTO=1");
		
		 
         while (rs.next()) {
             rowCount++;
         }
		
		if(rowCount==0) {
			inserisci=true;
		}
		
		if(inserisci) {
			rs= st.executeQuery("INSERT INTO jac_cg_pdc(CG_PDC_GRUPPO,CG_PDC_MASTRO,CG_PDC_SOTTOCONTO,CG_PDC_TIPO_GRUPPO,CG_PDC_TIPO_MASTRO,CG_PDC_DESCRIZIONE)"
					+ "VALUES(2,1,1,'P','B','Banca')");
		}
		
		
	}
}
