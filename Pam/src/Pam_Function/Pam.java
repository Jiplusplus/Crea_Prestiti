package Pam_Function;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Pam {

	public static Map<String, Integer> pam(Connection cn, int codice) throws SQLException {
    
       Scanner scanner = new Scanner(System.in);

       // Chiedi il codice anagrafico all'utente
       //System.out.print("Inserisci il codice anagrafico: ");
       int codiceAnagrafico = codice;

       // Dati iniziali per il primo prestito
       float Capitale_iniziale = 101000;
       int DurataMesi = 6;
       float TassoAnnuale = 24;

       // Data di esecuzione (usiamo la data corrente)
       LocalDate data = LocalDate.now();
       DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
       
       // Variabili per accumulare le somme
       float sommaQuotaCapitale = 0;
       float sommaQuotaInteressi = 0;

       // Generazione dei 10 prestiti con incremento di capitale e decremento del tasso
       for (int i = 0; i < 10; i++) {
           // Calcolo del tasso mensile
           float TassoMensile = (TassoAnnuale - (i * 2)) / 12 / 100;

           // Codice prestito unico per il cliente
           String codicePrestito = codiceAnagrafico + "_PAM_" + (i + 1);

           // Salva i dati di default nella tabella jac_ctr_base
           int dataEsecuzione=0;
           salvaDatiBase(
        		   cn,
               codiceAnagrafico,
               dataEsecuzione= Integer.valueOf(data.format(dateFormatter)),
               Capitale_iniziale + i * 100,
               codicePrestito,
               DurataMesi + i * 6,
               TassoAnnuale - (i * 2)
           );

           // Visualizza i dettagli del prestito corrente
           //System.out.println("\nPiano di Ammortamento - Prestito " + (i + 1));
           //System.out.println("Codice Anagrafico: " + codiceAnagrafico);
           //System.out.println("Codice Prestito: " + codicePrestito);
           //System.out.println("Data di Esecuzione: " + dataEsecuzione.format(dateFormatter));
           //System.out.println("Capitale: " + (Capitale_iniziale + i * 100) + " Euro");
           //System.out.println("Durata: " + (DurataMesi + i * 6) + " mesi");
           //System.out.println("Tasso: " + (TassoAnnuale - (i * 2)) + "% Annui");
           //System.out.println("-------------------------------------------------------------------------------");

           // Calcola e mostra il piano di ammortamento per ogni prestito
           // Aggiorna le somme di quota capitale e quota interessi
           float[] sommePrestito = generaPianoAmmortamento(
        		   cn,
               Capitale_iniziale + i * 100,
               DurataMesi + i * 6,
               TassoMensile,
               codicePrestito,
               data
           );
           sommaQuotaCapitale += sommePrestito[0];
           sommaQuotaInteressi += sommePrestito[1];
       }

       // Visualizza le somme finali
       //System.out.println("\nSomma totale delle quote capitale: " + sommaQuotaCapitale + " Euro");
       //System.out.println("Somma totale delle quote interessi: " + sommaQuotaInteressi + " Euro");

       //scanner.close();
       Map<String, Integer> Totali = new HashMap<>();
       Totali.put("sommaInteressi", (int) sommaQuotaInteressi);
       Totali.put("sommaCapitale", (int) sommaQuotaCapitale);
       return Totali;
   }

   public static float[] generaPianoAmmortamento(Connection cn,float capitale, int durataMesi, float tassoMensile, String codicePrestito, LocalDate data) throws SQLException {
       // Calcolo della rata mensile 
       float rata = (float) (capitale * tassoMensile / (1 - Math.pow(1 + tassoMensile, -durataMesi)));

       // Stampa l'intestazione del piano di ammortamento
       //System.out.println("-------------------------------------------------------------------------------");
       //System.out.printf("%-10s %-15s %-15s %-15s %-15s %-15s%n", "Mese", "Data", "Rata", "Quota Capitale", "Quota Interesse", "Capitale Residuo");

       float capitaleResiduo = capitale;

       // Variabili per accumulare le somme delle quote
       float sommaQuotaCapitale = 0;
       float sommaQuotaInteressi = 0;

       // Loop per generare il piano di ammortamento per ogni mese
       for (int mese = 1; mese <= durataMesi; mese++) {
           float interesse = capitaleResiduo * tassoMensile;
           float quotaCapitale = rata - interesse;
           capitaleResiduo -= quotaCapitale;

           // Calcolo della data della rata
           LocalDate dRata = data.plusMonths(mese);
           
           int dataRata=0;
           
           // Stampa i dettagli per il mese corrente
           /*System.out.printf("%-10d %-15s %-15.2f %-15.2f %-15.2f %-15.2f%n",
               mese,
               dataRata=Integer.valueOf(dRata.format(DateTimeFormatter.ofPattern("ddMMyyyy"))),
               rata,
               quotaCapitale,
               interesse,
               Math.max(capitaleResiduo, 0)
           );*/

           // Aggiorna le somme
           sommaQuotaCapitale += quotaCapitale;
           sommaQuotaInteressi += interesse;

           // Salvataggio dei dati nel database
           salvaNelDatabase(cn,codicePrestito, mese, rata, quotaCapitale, interesse, Math.max(capitaleResiduo, 0), dataRata);
       }

       // Stampa la fine del piano
       //System.out.println("-------------------------------------------------------------------------------");

       // Ritorna le somme di quota capitale e quota interessi
       return new float[]{sommaQuotaCapitale, sommaQuotaInteressi};
   }

   public static void salvaDatiBase(Connection cn,int codiceAnagrafico, int dataEsecuzione, float capitaleIniziale, String codicePrestito, int durata, float tasso) throws SQLException {
       //try (Connection conn = DriverManager.getConnection(dbUrl, user, pwd)) {
           String sql = "INSERT INTO jac_ctr_base (CTR_COD_ANAGRAFICO, CTR_DATA, CTR_IMPORTO, CTR_COD_PRESTITO, CTR_DURATA, CTR_TASSO) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

           try (PreparedStatement stmt = cn.prepareStatement(sql)) {
               stmt.setInt(1, codiceAnagrafico);
               stmt.setInt(2, dataEsecuzione);
               stmt.setFloat(3, capitaleIniziale);
               stmt.setString(4, codicePrestito);
               stmt.setInt(5, durata);
               stmt.setFloat(6, tasso);

               stmt.executeUpdate();
           }
       /*} catch (SQLException e) {
           e.printStackTrace();
       }*/
   }

   public static void salvaNelDatabase(Connection cn,String codicePrestito, int numeroRata, float rata, float quotaCapitale, float quotaInteresse, float capitaleResiduo, int dataRata) throws SQLException {
       //try (Connection conn = DriverManager.getConnection(dbUrl, user, pwd)) {
           String sql = "INSERT INTO jac_ctr_pam (CTRP_COD_PRESTITO, CTRP_NUMERO_RATA, CTRP_IMPORTO_RATA, CTRP_QUOTA_CAPITALE, CTRP_QUOTA_INTERESSI, CTRP_CAPITALE_RESIDUO) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

           try (PreparedStatement stmt = cn.prepareStatement(sql)) {
               stmt.setString(1, codicePrestito);
               stmt.setInt(2, numeroRata);
               stmt.setFloat(3, rata);
               stmt.setFloat(4, quotaCapitale);
               stmt.setFloat(5, quotaInteresse);
               stmt.setFloat(6, capitaleResiduo);

               stmt.executeUpdate();
           }
       /*} catch (SQLException e) {
           e.printStackTrace();
       }*/
   }
}