

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private BankService bankService;

    public LoginFrame() {
        bankService = new BankService();

        setTitle("Mini Banking System - Login");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(loginButton);

        add(panel);

        loginButton.addActionListener(e -> login());

        setVisible(true);
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (bankService.login(username, password)) {
            new DashboardFrame(username, bankService);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Credentials!");
        }
    }
}
