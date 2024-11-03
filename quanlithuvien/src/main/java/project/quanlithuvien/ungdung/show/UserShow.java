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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import project.quanlithuvien.ungdung.DTO.BookDTO;
import project.quanlithuvien.ungdung.DTO.ReaderDTO;
import project.quanlithuvien.ungdung.DTO.ReaderRequestDTO;

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
        this.setSize(1000, 600);
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
    //chinh sua user(xong)
    private JPanel createEditUserPanel() {
        JPanel editUserPanel = new JPanel(new GridBagLayout());
        editUserPanel.setBorder(BorderFactory.createTitledBorder("Chỉnh sửa độc giả"));
    
        
        JTextField nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(400, 30));
        JTextField emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(400, 30));
        JTextField newEmailField = new JTextField();
        newEmailField.setPreferredSize(new Dimension(400, 30));
        JTextField phoneField = new JTextField();
        phoneField.setPreferredSize(new Dimension(400, 30));
        JTextField addressField = new JTextField();
        addressField.setPreferredSize(new Dimension(400, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        gbc.gridx = 0; gbc.gridy = 0;
        editUserPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        editUserPanel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        editUserPanel.add(new JLabel("Email mới:"), gbc);
        gbc.gridx = 1;
        editUserPanel.add(newEmailField, gbc);
        newEmailField.setEditable(false);
    
        gbc.gridx = 0; gbc.gridy = 2;
        editUserPanel.add(new JLabel("Tên:"), gbc);
        gbc.gridx = 1;
        editUserPanel.add(nameField, gbc);
        nameField.setEditable(false); // Ban đầu không cho chỉnh sửa
    
        gbc.gridx = 0; gbc.gridy = 3;
        editUserPanel.add(new JLabel("Điện thoại:"), gbc);
        gbc.gridx = 1;
        editUserPanel.add(phoneField, gbc);
        phoneField.setEditable(false); // Ban đầu không cho chỉnh sửa
    
        gbc.gridx = 0; gbc.gridy = 4;
        editUserPanel.add(new JLabel("Địa chỉ:"), gbc);
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
        gbc.gridx = 0; gbc.gridy = 6;
        editUserPanel.add(editUserButton, gbc);
        editUserButton.setEnabled(false); // Ban đầu tắt nút chỉnh sửa
    
        // Sự kiện cho nút tìm kiếm
        searchButton.addActionListener(e -> {
            String emailInput = emailField.getText().trim();
            if (emailInput.isEmpty()) {
                JOptionPane.showMessageDialog(editUserPanel, "Vui lòng nhập email.");
                return;
            }
    
            // Gửi yêu cầu tìm kiếm đến API
            try {
                String uri = sendUserSearchRequest(null,emailInput,null,null);
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();
    
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    
                if (response.statusCode() == 200) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    List<ReaderDTO> readers = objectMapper.readValue(response.body(), objectMapper.getTypeFactory().constructCollectionType(List.class, ReaderDTO.class));
                    if(readers!=null){
                        ReaderDTO reader = readers.get(0);
                        nameField.setEditable(true);
                        phoneField.setEditable(true);
                        addressField.setEditable(true);
                        newEmailField.setEditable(true);
                        editUserButton.setEnabled(true);

                        // Điền thông tin vào các trường
                        nameField.setText(reader.getName());
                        phoneField.setText(reader.getPhone());
                        newEmailField.setText(reader.getEmail());
                        addressField.setText(reader.getAddress());
                    }
                    else{
                        JOptionPane.showMessageDialog(editUserPanel, "không có thông tin " );
                    }
                    
                } 
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(editUserPanel, "Có lỗi xảy ra: " + ex.getMessage());
            }
        });
    
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
                    .uri(URI.create("http://localhost:8081/api/readers/"+emailField.getText().trim()))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonRequest))
                    .build();
    
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    
                if (response.statusCode() == 200&&response.body().equals("Successfull")) {
                    JOptionPane.showMessageDialog(editUserPanel, response.body());
                    nameField.setText("");
                    newEmailField.setText("");
                    phoneField.setText("");
                    addressField.setText("");
                } else {
                    JOptionPane.showMessageDialog(editUserPanel,  response.body());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(editUserPanel, "Có lỗi xảy ra: " + ex.getMessage());
            }
        });
    
        return editUserPanel;
    }
    
    //them user(xong)
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
        //them doc gia
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
                    LocalDate date=LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String responseFromSever = response.body();
                    if(responseFromSever.equals("Successfull")){
                        tableModel.addRow(new Object[]{
                            readerRequestDTO.getName(),
                            readerRequestDTO.getEmail(),
                            readerRequestDTO.getPhone(),    
                            readerRequestDTO.getAddress(),
                            date.format(formatter),
                            "Active"
                        });
                        JOptionPane.showMessageDialog(this,responseFromSever, "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
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
                String uri = sendUserSearchRequest(null,null,null,null);
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri)) // Địa chỉ API của bạn
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();
    
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    
                // Xử lý phản hồi
                if (response.statusCode() == 200) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    List<ReaderDTO> readers = objectMapper.readValue(response.body(),new TypeReference<List<ReaderDTO>>() {});
    
                    // Xóa tất cả dữ liệu cũ trong table model
                    tableModel.setRowCount(0);
                    
                    // Thêm dữ liệu mới vào JTable
                    for (ReaderDTO reader : readers) {
                        tableModel.addRow(new Object[]{
                            reader.getName(),
                            reader.getEmail(),
                            reader.getPhone(),
                            reader.getAddress(),
                            reader.getRegistration_date(),
                            reader.getStatus()
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
    
    //tim kiem (xong)
    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder("Tìm kiếm độc giả"));

        // Các trường thông tin độc giả
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField addressField = new JTextField();

        // Đặt kích thước cho các trường thông tin
        nameField.setPreferredSize(new Dimension(200, 30));
        emailField.setPreferredSize(new Dimension(200, 30));
        phoneField.setPreferredSize(new Dimension(200, 30));
        addressField.setPreferredSize(new Dimension(200, 30));

        // Panel chứa các trường thông tin độc giả
        JPanel readerInfoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Các trường thông tin độc giả
        gbc.gridx = 0; gbc.gridy = 0;
        readerInfoPanel.add(new JLabel("Tên:"), gbc);
        gbc.gridx = 1;
        readerInfoPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        readerInfoPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        readerInfoPanel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        readerInfoPanel.add(new JLabel("Điện thoại:"), gbc);
        gbc.gridx = 1;
        readerInfoPanel.add(phoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        readerInfoPanel.add(new JLabel("Địa chỉ:"), gbc);
        gbc.gridx = 1;
        readerInfoPanel.add(addressField, gbc);

        // Thêm readerInfoPanel vào searchPanel
        searchPanel.add(new JScrollPane(readerInfoPanel), BorderLayout.CENTER); // Đưa readerInfoPanel vào JScrollPane để có thể cuộn

        // Nút tìm kiếm
        JButton searchButton = new JButton("Tìm kiếm độc giả");
        searchButton.setBackground(new Color(0, 155, 155));
        searchButton.setForeground(Color.WHITE);

        // Thêm ActionListener cho nút tìm kiếm
        searchButton.addActionListener(e -> {

            try {
                String uri = sendUserSearchRequest(nameField.getText().trim(),emailField.getText().trim(),phoneField.getText().trim(),addressField.getText().trim());
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                // Xử lý phản hồi
                if (response.statusCode() == 200) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    List<ReaderDTO> readers = objectMapper.readValue(response.body(),new TypeReference<List<ReaderDTO>>() {});

                    if (readers == null) {
                        JOptionPane.showMessageDialog(searchPanel, "Không tìm thấy độc giả");
                    } else {
                        tableModel.setRowCount(0);
                    
                        // Thêm dữ liệu mới vào JTable
                        for (ReaderDTO reader : readers) {
                            tableModel.addRow(new Object[]{
                                reader.getName(),
                                reader.getEmail(),
                                reader.getPhone(),
                                reader.getAddress(),
                                reader.getRegistration_date(),
                                reader.getStatus()
                            });
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(searchPanel, "Tìm kiếm không thành công: " + response.body());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(searchPanel, "Có lỗi xảy ra: " + ex.getMessage());
            }
        });

        // Thêm nút tìm kiếm vào searchPanel, chiếm toàn bộ chiều rộng
        searchPanel.add(searchButton, BorderLayout.SOUTH);

        return searchPanel;
    }
    //xoa sua (xong)
    private JPanel createDeleteUserPanel() {
        JPanel deleteUserPanel = new JPanel(new GridBagLayout());
        deleteUserPanel.setBorder(BorderFactory.createTitledBorder("Xóa độc giả"));
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        JTextField deleteField = new JTextField();
        deleteField.setPreferredSize(new Dimension(200, 30));
        
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
                        JOptionPane.showMessageDialog(this,responseFromSever, "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
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
    //gui ve (xong)
    public String sendUserSearchRequest(String name,String email,String phone,String address){
    StringBuilder uriBuilder = new StringBuilder("http://localhost:8081/api/readers?");
    if (name != null && !name.isEmpty()) {
        uriBuilder.append("name=").append(URLEncoder.encode(name, StandardCharsets.UTF_8)).append("&");
    }
    if (email != null && !email.isEmpty()) {
        uriBuilder.append("email=").append(URLEncoder.encode(email, StandardCharsets.UTF_8)).append("&");
    }
    if (phone != null && !phone.isEmpty()) {
        uriBuilder.append("phone=").append(URLEncoder.encode(phone, StandardCharsets.UTF_8)).append("&");
    }
    if (address != null && !address.isEmpty()) {
        uriBuilder.append("address=").append(URLEncoder.encode(address, StandardCharsets.UTF_8)).append("&");
    }
    
    if (uriBuilder.charAt(uriBuilder.length() - 1) == '&') {
        uriBuilder.deleteCharAt(uriBuilder.length() - 1);
    }
    System.out.println(uriBuilder.toString());
    return uriBuilder.toString();
}

}
