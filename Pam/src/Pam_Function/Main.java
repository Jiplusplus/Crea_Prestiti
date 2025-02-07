package Pam_Function;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    // Dati di connessione al database
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/JACMARE";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "ciao";

    public static void main(String[] args) {
        boolean visualizzareMenu = true;
        Scanner scanner = new Scanner(System.in);
        String scelta;
        Visualizzazione v = new Visualizzazione();

        // Connessione al database
        Connection cn = null;
        Statement st = null;

        try {
            // Caricamento esplicito del driver per MariaDB
            Class.forName("org.mariadb.jdbc.Driver");

            // Connessione al database
            cn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            st = cn.createStatement();

            System.out.println("✅ Connessione al database riuscita!");
            System.out.println("Benvenuto");

            do {
                System.out.println("MENU");
                System.out.println("Premi 1 se vuoi creare un nuovo prestito");
                System.out.println("Premi 2 se vuoi visualizzare i prestiti già creati");
                System.out.println("Premi 99 se vuoi uscire dal programma");

                boolean errore = false;
                do {
                    if (scanner.hasNextLine()) {
                        scelta = scanner.nextLine();
                    } else {
                        System.out.println("No input disponibile.");
                        break;
                    }

                    switch (scelta) {
                        case "1":
                            System.out.println("Opzione PRESTITO scelta");
                            visualizzareMenu = Prestito.prestito(st, cn);  // Passa connessione e statement
                            errore = false;
                            break;
                        case "2":
                            System.out.println("Opzione di VISUALIZZAZIONE PRESTITI scelta");
                            visualizzareMenu = v.stampaClienti();
                            errore = false;
                            break;
                        case "99":
                            System.out.println("USCITA dal programma effettuata");
                            visualizzareMenu = false;
                            errore = false;
                            break;
                        default:
                            System.out.println("Inserisci un numero presente nel MENU");
                            errore = true;
                            break;
                    }
                } while (errore);
            } while (visualizzareMenu);

        } catch (ClassNotFoundException e) {
            System.err.println("❌ Driver MariaDB non trovato: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("❌ Errore nella connessione al database: " + e.getMessage());
        } finally {
            try {
                if (st != null) st.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                System.err.println("Errore durante la chiusura della connessione: " + e.getMessage());
            }
            scanner.close();
        }
    }
}
