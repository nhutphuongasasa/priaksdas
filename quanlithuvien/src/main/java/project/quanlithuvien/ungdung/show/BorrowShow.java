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
import java.util.ArrayList;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import project.quanlithuvien.ungdung.DTO.LoanDTO;
import project.quanlithuvien.ungdung.DTO.LoanRequestDTO;
import project.quanlithuvien.ungdung.Models.BorrowModel;

public class BorrowShow extends JFrame {
    private List<BorrowModel> borrowRecords = new ArrayList<>();
    private JTable table;
    private DefaultTableModel tableModel;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public BorrowShow() {
        this.init();
    }

    private void init() {
        this.setTitle("Quản lý mượn sách");
        this.setSize(1200, 600);
        this.setLocationRelativeTo(null);

        // Card layout cho các panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Bảng hiển thị sách mượn
        tableModel = new DefaultTableModel(new Object[]{"Id","Name","Phone","Email","Title","Author","ISBN","Status","Loan_date","Return_date","Quantity"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(400, 600));

        // Panel mượn sách
        JPanel borrowBookPanel = createBorrowBookPanel();

        // Panel trả sách
        JPanel returnBookPanel = createReturnBookPanel();

        // Panel tìm kiếm sách
        JPanel searchPanel = createSearchBookPanel();

        // Thêm các panel vào mainPanel (CardLayout)
        mainPanel.add(borrowBookPanel, "Borrow Book");
        mainPanel.add(returnBookPanel, "Return Book");
        mainPanel.add(searchPanel, "Search Book");

        // Thanh nút điều hướng
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(0, 153, 153));

        JButton borrowBookTabButton = new JButton("Mượn sách");
        JButton returnBookTabButton = new JButton("Trả sách");
        JButton searchTabButton = new JButton("Tìm kiếm");

        borrowBookTabButton.addActionListener(e -> cardLayout.show(mainPanel, "Borrow Book"));
        returnBookTabButton.addActionListener(e -> cardLayout.show(mainPanel, "Return Book"));
        searchTabButton.addActionListener(e -> cardLayout.show(mainPanel, "Search Book"));

        buttonPanel.add(borrowBookTabButton);
        buttonPanel.add(returnBookTabButton);
        buttonPanel.add(searchTabButton);

        // Tạo SplitPane để phân chia không gian giữa bảng và các chức năng
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, mainPanel);
        splitPane.setDividerLocation(400);
        splitPane.setResizeWeight(0.5);

        // Thêm các thành phần vào JFrame
        this.add(buttonPanel, BorderLayout.NORTH);
        this.add(splitPane, BorderLayout.CENTER);
        this.setVisible(true);
    }
    //tạo mượn sách(xong)
    private JPanel createBorrowBookPanel() {
    JPanel borrowBookPanel = new JPanel(new GridBagLayout());
    borrowBookPanel.setBorder(BorderFactory.createTitledBorder("Mượn sách"));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // Các trường nhập liệu
    JTextField nameField = new JTextField();
    JTextField phoneField = new JTextField();
    JTextField emailField = new JTextField();
    JTextField titleField = new JTextField();
    JTextField authorField = new JTextField();
    JTextField isbnField = new JTextField();
    JTextField quantityField = new JTextField();

    // Thiết lập kích thước cho các trường nhập liệu
    nameField.setPreferredSize(new Dimension(200, 30));
    phoneField.setPreferredSize(new Dimension(200, 30));
    emailField.setPreferredSize(new Dimension(200, 30));
    titleField.setPreferredSize(new Dimension(200, 30));
    authorField.setPreferredSize(new Dimension(200, 30));
    isbnField.setPreferredSize(new Dimension(200, 30));
    quantityField.setPreferredSize(new Dimension(200, 30));

    // Thêm các nhãn và trường nhập liệu vào giao diện
    gbc.gridx = 0; gbc.gridy = 0;
    borrowBookPanel.add(new JLabel("Tên người mượn:"), gbc);
    gbc.gridx = 1;
    borrowBookPanel.add(nameField, gbc);

    gbc.gridx = 0; gbc.gridy = 1;
    borrowBookPanel.add(new JLabel("Số điện thoại:"), gbc);
    gbc.gridx = 1;
    borrowBookPanel.add(phoneField, gbc);

    gbc.gridx = 0; gbc.gridy = 2;
    borrowBookPanel.add(new JLabel("Email:"), gbc);
    gbc.gridx = 1;
    borrowBookPanel.add(emailField, gbc);

    gbc.gridx = 0; gbc.gridy = 3;
    borrowBookPanel.add(new JLabel("Tiêu đề sách:"), gbc);
    gbc.gridx = 1;
    borrowBookPanel.add(titleField, gbc);

    gbc.gridx = 0; gbc.gridy = 4;
    borrowBookPanel.add(new JLabel("Tác giả:"), gbc);
    gbc.gridx = 1;
    borrowBookPanel.add(authorField, gbc);

    gbc.gridx = 0; gbc.gridy = 5;
    borrowBookPanel.add(new JLabel("ISBN:"), gbc);
    gbc.gridx = 1;
    borrowBookPanel.add(isbnField, gbc);

    gbc.gridx = 0; gbc.gridy = 6;
    borrowBookPanel.add(new JLabel("Số lượng mượn:"), gbc);
    gbc.gridx = 1;
    borrowBookPanel.add(quantityField, gbc);

    // Nút mượn sách
    JButton borrowBookButton = new JButton("Mượn sách");
    borrowBookButton.setBackground(new Color(0, 155, 155));
    borrowBookButton.setForeground(Color.WHITE);
    gbc.gridx = 0; gbc.gridy = 7;
    gbc.gridwidth = 2;
    borrowBookPanel.add(borrowBookButton, gbc);

    // Nút in danh sách
    JButton printButton = new JButton("In danh sách");
    gbc.gridx = 0; gbc.gridy = 8;
    gbc.gridwidth = 2;
    borrowBookPanel.add(printButton, gbc);

    // Xử lý sự kiện cho nút mượn sách
    borrowBookButton.addActionListener(e -> {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String isbn = isbnField.getText().trim();
        String quantityText = quantityField.getText().trim();

        // Kiểm tra thông tin đầu vào
        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || 
            title.isEmpty() || author.isEmpty() || isbn.isEmpty() || 
            quantityText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.");
            return;
        }

        // Tạo đối tượng LoanRequestDTO
        LoanRequestDTO loanRequestDTO = new LoanRequestDTO();
        loanRequestDTO.setName(name);
        loanRequestDTO.setPhone(phone);
        loanRequestDTO.setEmail(email);
        loanRequestDTO.setTitle(title);
        loanRequestDTO.setAuthor(author);
        loanRequestDTO.setIsbn(isbn);
        loanRequestDTO.setQuantity(Integer.parseInt(quantityText));

        //  mượn sách
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonRequest = objectMapper.writeValueAsString(loanRequestDTO);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/api/loans"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String responseFromServer = response.body();
                JOptionPane.showMessageDialog(this, responseFromServer);
                // Xóa các trường nhập liệu
                nameField.setText("");
                phoneField.setText("");
                emailField.setText("");
                titleField.setText("");
                authorField.setText("");
                isbnField.setText("");
                quantityField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Mượn sách không thành công: " + response.body());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra: " + ex.getMessage());
        }
    });

    // in danh sách
    printButton.addActionListener(e -> {
        sendLoanRequest(null,null,null,null,null,null,null);
    });

    return borrowBookPanel;
}
    //tra sach(xong)
    private JPanel createReturnBookPanel() {
    JPanel returnBookPanel = new JPanel(new GridBagLayout());
    returnBookPanel.setBorder(BorderFactory.createTitledBorder("Trả sách"));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // Tạo JTextField mới để nhập ID sách
    JTextField loanIdField = new JTextField();
    loanIdField.setPreferredSize(new Dimension(400, 30));

    gbc.gridx = 0; gbc.gridy = 0;
    returnBookPanel.add(new JLabel("Nhập ID sách để trả sách:"), gbc);
    gbc.gridx = 1;
    returnBookPanel.add(loanIdField, gbc);

    JButton returnBookButton = new JButton("Trả sách");
    returnBookButton.setBackground(new Color(0, 155, 155));
    returnBookButton.setForeground(Color.WHITE);
    gbc.gridx = 1; gbc.gridy = 1;
    returnBookPanel.add(returnBookButton, gbc);
    
    // Xử lý sự kiện cho nút "Trả sách"
    returnBookButton.addActionListener(e -> {
        String loanId = loanIdField.getText().trim();

        if (loanId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ID cần trả mượn sách.");
        } 

        try {
            HttpClient client = HttpClient.newHttpClient();
        
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/api/loans/" + loanId))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();
        
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
            if (response.statusCode() == 200) {
                JOptionPane.showMessageDialog(this, response.body());
                tableModel.setRowCount(0);
            } else {
                JOptionPane.showMessageDialog(this, response.body());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi cập nhật sách.");
        }
    

    });

    return returnBookPanel;
}

    //tim trang thai mươn/ tra sách
    private JPanel createSearchBookPanel() {
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder("Tìm kiếm sách"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Các trường tìm kiếm
        JTextField nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(200, 30));

        JTextField phoneField = new JTextField();
        phoneField.setPreferredSize(new Dimension(200, 30));

        JTextField emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(200, 30));

        JTextField titleField = new JTextField();
        titleField.setPreferredSize(new Dimension(200, 30));

        JTextField authorField = new JTextField();
        authorField.setPreferredSize(new Dimension(200, 30));

        JTextField isbnField = new JTextField();
        isbnField.setPreferredSize(new Dimension(200, 30));

        JTextField quantityField = new JTextField();
        quantityField.setPreferredSize(new Dimension(200, 30));

        // Thêm các trường vào panel
        gbc.gridx = 0; gbc.gridy = 0;
        searchPanel.add(new JLabel("Họ tên:"), gbc);
        gbc.gridx = 1;
        searchPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        searchPanel.add(new JLabel("Số điện thoại:"), gbc);
        gbc.gridx = 1;
        searchPanel.add(phoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        searchPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        searchPanel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        searchPanel.add(new JLabel("Tiêu đề sách:"), gbc);
        gbc.gridx = 1;
        searchPanel.add(titleField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        searchPanel.add(new JLabel("Tác giả:"), gbc);
        gbc.gridx = 1;
        searchPanel.add(authorField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        searchPanel.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1;
        searchPanel.add(isbnField, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        searchPanel.add(new JLabel("Số lượng:"), gbc);
        gbc.gridx = 1;
        searchPanel.add(quantityField, gbc);

        JButton searchButton = new JButton("Tìm kiếm sách");
        searchButton.setBackground(new Color(0, 155, 155));
        searchButton.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 7; 
        gbc.gridwidth = 2; // Chiếm toàn bộ chiều rộng
        searchPanel.add(searchButton, gbc);

        searchButton.addActionListener(e -> {
            sendLoanRequest(nameField.getText().trim(),phoneField.getText().trim(),emailField.getText().trim(),titleField.getText().trim(),authorField.getText().trim(),isbnField.getText().trim(),quantityField.getText().trim());

            // Xóa các trường sau khi tìm kiếm
            nameField.setText("");
            phoneField.setText("");
            emailField.setText("");
            titleField.setText("");
            authorField.setText("");
            isbnField.setText("");
            quantityField.setText("");
        });

        return searchPanel;
    }

    //uri
    public void sendLoanRequest(String name, String phone, String email, String title, String author, String isbn, String quantity) {
    try {
        HttpClient client = HttpClient.newHttpClient();
        // Tạo URI với các tham số
        StringBuilder uriBuilder = new StringBuilder("http://localhost:8081/api/loans?");
        
        if (name != null && !name.isEmpty()) {
            uriBuilder.append("name=").append(URLEncoder.encode(name, StandardCharsets.UTF_8)).append("&");
        }
        if (phone != null && !phone.isEmpty()) {
            uriBuilder.append("phone=").append(URLEncoder.encode(phone, StandardCharsets.UTF_8)).append("&");
        }
        if (email != null && !email.isEmpty()) {
            uriBuilder.append("email=").append(URLEncoder.encode(email, StandardCharsets.UTF_8)).append("&");
        }
        if (title != null && !title.isEmpty()) {
            uriBuilder.append("title=").append(URLEncoder.encode(title, StandardCharsets.UTF_8)).append("&");
        }
        if (author != null && !author.isEmpty()) {
            uriBuilder.append("author=").append(URLEncoder.encode(author, StandardCharsets.UTF_8)).append("&");
        }
        if (isbn != null && !isbn.isEmpty()) {
            uriBuilder.append("isbn=").append(URLEncoder.encode(isbn, StandardCharsets.UTF_8)).append("&");
        }
        if (quantity != null) {
            uriBuilder.append("quantity=").append(quantity);
        }

        // Xóa dấu '&' cuối cùng nếu có
        String uri = uriBuilder.toString();
        if (uri.endsWith("&")) {
            uri = uri.substring(0, uri.length() - 1);
        }

        // Tạo yêu cầu HTTP
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(uri))
            .header("Content-Type", "application/json")
            .GET()
            .build();

        // Gửi yêu cầu và nhận phản hồi
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() == 200) {
           ObjectMapper objectMapper = new ObjectMapper();
            List<LoanDTO> loans = objectMapper.readValue(response.body(), objectMapper.getTypeFactory().constructCollectionType(List.class, LoanDTO.class));
            if(loans!=null){
                tableModel.setRowCount(0);
                for (LoanDTO loan : loans){
                    tableModel.addRow(new Object[]{
                        loan.getLoan_id(),
                        loan.getName(),
                        loan.getPhone(),
                        loan.getEmail(),
                        loan.getTitle(),
                        loan.getAuthor(),
                        loan.getIsbn(),
                        loan.getStatus(),
                        loan.getLoan_date(),
                        loan.getReturn_date(),
                        loan.getQuantity()
                    });
                } 
            }else{
                JOptionPane.showMessageDialog(this, "Có lỗi xảy ra "+response.body());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra "+response.body());
        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }
}
    
}
