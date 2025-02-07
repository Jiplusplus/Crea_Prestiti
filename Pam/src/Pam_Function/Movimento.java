package Pam_Function;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public class Movimento {
	//public Utente utente; 
	private int data_oggi; 
	private int progr; 
	private int operatore; 
	private int identificativo; 
	private char segno; 
	private int gruppo; 
	private int mastro; 
	private int sottoconto; 
	private int importo; 
	private String descrizione;
	
	
	
	public int get_max_progr() {

		try {
			String str = "";
			ConnectionDb dbconn = new ConnectionDb();
			Connection con = dbconn.connection();
			ResultSet rs;
			Statement st = con.createStatement();
			str = "select MAX(CGM_PROGRESSIVO) as max from jac_cg_movimento";
			rs = st.executeQuery(str);
			if (rs.next()) {
                int maxProgressivo = rs.getInt("max"); 
                if (rs.wasNull()) {
                    progr = 1; // Se il valore massimo è null, inizia da 0
                } else {
                    progr = maxProgressivo + 1; // Aggiungi 1 al valore massimo
                }
                
            }
			
		 dbconn.closeConnection(con);

		}catch(Exception e) {
			e.printStackTrace();
		}
		return progr;
	}
	

	public void registra_movimento_capitale(int i, int totaleCapitale, Statement st) {
		try {
			ConnectionDb dbconn = new ConnectionDb();
			Connection con = dbconn.connection();
			
			st = con.createStatement();
			
			data_oggi = 9122024;
			operatore = 15;
			identificativo = 1;
			segno = 'D';
			gruppo = 1; 
			mastro = 1; 
			progr = get_max_progr(); 
			System.out.println(progr);;
			sottoconto = i;
			importo = totaleCapitale;
			
			descrizione = "Capitale cliente";
			String str = "";
			
			str = "INSERT INTO jac_cg_movimento(CGM_DATA, CGM_OPERATORE, CGM_IDENTIFICATIVO, CGM_SEGNO_MOVIMENTO, CGM_PROGRESSIVO, CGM_GRUPPO, CGM_MASTRO, CGM_SOTTOCONTO, CGM_IMPORTO, CGM_DESCRIZIONE) "
			           + "VALUES ('" + data_oggi + "', '" + operatore + "', '" + identificativo + "', '" + segno + "', '" + progr + "', '" + gruppo + "', '" + mastro + "', '" + sottoconto + "', " + importo + ", '" + descrizione + "')";
			
			st.executeQuery(str);
			
			/////////////////////////////////////////////////////
			segno = 'A';
			gruppo = 2; 
			mastro = 1; 
			sottoconto = 1; 
			descrizione = "Banca";
			progr = get_max_progr(); 
			
			str = "INSERT INTO jac_cg_movimento(CGM_DATA, CGM_OPERATORE, CGM_IDENTIFICATIVO, CGM_SEGNO_MOVIMENTO, CGM_PROGRESSIVO, CGM_GRUPPO, CGM_MASTRO, CGM_SOTTOCONTO, CGM_IMPORTO, CGM_DESCRIZIONE) "
			           + "VALUES ('" + data_oggi + "', '" + operatore + "', '" + identificativo + "', '" + segno + "', '" + progr + "', '" + gruppo + "', '" + mastro + "', '" + sottoconto + "', " + importo + ", '" + descrizione + "')";
			
			st.executeQuery(str);
			
			
			 dbconn.closeConnection(con);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void registra_movimento_interessi(int i,int totaleInteressi,Statement st) {
		try {
			
			ConnectionDb dbconn = new ConnectionDb();
			Connection con = dbconn.connection();
			
			st = con.createStatement();
			
			data_oggi = 9122024;
			operatore = 15;
			identificativo = 2;
			segno = 'D';
			gruppo = 1; 
			mastro = 1; 
			progr = get_max_progr(); 
			sottoconto = i;
			importo = totaleInteressi;
			
			descrizione = "Interessi";
			
			String str = "";
			
			str = "INSERT INTO jac_cg_movimento(CGM_DATA, CGM_OPERATORE, CGM_IDENTIFICATIVO, CGM_SEGNO_MOVIMENTO, CGM_PROGRESSIVO, CGM_GRUPPO, CGM_MASTRO, CGM_SOTTOCONTO, CGM_IMPORTO, CGM_DESCRIZIONE) "
			           + "VALUES ('" + data_oggi + "', '" + operatore + "', '" + identificativo + "', '" + segno + "', '" + progr + "', '" + gruppo + "', '" + mastro + "', '" + sottoconto + "', " + importo + ", '" + descrizione + "')";
			
			st.executeQuery(str);
			
			/////////////////////////////////////////////////////
			segno = 'A';
			mastro = 2; 
			sottoconto = 1;
			progr = get_max_progr(); 
			
			str = "INSERT INTO jac_cg_movimento(CGM_DATA, CGM_OPERATORE, CGM_IDENTIFICATIVO, CGM_SEGNO_MOVIMENTO, CGM_PROGRESSIVO, CGM_GRUPPO, CGM_MASTRO, CGM_SOTTOCONTO, CGM_IMPORTO, CGM_DESCRIZIONE) "
			           + "VALUES ('" + data_oggi + "', '" + operatore + "', '" + identificativo + "', '" + segno + "', '" + progr + "', '" + gruppo + "', '" + mastro + "', '" + sottoconto + "', " + importo + ", '" + descrizione + "')";
			
			st.executeQuery(str);
			
			
			 dbconn.closeConnection(con);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
