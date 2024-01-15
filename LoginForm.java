package uuuuuu;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginForm extends JFrame{
	private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginForm() {
        super("Formulaire de Connexion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));

        JLabel usernameLabel = new JLabel("Nom d'utilisateur:");
        JLabel passwordLabel = new JLabel("Mot de passe:");

        Font labelFont = new Font("Arial", Font.BOLD, 20);
        usernameLabel.setFont(labelFont);
        passwordLabel.setFont(labelFont);

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        JButton loginButton = new JButton("Se Connecter");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredUsername = usernameField.getText();
                char[] enteredPasswordChars = passwordField.getPassword();
                String enteredPassword = new String(enteredPasswordChars);

                // Vérifier si le nom d'utilisateur et le mot de passe sont corrects
                if (isValidUser(enteredUsername, enteredPassword)) {
                    JOptionPane.showMessageDialog(LoginForm.this, "Connexion réussie", "Succès", JOptionPane.INFORMATION_MESSAGE);
                 // Ouvrir la nouvelle fenêtre de gestion des projets
                    new ProjetManagementWindow().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(LoginForm.this, "Nom d'utilisateur ou mot de passe incorrect", "Erreur de Connexion", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
     // Ajoutez un lien pour ouvrir le formulaire d'inscription
        JButton registerButton = new JButton("S'inscrire");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ouvrir le formulaire d'inscription lorsque le bouton est cliqué
                new RegistrationForm().setVisible(true);
            }
        });
        panel.add(registerButton);



        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel()); // espace vide
        panel.add(loginButton);
		panel.add(registerButton );
        add(panel);

        setVisible(true);
    }

    private boolean isValidUser(String enteredUsername, String enteredPassword) {
        // Lecture du fichier "information.txt" pour obtenir les informations d'authentification
        try (BufferedReader reader = new BufferedReader(new FileReader("informations.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String username = parts[0].trim();
                String password = parts[1].trim();

                // Comparer les informations saisies avec celles du fichier
                if (enteredUsername.equals(username) && enteredPassword.equals(password)) {
                    return true; // Correspondance trouvée
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Aucune correspondance trouvée
    }

}
