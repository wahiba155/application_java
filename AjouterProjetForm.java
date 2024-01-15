package uuuuuu;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AjouterProjetForm extends JFrame{
	private JTextField nomProjetField;
    private JTextField dateDebutField;
    private JTextField dateFinField;
    private JTextField budgetField;
    private JTextField statutField;
    private JTextField idField;

    public AjouterProjetForm() {
        super("Ajouter Projet");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(7, 2, 5, 5));

        JLabel nomLabel = new JLabel("Nom du Projet:");
        JLabel dateDebutLabel = new JLabel("Date de Début:");
        JLabel dateFinLabel = new JLabel("Date de Fin:");
        JLabel budgetLabel = new JLabel("Budget:");
        JLabel statutLabel = new JLabel("Statut:");
        JLabel idLabel = new JLabel("ID:");

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        nomLabel.setFont(labelFont);
        dateDebutLabel.setFont(labelFont);
        dateFinLabel.setFont(labelFont);
        budgetLabel.setFont(labelFont);
        statutLabel.setFont(labelFont);
        idLabel.setFont(labelFont);

        nomProjetField = new JTextField();
        dateDebutField = new JTextField();
        dateFinField = new JTextField();
        budgetField = new JTextField();
        statutField = new JTextField();
        idField = new JTextField();

        JButton ajouterButton = new JButton("Ajouter");
        ajouterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Récupérer les valeurs saisies
                String nomProjet = nomProjetField.getText();
                String dateDebut = dateDebutField.getText();
                String dateFin = dateFinField.getText();
                String budget = budgetField.getText();
                String statut = statutField.getText();
                String idString = idField.getText();
                int id = Integer.parseInt(idString);


                // Exemple : Afficher les informations dans la console
                System.out.println("Nouveau Projet ajouté : ");
                System.out.println("Nom : " + nomProjet);
                System.out.println("Date de Début : " + dateDebut);
                System.out.println("Date de Fin : " + dateFin);
                System.out.println("Budget : " + budget);
                System.out.println("Statut : " + statut);
                System.out.println("ID : " + id);

                // Appeler la méthode pour insérer le projet dans la base de données
                insererProjetDansLaBase(nomProjet, dateDebut, dateFin, budget, statut, id);

                // Fermer la fenêtre après l'ajout du projet
                dispose();
            }
        });

        panel.add(nomLabel);
        panel.add(nomProjetField);
        panel.add(dateDebutLabel);
        panel.add(dateDebutField);
        panel.add(dateFinLabel);
        panel.add(dateFinField);
        panel.add(budgetLabel);
        panel.add(budgetField);
        panel.add(statutLabel);
        panel.add(statutField);
        panel.add(idLabel);
        panel.add(idField);
        panel.add(new JLabel()); // espace vide
        panel.add(ajouterButton);

        add(panel);

        setVisible(true);
    }

    // Méthode pour insérer le projet dans la base de données
    private void insererProjetDansLaBase(String nomProjet, String dateDebut, String dateFin, String budget, String statut, int id) {
        // Informations de connexion à la base de données MySQL
        String url = "jdbc:mysql://localhost:3306/projet";
        String utilisateur = "root"; // Par défaut dans XAMPP, le nom d'utilisateur est souvent "root"
        String motDePasse = ""; // Laissez le mot de passe vide par défaut ou configurez-le selon votre installation

        try (Connection connection = DriverManager.getConnection(url, utilisateur, motDePasse)) {
            // Requête SQL d'insertion
            String query = "INSERT INTO projets2 (nom, dated, datef, budget, statut, idpr) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, nomProjet);
                preparedStatement.setString(2, dateDebut);
                preparedStatement.setString(3, dateFin);
                preparedStatement.setString(4, budget);
                preparedStatement.setString(5, statut);
                preparedStatement.setInt(6, id);

                // Exécution de la requête d'insertion
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
           System.out.println(ex.getMessage());
            // Gérer l'exception selon vos besoins
        }
    }
}
