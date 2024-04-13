package ui;
import javax.swing.*;
import javax.swing.JFrame;

public class UserInterface extends JFrame {
    public UserInterface() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.add(new JButton("1"));
        panel.add(new JButton("2"));
        panel.add(new JButton("3"));
        panel.add(new JButton("4"));
        panel.add(new JButton("5"));
        panel.add(new JButton("6"));
        panel.add(new JButton("7"));
        panel.add(new JButton("8"));
        panel.add(new JButton("9"));
        panel.add(new JButton("00"));
        panel.add(new JButton("0"));
        panel.add(new JButton("."));

        getContentPane().add(panel);

        pack();
        setSize(400,800);
        setVisible(true);

    }
}

class StartNewWindow {
    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        new UserInterface();
    }
}
