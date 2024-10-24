package project.quanlithuvien.ungdung.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDTO {
    private String title;
    private String author;
    private String publisher;
    private Long publication_year;
    private String isbn;
    private String category;
    private int quantity;
    private String available_qantity;
    
}
