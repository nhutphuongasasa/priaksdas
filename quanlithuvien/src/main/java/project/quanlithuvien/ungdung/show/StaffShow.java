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
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import project.quanlithuvien.ungdung.DTO.LibraryStaffDTO;
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
        this.setSize(1200, 600);
        this.setLocationRelativeTo(null);

        // Card layout cho các panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Bảng hiển thị nhân viên
        tableModel = new DefaultTableModel(new Object[]{"Name", "Email","Phone","Position","Hire_date","Active"}, 0);
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
    //them(xong)
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
    // JTextField positionField = new JTextField();
    String[] positions = {"Staff"};
    JComboBox<String> positionField = new JComboBox<>(positions);
    positionField.setSelectedItem("Staff");
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
        libraryStaffRequestDTO.setPosition(positionField.getSelectedItem().toString());
        libraryStaffRequestDTO.setUser_name(userNameField.getText().trim());
        libraryStaffRequestDTO.setPassword(new String(passwordField.getPassword().toString()).trim());

        // Gửi yêu cầu thêm nhân viên(xong)
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonRequest = objectMapper.writeValueAsString(libraryStaffRequestDTO);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/api/library-staff")) // Địa chỉ API của bạn
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JOptionPane.showMessageDialog(addStaffPanel, response.body());
                
                nameField.setText("");
                emailField.setText("");
                phoneField.setText("");
                positionField.setSelectedItem("staff");
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

    // Nút xem danh sách nhân viên(xong)
    JButton viewStaffButton = new JButton("Xem danh sách nhân viên");
    viewStaffButton.addActionListener(e -> {
        // Gửi yêu cầu lấy toan bo danh sách nhân viên
    try {
        HttpClient client = HttpClient.newHttpClient();
        String url = sendDtaffSearchRequest(null,null,null,null,null,null);
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url)) // Địa chỉ API của bạn
            .header("Content-Type", "application/json")
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            // Chuyển đổi phản hồi JSON thành danh sách nhân viên
            ObjectMapper objectMapper = new ObjectMapper();
            // List<LibraryStaffDTO> staffList = objectMapper.readValue(response.body(),new TypeReference<List<LibraryStaffDTO>>() {});

            List<LibraryStaffDTO> staffList = objectMapper.readValue(response.body(),objectMapper.getTypeFactory().constructCollectionType(List.class, LibraryStaffDTO.class));
            
            // Làm sạch bảng trước khi thêm dữ liệu mới
            tableModel.setRowCount(0);
            
            // Hiển thị thông tin nhân viên
            for (LibraryStaffDTO staff : staffList) {
                tableModel.addRow(new Object[]{
                    staff.getName(),
                    staff.getEmail(),
                    staff.getPhone(),
                    staff.getPosition(),
                    staff.getHire_date(),
                    staff.getActive()
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

    //nhan url danh sach nhan vien(xong)
    public String sendDtaffSearchRequest(String name,String email,String phone,String position,String password,String user_name){
    StringBuilder uriBuilder = new StringBuilder("http://localhost:8081/api/library-staff?");
    if (name != null && !name.isEmpty()) {
        uriBuilder.append("name=").append(URLEncoder.encode(name, StandardCharsets.UTF_8)).append("&");
    }
    if (email != null && !email.isEmpty()) {
        uriBuilder.append("email=").append(URLEncoder.encode(email, StandardCharsets.UTF_8)).append("&");
    }
    if (phone != null && !phone.isEmpty()) {
        uriBuilder.append("phone=").append(URLEncoder.encode(phone, StandardCharsets.UTF_8)).append("&");
    }
    if (position != null && !position.isEmpty()) {
        uriBuilder.append("position=").append(URLEncoder.encode(position, StandardCharsets.UTF_8)).append("&");
    }
    if (user_name != null && !user_name.isEmpty()) {
        uriBuilder.append("user_name=").append(URLEncoder.encode(user_name, StandardCharsets.UTF_8)).append("&");
    }
    if (password != null && !password.isEmpty()) {
        uriBuilder.append("password=").append(URLEncoder.encode(password, StandardCharsets.UTF_8)).append("&");
    }
    if (uriBuilder.charAt(uriBuilder.length() - 1) == '&') {
        uriBuilder.deleteCharAt(uriBuilder.length() - 1);
    }
    System.out.println(uriBuilder.toString());
    return uriBuilder.toString();
}

    //chinh sua (xong)
    private JPanel createEditStaffPanel() {
        JPanel editStaffPanel = new JPanel(new GridBagLayout());
        editStaffPanel.setBorder(BorderFactory.createTitledBorder("Sửa thông tin nhân viên"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Các trường nhập liệu
        JTextField nameField = new JTextField();
        String[] positions = {"Staff"};
        JComboBox<String> positionField = new JComboBox<>(positions);
        JTextField phoneField = new JTextField();
        JTextField newEmailField = new JTextField();

        // Tạo và thiết lập kích thước cho trường email
        gbc.gridx = 0; gbc.gridy = 0; // Đặt vị trí cho JLabel
        editStaffPanel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1; // Cột cho JTextField
        JTextField emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(200, 30)); // Kích thước mong muốn
        editStaffPanel.add(emailField, gbc);

        // Nút tìm kiếm bên phải trường Email
        JButton searchButton = new JButton("Tìm nhân viên");
        gbc.gridx = 2; // Đặt nút tìm kiếm ở bên phải trường Email
        editStaffPanel.add(searchButton, gbc);

        // Thêm trường tên
        gbc.gridx = 0; gbc.gridy = 1;
        editStaffPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        nameField.setPreferredSize(new Dimension(200, 30)); // Kích thước mong muốn
        editStaffPanel.add(nameField, gbc);
        nameField.setEditable(false);

        // Thêm trường Email mới
        gbc.gridx = 0; gbc.gridy = 2;
        editStaffPanel.add(new JLabel("New Email:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        newEmailField.setPreferredSize(new Dimension(200, 30)); // Kích thước mong muốn
        editStaffPanel.add(newEmailField, gbc);
        newEmailField.setEditable(false);

        // Thêm trường điện thoại
        gbc.gridx = 0; gbc.gridy = 3;
        editStaffPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        phoneField.setPreferredSize(new Dimension(200, 30)); // Kích thước mong muốn
        editStaffPanel.add(phoneField, gbc);
        phoneField.setEditable(false);

        // Thêm trường vị trí
        gbc.gridx = 0; gbc.gridy = 4;
        editStaffPanel.add(new JLabel("Position:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4;
        positionField.setPreferredSize(new Dimension(200, 30)); // Kích thước mong muốn
        editStaffPanel.add(positionField, gbc);
        positionField.setEditable(false);

        // Nút sửa
        JButton editStaffButton = new JButton("Sửa");
        editStaffButton.setBackground(new Color(0, 155, 155));
        editStaffButton.setForeground(Color.WHITE);
        gbc.gridx = 1; gbc.gridy = 5; // Đặt nút sửa ở phía dưới cùng
        gbc.gridwidth = 2; // Chiếm 2 cột
        editStaffPanel.add(editStaffButton, gbc);

        editStaffButton.addActionListener(e -> {
            LibraryStaffRequestDTO libraryStaffRequestDTO = new LibraryStaffRequestDTO();
            libraryStaffRequestDTO.setName(nameField.getText().trim());
            libraryStaffRequestDTO.setPosition(positionField.getSelectedItem().toString());
            libraryStaffRequestDTO.setEmail(newEmailField.getText().trim());
            libraryStaffRequestDTO.setPhone(phoneField.getText().trim());
            // Gửi yêu cầu cập nhật thông tin nhân viên
            try {
                HttpClient client = HttpClient.newHttpClient();
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonRequest = objectMapper.writeValueAsString(libraryStaffRequestDTO);
    
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8081/api/library-staff/" + emailField.getText().trim()))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonRequest))
                    .build();
    
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    
                if (response.statusCode() == 200) {
                    String responseFromServer = response.body();
                    if (responseFromServer.equals("Successfull")) {
                        JOptionPane.showMessageDialog(editStaffPanel, "Cập nhật thành công!");
                        emailField.setText("");
                        nameField.setText("");
                        positionField.setSelectedItem("Staff");
                        phoneField.setText("");
                        positionField.setEditable(false);
                        phoneField.setEditable(false);
                        newEmailField.setEditable(false);
                        nameField.setEditable(false);
                    }else{
                        JOptionPane.showMessageDialog(this, response.body());
                    tableModel.setRowCount(0);
                    }
                } 
            } catch (Exception k) {
                k.printStackTrace();
            } 
            
        });
        // Hành động cho nút tìm kiếm
        searchButton.addActionListener(e -> {
        String email = emailField.getText().trim();
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(editStaffPanel, "Vui lòng nhập email nhân viên.");
            return;
        }
        // Tìm nhân viên theo email
        try {
            String url = sendDtaffSearchRequest(null, email, null, null, null, null);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .GET()
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ObjectMapper objectMapper = new ObjectMapper();
                List<LibraryStaffDTO> staffs = objectMapper.readValue(response.body(),objectMapper.getTypeFactory().constructCollectionType(List.class, LibraryStaffDTO.class));
                if(staffs==null){
                JOptionPane.showMessageDialog(editStaffPanel,"Không có thông tin");
                }else{
                    LibraryStaffDTO staff=staffs.get(0);
                    // Cập nhật thông tin vào các trường
                    nameField.setText(staff.getName());
                    positionField.setSelectedItem("Staff");
                    phoneField.setText(staff.getPhone());
                    newEmailField.setText(staff.getEmail());
                    nameField.setEditable(true);
                    positionField.setEditable(true);
                    newEmailField.setEditable(true);
                    phoneField.setEditable(true);
                    }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(editStaffPanel, "Có lỗi xảy ra: " + ex.getMessage());
        }
    });

    return editStaffPanel;
}

    //xoa nhan vien (xong)
    private JPanel createDeleteStaffPanel() {
        JPanel deleteStaffPanel = new JPanel(new GridBagLayout());
        deleteStaffPanel.setBorder(BorderFactory.createTitledBorder("Xóa nhân viên"));
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        JTextField emailField = new JTextField(); // Thay idField bằng nameField
    
        gbc.gridx = 0; gbc.gridy = 0;
        deleteStaffPanel.add(new JLabel("Email nhân viên:"), gbc);
        gbc.gridx = 1;
        deleteStaffPanel.add(emailField, gbc);
    
        JButton deleteStaffButton = new JButton("Xóa");
        deleteStaffButton.setBackground(Color.RED);
        deleteStaffButton.setForeground(Color.WHITE);
        gbc.gridx = 1; gbc.gridy = 1;
        deleteStaffPanel.add(deleteStaffButton, gbc);
    
        deleteStaffButton.addActionListener(e -> {
            String emailInput = emailField.getText().trim();
            if (emailInput.isEmpty()) {
                JOptionPane.showMessageDialog(deleteStaffPanel, "Vui lòng nhập email nhân viên.");
                return;
            }
    
            // Gọi API để xóa nhân viên
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8081/api/library-staff/" + emailField.getText().trim()))
                        .header("Content-Type", "application/json")
                        .DELETE() // Sử dụng phương thức DELETE
                        .build();
    
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    
                if (response.statusCode() == 200) {
                    String responseFromSever = response.body();
                    if(responseFromSever.equals("Successfull")){
                        JOptionPane.showMessageDialog(deleteStaffPanel, response.body());
                    }
                    
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(deleteStaffPanel, "Có lỗi xảy ra: " + ex.getMessage());
            }
        });
    
        return deleteStaffPanel;
    }
    
}
