package uuuuuu;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ModifierProjetForm extends JFrame {
    private JTextField idProjetField;
    private JTable infoProjetTable;
    private DefaultTableModel tableModel;

    public ModifierProjetForm() {
        super("Modifier Projet");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JPanel inputPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        JLabel idLabel = new JLabel("ID du Projet:");
        idProjetField = new JTextField();
        JButton afficherButton = new JButton("Afficher");
        inputPanel.add(idLabel);
        inputPanel.add(idProjetField);
        inputPanel.add(afficherButton);

        panel.add(inputPanel, BorderLayout.NORTH);

        // Tableau pour afficher les informations du projet
        String[] columnNames = {"ID", "Nom", "Date de Début", "Date de Fin", "Budget", "Statut"};
        tableModel = new DefaultTableModel(columnNames, 0);
        infoProjetTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(infoProjetTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton modifierButton = new JButton("Modifier");
        modifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = infoProjetTable.getSelectedRow();
                if (selectedRow >= 0) {
                    String nomProjet = (String) tableModel.getValueAt(selectedRow, 1);
                    String dateDebut = (String) tableModel.getValueAt(selectedRow, 2);
                    String dateFin = (String) tableModel.getValueAt(selectedRow, 3);
                    String budget = (String) tableModel.getValueAt(selectedRow, 4);
                    String statut = (String) tableModel.getValueAt(selectedRow, 5);

                    // Appeler la méthode pour modifier le projet dans la base de données
                    boolean modificationReussie = modifierProjetDansLaBase(nomProjet, dateDebut, dateFin, budget, statut);

                    // Afficher un message de succès ou d'échec de la modification
                    if (modificationReussie) {
                        JOptionPane.showMessageDialog(ModifierProjetForm.this, "Projet modifié avec succès !");
                    } else {
                        JOptionPane.showMessageDialog(ModifierProjetForm.this, "Échec de la modification du projet.");
                    }
                } else {
                    JOptionPane.showMessageDialog(ModifierProjetForm.this, "Sélectionnez un projet à modifier.");
                }
            }
        });
        panel.add(modifierButton, BorderLayout.SOUTH);

        afficherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idProjet = Integer.parseInt(idProjetField.getText());
                afficherInformationsProjet(idProjet);
            }
        });

        add(panel);

        setVisible(true);
    }

    private void afficherInformationsProjet(int idProjet) {
        // Informations de connexion à la base de données MySQL
        String url = "jdbc:mysql://localhost:3306/projet";
        String utilisateur = "root"; // Par défaut dans XAMPP, le nom d'utilisateur est souvent "root"
        String motDePasse = ""; // Laissez le mot de passe vide par défaut ou configurez-le selon votre installation

        try (Connection connection = DriverManager.getConnection(url, utilisateur, motDePasse)) {
            // Requête SQL pour récupérer les informations du projet
            String query = "SELECT * FROM projets2 WHERE idpr = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, idProjet);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Vider le tableau existant
                    //tableModel.setRowCount(0);

                    // Parcourir les résultats et ajouter les informations dans le tableau
                    while (resultSet.next()) {
                        int id = resultSet.getInt("idpr");
                        String nom = resultSet.getString("nom");
                        String dateDebut = resultSet.getString("dated");
                        String dateFin = resultSet.getString("datef");
                        String budget = resultSet.getString("budget");
                        String statut = resultSet.getString("statut");

                        Object[] rowData = {id, nom, dateDebut, dateFin, budget, statut};
                        tableModel.addRow(rowData);
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private boolean modifierProjetDansLaBase(String nomProjet, String dateDebut, String dateFin, String budget, String statut) {
        String url = "jdbc:mysql://localhost:3306/projet";
        String utilisateur = "root";
        String motDePasse = "";

        int selectedRow = infoProjetTable.getSelectedRow();
        if (selectedRow >= 0) {
            int idProjet = (int) tableModel.getValueAt(selectedRow, 0);

            try (Connection connection = DriverManager.getConnection(url, utilisateur, motDePasse)) {
                StringBuilder queryBuilder = new StringBuilder("UPDATE projets2 SET");
                boolean isFirstField = true;

                if (!nomProjet.isEmpty()) {
                    if (!isFirstField) {
                        queryBuilder.append(",");
                    }
                    queryBuilder.append(" nom = ?");
                    isFirstField = false;
                }
                if (!dateDebut.isEmpty()) {
                    if (!isFirstField) {
                        queryBuilder.append(",");
                    }
                    queryBuilder.append(" dated = ?");
                    isFirstField = false;
                }
                if (!dateFin.isEmpty()) {
                    if (!isFirstField) {
                        queryBuilder.append(",");
                    }
                    queryBuilder.append(" datef = ?");
                    isFirstField = false;
                }
                if (!budget.isEmpty()) {
                    if (!isFirstField) {
                        queryBuilder.append(",");
                    }
                    queryBuilder.append(" budget = ?");
                    isFirstField = false;
                }
                if (!statut.isEmpty()) {
                    if (!isFirstField) {
                        queryBuilder.append(",");
                    }
                    queryBuilder.append(" statut = ?");
                }

                queryBuilder.append(" WHERE idpr = ?");

                try (PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString())) {
                    int parameterIndex = 1;

                    if (!nomProjet.isEmpty()) {
                        preparedStatement.setString(parameterIndex++, nomProjet);
                    }
                    if (!dateDebut.isEmpty()) {
                        preparedStatement.setString(parameterIndex++, dateDebut);
                    }
                    if (!dateFin.isEmpty()) {
                        preparedStatement.setString(parameterIndex++, dateFin);
                    }
                    if (!budget.isEmpty()) {
                        preparedStatement.setString(parameterIndex++, budget);
                    }
                    if (!statut.isEmpty()) {
                        preparedStatement.setString(parameterIndex++, statut);
                    }

                    preparedStatement.setInt(parameterIndex, idProjet);

                    int rowsAffected = preparedStatement.executeUpdate();

                    return rowsAffected > 0;
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                return false;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ModifierProjetForm();
            }
        });
    }
}