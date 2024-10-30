package project.quanlithuvien.ungdung.Model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
public class CategoryEntity implements Comparable<CategoryEntity>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    private Integer category_id;

    @Column(name="name",nullable = false,unique=true)
    private String name;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "bookcategories",
        joinColumns = @JoinColumn(name = "category_id",referencedColumnName="category_id"),
        inverseJoinColumns = @JoinColumn(name = "book_id",referencedColumnName="book_id"))
    private List<BookEntity> books = new ArrayList<>();

    @Override
    public int compareTo(CategoryEntity o) {
        return this.getName().compareTo(o.getName());
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBooks(List<BookEntity> books) {
        this.books = books;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public String getName() {
        return name;
    }

    public List<BookEntity> getBooks() {
        return books;
    }
    
    


}
