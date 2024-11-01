package project.quanlithuvien.ungdung.Model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;



@Entity
@Table(name="books")
public class BookEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer book_id;

    @Column(name="title",nullable=false)
    private String title;

    @Column(name="author",nullable = false)
    private String author;

    @Column(name="publisher",nullable = false)
    private String publisher;

    @Column(name="publication_year")
    private Integer publication_year;

    @Column(name="isbn",unique = true, nullable = false)
    private String isbn;

    @Column(name="quantity",nullable = false)
    private Integer quantity;

    @Column(name="available_quantity",nullable = false)
    private int available_quantity;

    @ManyToMany(mappedBy = "books",cascade=CascadeType.ALL)
    private List<CategoryEntity> categories= new ArrayList<>();

    @OneToMany(mappedBy = "book",fetch=FetchType.LAZY)
    private List<LoanEntity> loans = new ArrayList<>();

    public void setBook_id(Integer book_id) {
        this.book_id = book_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setPublication_year(Integer publication_year) {
        this.publication_year = publication_year;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setAvailable_quantity(int available_quantity) {
        this.available_quantity = available_quantity;
    }

    public void setCategories(List<CategoryEntity> categories) {
        this.categories = categories;
    }

    public void setLoans(List<LoanEntity> loans) {
        this.loans = loans;
    }

    public Integer getBook_id() {
        return book_id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public Integer getPublication_year() {
        return publication_year;
    }

    public String getIsbn() {
        return isbn;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public int getAvailable_quantity() {
        return available_quantity;
    }

    public List<CategoryEntity> getCategories() {
        return categories;
    }

    public List<LoanEntity> getLoans() {
        return loans;
    }

    

}
