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

import project.quanlithuvien.ungdung.Models.StaffModel;

public class StaffShow extends JFrame {
    private StaffModel staffModel;
    private DefaultTableModel tableModel;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public StaffShow() {
        this.staffModel = new StaffModel();
        this.init();
    }

    private void init() {
        this.setTitle("Quản lý nhân viên thư viện");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);

        // Card layout cho các panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Bảng hiển thị nhân viên
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Position", "Email"}, 0);
        JTable table = new JTable(tableModel);
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

    private JPanel createAddStaffPanel() {
        JPanel addStaffPanel = new JPanel(new GridBagLayout());
        addStaffPanel.setBorder(BorderFactory.createTitledBorder("Thêm nhân viên mới"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField positionField = new JTextField();
        JTextField emailField = new JTextField();

        gbc.gridx = 0; gbc.gridy = 0;
        addStaffPanel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        addStaffPanel.add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        addStaffPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        addStaffPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        addStaffPanel.add(new JLabel("Position:"), gbc);
        gbc.gridx = 1;
        addStaffPanel.add(positionField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        addStaffPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        addStaffPanel.add(emailField, gbc);

        JButton addStaffButton = new JButton("Thêm");
        addStaffButton.setBackground(new Color(0, 155, 155));
        addStaffButton.setForeground(Color.WHITE);
        gbc.gridx = 1; gbc.gridy = 4;
        addStaffPanel.add(addStaffButton, gbc);

        return addStaffPanel;
    }

    private JPanel createEditStaffPanel() {
        JPanel editStaffPanel = new JPanel(new GridBagLayout());
        editStaffPanel.setBorder(BorderFactory.createTitledBorder("Sửa thông tin nhân viên"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField positionField = new JTextField();
        JTextField emailField = new JTextField();

        gbc.gridx = 0; gbc.gridy = 0;
        editStaffPanel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        editStaffPanel.add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        editStaffPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        editStaffPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        editStaffPanel.add(new JLabel("Position:"), gbc);
        gbc.gridx = 1;
        editStaffPanel.add(positionField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        editStaffPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        editStaffPanel.add(emailField, gbc);

        JButton editStaffButton = new JButton("Sửa");
        editStaffButton.setBackground(new Color(0, 155, 155));
        editStaffButton.setForeground(Color.WHITE);
        gbc.gridx = 1; gbc.gridy = 4;
        editStaffPanel.add(editStaffButton, gbc);

        return editStaffPanel;
    }

    private JPanel createDeleteStaffPanel() {
        JPanel deleteStaffPanel = new JPanel(new GridBagLayout());
        deleteStaffPanel.setBorder(BorderFactory.createTitledBorder("Xóa nhân viên"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField idField = new JTextField();

        gbc.gridx = 0; gbc.gridy = 0;
        deleteStaffPanel.add(new JLabel("ID nhân viên:"), gbc);
        gbc.gridx = 1;
        deleteStaffPanel.add(idField, gbc);

        JButton deleteStaffButton = new JButton("Xóa");
        deleteStaffButton.setBackground(Color.RED);
        deleteStaffButton.setForeground(Color.WHITE);
        gbc.gridx = 1; gbc.gridy = 1;
        deleteStaffPanel.add(deleteStaffButton, gbc);

        return deleteStaffPanel;
    }
}
