package project.quanlithuvien.ungdung.Models;

public class BorrowModel {
    private String name;
    private String phone;
    private String email;
    private String title;
    private String author;
    private String isbn;
    private Integer quantity;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public BorrowModel(String name, String phone, String email, String title, String author, String isbn,
            Integer quantity) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.quantity = quantity;
    }
    public BorrowModel() {
    }

    


}
