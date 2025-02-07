package Pam_Function;

import java.sql.Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Visualizzazione {
public static boolean stampaClienti() throws SQLException {
		
		/*try {
		
*/

	ConnectionDb dbconn = new ConnectionDb();
	Connection con = dbconn.connection();
	
	Statement st = con.createStatement();
			Scanner scanner = new Scanner(System.in);
			boolean ripeti=true;
			String str="";
			
			str = "SELECT a.ANA_NOME, a.ANA_COGNOME, COUNT(c.CTR_COD_PRESTITO) AS numeroPrestiti, AVG(p.CTRP_IMPORTO_RATA) AS mediaRata, AVG(p.CTRP_QUOTA_INTERESSI) AS mediaInteressi, AVG(p.CTRP_QUOTA_CAPITALE) AS mediaCapitale FROM jac_ana_base a, jac_ctr_base c, jac_ctr_pam p WHERE a.ANA_COD_ANAGRAFICO = c.CTR_COD_ANAGRAFICO\r\n"
					+ "AND c.CTR_COD_PRESTITO = p.CTRP_COD_PRESTITO"
					+ "";
			
			ResultSet rs = st.executeQuery(str);
	        
			while (rs.next()) {
	            String nome = rs.getString("ANA_NOME");
	            String cognome = rs.getString("ANA_COGNOME");
	            int numeroPrestiti = rs.getInt("numeroPrestiti");
	            float mediaRata = rs.getFloat("mediaRata");
	            float mediaInteressi = rs.getFloat("mediaInteressi");
	            float mediaCapitale = rs.getFloat("mediaCapitale");
	            
	            System.out.println("Nome: " +  nome + "\n" + 
	            "Cognome: " + cognome + "\n" 
	            + "Numero prestiti: " + numeroPrestiti + "\n" + 
	            "Media rata: " + mediaRata + "\n" + 
	            "Media interessi: " + mediaInteressi + "\n" + 
	            "Media capitale: " + mediaCapitale);
	        }
			
			while(ripeti) {
				 System.out.println("VUOI USCIRE?");
				 String scelta=scanner.nextLine();
				 
				if(scelta.toUpperCase().equals("SI")) {
						return true;
					 
					 
				 }else {
					 System.out.println("Digitare SI quando si vuole ritornare al menu");
				 }
				 
			}
			dbconn.closeConnection(con);
			return false;

	        /*;
		
		} catch(Exception e) {
			e.printStackTrace();
		}*/

	}
}
