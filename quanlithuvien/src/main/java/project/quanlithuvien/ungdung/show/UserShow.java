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
import java.net.http.HttpResponse;

import java.util.List;
import java.net.http.HttpRequest;
import java.net.URLEncoder;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import project.quanlithuvien.ungdung.DTO.*;

public class UserShow extends JFrame{
    private JTable table;
    private DefaultTableModel tableModel;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public UserShow() {
        this.init();
    }

    private void init() {
        this.setTitle("Quản lý độc giả");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);

        // Card layout cho các panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Bảng hiển thị độc giả
        tableModel = new DefaultTableModel(new Object[]{"Name", "Email","Phone", "Address", "Registration_Date","Status"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.setPreferredSize(new Dimension(400, 600));

        // Panel thêm độc giả
        JPanel addUserPanel = createAddUserPanel();

        // Panel tìm kiếm độc giả
        JPanel searchPanel = createSearchPanel();

        // Panel xóa độc giả
        JPanel deleteUserPanel = createDeleteUserPanel();
        //Panel chinh sua user
        JPanel editUserPanel = createEditUserPanel();
        // Thêm các panel vào mainPanel (CardLayout)
        mainPanel.add(addUserPanel, "Add User");
        mainPanel.add(searchPanel, "Search User");
        mainPanel.add(deleteUserPanel, "Delete User");
        mainPanel.add(editUserPanel, "Edit User");

        // Thanh nút điều hướng
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(0, 153, 153));

        JButton addUserTabButton = new JButton("Thêm người dùng");
        JButton searchTabButton = new JButton("Tìm kiếm");
        JButton deleteUserTabButton = new JButton("Xóa");
        JButton editUserTaButton = new JButton("Chỉnh sửa");

        addUserTabButton.addActionListener(e -> cardLayout.show(mainPanel, "Add User"));
        searchTabButton.addActionListener(e -> cardLayout.show(mainPanel, "Search User"));
        deleteUserTabButton.addActionListener(e -> cardLayout.show(mainPanel, "Delete User"));
        editUserTaButton.addActionListener(e -> cardLayout.show(mainPanel, "Edit User"));

        buttonPanel.add(addUserTabButton);
        buttonPanel.add(searchTabButton);
        buttonPanel.add(deleteUserTabButton);
        buttonPanel.add(editUserTaButton);

        // Tạo SplitPane để phân chia không gian giữa bảng và các chức năng
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, mainPanel);
        splitPane.setDividerLocation(400);
        splitPane.setResizeWeight(0.5);

        // Thêm các thành phần vào JFrame
        this.add(buttonPanel, BorderLayout.NORTH);
        this.add(splitPane, BorderLayout.CENTER);
        this.setVisible(true);
    }
    //chinh sua user
    private JPanel createEditUserPanel() {
        JPanel editUserPanel = new JPanel(new GridBagLayout());
        editUserPanel.setBorder(BorderFactory.createTitledBorder("Chỉnh sửa độc giả"));
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        JTextField nameField = new JTextField(80);
        JTextField emailField = new JTextField(80);
        JTextField newEmailField = new JTextField(80);
        JTextField phoneField = new JTextField(80);
        JTextField addressField = new JTextField(80);
    
        gbc.gridx = 0; gbc.gridy = 0;
        editUserPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        editUserPanel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        editUserPanel.add(new JLabel("New Email:"), gbc);
        gbc.gridx = 1;
        editUserPanel.add(emailField, gbc);
        newEmailField.setEditable(false);
    
        gbc.gridx = 0; gbc.gridy = 2;
        editUserPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        editUserPanel.add(nameField, gbc);
        nameField.setEditable(false); // Ban đầu không cho chỉnh sửa
    
        gbc.gridx = 0; gbc.gridy = 3;
        editUserPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        editUserPanel.add(phoneField, gbc);
        phoneField.setEditable(false); // Ban đầu không cho chỉnh sửa
    
        gbc.gridx = 0; gbc.gridy = 4;
        editUserPanel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1;
        editUserPanel.add(addressField, gbc);
        addressField.setEditable(false); // Ban đầu không cho chỉnh sửa
    
        JButton searchButton = new JButton("Tìm kiếm");
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        editUserPanel.add(searchButton, gbc);
    
        JButton editUserButton = new JButton("Chỉnh sửa");
        editUserButton.setBackground(new Color(0, 155, 155));
        editUserButton.setForeground(Color.WHITE);
        gbc.gridx = 1; gbc.gridy = 6;
        editUserPanel.add(editUserButton, gbc);
        editUserButton.setEnabled(false); // Ban đầu tắt nút chỉnh sửa
    
        // Sự kiện cho nút tìm kiếm
        // searchButton.addActionListener(e -> {
        //     String emailInput = emailField.getText().trim();
        //     if (emailInput.isEmpty()) {
        //         JOptionPane.showMessageDialog(editUserPanel, "Vui lòng nhập email.");
        //         return;
        //     }
    
        //     // Gửi yêu cầu tìm kiếm đến API
        //     try {
        //         HttpClient client = HttpClient.newHttpClient();
        //         HttpRequest request = HttpRequest.newBuilder()
        //             .uri(URI.create("http://localhost:8081/api/readers/" + URLEncoder.encode(emailInput, "UTF-8")))
        //             .header("Content-Type", "application/json")
        //             .GET()
        //             .build();
    
        //         HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    
        //         if (response.statusCode() == 200) {
        //             ObjectMapper objectMapper = new ObjectMapper();
        //             ReaderRequestDTO reader = objectMapper.readValue(response.body(), ReaderRequestDTO.class);
    
        //             // Điền thông tin vào các trường
        //             nameField.setText(reader.getName());
        //             phoneField.setText(reader.getPhone());
        //             newEmailField.setText(reader.getEmail());
        //             addressField.setText(reader.getAddress());
    
        //             // Cho phép chỉnh sửa các trường và bật nút chỉnh sửa
        //             nameField.setEditable(true);
        //             phoneField.setEditable(true);
        //             addressField.setEditable(true);
        //             newEmailField.setEditable(true);
        //             editUserButton.setEnabled(true);
        //         } else {
        //             JOptionPane.showMessageDialog(editUserPanel, "Không tìm thấy email: " + emailInput);
        //         }
        //     } catch (Exception ex) {
        //         ex.printStackTrace();
        //         JOptionPane.showMessageDialog(editUserPanel, "Có lỗi xảy ra: " + ex.getMessage());
        //     }
        // });
    
        // Thêm ActionListener cho nút chỉnh sửa
        editUserButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = newEmailField.getText().trim();
            String phone = phoneField.getText().trim();
            String address = addressField.getText().trim();
    
            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(editUserPanel, "Vui lòng điền đầy đủ thông tin.");
                return;
            }
    
            ReaderRequestDTO readerRequestDTO = new ReaderRequestDTO();
            readerRequestDTO.setName(name);
            readerRequestDTO.setEmail(email);
            readerRequestDTO.setPhone(phone);
            readerRequestDTO.setAddress(address);
    
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonRequest = objectMapper.writeValueAsString(readerRequestDTO);
    
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8081/api/readers/update"))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonRequest))
                    .build();
    
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    
                if (response.statusCode() == 200) {
                    JOptionPane.showMessageDialog(editUserPanel, "Cập nhật thành công!");
                    nameField.setText("");
                    newEmailField.setText("");
                    phoneField.setText("");
                    addressField.setText("");
                } else {
                    JOptionPane.showMessageDialog(editUserPanel, "Cập nhật không thành công: " + response.body());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(editUserPanel, "Có lỗi xảy ra: " + ex.getMessage());
            }
        });
    
        return editUserPanel;
    }
    
    
    //them user(test)
    private JPanel createAddUserPanel() {
        JPanel addUserPanel = new JPanel(new GridBagLayout());
        addUserPanel.setBorder(BorderFactory.createTitledBorder("Thêm độc giả mới"));
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField addressField = new JTextField();
    
        // Tạo các trường nhập liệu
        gbc.gridx = 0; gbc.gridy = 0;
        addUserPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        addUserPanel.add(nameField, gbc);
    
        gbc.gridx = 0; gbc.gridy = 1;
        addUserPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        addUserPanel.add(emailField, gbc);
    
        gbc.gridx = 0; gbc.gridy = 2;
        addUserPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        addUserPanel.add(phoneField, gbc);
    
        gbc.gridx = 0; gbc.gridy = 3;
        addUserPanel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1;
        addUserPanel.add(addressField, gbc);
    
        // Nút thêm độc giả
        JButton addUserButton = new JButton("Thêm người dùng");
        addUserButton.setBackground(new Color(0, 155, 155));
        addUserButton.setForeground(Color.WHITE);
        gbc.gridx = 1; gbc.gridy = 4;
        addUserPanel.add(addUserButton, gbc);
    
        addUserButton.addActionListener(e -> {
            ReaderRequestDTO readerRequestDTO = new ReaderRequestDTO();
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String address = addressField.getText().trim();
    
            // Kiểm tra các trường nhập liệu
            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin.");
                return;
            }
    
            readerRequestDTO.setName(name);
            readerRequestDTO.setEmail(email);
            readerRequestDTO.setPhone(phone);
            readerRequestDTO.setAddress(address);
    
            // Gửi HTTP POST request
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonRequest = objectMapper.writeValueAsString(readerRequestDTO);
                
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8081/api/readers"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                    .build();
    
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    
                if (response.statusCode() == 200) {

                    String responseFromSever = response.body();
                    if(responseFromSever.equals("Successfull")){
                        tableModel.addRow(new Object[]{
                            readerRequestDTO.getName(),
                            readerRequestDTO.getEmail(),
                            readerRequestDTO.getPhone(),    
                            readerRequestDTO.getAddress()
                        });
                    }
                    else{
                        JOptionPane.showMessageDialog(this,responseFromSever, "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                    nameField.setText("");
                    emailField.setText("");
                    phoneField.setText("");
                    addressField.setText("");
                } 
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Có lỗi xảy ra: " + ex.getMessage());
            }
        });
    
        // Nút in danh sách độc giả
        JButton printUsersButton = new JButton("In danh sách độc giả");
        printUsersButton.setBackground(new Color(0, 155, 155));
        printUsersButton.setForeground(Color.WHITE);
        gbc.gridx = 1; gbc.gridy = 5;
        addUserPanel.add(printUsersButton, gbc);
    
        printUsersButton.addActionListener(e -> {
            // Gửi HTTP GET request để lấy danh sách độc giả
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8081/api/readers")) // Địa chỉ API của bạn
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();
    
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    
                // Xử lý phản hồi
                if (response.statusCode() == 200) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    List<ReaderRequestDTO> readers = objectMapper.readValue(response.body(),
                            new TypeReference<List<ReaderRequestDTO>>() {});
    
                    // Xóa tất cả dữ liệu cũ trong table model
                    tableModel.setRowCount(0);
                    
                    // Thêm dữ liệu mới vào JTable
                    for (ReaderRequestDTO reader : readers) {
                        tableModel.addRow(new Object[]{
                            reader.getName(),
                            reader.getEmail(),
                            reader.getPhone(),
                            reader.getAddress()
                        });
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể lấy danh sách độc giả: " + response.body());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Có lỗi xảy ra: " + ex.getMessage());
            }
        });
    
        return addUserPanel;
    }
    
    //tim kiem sua lai model o ten email dien thoai
    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder("Tìm kiếm độc giả"));
    
        JTextField searchField = new JTextField();
        JButton searchButton = new JButton("Tìm kiếm");
        searchButton.setBackground(new Color(0, 155, 155));
        searchButton.setForeground(Color.WHITE);
    
        JTable searchTable = new JTable(tableModel);
        searchTable.setFillsViewportHeight(true);
        
        searchButton.addActionListener(e -> {
            String emailInput = searchField.getText().trim().toLowerCase();
            tableModel.setRowCount(0); // Xóa tất cả các hàng trong bảng
    
            // Kiểm tra nếu email đã nhập là rỗng
            if (emailInput.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập email để tìm kiếm.");
                return;
            }
    
            // Gửi yêu cầu HTTP GET để tìm kiếm độc giả theo email
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8081/api/readers/" + URLEncoder.encode(emailInput, "UTF-8"))) // Địa chỉ API tìm kiếm theo email
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();
    
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    
                // Xử lý phản hồi
                if (response.statusCode() == 200) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    ReaderRequestDTO reader = objectMapper.readValue(response.body(), ReaderRequestDTO.class); // Chuyển đổi phản hồi JSON thành đối tượng ReaderRequestDTO
    
                    if (reader == null) {
                        JOptionPane.showMessageDialog(this, "Không tìm thấy độc giả nào với email: " + emailInput);
                    } else {
                        // Thêm thông tin độc giả vào bảng
                        tableModel.addRow(new Object[]{
                            reader.getName(),
                            reader.getEmail(),
                            reader.getPhone(),
                            reader.getAddress()
                        });
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Tìm kiếm không thành công: " + response.body());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Có lỗi xảy ra: " + ex.getMessage());
            }
        });
    
        JPanel searchInputPanel = new JPanel(new BorderLayout());
        searchInputPanel.add(new JLabel("Nhập email để tìm kiếm:"), BorderLayout.WEST);
        searchInputPanel.add(searchField, BorderLayout.CENTER);
        searchInputPanel.add(searchButton, BorderLayout.EAST);
    
        searchPanel.add(searchInputPanel, BorderLayout.NORTH);
        searchPanel.add(new JScrollPane(searchTable), BorderLayout.CENTER); // Đưa JTable vào JScrollPane để có thể cuộn
    
        return searchPanel;
    }
    //xoa sua lai thay o xoa them name bang xoa theo email
    private JPanel createDeleteUserPanel() {
        JPanel deleteUserPanel = new JPanel(new GridBagLayout());
        deleteUserPanel.setBorder(BorderFactory.createTitledBorder("Xóa độc giả"));
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        JTextField deleteField = new JTextField();
        JButton deleteButton = new JButton("Xóa độc giả");
    
        gbc.gridx = 0; gbc.gridy = 0;
        deleteUserPanel.add(new JLabel("Nhập email độc giả để xóa:"), gbc);
        gbc.gridx = 1;
        deleteUserPanel.add(deleteField, gbc);
    
        gbc.gridx = 1; gbc.gridy = 1;
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);
        deleteUserPanel.add(deleteButton, gbc);
    
        deleteButton.addActionListener(e -> {
            String emailInput = deleteField.getText().trim();
            if (emailInput.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập email độc giả.");
                return;
            }
    
            try {
                // Gửi yêu cầu HTTP GET để tìm kiếm độc giả theo email
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8081/api/readers/" + deleteField.getText().trim())) 
                    .header("Content-Type", "application/json")
                    .DELETE()
                    .build();
    
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    
                if (response.statusCode() == 200) {
                    String responseFromSever = response.body();
                    if(responseFromSever.equals("Successfull")){
                        tableModel.setRowCount(0);
                    }
                    else{
                        JOptionPane.showMessageDialog(this,responseFromSever, "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
                    }   
                } 
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Có lỗi xảy ra: " + ex.getMessage());
            }
            deleteField.setText("");
        });
    
        return deleteUserPanel;
    }
    

}
