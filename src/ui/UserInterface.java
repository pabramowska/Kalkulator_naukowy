package ui;

import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class UserInterface extends JFrame {
    private JTextField displayField; // Pole tekstowe dla wyświetlanego tekstu

    public UserInterface() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout()); // Ustawienie GridBagLayout jako menedżera układu

        // Konfiguracja dla JTextField
        displayField = new JTextField("Wpisz liczbę: ");
        displayField.setEditable(false); // Na razie pole nie będzie edytowalne
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 5; // Rozciąga pole tekstowe na szerokość 5 kolumn
        gbc.fill = GridBagConstraints.HORIZONTAL; // Rozciąga komponent poziomo
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(displayField, gbc); // Dodanie pola tekstowego z ustawieniami GridBagConstraints

        // Dodawanie przycisków
        addButtons();

        pack(); // Dostosuj rozmiar okna do zawartości
        setVisible(true); // Pokaż okno
    }

    private void addButtons() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Dodanie funkcji matematycznych na górze
        String[] funcs = {"sin", "cos", "tan", "ln"};
        for (int i = 0; i < funcs.length; i++) {
            gbc.gridx = i;
            gbc.gridy = 1;
            add(new JButton(funcs[i]), gbc);
        }

        // Dodanie przycisków numerycznych
        int btnNumber = 1;
        for (int row = 2; row <= 4; row++) {
            for (int col = 0; col < 3; col++) {
                gbc.gridx = col;
                gbc.gridy = row;
                if (btnNumber <= 9) {
                    add(new JButton(String.valueOf(btnNumber++)), gbc);
                }
            }
        }

        // Dodanie pozostałych przycisków numerycznych
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JButton("00"), gbc);
        gbc.gridx = 1;
        add(new JButton("0"), gbc);
        gbc.gridx = 2;
        add(new JButton("."), gbc);

        // Dodanie przycisków operacji z prawej strony
        String[] ops = {"/", "*", "-", "+", "="};
        for (int i = 0; i < ops.length; i++) {
            gbc.gridx = 3;
            gbc.gridy = i + 2;
            add(new JButton(ops[i]), gbc);
        }

        // Dodanie sqrt, (, ) pod cyframi
        String[] extras = {"sqrt", "(", ")"};
        for (int i = 0; i < extras.length; i++) {
            gbc.gridx = i;
            gbc.gridy = 6;
            add(new JButton(extras[i]), gbc);
        }
    }

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        new UserInterface();
    }
}
