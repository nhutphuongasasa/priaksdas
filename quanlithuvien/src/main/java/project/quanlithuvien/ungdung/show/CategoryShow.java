package project.quanlithuvien.ungdung.show;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import project.quanlithuvien.ungdung.Models.CategoryModel;

public class CategoryShow extends JFrame {
    private CategoryModel categoryModel;
    private DefaultTableModel tableModel;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public CategoryShow() {
        this.categoryModel = new CategoryModel();
        this.init();
    }

    private void init() {
        this.setTitle("Quản lý danh mục sách");
        this.setSize(1200, 600);
        this.setLocationRelativeTo(null);

        // Card layout cho các panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Bảng hiển thị danh mục sách
        tableModel = new DefaultTableModel(new Object[]{ "Category Name"}, 0);
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

    private JPanel createAddCategoryPanel() {
        JPanel addCategoryPanel = new JPanel(new GridBagLayout());
        addCategoryPanel.setBorder(BorderFactory.createTitledBorder("Thêm danh mục mới"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField categoryIdField = new JTextField();
        JTextField categoryNameField = new JTextField();

        // Tạo các trường nhập liệu
        gbc.gridx = 0; gbc.gridy = 0;
        addCategoryPanel.add(new JLabel("Category ID:"), gbc);
        gbc.gridx = 1;
        addCategoryPanel.add(categoryIdField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        addCategoryPanel.add(new JLabel("Category Name:"), gbc);
        gbc.gridx = 1;
        addCategoryPanel.add(categoryNameField, gbc);

        // Nút thêm danh mục
        JButton addCategoryButton = new JButton("Add Category");
        addCategoryButton.setBackground(new Color(0, 155, 155));
        addCategoryButton.setForeground(Color.WHITE);

        gbc.gridx = 1; gbc.gridy = 2;
        addCategoryPanel.add(addCategoryButton, gbc);

        addCategoryButton.addActionListener(e -> {
            String categoryId = categoryIdField.getText();
            String categoryName = categoryNameField.getText();

            tableModel.addRow(new Object[]{categoryId, categoryName});
            categoryIdField.setText("");
            categoryNameField.setText("");
        });

        return addCategoryPanel;
    }

    private JPanel createEditCategoryPanel() {
        JPanel editCategoryPanel = new JPanel(new GridBagLayout());
        editCategoryPanel.setBorder(BorderFactory.createTitledBorder("Sửa danh mục"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField categoryIdField = new JTextField();
        JTextField newCategoryNameField = new JTextField();

        gbc.gridx = 0; gbc.gridy = 0;
        editCategoryPanel.add(new JLabel("Category ID cần sửa:"), gbc);
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
            String categoryId = categoryIdField.getText();
            String newCategoryName = newCategoryNameField.getText();

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (tableModel.getValueAt(i, 0).equals(categoryId)) {
                    tableModel.setValueAt(newCategoryName, i, 1);
                    break;
                }
            }
            categoryIdField.setText("");
            newCategoryNameField.setText("");
        });

        return editCategoryPanel;
    }

    private JPanel createDeleteCategoryPanel() {
        JPanel deleteCategoryPanel = new JPanel(new GridBagLayout());
        deleteCategoryPanel.setBorder(BorderFactory.createTitledBorder("Xóa danh mục"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField categoryIdField = new JTextField();

        gbc.gridx = 0; gbc.gridy = 0;
        deleteCategoryPanel.add(new JLabel("Category ID cần xóa:"), gbc);
        gbc.gridx = 1;
        deleteCategoryPanel.add(categoryIdField, gbc);

        JButton deleteCategoryButton = new JButton("Xóa danh mục");
        deleteCategoryButton.setBackground(Color.RED);
        deleteCategoryButton.setForeground(Color.WHITE);

        gbc.gridx = 1; gbc.gridy = 1;
        deleteCategoryPanel.add(deleteCategoryButton, gbc);

        deleteCategoryButton.addActionListener(e -> {
            String categoryId = categoryIdField.getText();

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (tableModel.getValueAt(i, 0).equals(categoryId)) {
                    tableModel.removeRow(i);
                    break;
                }
            }
            categoryIdField.setText("");
        });

        return deleteCategoryPanel;
    }
}
