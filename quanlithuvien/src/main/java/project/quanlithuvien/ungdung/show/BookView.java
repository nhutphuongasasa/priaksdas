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

import com.fasterxml.jackson.databind.ObjectMapper;

import project.quanlithuvien.ungdung.DTO.BookRequestDTO;
import project.quanlithuvien.ungdung.Models.BookModel;

public class BookView extends JFrame {
    private BookModel book;
    private JTable table;
    private DefaultTableModel tableModel;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public BookView(){
        new BookModel();
        this.init();
    }

    private void init(){
        this.setTitle("Quản lý sách");
        this.setSize(800, 600);
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

        // Thêm các panel vào mainPanel (CardLayout)
        mainPanel.add(addBookPanel, "Add Book");
        mainPanel.add(searchPanel, "Search");
        mainPanel.add(deleteBookPanel, "Delete Book");

        // Thanh nút điều hướng
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(0, 153, 153));

        JButton addBookTabButton = new JButton("Thêm sách");
        JButton searchTabButton = new JButton("Tìm kiếm sách");
        JButton deleteBookTabButton = new JButton("Xóa sách"); 

        // Chuyển đổi giao diện mỗi khi nhấn vào nút
        addBookTabButton.addActionListener(e -> cardLayout.show(mainPanel, "Add Book"));
        searchTabButton.addActionListener(e -> cardLayout.show(mainPanel, "Search"));
        deleteBookTabButton.addActionListener(e -> cardLayout.show(mainPanel, "Delete Book"));

        buttonPanel.add(addBookTabButton);
        buttonPanel.add(searchTabButton);
        buttonPanel.add(deleteBookTabButton);

        // Tạo SplitPane để phân chia không gian giữa bảng và các chức năng
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, mainPanel);
        splitPane.setDividerLocation(400);
        splitPane.setResizeWeight(0.5);

        // Thêm các thành phần vào JFrame
        this.add(buttonPanel, BorderLayout.NORTH);
        this.add(splitPane, BorderLayout.CENTER);
        this.setVisible(true);
    }

    private JPanel createAddBookPanel() {
        JPanel addBookPanel = new JPanel(new GridBagLayout());
        addBookPanel.setBorder(BorderFactory.createTitledBorder("Thêm sách mới"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Các trường nhập liệu mới
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

        
            addBookButton.addActionListener(e -> {
    BookRequestDTO bookRequestDTO = new BookRequestDTO();
    bookRequestDTO.setTitle(titleField.getText());
    bookRequestDTO.setAuthor(authorField.getText());
    bookRequestDTO.setPublisher(publisherField.getText());
    bookRequestDTO.setPublication_year(Long.parseLong(publicationYearField.getText()));
    bookRequestDTO.setIsbn(isbnField.getText());
    bookRequestDTO.setCategory(List.of(categoryField.getText()));
    bookRequestDTO.setQuantity(Integer.parseInt(quantityField.getText()));
    bookRequestDTO.setAvailable_quantity(Integer.parseInt(availableQuantityField.getText()));

    // Gửi HTTP POST request
    try {
        // Chuyển đổi đối tượng BookRequestDTO thành JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(bookRequestDTO);

        // Tạo HTTP Client
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8081/api/books")) // Địa chỉ API của bạn
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
            .build();

        // Gửi request và nhận phản hồi
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Xử lý phản hồi
        if (response.statusCode() == 200) {
            System.out.println("Book added successfully: " + response.body());
        } else {
            System.out.println("Failed to add book: " + response.body());
        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }
});
        

        return addBookPanel;
    }
    //them len bang
    private void updateTable(BookRequestDTO bookRequestDTO) {
        tableModel.addRow(new Object[]{
            bookRequestDTO.getTitle(),
            bookRequestDTO.getAuthor(),
            bookRequestDTO.getPublisher(),
            bookRequestDTO.getPublication_year(),
            bookRequestDTO.getIsbn(),
            bookRequestDTO.getCategory().get(0), // Giả định chỉ có một category
            bookRequestDTO.getQuantity(),
            bookRequestDTO.getAvailable_quantity()
        });
    }
    
    // Các hàm tạo searchPanel và deleteBookPanel sẽ giữ nguyên
    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder("Tìm kiếm sách"));

        JTextField searchField = new JTextField();
        JTextArea searchResultArea = new JTextArea();
        searchResultArea.setEditable(false);
        JButton searchButton = new JButton("Tìm kiếm sách");
        searchButton.setBackground(new Color(0, 155, 155));
        searchButton.setForeground(Color.WHITE);

        // searchButton.addActionListener(e -> {
        //     String searchText = searchField.getText().toLowerCase();
        //     searchResultArea.setText("");
        //     for (BookModel book : books) {
        //         if (book.getTitle().toLowerCase().contains(searchText) ||
        //             book.getAuthor().toLowerCase().contains(searchText) ||
        //             book.getPublisher().toLowerCase().contains(searchText)) {
        //             searchResultArea.append(book.toString() + "\n");
        //         }
        //     }
        // });

        JPanel searchInputPanel = new JPanel(new BorderLayout());
        searchInputPanel.add(new JLabel("Nhập từ khóa tìm kiếm:"), BorderLayout.WEST);
        searchInputPanel.add(searchField, BorderLayout.CENTER);
        searchInputPanel.add(searchButton, BorderLayout.EAST);

        searchPanel.add(searchInputPanel, BorderLayout.NORTH);
        searchPanel.add(new JScrollPane(searchResultArea), BorderLayout.CENTER);

        return searchPanel;
    }

    private JPanel createDeleteBookPanel() {
        JPanel deleteBookPanel = new JPanel(new GridBagLayout());
        deleteBookPanel.setBorder(BorderFactory.createTitledBorder("Xóa sách"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField deleteField = new JTextField();
        JButton deleteButton = new JButton("Xóa sách");

        gbc.gridx = 0; gbc.gridy = 0;
        deleteBookPanel.add(new JLabel("Nhập tên hoặc mã sách:"), gbc);
        gbc.gridx = 1;
        deleteBookPanel.add(deleteField, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);
        deleteBookPanel.add(deleteButton, gbc);

        deleteButton.addActionListener(e -> {
            String input = deleteField.getText().trim();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên hoặc mã sách.");
                return;
            }

            boolean found = false;
            // for (int i = books.size() - 1; i >= 0; i--) {
            //     BookModel book = books.get(i);
            //     // if (book.getTitle().equalsIgnoreCase(input) || String.valueOf(book.getId()).equals(input)) {
            //     //     books.remove(i);
            //     //     tableModel.removeRow(i);
            //     //     found = true;
            //     // }
            // }

            if (found) {
                JOptionPane.showMessageDialog(this, "Đã xóa sách thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy sách với tên hoặc mã: " + input);
            }
            deleteField.setText("");
        });

        return deleteBookPanel;
    }
}
