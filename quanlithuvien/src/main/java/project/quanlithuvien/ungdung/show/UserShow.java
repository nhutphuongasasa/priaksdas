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
import java.util.ArrayList;
import java.util.List;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import project.quanlithuvien.ungdung.DTO.*;
import project.quanlithuvien.ungdung.Models.UserModel;
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
        tableModel = new DefaultTableModel(new Object[]{"Name", "Age", "Email"}, 0);
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
    
        // Các trường nhập liệu
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField addressField = new JTextField();
    
        // Nhãn cho các trường nhập liệu
        gbc.gridx = 0; gbc.gridy = 0;
        editUserPanel.add(new JLabel("Tên:"), gbc);
        gbc.gridx = 1;
        editUserPanel.add(nameField, gbc);
    
        gbc.gridx = 0; gbc.gridy = 1;
        editUserPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        editUserPanel.add(emailField, gbc);
    
        gbc.gridx = 0; gbc.gridy = 2;
        editUserPanel.add(new JLabel("Điện thoại:"), gbc);
        gbc.gridx = 1;
        editUserPanel.add(phoneField, gbc);
    
        gbc.gridx = 0; gbc.gridy = 3;
        editUserPanel.add(new JLabel("Địa chỉ:"), gbc);
        gbc.gridx = 1;
        editUserPanel.add(addressField, gbc);
    
        // Nút tìm kiếm người dùng
        JButton searchButton = new JButton("Tìm kiếm");
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        editUserPanel.add(searchButton, gbc);
        
        // Nút chỉnh sửa thông tin người dùng
        JButton editUserButton = new JButton("Chỉnh sửa");
        editUserButton.setBackground(new Color(0, 155, 155));
        editUserButton.setForeground(Color.WHITE);
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 5;
        editUserPanel.add(editUserButton, gbc);
    
        // Thêm ActionListener cho nút tìm kiếm
        searchButton.addActionListener(e -> {
            String nameInput = nameField.getText().trim();
            if (nameInput.isEmpty()) {
                JOptionPane.showMessageDialog(editUserPanel, "Vui lòng nhập tên người dùng.");
                return;
            }
    
            // Gửi yêu cầu tìm kiếm đến API
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8081/api/readers/search?name=" + URLEncoder.encode(nameInput, "UTF-8")))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();
    
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    
                if (response.statusCode() == 200) {
                    // Chuyển đổi phản hồi JSON thành danh sách ReaderRequestDTO
                    ObjectMapper objectMapper = new ObjectMapper();
                    List<ReaderRequestDTO> readers = objectMapper.readValue(response.body(),
                            new TypeReference<List<ReaderRequestDTO>>() {});
    
                    if (readers.isEmpty()) {
                        JOptionPane.showMessageDialog(editUserPanel, "Không tìm thấy người dùng với tên: " + nameInput);
                    } else {
                        // Giả sử chỉ có một người dùng trùng tên, nếu có nhiều hơn cần xử lý
                        ReaderRequestDTO reader = readers.get(0);
                        // Điền thông tin vào các trường
                        emailField.setText(reader.getEmail());
                        phoneField.setText(reader.getPhone());
                        addressField.setText(reader.getAddress());
                    }
                } else {
                    JOptionPane.showMessageDialog(editUserPanel, "Lỗi khi tìm kiếm: " + response.body());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(editUserPanel, "Có lỗi xảy ra: " + ex.getMessage());
            }
        });
    
        // Thêm ActionListener cho nút chỉnh sửa
        editUserButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String address = addressField.getText().trim();
    
            // Kiểm tra các trường nhập liệu
            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(editUserPanel, "Vui lòng điền đầy đủ thông tin.");
                return;
            }
    
            // Tạo đối tượng ReaderRequestDTO để cập nhật
            ReaderRequestDTO readerRequestDTO = new ReaderRequestDTO();
            readerRequestDTO.setName(name);
            readerRequestDTO.setEmail(email);
            readerRequestDTO.setPhone(phone);
            readerRequestDTO.setAddress(address);
    
            // Gửi yêu cầu PUT để cập nhật thông tin người dùng
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonRequest = objectMapper.writeValueAsString(readerRequestDTO);
    
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8081/api/readers/update")) // Địa chỉ API của bạn
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonRequest))
                    .build();
    
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    
                if (response.statusCode() == 200) {
                    JOptionPane.showMessageDialog(editUserPanel, "Cập nhật thành công!");
                    // Xóa thông tin trong các trường nhập liệu
                    nameField.setText("");
                    emailField.setText("");
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
    
    //them user
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
                    tableModel.addRow(new Object[]{name, email, phone, address});
                    nameField.setText("");
                    emailField.setText("");
                    phoneField.setText("");
                    addressField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm độc giả không thành công: " + response.body());
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
    

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder("Tìm kiếm độc giả"));
    
        JTextField searchField = new JTextField();
        JTextArea searchResultArea = new JTextArea();
        searchResultArea.setEditable(false);
        JButton searchButton = new JButton("Tìm kiếm");
        searchButton.setBackground(new Color(0, 155, 155));
        searchButton.setForeground(Color.WHITE);
    
        searchButton.addActionListener(e -> {
            String searchText = searchField.getText().toLowerCase();
            searchResultArea.setText("");
    
            // Gửi yêu cầu HTTP GET để tìm kiếm độc giả theo tên
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8081/api/readers/search?name=" + URLEncoder.encode(searchText, "UTF-8"))) // Địa chỉ API tìm kiếm
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();
    
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    
                // Xử lý phản hồi
                if (response.statusCode() == 200) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    List<ReaderRequestDTO> readers = objectMapper.readValue(response.body(), new TypeReference<List<ReaderRequestDTO>>() {});
    
                    if (readers.isEmpty()) {
                        searchResultArea.setText("Không tìm thấy độc giả nào.");
                    } else {
                        // In ra danh sách độc giả tìm được
                        for (ReaderRequestDTO reader : readers) {
                            searchResultArea.append(reader.toString() + "\n");
                        }
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
        searchInputPanel.add(new JLabel("Nhập từ khóa tìm kiếm:"), BorderLayout.WEST);
        searchInputPanel.add(searchField, BorderLayout.CENTER);
        searchInputPanel.add(searchButton, BorderLayout.EAST);
    
        searchPanel.add(searchInputPanel, BorderLayout.NORTH);
        searchPanel.add(new JScrollPane(searchResultArea), BorderLayout.CENTER);
    
        return searchPanel;
    }
    

    private JPanel createDeleteUserPanel() {
        JPanel deleteUserPanel = new JPanel(new GridBagLayout());
        deleteUserPanel.setBorder(BorderFactory.createTitledBorder("Xóa độc giả"));
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        JTextField deleteField = new JTextField();
        JButton deleteButton = new JButton("Xóa độc giả");
    
        gbc.gridx = 0; gbc.gridy = 0;
        deleteUserPanel.add(new JLabel("Nhập tên độc giả:"), gbc);
        gbc.gridx = 1;
        deleteUserPanel.add(deleteField, gbc);
    
        gbc.gridx = 1; gbc.gridy = 1;
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);
        deleteUserPanel.add(deleteButton, gbc);
    
        deleteButton.addActionListener(e -> {
            String nameInput = deleteField.getText().trim();
            if (nameInput.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên độc giả.");
                return;
            }
    
            try {
                // Gửi yêu cầu HTTP GET để tìm kiếm độc giả theo tên
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8081/api/readers/search?name=" + URLEncoder.encode(nameInput, "UTF-8"))) // Địa chỉ API tìm kiếm
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();
    
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    
                if (response.statusCode() == 200) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    List<ReaderRequestDTO> readers = objectMapper.readValue(response.body(), new TypeReference<List<ReaderRequestDTO>>() {});
    
                    if (readers.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Không tìm thấy độc giả với tên: " + nameInput);
                    } else if (readers.size() == 1) {
                        // Nếu chỉ có một độc giả, thực hiện xóa ngay
                        confirmAndDeleteUser(readers.get(0));
                    } else {
                        // Nếu có nhiều độc giả, yêu cầu nhập địa chỉ
                        String addressInput = JOptionPane.showInputDialog(this, "Có nhiều độc giả trùng tên. Vui lòng nhập địa chỉ để xác định độc giả cần xóa:");
                        if (addressInput != null && !addressInput.trim().isEmpty()) {
                            for (ReaderRequestDTO reader : readers) {
                                if (reader.getAddress().equalsIgnoreCase(addressInput.trim())) {
                                    confirmAndDeleteUser(reader);
                                    return; // Thoát sau khi tìm thấy và xóa
                                }
                            }
                            JOptionPane.showMessageDialog(this, "Không tìm thấy độc giả với địa chỉ: " + addressInput);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm độc giả: " + response.body());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Có lỗi xảy ra: " + ex.getMessage());
            }
            deleteField.setText("");
        });
    
        return deleteUserPanel;
    }
    
    // Phương thức để xác nhận và xóa độc giả
    private void confirmAndDeleteUser(ReaderRequestDTO reader) {
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa độc giả " + reader.getName() + "?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Gửi yêu cầu HTTP DELETE để xóa độc giả
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8081/api/readers/" + reader.getName()))
                    .header("Content-Type", "application/json")
                    .DELETE()
                    .build();
    
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    
                if (response.statusCode() == 200) {
                    // Cập nhật lại JTable nếu xóa thành công
                    JOptionPane.showMessageDialog(this, "Đã xóa độc giả thành công!");
                    // Cập nhật lại dữ liệu trong bảng ở đây nếu cần
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi khi xóa độc giả: " + response.body());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Có lỗi xảy ra: " + ex.getMessage());
            }
        }
    }
    
}
