package project.quanlithuvien.ungdung.Models;

import java.util.ArrayList;
import java.util.List;
public class BookModel {
    private String title;
    private String author;
    private String publisher;
    private Long publication_year;
    private String isbn;
    private List<String> category = new ArrayList<>();
    private Integer quantity;
    private Integer available_quantity;
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
    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public Long getPublication_year() {
        return publication_year;
    }
    public void setPublication_year(Long publication_year) {
        this.publication_year = publication_year;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public List<String> getCategory() {
        return category;
    }
    public void setCategory(List<String> category) {
        this.category = category;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public Integer getAvailable_quantity() {
        return available_quantity;
    }
    public void setAvailable_quantity(Integer available_quantity) {
        this.available_quantity = available_quantity;
    }
    public BookModel() {
    }
    public BookModel(String title, String author, String publisher, Long publication_year, String isbn,
            List<String> category, Integer quantity, Integer available_quantity) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publication_year = publication_year;
        this.isbn = isbn;
        this.category = category;
        this.quantity = quantity;
        this.available_quantity = available_quantity;
    }

    
}
