package project.quanlithuvien.ungdung.Model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Table(name="loans")
@Entity

public class LoanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer loan_id; 

    @ManyToOne
    @JoinColumn(name = "reader_id", nullable = false)
    private ReaderEntity reader; 

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book; 

    @Column(name="return_date")
    private LocalDate return_date;

    @Column(name = "loan_date", nullable = false)
    private LocalDate loan_date;

    @Column(name = "quantity", nullable = false) 
    private Integer quantity; 

    @Column(name="status",nullable = false)
    private String status;

    public Integer getLoan_id() {
        return loan_id;
    }

    public ReaderEntity getReader() {
        return reader;
    }

    public BookEntity getBook() {
        return book;
    }

    public LocalDate getReturn_date() {
        return return_date;
    }

    public LocalDate getLoan_date() {
        return loan_date;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setLoan_id(Integer loan_id) {
        this.loan_id = loan_id;
    }

    public void setReader(ReaderEntity reader) {
        this.reader = reader;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    public void setReturn_date(LocalDate return_date) {
        this.return_date = return_date;
    }

    public void setLoan_date(LocalDate loan_date) {
        this.loan_date = loan_date;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
