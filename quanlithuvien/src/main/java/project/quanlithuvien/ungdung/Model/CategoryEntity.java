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
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
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
    
    


}
