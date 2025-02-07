package Pam_Function;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.TimeZone;

public class Prestito {
	public static boolean prestito(Statement st, Connection cn) throws SQLException {
		
		 /*String driver = "org.mariadb.jdbc";
	 String dbUrl="jdbc:mariadb://localhost:3306/JACMARE";
		 String user="root";
		 String psw="root";
		 
		 try {*/
			 /*Connection cn=DriverManager.getConnection(dbUrl,user,psw);
			 Statement st = cn.createStatement();*/
			 String scelta;
			 boolean ripeti=true;
			 boolean creaPrestiti=false;
			 Scanner scanner = new Scanner(System.in);
			 Movimento movimento = new Movimento();
			 Pam pam = new Pam();
			 
			 List<Integer> elencoCodAna = new ArrayList<Integer>();
			 List<Integer> elencoCodAnaClientiScelti = new ArrayList<Integer>();
			 elencoCodAna=visualizzaCodiciAna(st,elencoCodAna);
			 elencoCodAnaClientiScelti=richiestaCodici(elencoCodAna,elencoCodAnaClientiScelti);
			 visualizzaClientiScelti(st, elencoCodAnaClientiScelti);
			 if (elencoCodAna.size() >= 5) {
			 while(ripeti) {
				 System.out.println("VUOI CONFERMARE?");
				 scelta=scanner.nextLine();
				 if (scelta.toUpperCase().equals("NO") || scelta.toUpperCase().equals("SI")) {
					 ripeti=false;
					 if(scelta.toUpperCase().equals("SI")) {
						 creaPrestiti=true;
					 }else {
						 return true;
					 }
					 
				 }else {
					 System.out.println("Digitare SI o NO");
				 }
				 
			 }
			 
			 if(creaPrestiti) {
				 
				 PDC.pdc(st);
				 for(int i:elencoCodAnaClientiScelti) {
					 Map<String, Integer> Totali = pam.pam(cn ,i);
					 int totaleInteressi=Totali.get("sommaInteressi");
					 int totaleCapitale=Totali.get("sommaCapitale");
					 movimento.registra_movimento_interessi(i,totaleInteressi,st);
					 movimento.registra_movimento_capitale(i,totaleCapitale,st);
					 System.out.println("finita la classse mov");
				 }
				 return true;
			 }else {
				 return true;
			 }
			 
			 } else {
				 System.out.println("Non ci sono abbastanza clienti");
				 return false;
				 }
		 /*}catch (SQLException e) {
			 
				e.printStackTrace();
		}*/

	}
	
	private static List<Integer> visualizzaCodiciAna(Statement st, List<Integer>elencoCodAna) throws SQLException {
		
       
       
		ResultSet rs= st.executeQuery("SELECT ANA_COD_ANAGRAFICO, ANA_COGNOME, ANA_NOME FROM jac_ana_base "
				+ "WHERE ANA_COD_ANAGRAFICO "
				+ "	NOT IN(SELECT CTR_COD_ANAGRAFICO "
				+ "	FROM jac_ctr_base); ");
		
		
			System.out.println("CODICE_ANAG	COGNOME		NOME");
			while(rs.next()) {
				elencoCodAna.add(rs.getInt("ANA_COD_ANAGRAFICO"));
				System.out.print(rs.getInt("ANA_COD_ANAGRAFICO")+"		");
				System.out.print(rs.getString("ANA_COGNOME")+"		");
				System.out.println(rs.getString("ANA_NOME"));
			}
			
			return elencoCodAna;

		
	}
	
	private static List<Integer> richiestaCodici(List<Integer>elencoCodAna, List<Integer>elencoCodAnaClientiScelti) {
		int conta=1;
		 do {
			 boolean controllo=true;
			 boolean ripeti=true;
			 Scanner scanner = new Scanner(System.in);
			 String codice;
			 int codAna;
			 while (ripeti) { 
				System.out.println("Inserisci il codice anagrafico del "+conta+" cliente di cui vuoi effettuare il prestito");
				codice=scanner.nextLine();
				try {
					codAna=Integer.parseInt(codice);
					 if(!elencoCodAna.contains(codAna)) {
						 System.out.println("VALORE NON VALIDO. Inserire un codice presente nell'elenco sovrastante");	
						 controllo=false;
					 }else {
						 if(elencoCodAnaClientiScelti.contains(codAna)) {
							 System.out.println("VALORE NON VALIDO. Codice già scelto");	
							 controllo=false;
						 }else {
							 elencoCodAnaClientiScelti.add(codAna);
						 }
					 }
					 
					ripeti=false;			
				}catch(Exception e) {
					System.out.println("VALORE NON VALIDO. Inserire un numero");				
				}
			}
			  
			 
			 if(controllo) {
				 conta++;
			 }
			 
		 }while(conta<=5);
		 
		 return elencoCodAnaClientiScelti;
	}

	private static void visualizzaClientiScelti(Statement st, List<Integer> elencoCodAnaClientiScelti) throws SQLException {
		System.out.println("CODICE_ANAG	COGNOME		NOME");
		for(int i:elencoCodAnaClientiScelti) {
			ResultSet rs= st.executeQuery("SELECT ANA_COD_ANAGRAFICO, ANA_COGNOME, ANA_NOME FROM jac_ana_base WHERE ANA_COD_ANAGRAFICO=" + i);		
			while(rs.next()) {
				System.out.print(rs.getInt("ANA_COD_ANAGRAFICO")+"		");
				System.out.print(rs.getString("ANA_COGNOME")+"		");
				System.out.println(rs.getString("ANA_NOME"));
			}
		}
	}

}
