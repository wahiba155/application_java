package uuuuuu;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class RegistrationForm extends JFrame{
	 private JTextField newUsernameField;
	    private JPasswordField newPasswordField;

	    public RegistrationForm() {
	        super("Formulaire d'Inscription");
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setSize(300, 200);
	        setLocationRelativeTo(null);

	        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));

	        JLabel newUsernameLabel = new JLabel("Nouvel utilisateur:");
	        JLabel newPasswordLabel = new JLabel("Nouveau mot de passe:");

	        Font labelFont = new Font("Arial", Font.BOLD, 14);
	        newUsernameLabel.setFont(labelFont);
	        newPasswordLabel.setFont(labelFont);

	        newUsernameField = new JTextField();
	        newPasswordField = new JPasswordField();
	 
	        JButton registerButton = new JButton("S'Inscrire");
	        registerButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                // Ajoutez votre logique d'inscription ici
	                String newUsername = newUsernameField.getText();
	                char[] newPasswordChars = newPasswordField.getPassword();
	                String newPassword = new String(newPasswordChars);

	                // Enregistrement des informations dans le fichier "information.txt"
	                writeToTextFile(newUsername, newPassword);

	                // Exemple de traitement : affiche une alerte avec les nouvelles informations
	                String message =newUsername + ", votre inscripion a bien ete reussite !";
	                JOptionPane.showMessageDialog(RegistrationForm.this, message, "Inscription Réussie", JOptionPane.INFORMATION_MESSAGE);

	                // Fermer la fenêtre d'inscription après l'inscription
	                dispose();
	            }
	        });

	        panel.add(newUsernameLabel);
	        panel.add(newUsernameField);
	        panel.add(newPasswordLabel);
	        panel.add(newPasswordField);
	        panel.add(registerButton);
	        add(panel);

	        setVisible(true);
	    }

	    private void writeToTextFile(String username, String password) {
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter("informations.txt", true))) {
	            writer.write(username + "," + password);
	            writer.newLine(); // Ajouter une nouvelle ligne pour chaque enregistrement
	        } catch (IOException ex) {
	            ex.printStackTrace();
	            // Gérer l'exception selon vos besoins
	        }
	    }
}
