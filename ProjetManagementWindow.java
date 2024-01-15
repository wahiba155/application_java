package uuuuuu;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProjetManagementWindow extends JFrame {
	public ProjetManagementWindow() {
        super("Gestion des Projets");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));

        JButton ajouterProjetButton = new JButton("Ajouter Projet");
        JButton modifierProjetButton = new JButton("Modifier Projet");
        JButton supprimerProjetButton = new JButton("Supprimer Projet");
        JButton rechercherProjetButton = new JButton("Rechercher Projet");

        // Ajouter des ActionListener aux boutons pour définir le comportement lorsqu'ils sont cliqués
        ajouterProjetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	new AjouterProjetForm().setVisible(true);
            }
        });

        modifierProjetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	new ModifierProjetForm().setVisible(true);
                //JOptionPane.showMessageDialog(ProjetManagementWindow.this, "Fonctionnalité non implémentée : Modifier Projet");
            }
        });

        supprimerProjetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	new SupprimerProjetForm().setVisible(true);
            	//JOptionPane.showMessageDialog(ProjetManagementWindow.this, "Fonctionnalité non implémentée : Supprimer Projet");
            }
        });

        rechercherProjetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	new RechercherProjetForm().setVisible(true);
                //JOptionPane.showMessageDialog(ProjetManagementWindow.this, "Fonctionnalité non implémentée : Rechercher Projet");
            }
        });

        panel.add(ajouterProjetButton);
        panel.add(modifierProjetButton);
        panel.add(supprimerProjetButton);
        panel.add(rechercherProjetButton);

        add(panel);

        setVisible(true);
    }
}
