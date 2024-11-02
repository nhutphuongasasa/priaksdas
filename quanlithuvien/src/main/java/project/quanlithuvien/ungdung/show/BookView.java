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

import project.quanlithuvien.ungdung.DTO.BookDTO;
import project.quanlithuvien.ungdung.DTO.BookRequestDTO;

public class BookView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public BookView(){
        this.init();
    }

    private void init(){
        this.setTitle("Quản lý sách");
        this.setSize(1200, 600);
        this.setLocationRelativeTo(null);

        // Card layout cho các panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Bảng hiển thị sách
        tableModel = new DefaultTableModel(new Object[]{
            "Title", "Author", "Publisher", "Publication Year", 
            "ISBN", "Category", "Quantity", "Available Quantity"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.setPreferredSize(new Dimension(400, 600));

        // Panel thêm sách
        JPanel addBookPanel = createAddBookPanel();

        // Panel tìm kiếm sách
        JPanel searchPanel = createSearchPanel();

        // Panel xóa sách
        JPanel deleteBookPanel = createDeleteBookPanel();
        //chinh sua sach
        JPanel editBookPanel = createEditBookPanel();
        // Thêm các panel vào mainPanel (CardLayout)
        mainPanel.add(addBookPanel, "Add Book");
        mainPanel.add(searchPanel, "Search");
        mainPanel.add(deleteBookPanel, "Delete Book");
        mainPanel.add(editBookPanel, "Edit Book");
        
        // Thanh nút điều hướng
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(0, 153, 153));
        
        JButton addBookTabButton = new JButton("Thêm sách");
        JButton searchTabButton = new JButton("Tìm kiếm sách");
        JButton deleteBookTabButton = new JButton("Xóa sách"); 
        JButton editBookTabButton = new JButton("Chỉnh sửa sách");
        
        

        // Chuyển đổi giao diện mỗi khi nhấn vào nút
        addBookTabButton.addActionListener(e -> cardLayout.show(mainPanel, "Add Book"));
        searchTabButton.addActionListener(e -> cardLayout.show(mainPanel, "Search"));
        deleteBookTabButton.addActionListener(e -> cardLayout.show(mainPanel, "Delete Book"));
        editBookTabButton.addActionListener(e -> cardLayout.show(mainPanel, "Edit Book"));

        buttonPanel.add(addBookTabButton);
        buttonPanel.add(searchTabButton);
        buttonPanel.add(deleteBookTabButton);
        buttonPanel.add(editBookTabButton);


        // Tạo SplitPane để phân chia không gian giữa bảng và các chức năng
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, mainPanel);
        splitPane.setDividerLocation(400);
        splitPane.setResizeWeight(0.5);

        // Thêm các thành phần vào JFrame
        this.add(buttonPanel, BorderLayout.NORTH);
        this.add(splitPane, BorderLayout.CENTER);
        this.setVisible(true);
    }
    //them sach(xong)
    private JPanel createAddBookPanel() {
        JPanel addBookPanel = new JPanel(new GridBagLayout());
        addBookPanel.setBorder(BorderFactory.createTitledBorder("Thêm sách mới"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Các trường nhập liệu
        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField publisherField = new JTextField();
        JTextField publicationYearField = new JTextField();
        JTextField isbnField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField quantityField = new JTextField();
        JTextField availableQuantityField = new JTextField();

        // Thêm các nhãn và trường nhập liệu vào giao diện
        gbc.gridx = 0; gbc.gridy = 0;
        addBookPanel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        addBookPanel.add(titleField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        addBookPanel.add(new JLabel("Author:"), gbc);
        gbc.gridx = 1;
        addBookPanel.add(authorField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        addBookPanel.add(new JLabel("Publisher:"), gbc);
        gbc.gridx = 1;
        addBookPanel.add(publisherField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        addBookPanel.add(new JLabel("Publication Year:"), gbc);
        gbc.gridx = 1;
        addBookPanel.add(publicationYearField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        addBookPanel.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1;
        addBookPanel.add(isbnField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        addBookPanel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        addBookPanel.add(categoryField, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        addBookPanel.add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 1;
        addBookPanel.add(quantityField, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        addBookPanel.add(new JLabel("Available Quantity:"), gbc);
        gbc.gridx = 1;
        addBookPanel.add(availableQuantityField, gbc);

        // Nút thêm sách
        JButton addBookButton = new JButton("Thêm sách");
        addBookButton.setBackground(new Color(0, 155, 155));
        addBookButton.setForeground(Color.WHITE);
        gbc.gridx = 1; gbc.gridy = 8;
        addBookPanel.add(addBookButton, gbc);

        // Nút in toàn bộ thông tin sách
        JButton printAllBooksButton = new JButton("In toàn bộ sách");
        gbc.gridx = 1; gbc.gridy = 9;
        addBookPanel.add(printAllBooksButton, gbc);


        addBookButton.addActionListener(e -> {
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            String publisher = publisherField.getText().trim();
            String publicationYearText = publicationYearField.getText().trim();
            String isbn = isbnField.getText().trim();
            String categoryText = categoryField.getText().trim();
            String quantityText = quantityField.getText().trim();
            String availableQuantityText = availableQuantityField.getText().trim();

            // Kiểm tra sự hợp lệ của thông tin nhập vào
            if (title.isEmpty() || author.isEmpty() || publisher.isEmpty() || 
                publicationYearText.isEmpty() || isbn.isEmpty() || 
                categoryText.isEmpty() || quantityText.isEmpty() || 
                availableQuantityText.isEmpty()) {
                
                JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin.");
                return; // Dừng thực hiện nếu có trường không hợp lệ
            }

            BookRequestDTO bookRequestDTO = new BookRequestDTO();
            bookRequestDTO.setTitle(titleField.getText());
            bookRequestDTO.setAuthor(authorField.getText());
            bookRequestDTO.setPublisher(publisherField.getText());
            bookRequestDTO.setPublication_year(Integer.parseInt(publicationYearField.getText()));
            bookRequestDTO.setIsbn(isbnField.getText());
            bookRequestDTO.setCategory(List.of(categoryField.getText().split("/+")));
            bookRequestDTO.setQuantity(Integer.parseInt(quantityField.getText()));
            bookRequestDTO.setAvailable_quantity(Integer.parseInt(availableQuantityField.getText()));
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonRequest = objectMapper.writeValueAsString(bookRequestDTO);

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8081/api/books"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                    .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    String responseFromSever =response.body();
                    if(responseFromSever.equals("Successfull")){
                        tableModel.addRow(new Object[]{
                            bookRequestDTO.getTitle(),
                            bookRequestDTO.getAuthor(),
                            bookRequestDTO.getPublisher(),
                            bookRequestDTO.getPublication_year(),
                            bookRequestDTO.getIsbn(),
                            bookRequestDTO.getCategory(),
                            bookRequestDTO.getQuantity(),
                            bookRequestDTO.getAvailable_quantity()
                        });
                    }
                    else{
                        JOptionPane.showMessageDialog(addBookPanel,responseFromSever, "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                    titleField.setText("");
                    authorField.setText("");
                    publisherField.setText("");
                    publicationYearField.setText("");
                    isbnField.setText("");
                    categoryField.setText("");
                    quantityField.setText("");
                    availableQuantityField.setText("");
                    
                } else {
                    System.out.println("Failed to add book: " + response.body());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Sự kiện khi nhấn nút "In toàn bộ sách"
        printAllBooksButton.addActionListener(e -> {
            sendBookSearchRequest(null,null, null, null, null, null, null, null);
        });

        return addBookPanel;
    }
    //ham gui yeu cau in toan bo sach (xong)
    public void sendBookSearchRequest(String title, String author, String publisher, Integer publication_year, String isbn, List<String> categories, Integer quantity, Integer available_quantity) {
    try {
        HttpClient client = HttpClient.newHttpClient();
        // Tạo URI với các tham số
        StringBuilder uriBuilder = new StringBuilder("http://localhost:8081/api/books?categories=&");
        
        if (title != null && !title.isEmpty()) {
            uriBuilder.append("title=").append(URLEncoder.encode(title, StandardCharsets.UTF_8)).append("&");
        }
        if (author != null && !author.isEmpty()) {
            uriBuilder.append("author=").append(URLEncoder.encode(author, StandardCharsets.UTF_8)).append("&");
        }
        if (publisher != null && !publisher.isEmpty()) {
            uriBuilder.append("publisher=").append(URLEncoder.encode(publisher, StandardCharsets.UTF_8)).append("&");
        }
        if (publication_year != null) {
            uriBuilder.append("publication_year=").append(publication_year).append("&");
        }
        if (isbn != null && !isbn.isEmpty()) {
            uriBuilder.append("isbn=").append(URLEncoder.encode(isbn, StandardCharsets.UTF_8)).append("&");
        }
        if (categories != null && !categories.isEmpty()) {
            String categoriesParam = String.join("/", categories);
            uriBuilder.append("categories=").append(URLEncoder.encode(categoriesParam, StandardCharsets.UTF_8)).append("&");
        }
        if (quantity != null) {
            uriBuilder.append("quantity=").append(quantity).append("&");
        }
        if (available_quantity != null) {
            uriBuilder.append("available_quantity=").append(available_quantity);
        }

        // Xóa dấu '&' cuối cùng nếu có
        String uri = uriBuilder.toString();
        if (uri.endsWith("&")) {
            uri = uri.substring(0, uri.length() - 1);
        }


        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(uri))
            .header("Content-Type", "application/json")
            .GET()
            .build();
        // Gửi yêu cầu và nhận phản hồi
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            ObjectMapper objectMapper = new ObjectMapper();
            List<BookDTO> books = objectMapper.readValue(response.body(), objectMapper.getTypeFactory().constructCollectionType(List.class, BookDTO.class));
            tableModel.setRowCount(0);
            for (BookDTO book : books){
                tableModel.addRow(new Object[]{
                    book.getTitle(),
                    book.getAuthor(),
                    book.getPublisher(),
                    book.getPublication_year(),
                    book.getIsbn(),
                    book.getCategory(),
                    book.getQuantity(),
                    book.getAvailable_quantity()
                });
            } 
            
        } else {
            System.out.println("Failed to retrieve books: " + response.body());
        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }
}

    //tim kiem(xong)
    private JPanel createSearchPanel() {

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder("Tìm kiếm sách"));
    
        // JTextField searchField = new JTextField();
        // searchField.setPreferredSize(new Dimension(200, 30));
        JButton searchButton = new JButton("Tìm kiếm sách");
        searchButton.setBackground(new Color(0, 155, 155));
        searchButton.setForeground(Color.WHITE);
    
        JPanel searchInputPanel = new JPanel(new BorderLayout());
        // searchInputPanel.add(searchField);
        searchInputPanel.add(searchButton);
        // Các trường thông tin sách
        JTextField isbnField = new JTextField();
        isbnField.setPreferredSize(new Dimension(200, 30));
        JTextField titleField = new JTextField();
        titleField.setPreferredSize(new Dimension(200, 30));
        JTextField authorField = new JTextField();
        authorField.setPreferredSize(new Dimension(200, 30));
        JTextField publisherField = new JTextField();
        publisherField.setPreferredSize(new Dimension(200, 30));
        JTextField publicationYearField = new JTextField();
        publicationYearField.setPreferredSize(new Dimension(200, 30));
        JTextField categoryField = new JTextField();
        categoryField.setPreferredSize(new Dimension(200, 30));
        JTextField quantityField = new JTextField();
        quantityField.setPreferredSize(new Dimension(200, 30));
        JTextField availableQuantityField = new JTextField();
        availableQuantityField.setPreferredSize(new Dimension(200, 30));
    
        // Panel chứa các trường thông tin sách
        JPanel bookInfoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        // ISBN Field
        gbc.gridx = 0; gbc.gridy = 0;
        bookInfoPanel.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1;
        bookInfoPanel.add(isbnField, gbc);
    
        gbc.gridx = 0; gbc.gridy = 1;
        bookInfoPanel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        bookInfoPanel.add(titleField, gbc);
    
        gbc.gridx = 0; gbc.gridy = 2;
        bookInfoPanel.add(new JLabel("Author:"), gbc);
        gbc.gridx = 1;
        bookInfoPanel.add(authorField, gbc);
    
        gbc.gridx = 0; gbc.gridy = 3;
        bookInfoPanel.add(new JLabel("Publisher:"), gbc);
        gbc.gridx = 1;
        bookInfoPanel.add(publisherField, gbc);
    
        gbc.gridx = 0; gbc.gridy = 4;
        bookInfoPanel.add(new JLabel("Publication Year:"), gbc);
        gbc.gridx = 1;
        bookInfoPanel.add(publicationYearField, gbc);
    
        gbc.gridx = 0; gbc.gridy = 5;
        bookInfoPanel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        bookInfoPanel.add(categoryField, gbc);
    
        gbc.gridx = 0; gbc.gridy = 6;
        bookInfoPanel.add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 1;
        bookInfoPanel.add(quantityField, gbc);
    
        gbc.gridx = 0; gbc.gridy = 7;
        bookInfoPanel.add(new JLabel("Available Quantity:"), gbc);
        gbc.gridx = 1;
        bookInfoPanel.add(availableQuantityField, gbc);
        // Thêm các panel vào searchPanel
        searchPanel.add(searchInputPanel, BorderLayout.SOUTH);
        searchPanel.add(bookInfoPanel, BorderLayout.CENTER);

        searchButton.addActionListener(e -> {
            sendBookSearchRequest(titleField.getText().trim(), authorField.getText().trim(), publisherField.getText(), null, isbnField.getText().trim(), null, null, null);
            isbnField.setText("");
            titleField.setText("");
            authorField.setText("");
            publisherField.setText("");
            publicationYearField.setText("");
            categoryField.setText("");
            quantityField.setText("");
            availableQuantityField.setText("");
        });

        return searchPanel;
    }

    
    //chinh sua sach(xong)
    private JPanel createEditBookPanel() {
        JPanel editBookPanel = new JPanel(new GridBagLayout());
        editBookPanel.setBorder(BorderFactory.createTitledBorder("Chỉnh sửa sách"));
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        // Trường nhập ISBN để tìm sách
        JTextField isbnField = new JTextField(40);
        JButton searchButton = new JButton("Tìm sách");
    
        gbc.gridx = 0; gbc.gridy = 0;
        editBookPanel.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1;
        editBookPanel.add(isbnField, gbc);
        gbc.gridx = 2;
        editBookPanel.add(searchButton, gbc);
    
        // Các trường nhập liệu khác (ngoại trừ ISBN)
        JTextField newIsbnField = new JTextField(40);
        JTextField titleField = new JTextField(40);
        JTextField authorField = new JTextField(40);
        JTextField publisherField = new JTextField(40);
        JTextField publicationYearField = new JTextField(40);
        JTextField categoryField = new JTextField(40);
        JTextField quantityField = new JTextField(40);
        JTextField availableQuantityField = new JTextField(40);
    
        // Thêm các nhãn và trường nhập liệu vào giao diện, ban đầu không cho chỉnh sửa
        gbc.gridx = 0; gbc.gridy = 1;
        editBookPanel.add(new JLabel("New ISBN:"), gbc);
        gbc.gridx = 1;
        editBookPanel.add(newIsbnField, gbc);
        newIsbnField.setEditable(false);

        gbc.gridx = 0; gbc.gridy =2;
        editBookPanel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        editBookPanel.add(titleField, gbc);
        titleField.setEditable(false);
    
        gbc.gridx = 0; gbc.gridy = 3;
        editBookPanel.add(new JLabel("Author:"), gbc);
        gbc.gridx = 1;
        editBookPanel.add(authorField, gbc);
        authorField.setEditable(false);
    
        gbc.gridx = 0; gbc.gridy =4;
        editBookPanel.add(new JLabel("Publisher:"), gbc);
        gbc.gridx = 1;
        editBookPanel.add(publisherField, gbc);
        publisherField.setEditable(false);
    
        gbc.gridx = 0; gbc.gridy = 5;
        editBookPanel.add(new JLabel("Publication Year:"), gbc);
        gbc.gridx = 1;
        editBookPanel.add(publicationYearField, gbc);
        publicationYearField.setEditable(false);
    
        gbc.gridx = 0; gbc.gridy = 6;
        editBookPanel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        editBookPanel.add(categoryField, gbc);
        categoryField.setEditable(false);
    
        gbc.gridx = 0; gbc.gridy = 7;
        editBookPanel.add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 1;
        editBookPanel.add(quantityField, gbc);
        quantityField.setEditable(false);
    
        gbc.gridx = 0; gbc.gridy = 8;
        editBookPanel.add(new JLabel("Available Quantity:"), gbc);
        gbc.gridx = 1;
        editBookPanel.add(availableQuantityField, gbc);
        availableQuantityField.setEditable(false);
    
        // Nút chỉnh sửa sách
        JButton editBookButton = new JButton("Chỉnh sửa");
        editBookButton.setBackground(new Color(0, 155, 155));
        editBookButton.setForeground(Color.WHITE);
        gbc.gridx = 1; gbc.gridy = 9;
        editBookPanel.add(editBookButton, gbc);
        editBookButton.setEnabled(false); // Ban đầu tắt nút chỉnh sửa
    
        // Sự kiện khi nhấn nút "Tìm sách"
        searchButton.addActionListener(e -> {
            String isbn = isbnField.getText().trim();
            if (isbn.isEmpty()) {
                JOptionPane.showMessageDialog(editBookPanel, "Vui lòng nhập ISBN để tìm sách.");
                return;
            }
    
            // Tìm sách qua API(xong)
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8081/api/books?categories=&isbn=" + isbn)) // API tìm sách theo ISBN
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();
    
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                
                if (response.statusCode() == 200) {
                    // Chuyển đổi JSON thành đối tượng BookRequestDTO
                    ObjectMapper objectMapper = new ObjectMapper();
                    List<BookDTO> books = objectMapper.readValue(response.body(), objectMapper.getTypeFactory().constructCollectionType(List.class, BookDTO.class));
                    BookDTO book =books.get(0);

                    tableModel.setRowCount(0);
            
                    tableModel.addRow(new Object[]{
                        book.getTitle(),
                        book.getAuthor(),
                        book.getPublisher(),
                        book.getPublication_year(),
                        book.getIsbn(),
                        book.getCategory(),
                        book.getQuantity(),
                        book.getAvailable_quantity()
                    });
            
                    // Điền thông tin vào các trường và cho phép chỉnh sửa
                    newIsbnField.setText(book.getIsbn());
                    titleField.setText(book.getTitle());
                    authorField.setText(book.getAuthor());
                    publisherField.setText(book.getPublisher());
                    publicationYearField.setText(String.valueOf(book.getPublication_year()));
                    categoryField.setText(book.getCategory()); // Giả định chỉ có một category
                    quantityField.setText(String.valueOf(book.getQuantity()));
                    availableQuantityField.setText(String.valueOf(book.getAvailable_quantity()));
    
                    // Cho phép chỉnh sửa các trường và bật nút chỉnh sửa
                    newIsbnField.setEditable(true);
                    titleField.setEditable(true);
                    authorField.setEditable(true);
                    publisherField.setEditable(true);
                    publicationYearField.setEditable(true);
                    categoryField.setEditable(true);
                    quantityField.setEditable(true);
                    availableQuantityField.setEditable(true);
                    editBookButton.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(editBookPanel, "Không tìm thấy sách với ISBN: " + isbn);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(editBookPanel, "Có lỗi xảy ra khi tìm kiếm sách.");
            }
        });
    
        // Sự kiện khi nhấn nút "Chỉnh sửa"(xong)
        editBookButton.addActionListener(e -> {
            // Tạo DTO với dữ liệu từ các trường nhập
            BookRequestDTO bookRequestDTO = new BookRequestDTO();
            bookRequestDTO.setIsbn(newIsbnField.getText());
            bookRequestDTO.setTitle(titleField.getText());
            bookRequestDTO.setAuthor(authorField.getText());
            bookRequestDTO.setPublisher(publisherField.getText());
            bookRequestDTO.setPublication_year(Integer.parseInt(publicationYearField.getText()));
            bookRequestDTO.setCategory(List.of(categoryField.getText()));
            bookRequestDTO.setQuantity(Integer.parseInt(quantityField.getText()));
            bookRequestDTO.setAvailable_quantity(Integer.parseInt(availableQuantityField.getText()));
    
            // Gửi yêu cầu cập nhật sách qua API
            try {
                HttpClient client = HttpClient.newHttpClient();
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonRequest = objectMapper.writeValueAsString(bookRequestDTO);
    
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8081/api/books/" + isbnField.getText().trim()))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonRequest))
                    .build();
    
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    
                if (response.statusCode() == 200) {
                    JOptionPane.showMessageDialog(editBookPanel, "Chỉnh sửa sách thành công!");
                    tableModel.setRowCount(0);

                } else {
                    JOptionPane.showMessageDialog(editBookPanel, "Chỉnh sửa sách thất bại: " + response.body());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(editBookPanel, "Có lỗi xảy ra khi cập nhật sách.");
            }
        });
    
        return editBookPanel;
    }
    
    //xoa sach(xong)
    private JPanel createDeleteBookPanel() {
        JPanel deleteBookPanel = new JPanel(new GridBagLayout());
        deleteBookPanel.setBorder(BorderFactory.createTitledBorder("Xóa sách"));
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        JTextField isbnField = new JTextField();
        JButton deleteButton = new JButton("Xóa sách");
    
        gbc.gridx = 0; gbc.gridy = 0;
        deleteBookPanel.add(new JLabel("Nhập ISBN sách:"), gbc);
        gbc.gridx = 1;
        deleteBookPanel.add(isbnField, gbc);
    
        gbc.gridx = 1; gbc.gridy = 1;
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);
        deleteBookPanel.add(deleteButton, gbc);
    
        deleteButton.addActionListener(e -> {
            String isbn = isbnField.getText().trim();
            if (isbn.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập ISBN sách.");
                return;
            }
            try {
                String urlString = "http://localhost:8081/api/books/" + isbn; // Địa chỉ API của bạn
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlString))
                    .DELETE() // Đặt phương thức là DELETE
                    .build();
    
                // Gửi request và nhận phản hồi
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    
                // Xử lý phản hồi
                if (response.statusCode() == 200) {
                    String responseFromSever = response.body();
                    JOptionPane.showMessageDialog(this,responseFromSever);
                } 
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
        });
    
        return deleteBookPanel;
    }
    
}
