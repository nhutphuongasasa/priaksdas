package project.quanlithuvien.ungdung.DTO;


public class LoanDTO {
    private Integer loan_id;
    private String name;
    private String phone;
    private String email;
    private String title;
    private String author;
    private String isbn;
    private String status; 
    private String loan_date;
    private String return_date;
    private Integer quantity;

    // public LoanDTO(Integer loan_id, String name, String phone, String email,
    //                String title, String author, String isbn, String status,
    //                String loan_date, String return_date, Integer quantity) {
    //     this.loan_id = loan_id;
    //     this.name = name;
    //     this.phone = phone;
    //     this.email = email;
    //     this.title = title;
    //     this.author = author;
    //     this.isbn = isbn;
    //     this.status = status;
    //     this.loan_date = loan_date;
    //     this.return_date = return_date;
    //     this.quantity = quantity;
    // }

    public Integer getLoan_id() {
        return loan_id;
    }
    public void setLoan_id(Integer loan_id) {
        this.loan_id = loan_id;
    }
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
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getLoan_date() {
        return loan_date;
    }
    public void setLoan_date(String loan_date) {
        this.loan_date = loan_date;
    }
    public String getReturn_date() {
        return return_date;
    }
    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    
}
