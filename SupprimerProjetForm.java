package uuuuuu;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SupprimerProjetForm extends JFrame{
private JTextField idField;
    
	public SupprimerProjetForm() {
        super("Ajouter Projet");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        JLabel idLabel = new JLabel("ID:");

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        idLabel.setFont(labelFont);
        idField = new JTextField();
        
        JButton ajouterButton = new JButton("Supprimer");
        ajouterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            
                String idString = idField.getText();
                int id = Integer.parseInt(idString);
                
                System.out.println("ID : " + id);

                // Appeler la méthode pour insérer le projet dans la base de données
                supprimerProjetDansLaBase(id);

                // Fermer la fenêtre après l'ajout du projet
                dispose();
        }
    });
        
        panel.add(idLabel);
        panel.add(idField);
        panel.add(new JLabel()); // espace vide
        panel.add(ajouterButton);
        
        add(panel);
        setVisible(true);
}
	
	private void supprimerProjetDansLaBase(int id) {
	    // Informations de connexion à la base de données MySQL
	    String url = "jdbc:mysql://localhost:3306/projet";
	    String utilisateur = "root";
	    String motDePasse = "";

	    try (Connection connection = DriverManager.getConnection(url, utilisateur, motDePasse)) {
	        // Requête SQL de suppression
	        String query = "DELETE FROM projets2 WHERE idpr = ?";
	        
	        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            // Définir la valeur de l'id
	            preparedStatement.setInt(1, id);

	            // Exécution de la requête de suppression
	            int rowsAffected = preparedStatement.executeUpdate();

	            // Vérifier si des lignes ont été supprimées
	            if (rowsAffected > 0) {
	                JOptionPane.showMessageDialog(this, "Suppression réussie", "Succès", JOptionPane.INFORMATION_MESSAGE);
	            } else {
	                JOptionPane.showMessageDialog(this, "Aucun projet trouvé avec l'ID spécifié", "Erreur de suppression", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    } catch (SQLException ex) {
	        System.out.println(ex.getMessage());
	        // Gérer l'exception selon vos besoins
	    }
	}
}
