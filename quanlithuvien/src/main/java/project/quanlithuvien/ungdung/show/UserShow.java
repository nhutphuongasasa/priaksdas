package project.quanlithuvien.ungdung.show;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import project.quanlithuvien.ungdung.Models.UserModel;

public class UserShow extends JFrame {
    private List<UserModel> users = new ArrayList<>();
    private JTable table;
    private DefaultTableModel tableModel;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public UserShow() {
        new UserModel();
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

        // Thêm các panel vào mainPanel (CardLayout)
        mainPanel.add(addUserPanel, "Add User");
        mainPanel.add(searchPanel, "Search User");
        mainPanel.add(deleteUserPanel, "Delete User");

        // Thanh nút điều hướng
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(0, 153, 153));

        JButton addUserTabButton = new JButton("Thêm người dùng");
        JButton searchTabButton = new JButton("Tìm kiếm");
        JButton deleteUserTabButton = new JButton("Xóa");

        addUserTabButton.addActionListener(e -> cardLayout.show(mainPanel, "Add User"));
        searchTabButton.addActionListener(e -> cardLayout.show(mainPanel, "Search User"));
        deleteUserTabButton.addActionListener(e -> cardLayout.show(mainPanel, "Delete User"));

        buttonPanel.add(addUserTabButton);
        buttonPanel.add(searchTabButton);
        buttonPanel.add(deleteUserTabButton);

        // Tạo SplitPane để phân chia không gian giữa bảng và các chức năng
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, mainPanel);
        splitPane.setDividerLocation(400);
        splitPane.setResizeWeight(0.5);

        // Thêm các thành phần vào JFrame
        this.add(buttonPanel, BorderLayout.NORTH);
        this.add(splitPane, BorderLayout.CENTER);
        this.setVisible(true);
    }

    private JPanel createAddUserPanel() {
        JPanel addUserPanel = new JPanel(new GridBagLayout());
        addUserPanel.setBorder(BorderFactory.createTitledBorder("Thêm độc giả mới"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField emailField = new JTextField();

        // Tạo các trường nhập liệu
        gbc.gridx = 0; gbc.gridy = 0;
        addUserPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        addUserPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        addUserPanel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 1;
        addUserPanel.add(ageField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        addUserPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        addUserPanel.add(emailField, gbc);

        // Nút thêm độc giả
        JButton addUserButton = new JButton("Thêm người dùng");
        addUserButton.setBackground(new Color(0, 155, 155));
        addUserButton.setForeground(Color.WHITE);

        gbc.gridx = 1; gbc.gridy = 3;
        addUserPanel.add(addUserButton, gbc);

        addUserButton.addActionListener(e -> {
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String email = emailField.getText();
            // UserModel user = new UserModel(name, age, email);

            // users.add(user);
            tableModel.addRow(new Object[]{name, age, email});

            nameField.setText("");
            ageField.setText("");
            emailField.setText("");
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
            for (UserModel user : users) {
                if (user.getName().toLowerCase().contains(searchText) ||
                    user.getEmail().toLowerCase().contains(searchText)) {
                    searchResultArea.append(user.toString() + "\n");
                }
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
        deleteUserPanel.add(new JLabel("Nhập tên hoặc mã độc giả:"), gbc);
        gbc.gridx = 1;
        deleteUserPanel.add(deleteField, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);
        deleteUserPanel.add(deleteButton, gbc);

        deleteButton.addActionListener(e -> {
            String input = deleteField.getText().trim();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên hoặc mã độc giả.");
                return;
            }

            boolean found = false;
            for (int i = users.size() - 1; i >= 0; i--) {
                UserModel user = users.get(i);
                // if (user.getName().equalsIgnoreCase(input) || String.valueOf(user.getId()).equals(input)) {
                //     users.remove(i);
                //     tableModel.removeRow(i);
                //     found = true;
                // }
            }

            if (found) {
                JOptionPane.showMessageDialog(this, "Đã xóa độc giả thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy độc giả với tên hoặc mã: " + input);
            }
            deleteField.setText("");
        });

        return deleteUserPanel;
    }
}
