package project.quanlithuvien.ungdung.show;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        JButton exiButton = new JButton("Thoát");
        exiButton.setBackground(Color.RED);
        exiButton.setForeground(Color.WHITE);
        exiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
        loginButton.addActionListener(new ActionListener() {   
            @Override
            public void actionPerformed(ActionEvent e){
                new LibraryManagementUI();
            }
        });

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
        loginPanel.add(exiButton, gbc);
        return loginPanel;
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
    //     System.out.println("Chạy chương trình thành công");
    // }
}
