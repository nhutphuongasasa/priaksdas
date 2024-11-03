package project.quanlithuvien.ungdung.show;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.fasterxml.jackson.databind.ObjectMapper;

import project.quanlithuvien.ungdung.DTO.Login;

public class Main extends JFrame {

    public Main() {
        initUI();
    }

    private void initUI() {
        setTitle("Chương trình quản lý thư viện");
        setSize(1000, 600);
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

        loginButton.addActionListener(e -> {
             try {
                Login login = new Login();
                login.setPassword(String.valueOf(passwordField.getPassword()));
                login.setUser_name(usernameField.getText().trim());
                if(login.getUser_name()==null || login.getUser_name().isEmpty() || login.getPassword().isEmpty()||login.getPassword()==null){
                    JOptionPane.showMessageDialog(this, "Vui òng nhập đầy đủ thông tin");
                }
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonRequest = objectMapper.writeValueAsString(login);
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/api/admin/login")) // Địa chỉ API của bạn
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();
    
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                String responseFromSever = response.body();
                if (response.statusCode() == 200) {
                    if(responseFromSever.equals("Admin")){
                        switchToLibraryManagementUI("Admin");
                    }else if(responseFromSever.equals("Staff")){
                        switchToLibraryManagementUI("Staff");
                    }
                    else{
                        JOptionPane.showMessageDialog(this, responseFromSever);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(this, responseFromSever); 
                }  
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "error");
            }
        
        });

        styleButton(loginButton);

        return loginPanel;
    }

    private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(0, 155, 155));
        button.setForeground(Color.WHITE);
    }

    private void switchToLibraryManagementUI(String role) {
        this.setVisible(false); // Ẩn `Main`
        
        LibraryManagementUI libraryManagementUI = new LibraryManagementUI(role);
        libraryManagementUI.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Main.this.setVisible(true); // Hiển thị lại `Main` khi đóng `LibraryManagementUI`
            }
        });

        libraryManagementUI.setVisible(true); // Hiển thị `LibraryManagementUI`
    }

    
}
