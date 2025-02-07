package Pam_Function;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class ConnectionDb {
	private static String db_url = "jdbc:mariadb://localhost:3306/JACMARE";
	private static String username  = "root"; 
	private static String pwd = "ciao";
	
	
	public Connection connection() {
		
		Connection conn = null;
		
		try {
			
			conn = DriverManager.getConnection(db_url, username, pwd);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return conn;
		
	}
	
    public void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Connessione chiusa!");
            } catch (SQLException e) {
                System.err.println("Errore durante la chiusura della connessione!");
                e.printStackTrace();
            }
        }
    }

}
