


import javax.swing.*;

import java.awt.*;

public class DashboardFrame extends JFrame {

    private BankService bankService;
    private String username;
    private JLabel balanceLabel;

    public DashboardFrame(String username, BankService service) {
        this.username = username;
        this.bankService = service;

        setTitle("Bank Dashboard");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new GridLayout(5, 1, 10, 10));

        balanceLabel = new JLabel();
        updateBalance();

        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton logoutBtn = new JButton("Logout");

        add(balanceLabel);
        add(depositBtn);
        add(withdrawBtn);
        add(logoutBtn);

        depositBtn.addActionListener(e -> deposit());
        withdrawBtn.addActionListener(e -> withdraw());
        logoutBtn.addActionListener(e -> logout());

        setVisible(true);
    }

    private void updateBalance() {
        double balance = bankService.getBalance(username);
        balanceLabel.setText("Current Balance: ₹ " + balance);
    }

    private void deposit() {
        String input = JOptionPane.showInputDialog("Enter deposit amount:");
        double amount = Double.parseDouble(input);
        bankService.deposit(username, amount);
        updateBalance();
    }

    private void withdraw() {
        String input = JOptionPane.showInputDialog("Enter withdrawal amount:");
        double amount = Double.parseDouble(input);

        if (!bankService.withdraw(username, amount)) {
            JOptionPane.showMessageDialog(this, "Insufficient Balance!");
        }

        updateBalance();
    }

    private void logout() {
        new LoginFrame();
        dispose();
    }
}
