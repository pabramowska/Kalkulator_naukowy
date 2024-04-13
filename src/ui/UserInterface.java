package ui;
import javax.swing.JFrame;

public class UserInterface extends JFrame {
    public UserInterface() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(400,100);
        setVisible(true);
    }
}

class StartNewWindow {
    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        new UserInterface();
    }
}
