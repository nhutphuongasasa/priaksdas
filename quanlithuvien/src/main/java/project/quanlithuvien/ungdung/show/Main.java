package project.quanlithuvien.ungdung.show;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public Main() {
        initUI();
    }

    private void initUI() {
        setTitle("Chương trình quản lý thư viện");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel loginPanel = createLoginPanel();
        JPanel registerPanel = createRegisterPanel();

        mainPanel.add(loginPanel, "Login");
        mainPanel.add(registerPanel, "Register");

        this.add(mainPanel);
        cardLayout.show(mainPanel, "Login");
    }

    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(204, 255, 255)); // Màu nền khác biệt cho panel đăng nhập
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Đăng nhập hệ thống", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0, 102, 102));

        JTextField usernameField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);

        JButton loginButton = new JButton("Đăng nhập");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                new LibraryManagementUI();
            }
        });
        JButton switchToRegisterButton = new JButton("Đăng ký");

        styleButton(loginButton);
        styleButton(switchToRegisterButton);

        switchToRegisterButton.addActionListener(e -> cardLayout.show(mainPanel, "Register"));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        loginPanel.add(titleLabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        loginPanel.add(new JLabel("Tên đăng nhập:"), gbc);
        gbc.gridx = 1;
        loginPanel.add(usernameField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        loginPanel.add(new JLabel("Mật khẩu:"), gbc);
        gbc.gridx = 1;
        loginPanel.add(passwordField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        loginPanel.add(loginButton, gbc);
        gbc.gridx = 1;
        loginPanel.add(switchToRegisterButton, gbc);

        return loginPanel;
    }

    private JPanel createRegisterPanel() {
        JPanel registerPanel = new JPanel(new GridBagLayout());
        registerPanel.setBackground(new Color(255, 230, 230)); // Màu nền khác biệt cho panel đăng ký
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Đăng ký tài khoản", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0, 102, 102));

        JTextField usernameField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);
        JPasswordField confirmPasswordField = new JPasswordField(15);

        JButton registerButton = new JButton("Đăng ký");
        JButton switchToLoginButton = new JButton("Quay lại đăng nhập");

        styleButton(registerButton);
        styleButton(switchToLoginButton);

        switchToLoginButton.addActionListener(e -> cardLayout.show(mainPanel, "Login"));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        registerPanel.add(titleLabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        registerPanel.add(new JLabel("Tên đăng nhập:"), gbc);
        gbc.gridx = 1;
        registerPanel.add(usernameField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        registerPanel.add(new JLabel("Mật khẩu:"), gbc);
        gbc.gridx = 1;
        registerPanel.add(passwordField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        registerPanel.add(new JLabel("Xác nhận mật khẩu:"), gbc);
        gbc.gridx = 1;
        registerPanel.add(confirmPasswordField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        registerPanel.add(registerButton, gbc);
        gbc.gridx = 1;
        registerPanel.add(switchToLoginButton, gbc);

        return registerPanel;
    }

    private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(0, 155, 155));
        button.setForeground(Color.WHITE);
    }

    // public static void main(String[] args) {
    //     Main main = new Main();
    //     main.setVisible(true);
    // }
}
