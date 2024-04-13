package ui;
import logic.CalculatorEngine;
import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

/**
 * Klasa UserInterface odpowiada za tworzenie graficznego interfejsu użytkownika dla kalkulatora.
 * Zapewnia wizualizację wprowadzania danych oraz prezentację wyników operacji matematycznych.
 */
public class UserInterface extends JFrame {
    private JTextField displayField;
    /**
     * Konstruktor klasy UserInterface, inicjalizuje interfejs użytkownika, ustawiając odpowiednie parametry
     * okna oraz rozmieszczając elementy wewnątrz okna.
     */
    public UserInterface() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());


        displayField = new JTextField();
        displayField.setEditable(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(displayField, gbc);


        addButtons();

        pack();
        setVisible(true);
    }
    /**
     * Metoda tworząca i dodająca przyciski do interfejsu użytkownika.
     */
    private void addButtons() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;


        String[] funcs = {"sin", "cos", "tan", "ln"};
        int gridxStart = 0;
        for (int i = 0; i < funcs.length; i++) {
            gbc.gridx = gridxStart + i;
            gbc.gridy = 1;
            addButtonWithText(funcs[i], gbc.gridx, gbc.gridy, gbc);

        }


        int btnNumber = 1;
        for (int row = 2; row <= 4; row++) {
            for (int col = 0; col < 3; col++) {
                addButtonWithText(String.valueOf(btnNumber++), col, row, gbc);
            }
        }


        addButtonWithText("00", 0, 5, gbc);
        addButtonWithText("0", 1, 5, gbc);
        addButtonWithText(".", 2, 5, gbc);


        String[] ops = {"/", "*", "-", "+", "="};
        addActionButtons(ops, 3, 2, gbc);


        String[] extras = {"sqrt", "(", ")"};
        int start = 0;

        for (int i = 0; i < extras.length; i++) {
            gbc.gridx = start + i;
            gbc.gridy = 6;
            gbc.gridwidth = 1;
            addButtonWithText(extras[i], gbc.gridx, gbc.gridy, gbc);

        }
    }
    /**
     * Metoda dodająca przycisk z określonym tekstem w danym miejscu siatki.
     * @param text Tekst wyświetlany na przycisku.
     * @param gridx Pozycja kolumny w siatce.
     * @param gridy Pozycja wiersza w siatce.
     * @param gbc Konfiguracja położenia i rozmiaru przycisku.
     */
    private void addButtonWithText(String text, int gridx, int gridy, GridBagConstraints gbc) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        JButton button = new JButton(text);
        button.addActionListener(e -> displayField.setText(displayField.getText() + text));
        add(button, gbc);
    }
    /**
     * Dodaje przycisk "Clear" do interfejsu użytkownika, który służy do czyszczenia pola tekstowego.
     * Przycisk ten zajmuje jedną kolumnę i umieszczony jest na końcu wszystkich przycisków.
     */
    private void addClearButton(GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 4;
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> displayField.setText(""));
        add(clearButton, gbc);
    }

    /**
     * Metoda dodająca grupę przycisków z etykietami, rozpoczynając od określonej pozycji.
     * @param buttonLabels Tablica etykiet przycisków.
     * @param gridxStart Początkowa pozycja kolumny w siatce dla grupy przycisków.
     * @param gridyStart Początkowa pozycja wiersza w siatce dla grupy przycisków.
     * @param gbc Konfiguracja położenia i rozmiaru przycisków.
     */
    private void addActionButtons(String[] buttonLabels, int gridxStart, int gridyStart, GridBagConstraints gbc) {
        for (int i = 0; i < buttonLabels.length; i++) {
            gbc.gridx = gridxStart;
            gbc.gridy = gridyStart + i;
            JButton button = new JButton(buttonLabels[i]);
            if (buttonLabels[i].equals("=")) {
                button.addActionListener(e -> processExpression(displayField.getText()));
            } else {
                button.addActionListener(e -> displayField.setText(displayField.getText() + button.getText()));
            }
            add(button, gbc);
        }
        addClearButton(gbc);
    }
    /**
     * Metoda przetwarzająca wyrażenie matematyczne i wyświetlająca wynik w polu tekstowym.
     * @param expression Wyrażenie matematyczne do obliczenia.
     */
    public void processExpression(String expression) {
        try {
            double result = CalculatorEngine.calculate(expression);
            displayField.setText(String.valueOf(result));
        } catch (Exception e) {
            displayField.setText("Error");
        }
    }

}
