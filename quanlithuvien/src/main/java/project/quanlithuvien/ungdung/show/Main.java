package project.quanlithuvien.ungdung.show;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame {
    public Main() {
        initUI();
    }

    private void initUI() {
        setTitle("Chương trình quản lý thư viện");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel loginPanel = createLoginPanel();

        this.add(loginPanel);
    }

    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(204, 255, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Đăng nhập", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0, 102, 102));

        JTextField usernameField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);

        JButton loginButton = new JButton("Đăng nhập");
        JButton exitButton = new JButton("Thoát");
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(e -> System.exit(0));

        loginButton.addActionListener(e -> switchToLibraryManagementUI());

        styleButton(loginButton);

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
        loginPanel.add(exitButton, gbc);

        return loginPanel;
    }

    private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(0, 155, 155));
        button.setForeground(Color.WHITE);
    }

    private void switchToLibraryManagementUI() {
        this.setVisible(false); // Ẩn `Main`
        
        LibraryManagementUI libraryManagementUI = new LibraryManagementUI();
        libraryManagementUI.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Main.this.setVisible(true); // Hiển thị lại `Main` khi đóng `LibraryManagementUI`
            }
        });

        libraryManagementUI.setVisible(true); // Hiển thị `LibraryManagementUI`
    }

    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(() -> {
    //         Main main = new Main();
    //         main.setVisible(true);
    //         System.out.println("Chạy chương trình thành công");
    //     });
    // }
}
