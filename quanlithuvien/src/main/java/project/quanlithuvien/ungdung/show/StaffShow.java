package project.quanlithuvien.ungdung.show;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.net.URLEncoder;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import project.quanlithuvien.ungdung.DTO.LibraryStaffRequestDTO;

public class StaffShow extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public StaffShow(){
        this.init();
    }

    private void init() {
        this.setTitle("Quản lý nhân viên thư viện");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);

        // Card layout cho các panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Bảng hiển thị nhân viên
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Position", "Email"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.setPreferredSize(new Dimension(400, 600));

        // Panel thêm nhân viên
        JPanel addStaffPanel = createAddStaffPanel();

        // Panel sửa thông tin nhân viên
        JPanel editStaffPanel = createEditStaffPanel();

        // Panel xóa nhân viên
        JPanel deleteStaffPanel = createDeleteStaffPanel();

        // Thêm các panel vào mainPanel (CardLayout)
        mainPanel.add(addStaffPanel, "Add Staff");
        mainPanel.add(editStaffPanel, "Edit Staff");
        mainPanel.add(deleteStaffPanel, "Delete Staff");

        // Thanh nút điều hướng
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(0, 153, 153));

        JButton addStaffTabButton = new JButton("Thêm nhân viên");
        JButton editStaffTabButton = new JButton("Sửa thông tin");
        JButton deleteStaffTabButton = new JButton("Xóa nhân viên");

        addStaffTabButton.addActionListener(e -> cardLayout.show(mainPanel, "Add Staff"));
        editStaffTabButton.addActionListener(e -> cardLayout.show(mainPanel, "Edit Staff"));
        deleteStaffTabButton.addActionListener(e -> cardLayout.show(mainPanel, "Delete Staff"));

        buttonPanel.add(addStaffTabButton);
        buttonPanel.add(editStaffTabButton);
        buttonPanel.add(deleteStaffTabButton);

        // Tạo SplitPane để phân chia không gian giữa bảng và các chức năng
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, mainPanel);
        splitPane.setDividerLocation(400);
        splitPane.setResizeWeight(0.5);

        // Thêm các thành phần vào JFrame
        this.setLayout(new BorderLayout());
        this.add(buttonPanel, BorderLayout.NORTH);
        this.add(splitPane, BorderLayout.CENTER);
        this.setVisible(true);
    }

   private JPanel createAddStaffPanel() {
    JPanel addStaffPanel = new JPanel(new GridBagLayout());
    addStaffPanel.setBorder(BorderFactory.createTitledBorder("Thêm nhân viên mới"));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // Các trường nhập liệu (không có ID)
    JTextField nameField = new JTextField();
    JTextField emailField = new JTextField();
    JTextField phoneField = new JTextField();
    JTextField positionField = new JTextField();
    JTextField userNameField = new JTextField();
    JPasswordField passwordField = new JPasswordField();

    // Thêm nhãn và trường nhập liệu
    gbc.gridx = 0; gbc.gridy = 0;
    addStaffPanel.add(new JLabel("Name:"), gbc);
    gbc.gridx = 1;
    addStaffPanel.add(nameField, gbc);

    gbc.gridx = 0; gbc.gridy = 1;
    addStaffPanel.add(new JLabel("Email:"), gbc);
    gbc.gridx = 1;
    addStaffPanel.add(emailField, gbc);

    gbc.gridx = 0; gbc.gridy = 2;
    addStaffPanel.add(new JLabel("Phone:"), gbc);
    gbc.gridx = 1;
    addStaffPanel.add(phoneField, gbc);

    gbc.gridx = 0; gbc.gridy = 3;
    addStaffPanel.add(new JLabel("Position:"), gbc);
    gbc.gridx = 1;
    addStaffPanel.add(positionField, gbc);

    gbc.gridx = 0; gbc.gridy = 4;
    addStaffPanel.add(new JLabel("Username:"), gbc);
    gbc.gridx = 1;
    addStaffPanel.add(userNameField, gbc);

    gbc.gridx = 0; gbc.gridy = 5;
    addStaffPanel.add(new JLabel("Password:"), gbc);
    gbc.gridx = 1;
    addStaffPanel.add(passwordField, gbc);

    JButton addStaffButton = new JButton("Thêm");
    addStaffButton.addActionListener(e -> {
        LibraryStaffRequestDTO libraryStaffRequestDTO = new LibraryStaffRequestDTO();
        libraryStaffRequestDTO.setName(nameField.getText().trim());
        libraryStaffRequestDTO.setEmail(emailField.getText().trim());
        libraryStaffRequestDTO.setPhone(phoneField.getText().trim());
        libraryStaffRequestDTO.setPosition(positionField.getText().trim());
        libraryStaffRequestDTO.setUser_name(userNameField.getText().trim());
        libraryStaffRequestDTO.setPassword(new String(passwordField.getPassword()).trim());

        // Gửi yêu cầu thêm nhân viên
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonRequest = objectMapper.writeValueAsString(libraryStaffRequestDTO);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/api/staff/add")) // Địa chỉ API của bạn
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JOptionPane.showMessageDialog(addStaffPanel, "Thêm nhân viên thành công!");
                // Xóa thông tin trong các trường nhập liệu
                nameField.setText("");
                emailField.setText("");
                phoneField.setText("");
                positionField.setText("");
                userNameField.setText("");
                passwordField.setText("");
            } else {
                JOptionPane.showMessageDialog(addStaffPanel, "Thêm nhân viên không thành công: " + response.body());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(addStaffPanel, "Có lỗi xảy ra: " + ex.getMessage());
        }
    });

    addStaffButton.setBackground(new Color(0, 155, 155));
    addStaffButton.setForeground(Color.WHITE);
    gbc.gridx = 1; gbc.gridy = 6;
    addStaffPanel.add(addStaffButton, gbc);

    // Nút in thông tin nhân viên
    JButton viewStaffButton = new JButton("Xem danh sách nhân viên");
    viewStaffButton.addActionListener(e -> {
        // Gửi yêu cầu lấy danh sách nhân viên
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/api/staff/all")) // Địa chỉ API của bạn
                .header("Content-Type", "application/json")
                .GET()
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Chuyển đổi phản hồi JSON thành danh sách nhân viên
                ObjectMapper objectMapper = new ObjectMapper();
                List<LibraryStaffRequestDTO> staffList = objectMapper.readValue(response.body(),
                        new TypeReference<List<LibraryStaffRequestDTO>>() {});
            
                // Làm sạch bảng trước khi thêm dữ liệu mới
                tableModel.setRowCount(0);
            
                // Hiển thị thông tin nhân viên
                for (LibraryStaffRequestDTO staff : staffList) {
                    tableModel.addRow(new Object[]{
                        staff.getName(),
                        staff.getEmail(),
                        staff.getPhone(),
                        staff.getPosition(),
                        staff.getUser_name()
                    });
                }
            } else {
                JOptionPane.showMessageDialog(addStaffPanel, "Không thể lấy danh sách nhân viên: " + response.body());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(addStaffPanel, "Có lỗi xảy ra: " + ex.getMessage());
        }
    });

    viewStaffButton.setBackground(new Color(0, 155, 155));
    viewStaffButton.setForeground(Color.WHITE);
    gbc.gridx = 1; gbc.gridy = 7;
    addStaffPanel.add(viewStaffButton, gbc);

    return addStaffPanel;
}


private JPanel createEditStaffPanel() {
    JPanel editStaffPanel = new JPanel(new GridBagLayout());
    editStaffPanel.setBorder(BorderFactory.createTitledBorder("Sửa thông tin nhân viên"));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JTextField nameField = new JTextField();
    JTextField positionField = new JTextField();
    JTextField emailField = new JTextField();

    gbc.gridx = 0; gbc.gridy = 0;
    editStaffPanel.add(new JLabel("Tên nhân viên:"), gbc);
    gbc.gridx = 1;
    editStaffPanel.add(nameField, gbc);

    gbc.gridx = 0; gbc.gridy = 1;
    editStaffPanel.add(new JLabel("Chức vụ:"), gbc);
    gbc.gridx = 1;
    editStaffPanel.add(positionField, gbc);

    gbc.gridx = 0; gbc.gridy = 2;
    editStaffPanel.add(new JLabel("Email:"), gbc);
    gbc.gridx = 1;
    editStaffPanel.add(emailField, gbc);

    JButton searchButton = new JButton("Tìm nhân viên");
    searchButton.addActionListener(e -> {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(editStaffPanel, "Vui lòng nhập tên nhân viên.");
            return;
        }
        
        // Tìm nhân viên theo tên
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/api/staff/search?name=" + URLEncoder.encode(name, "UTF-8"))) // Địa chỉ API tìm kiếm
                .header("Content-Type", "application/json")
                .GET()
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ObjectMapper objectMapper = new ObjectMapper();
                LibraryStaffRequestDTO staff = objectMapper.readValue(response.body(), LibraryStaffRequestDTO.class);

                // Cập nhật thông tin vào các trường
                nameField.setText(staff.getName());
                positionField.setText(staff.getPosition());
                emailField.setText(staff.getEmail());
            } else {
                JOptionPane.showMessageDialog(editStaffPanel, "Không tìm thấy nhân viên với tên: " + name);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(editStaffPanel, "Có lỗi xảy ra: " + ex.getMessage());
        }
    });

    gbc.gridx = 1; gbc.gridy = 3;
    editStaffPanel.add(searchButton, gbc);

    JButton editStaffButton = new JButton("Sửa");
    editStaffButton.addActionListener(e -> {
        // Tạo đối tượng nhân viên để cập nhật
        LibraryStaffRequestDTO libraryStaffRequestDTO = new LibraryStaffRequestDTO();
        libraryStaffRequestDTO.setName(nameField.getText().trim());
        libraryStaffRequestDTO.setPosition(positionField.getText().trim());
        libraryStaffRequestDTO.setEmail(emailField.getText().trim());

        // Gửi yêu cầu cập nhật thông tin nhân viên
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonRequest = objectMapper.writeValueAsString(libraryStaffRequestDTO);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/api/staff/edit")) // Địa chỉ API chỉnh sửa
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JOptionPane.showMessageDialog(editStaffPanel, "Cập nhật thông tin nhân viên thành công!");
                // Xóa thông tin trong các trường nhập liệu
                nameField.setText("");
                positionField.setText("");
                emailField.setText("");
            } else {
                JOptionPane.showMessageDialog(editStaffPanel, "Cập nhật không thành công: " + response.body());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(editStaffPanel, "Có lỗi xảy ra: " + ex.getMessage());
        }
    });

    editStaffButton.setBackground(new Color(0, 155, 155));
    editStaffButton.setForeground(Color.WHITE);
    gbc.gridx = 1; gbc.gridy = 4;
    editStaffPanel.add(editStaffButton, gbc);

    return editStaffPanel;
}

    //xoa nhan vien
    private JPanel createDeleteStaffPanel() {
        JPanel deleteStaffPanel = new JPanel(new GridBagLayout());
        deleteStaffPanel.setBorder(BorderFactory.createTitledBorder("Xóa nhân viên"));
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        JTextField nameField = new JTextField(); // Thay idField bằng nameField
    
        gbc.gridx = 0; gbc.gridy = 0;
        deleteStaffPanel.add(new JLabel("Tên nhân viên:"), gbc);
        gbc.gridx = 1;
        deleteStaffPanel.add(nameField, gbc);
    
        JButton deleteStaffButton = new JButton("Xóa");
        deleteStaffButton.setBackground(Color.RED);
        deleteStaffButton.setForeground(Color.WHITE);
        gbc.gridx = 1; gbc.gridy = 1;
        deleteStaffPanel.add(deleteStaffButton, gbc);
    
        deleteStaffButton.addActionListener(e -> {
            String nameInput = nameField.getText().trim();
            if (nameInput.isEmpty()) {
                JOptionPane.showMessageDialog(deleteStaffPanel, "Vui lòng nhập tên nhân viên.");
                return;
            }
    
            // Gọi API để xóa nhân viên
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8081/api/staff/delete?name=" + URLEncoder.encode(nameInput, "UTF-8")))
                        .header("Content-Type", "application/json")
                        .DELETE() // Sử dụng phương thức DELETE
                        .build();
    
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    
                if (response.statusCode() == 200) {
                    JOptionPane.showMessageDialog(deleteStaffPanel, "Đã xóa nhân viên: " + nameInput);
                } else {
                    JOptionPane.showMessageDialog(deleteStaffPanel, "Không tìm thấy nhân viên với tên: " + nameInput);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(deleteStaffPanel, "Có lỗi xảy ra: " + ex.getMessage());
            }
        });
    
        return deleteStaffPanel;
    }
    
}
