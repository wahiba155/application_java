package uuuuuu;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RechercherProjetForm extends JFrame {
    private JTextField idProjetField;
    private JTable resultatTable;

    public RechercherProjetForm() {
        super("Rechercher Projet");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JPanel recherchePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel idLabel = new JLabel("ID du Projet:");
        idProjetField = new JTextField(10);
        JButton rechercherButton = new JButton("Rechercher");
        rechercherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Récupérer l'ID du projet saisi
                String idProjetString = idProjetField.getText();
                int idProjet = Integer.parseInt(idProjetString);

                // Appeler la méthode pour rechercher le projet dans la base de données
                DefaultTableModel tableModel = rechercherProjetDansLaBase(idProjet);

                // Afficher le résultat dans le tableau
                resultatTable.setModel(tableModel);
            }
        });
        recherchePanel.add(idLabel);
        recherchePanel.add(idProjetField);
        recherchePanel.add(rechercherButton);

        panel.add(recherchePanel, BorderLayout.NORTH);

        resultatTable = new JTable();
        resultatTable.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(resultatTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);

        setVisible(true);
    }

    // Méthode pour rechercher le projet dans la base de données
    private DefaultTableModel rechercherProjetDansLaBase(int idProjet) {
        // Informations de connexion à la base de données MySQL
        String url = "jdbc:mysql://localhost:3306/projet";
        String utilisateur = "root"; // Par défaut dans XAMPP, le nom d'utilisateur est souvent "root"
        String motDePasse = ""; // Laissez le mot de passe vide par défaut ou configurez-le selon votre installation

        // Modèle de tableau pour afficher les informations du projet
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Nom");
        tableModel.addColumn("Date de Début");
        tableModel.addColumn("Date de Fin");
        tableModel.addColumn("Budget");
        tableModel.addColumn("Statut");

        try (Connection connection = DriverManager.getConnection(url, utilisateur, motDePasse)) {
            // Requête SQL de recherche
            String query = "SELECT * FROM projets2 WHERE idpr = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, idProjet);

                // Exécution de la requête de recherche
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    // Récupération des informations du projet
                    String nomProjet = resultSet.getString("nom");
                    String dateDebut = resultSet.getString("dated");
                    String dateFin = resultSet.getString("datef");
                    String budget = resultSet.getString("budget");
                    String statut = resultSet.getString("statut");

                    // Ajout des informations du projet dans le modèle de tableau
                    tableModel.addRow(new Object[]{nomProjet, dateDebut, dateFin, budget, statut});
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return tableModel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RechercherProjetForm();
            }
        });
    }
}