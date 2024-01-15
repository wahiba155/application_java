package uuuuuu;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.SwingUtilities;

public class Main {
	public static void main(String[] args) {
		 SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                new LoginForm();
	            }
});
		 
		
		 String url = "jdbc:mysql://localhost:3306/projet";
		 String utilisateur = "root"; 
		 String motDePasse = ""; 

		 try {
		     // Charger le pilote JDBC
		     Class.forName("com.mysql.cj.jdbc.Driver");

		     //  la connexion
		     Connection connection = DriverManager.getConnection(url, utilisateur, motDePasse);

		     
		     System.out.println("Connexion à la base de données réussie");
		     
		     // Fermez la connexion
		     connection.close();
		 } catch (ClassNotFoundException | SQLException e) {
		     e.printStackTrace();
		     System.err.println("Erreur de connexion à la base de données");
		 }

		 
	}
}
