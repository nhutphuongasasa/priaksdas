package project.quanlithuvien.ungdung.show;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LibraryManagementUI extends JFrame {
    
    public LibraryManagementUI() {
        setTitle("Quản Lý Thư Viện");
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Tạo main panel chứa tất cả các phần
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Tiêu đề
        JLabel titleLabel = new JLabel("Quản Lý Thư Viện", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE); 
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0, 153, 153)); // Màu xanh ngọc
        mainPanel.add(titleLabel, BorderLayout.NORTH);  
        
        // Panel chứa các nút chức năng
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        buttonPanel.setBackground(new Color(240, 240, 240)); // Màu nền cho buttonPanel
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40)); // Thêm lề cho buttonPanel
        
        // Nút quản lý sách
        JButton manageBookButton = new JButton("Quản lý sách");
        manageBookButton.setFont(new Font("Arial", Font.BOLD, 18));
        manageBookButton.setBackground(new Color(0, 204, 204));
        manageBookButton.setForeground(Color.WHITE);
        manageBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                new BookView();
            }
        });

        // Nút quản lý độc giả
        JButton manageReadersButton = new JButton("Quản lý độc giả");
        manageReadersButton.setFont(new Font("Arial", Font.BOLD, 18));
        manageReadersButton.setBackground(new Color(0, 204, 204));
        manageReadersButton.setForeground(Color.WHITE);
        manageReadersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                new UserShow();
            }
        });

        // Nút quản lý mượn sách
        JButton manageLoansButton = new JButton("Quản lý mượn sách");
        manageLoansButton.setFont(new Font("Arial", Font.BOLD, 18));
        manageLoansButton.setBackground(new Color(0, 204, 204));
        manageLoansButton.setForeground(Color.WHITE);
        manageLoansButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                new BorrowShow();
            }
        });

        // Nút thoát chương trình
        JButton exitButton = new JButton("Thoát chương trình");
        exitButton.setFont(new Font("Arial", Font.BOLD, 18));
        exitButton.setBackground(new Color(255, 0, 0)); // Màu đỏ cho nút thoát
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
        
        JButton categoryButton = new JButton("Quản lý danh mục sách");
        categoryButton.setFont(new Font("Arial", Font.BOLD, 18));
        categoryButton.setBackground(new Color(0, 204, 204));
        categoryButton.setForeground(Color.WHITE);
        categoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                new CategoryShow();
            }
        });

        JButton staffButton = new JButton("Quản lý nhân viên");
        staffButton.setFont(new Font("Arial", Font.BOLD, 18));
        staffButton.setBackground(new Color(0, 204, 204));
        staffButton.setForeground(Color.WHITE);
        staffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                new StaffShow();
            }
        });

        // Thêm các nút vào buttonPanel
        buttonPanel.add(manageBookButton);
        buttonPanel.add(manageReadersButton);
        buttonPanel.add(manageLoansButton);
        buttonPanel.add(categoryButton);
        buttonPanel.add(staffButton);

        JPanel footerPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        footerPanel.setBackground(new Color(240, 240, 240)); // Màu nền cho footerPanel
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 100, 10, 100));
        footerPanel.add(exitButton);
        
        // Thêm buttonPanel vào mainPanel
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        
        // Thêm mainPanel vào JFrame
        this.add(mainPanel);
        this.setVisible(true);

    }
}
