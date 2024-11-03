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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.fasterxml.jackson.databind.ObjectMapper;

import project.quanlithuvien.ungdung.DTO.CategoryDTO;


public class CategoryShow extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public CategoryShow() {
        this.init();
    }

    private void init() {
        this.setTitle("Quản lý danh mục sách");
        this.setSize(1000, 600);
        this.setLocationRelativeTo(null);

        // Card layout cho các panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Bảng hiển thị danh mục sách
        tableModel = new DefaultTableModel(new Object[]{ "Id","Category Name"}, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.setPreferredSize(new Dimension(400, 600));

        // Panel thêm danh mục
        JPanel addCategoryPanel = createAddCategoryPanel();

        // Panel sửa danh mục
        JPanel editCategoryPanel = createEditCategoryPanel();

        // Panel xóa danh mục
        JPanel deleteCategoryPanel = createDeleteCategoryPanel();

        // Thêm các panel vào mainPanel (CardLayout)
        mainPanel.add(addCategoryPanel, "Add Category");
        mainPanel.add(editCategoryPanel, "Edit Category");
        mainPanel.add(deleteCategoryPanel, "Delete Category");

        // Thanh nút điều hướng
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(0, 153, 153));

        JButton addCategoryButton = new JButton("Thêm danh mục");
        JButton editCategoryButton = new JButton("Sửa danh mục");
        JButton deleteCategoryButton = new JButton("Xoá danh mục");

        addCategoryButton.addActionListener(e -> cardLayout.show(mainPanel, "Add Category"));
        editCategoryButton.addActionListener(e -> cardLayout.show(mainPanel, "Edit Category"));
        deleteCategoryButton.addActionListener(e -> cardLayout.show(mainPanel, "Delete Category"));

        buttonPanel.add(addCategoryButton);
        buttonPanel.add(editCategoryButton);
        buttonPanel.add(deleteCategoryButton);

        // Tạo SplitPane để phân chia không gian giữa bảng và các chức năng
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, mainPanel);
        splitPane.setDividerLocation(400);
        splitPane.setResizeWeight(0.5);

        // Thêm các thành phần vào JFrame
        this.add(buttonPanel, BorderLayout.NORTH);
        this.add(splitPane, BorderLayout.CENTER);
        this.setVisible(true);
    }
    //them(xong)
   private JPanel createAddCategoryPanel() {
    JPanel addCategoryPanel = new JPanel(new GridBagLayout());
    addCategoryPanel.setBorder(BorderFactory.createTitledBorder("Thêm danh mục mới"));

    table = new JTable(tableModel);
    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    scrollPane.setPreferredSize(new Dimension(400, 600));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JTextField categoryNameField = new JTextField();

    // Tạo trường nhập liệu
    gbc.gridx = 0; gbc.gridy = 0;
    addCategoryPanel.add(new JLabel("Category Name:"), gbc);
    gbc.gridx = 1;
    addCategoryPanel.add(categoryNameField, gbc);

    // Nút thêm danh mục
    JButton addCategoryButton = new JButton("Add Category");
    addCategoryButton.setBackground(new Color(0, 155, 155));
    addCategoryButton.setForeground(Color.WHITE);

    gbc.gridx = 1; gbc.gridy = 2;
    addCategoryPanel.add(addCategoryButton, gbc);

    // Nút xem danh sách danh mục
    JButton viewCategoriesButton = new JButton("View All Categories");
    viewCategoriesButton.setBackground(new Color(0, 155, 155));
    viewCategoriesButton.setForeground(Color.WHITE);
    
    gbc.gridx = 1; gbc.gridy = 3;
    addCategoryPanel.add(viewCategoriesButton, gbc);

    // thêm danh mục(xong)
    addCategoryButton.addActionListener(e -> {
        String categoryName = categoryNameField.getText().trim();

        if (categoryName.isEmpty()) {
            JOptionPane.showMessageDialog(addCategoryPanel, "Vui lòng nhập tên danh mục.");
            return;
        }

        try {
            // Tạo yêu cầu thêm danh mục mới qua API
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/api/categories"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("\"" + categoryName + "\"")) // Đưa tên danh mục vào JSON
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Thêm danh mục vào bảng hiển thị
                JOptionPane.showMessageDialog(addCategoryPanel, response.body());
                categoryNameField.setText("");
            } else {
                JOptionPane.showMessageDialog(addCategoryPanel, "Thêm danh mục không thành công: " + response.body());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(addCategoryPanel, "Có lỗi xảy ra: " + ex.getMessage());
        }
    });

    //  xem danh sách danh mục
    viewCategoriesButton.addActionListener(e -> {
        try {
            // Tạo yêu cầu lấy tất cả danh mục từ API
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/api/categories")) // Địa chỉ API lấy tất cả danh mục
                .header("Content-Type", "application/json")
                .GET()
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                ObjectMapper objectMapper = new ObjectMapper();
                List<CategoryDTO> categories = objectMapper.readValue(response.body(), objectMapper.getTypeFactory().constructCollectionType(List.class, CategoryDTO.class));
                System.out.println(categories.size());
                // Xóa hết các hàng trong bảng để cập nhật lại
                tableModel.setRowCount(0);
                
                // Thêm danh sách danh mục vào bảng
                for (CategoryDTO category : categories) {
                    tableModel.addRow(new Object[]{
                    category.getCategory_id(),
                    category.getName()
                    });
                }
                
                JOptionPane.showMessageDialog(addCategoryPanel, "Đã tải danh sách danh mục!");
            } else {
                JOptionPane.showMessageDialog(addCategoryPanel, "Không thể tải danh sách danh mục: " + response.body());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(addCategoryPanel, "Có lỗi xảy ra: " + ex.getMessage());
        }
    });
    return addCategoryPanel;
}


    //chinh sua(xong)
    private JPanel createEditCategoryPanel() {
        JPanel editCategoryPanel = new JPanel(new GridBagLayout());
        editCategoryPanel.setBorder(BorderFactory.createTitledBorder("Sửa danh mục"));
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        JTextField categoryIdField = new JTextField(20);
        JTextField newCategoryNameField = new JTextField(20);
    
        gbc.gridx = 0; gbc.gridy = 0;
        editCategoryPanel.add(new JLabel("Id danh mục cần sửa:"), gbc);
        gbc.gridx = 1;
        editCategoryPanel.add(categoryIdField, gbc);
    
        gbc.gridx = 0; gbc.gridy = 1;
        editCategoryPanel.add(new JLabel("Tên danh mục mới:"), gbc);
        gbc.gridx = 1;
        editCategoryPanel.add(newCategoryNameField, gbc);
    
        JButton editCategoryButton = new JButton("Sửa danh mục");
        editCategoryButton.setBackground(new Color(0, 155, 155));
        editCategoryButton.setForeground(Color.WHITE);
        
        gbc.gridx = 1; gbc.gridy = 2;
        editCategoryPanel.add(editCategoryButton, gbc);
    
        editCategoryButton.addActionListener(e -> {
            String categoryId = categoryIdField.getText().trim();
            String newCategoryName = newCategoryNameField.getText().trim();
    
            if (categoryId.isEmpty()) {
                JOptionPane.showMessageDialog(editCategoryPanel, "Vui lòng nhập id danh mục cần sửa.");
                return;
            }
    
            if (newCategoryName.isEmpty()) {
                JOptionPane.showMessageDialog(editCategoryPanel, "Vui lòng nhập tên danh mục mới");
                return;
            }
    
            try {
                // Gửi yêu cầu PUT để cập nhật danh mục mới
                HttpRequest updateRequest = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8081/api/categories/" + categoryId))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString("\"" + newCategoryName + "\""))
                    .build();
    
                HttpClient client = HttpClient.newHttpClient();
                HttpResponse<String> updateResponse = client.send(updateRequest, HttpResponse.BodyHandlers.ofString());
    
                if (updateResponse.statusCode() == 200) {
                    JOptionPane.showMessageDialog(editCategoryPanel, updateResponse.body());
                    categoryIdField.setText("");
                    newCategoryNameField.setText("");
                } else {
                    JOptionPane.showMessageDialog(editCategoryPanel, "Sửa danh mục không thành công: " + updateResponse.body());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(editCategoryPanel, "Có lỗi xảy ra: " + ex.getMessage());
            }
        });
    
        return editCategoryPanel;
    }
    
    
    //xoa(xong)
    private JPanel createDeleteCategoryPanel() {
        JPanel deleteCategoryPanel = new JPanel(new GridBagLayout());
        deleteCategoryPanel.setBorder(BorderFactory.createTitledBorder("Xóa danh mục"));
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        JTextField categoryIdField = new JTextField(20);
    
        gbc.gridx = 0; gbc.gridy = 0;
        deleteCategoryPanel.add(new JLabel("Id danh mục cần xóa:"), gbc);
        gbc.gridx = 1;
        deleteCategoryPanel.add(categoryIdField, gbc);
    
        JButton deleteCategoryButton = new JButton("Xóa danh mục");
        deleteCategoryButton.setBackground(Color.RED);
        deleteCategoryButton.setForeground(Color.WHITE);
    
        gbc.gridx = 1; gbc.gridy = 1;
        deleteCategoryPanel.add(deleteCategoryButton, gbc);
    
        deleteCategoryButton.addActionListener(e -> {
            String categoryName = categoryIdField.getText().trim();
    
            if (categoryName.isEmpty()) {
                JOptionPane.showMessageDialog(deleteCategoryPanel, "Vui lòng nhập tên danh mục cần xóa.");
                return;
            }
    
            try {
                // Gửi yêu cầu DELETE để xóa danh mục theo tên
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest deleteRequest = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8081/api/categories/" + categoryName))
                    .header("Content-Type", "application/json")
                    .DELETE()
                    .build();
    
                HttpResponse<String> response = client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());
    
                if (response.statusCode() == 200) {
                    JOptionPane.showMessageDialog(deleteCategoryPanel, "Xóa danh mục thành công!");
                    categoryIdField.setText(""); // Xóa trường nhập liệu
                } else {
                    JOptionPane.showMessageDialog(deleteCategoryPanel, "Xóa danh mục không thành công: " + response.body());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(deleteCategoryPanel, "Có lỗi xảy ra: " + ex.getMessage());
            }
        });
    
        return deleteCategoryPanel;
    }
    
    
}
