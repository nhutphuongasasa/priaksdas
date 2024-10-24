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
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="books")
public class BookEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long book_id;

    @Column(name="title",nullable=false)
    private String title;

    @Column(name="author",nullable = false)
    private String author;

    @Column(name="publisher",nullable = false)
    private String publisher;

    @Column(name="publication_year")
    private Long publication_year;

    @Column(name="isbn",unique = true, nullable = false)
    private String isbn;

    @Column(name="quantity",nullable = false)
    private int quantity;

    @Column(name="available_quantity",nullable = false)
    private int available_quantity;

    @ManyToMany(mappedBy = "books",cascade=CascadeType.ALL)
    private List<CategoryEntity> categories= new ArrayList<>();

    @OneToMany(mappedBy = "book",fetch=FetchType.LAZY)
    private List<LoanEntity> loans = new ArrayList<>();

}
