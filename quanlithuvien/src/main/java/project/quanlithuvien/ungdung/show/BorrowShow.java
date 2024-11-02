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
        tableModel = new DefaultTableModel(new Object[]{"Book Title", "Borrower Name", "Borrow Date", "Return Date"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(400, 600));

        // Panel mượn sách
        JPanel borrowBookPanel = createBorrowBookPanel();

        // Panel trả sách
        JPanel returnBookPanel = createReturnBookPanel();

        // Panel tìm kiếm sách
        JPanel searchPanel = createSearchPanel();

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

    private JPanel createBorrowBookPanel() {
        JPanel borrowBookPanel = new JPanel(new GridBagLayout());
        borrowBookPanel.setBorder(BorderFactory.createTitledBorder("Mượn sách"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField bookTitleField = new JTextField();
        JTextField borrowerNameField = new JTextField();
        JTextField borrowDateField = new JTextField();

        gbc.gridx = 0; gbc.gridy = 0;
        borrowBookPanel.add(new JLabel("Book Title:"), gbc);
        gbc.gridx = 1;
        borrowBookPanel.add(bookTitleField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        borrowBookPanel.add(new JLabel("Borrower Name:"), gbc);
        gbc.gridx = 1;
        borrowBookPanel.add(borrowerNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        borrowBookPanel.add(new JLabel("Borrow Date:"), gbc);
        gbc.gridx = 1;
        borrowBookPanel.add(borrowDateField, gbc);

        JButton borrowBookButton = new JButton("Mượn sách");
        borrowBookButton.setBackground(new Color(0, 155, 155));
        borrowBookButton.setForeground(Color.WHITE);
        gbc.gridx = 1; gbc.gridy = 3;
        borrowBookPanel.add(borrowBookButton, gbc);

        borrowBookButton.addActionListener(e -> {
            String bookTitle = bookTitleField.getText();
            String borrowerName = borrowerNameField.getText();
            String borrowDate = borrowDateField.getText();

            if (bookTitle.isEmpty() || borrowerName.isEmpty() || borrowDate.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin sách.");
            } else {
                tableModel.addRow(new Object[]{bookTitle, borrowerName, borrowDate, "Not returned"});
                bookTitleField.setText("");
                borrowerNameField.setText("");
                borrowDateField.setText("");
            }
        });

        return borrowBookPanel;
    }

    private JPanel createReturnBookPanel() {
        JPanel returnBookPanel = new JPanel(new GridBagLayout());
        returnBookPanel.setBorder(BorderFactory.createTitledBorder("Trả sách"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField returnBookTitleField = new JTextField();
        JTextField returnDateField = new JTextField();

        gbc.gridx = 0; gbc.gridy = 0;
        returnBookPanel.add(new JLabel("Book Title:"), gbc);
        gbc.gridx = 1;
        returnBookPanel.add(returnBookTitleField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        returnBookPanel.add(new JLabel("Return Date:"), gbc);
        gbc.gridx = 1;
        returnBookPanel.add(returnDateField, gbc);

        JButton returnBookButton = new JButton("Trả sách");
        returnBookButton.setBackground(new Color(0, 155, 155));
        returnBookButton.setForeground(Color.WHITE);
        gbc.gridx = 1; gbc.gridy = 2;
        returnBookPanel.add(returnBookButton, gbc);

        returnBookButton.addActionListener(e -> {
            String returnBookTitle = returnBookTitleField.getText();
            String returnDate = returnDateField.getText();

            if (returnBookTitle.isEmpty() || returnDate.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin sách.");
            } else {
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    if (tableModel.getValueAt(i, 0).equals(returnBookTitle) &&
                        tableModel.getValueAt(i, 3).equals("Not returned")) {
                        tableModel.setValueAt(returnDate, i, 3);
                        JOptionPane.showMessageDialog(this, "Sách đã được trả thành công!");
                        break;
                    }
                }
                returnBookTitleField.setText("");
                returnDateField.setText("");
            }
        });

        return returnBookPanel;
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder("Tìm kiếm sách mượn"));

        JTextField searchField = new JTextField();
        JTextArea searchResultArea = new JTextArea();
        searchResultArea.setEditable(false);
        JButton searchButton = new JButton("Tìm kiếm");
        searchButton.setBackground(new Color(0, 155, 155));
        searchButton.setForeground(Color.WHITE);

        searchButton.addActionListener(e -> {
            String searchText = searchField.getText().toLowerCase();
            searchResultArea.setText("");
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String bookTitle = tableModel.getValueAt(i, 0).toString().toLowerCase();
                String borrowerName = tableModel.getValueAt(i, 1).toString().toLowerCase();
                if (bookTitle.contains(searchText) || borrowerName.contains(searchText)) {
                    searchResultArea.append("Book: " + tableModel.getValueAt(i, 0) +
                            ", Borrower: " + tableModel.getValueAt(i, 1) + "\n");
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
}
