package project.quanlithuvien.ungdung.show;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LibraryManagementUI extends JFrame {
    
    public LibraryManagementUI() {
        setTitle("Quản Lý Thư Viện");
        setSize(1200, 600);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Quản Lý Thư Viện", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE); 
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0, 153, 153)); 
        mainPanel.add(titleLabel, BorderLayout.NORTH);  
        
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40)); 

        JButton manageBookButton = new JButton("Quản lý sách");
        manageBookButton.setFont(new Font("Arial", Font.BOLD, 18));
        manageBookButton.setBackground(new Color(0, 204, 204));
        manageBookButton.setForeground(Color.WHITE);
        manageBookButton.addActionListener(e -> switchToNewInterface(new BookView()));

        JButton manageReadersButton = new JButton("Quản lý độc giả");
        manageReadersButton.setFont(new Font("Arial", Font.BOLD, 18));
        manageReadersButton.setBackground(new Color(0, 204, 204));
        manageReadersButton.setForeground(Color.WHITE);
        manageReadersButton.addActionListener(e -> switchToNewInterface(new UserShow()));

        JButton manageLoansButton = new JButton("Quản lý mượn sách");
        manageLoansButton.setFont(new Font("Arial", Font.BOLD, 18));
        manageLoansButton.setBackground(new Color(0, 204, 204));
        manageLoansButton.setForeground(Color.WHITE);
        manageLoansButton.addActionListener(e -> switchToNewInterface(new BorrowShow()));

        JButton categoryButton = new JButton("Quản lý danh mục sách");
        categoryButton.setFont(new Font("Arial", Font.BOLD, 18));
        categoryButton.setBackground(new Color(0, 204, 204));
        categoryButton.setForeground(Color.WHITE);
        categoryButton.addActionListener(e -> switchToNewInterface(new CategoryShow()));

        JButton staffButton = new JButton("Quản lý nhân viên");
        staffButton.setFont(new Font("Arial", Font.BOLD, 18));
        staffButton.setBackground(new Color(0, 204, 204));
        staffButton.setForeground(Color.WHITE);
        staffButton.addActionListener(e -> switchToNewInterface(new StaffShow()));

        JButton exitButton = new JButton("Thoát chương trình");
        exitButton.setFont(new Font("Arial", Font.BOLD, 18));
        exitButton.setBackground(new Color(255, 0, 0)); 
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(e -> System.exit(0));
        
        buttonPanel.add(manageBookButton);
        buttonPanel.add(manageReadersButton);
        buttonPanel.add(manageLoansButton);
        buttonPanel.add(categoryButton);
        buttonPanel.add(staffButton);

        JPanel footerPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        footerPanel.setBackground(new Color(240, 240, 240));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 100, 10, 100));
        footerPanel.add(exitButton);
        
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        
        this.add(mainPanel);
        // this.setVisible(true);
    }

    private void switchToNewInterface(JFrame newFrame) {
        this.setVisible(false); // Ẩn `LibraryManagementUI`
        
        newFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                LibraryManagementUI.this.setVisible(true); // Hiển thị lại `LibraryManagementUI` khi đóng giao diện mới
            }
        });

        newFrame.setVisible(true); // Hiển thị giao diện mới
    }
}

