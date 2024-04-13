package ui;

import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
        int gridxStart = 0;
        for (int i = 0; i < funcs.length; i++) {
            gbc.gridx = gridxStart + i;
            gbc.gridy = 1;
            addButtonWithText(funcs[i], gbc.gridx, gbc.gridy, gbc);

        }

        // Dodanie przycisków numerycznych
        int btnNumber = 1;
        for (int row = 2; row <= 4; row++) {
            for (int col = 0; col < 3; col++) {
                addButtonWithText(String.valueOf(btnNumber++), col, row, gbc);
            }
        }

        // Dodanie pozostałych przycisków numerycznych
        addButtonWithText("00", 0, 5, gbc);
        addButtonWithText("0", 1, 5, gbc);
        addButtonWithText(".", 2, 5, gbc);

        // Dodanie przycisków operacji z prawej strony
        String[] ops = {"/", "*", "-", "+", "="};
        addActionButtons(ops, 3, 2, gbc);

        // Dodanie sqrt, (, ) pod cyframi
        String[] extras = {"sqrt", "(", ")"};
        int start = 0;

        for (int i = 0; i < extras.length; i++) {
            gbc.gridx = start + i;
            gbc.gridy = 6;
            addButtonWithText(extras[i], gbc.gridx, gbc.gridy, gbc);

        }
        //addActionButtons(extras, 0, 5, gbc);
    }

    private void addButtonWithText(String text, int gridx, int gridy, GridBagConstraints gbc) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        JButton button = new JButton(text);
        button.addActionListener(e -> displayField.setText(displayField.getText() + text));
        add(button, gbc);
    }

    private void addActionButtons(String[] buttonLabels, int gridxStart, int gridyStart, GridBagConstraints gbc) {
        for (int i = 0; i < buttonLabels.length; i++) {
            gbc.gridx = gridxStart;
            gbc.gridy = gridyStart + i;
            addButtonWithText(buttonLabels[i], gbc.gridx, gbc.gridy, gbc);
        }
    }

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        new UserInterface();
    }
}
