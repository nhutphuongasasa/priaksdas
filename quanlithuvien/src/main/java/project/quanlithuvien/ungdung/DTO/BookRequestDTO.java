package project.quanlithuvien.ungdung.DTO;



import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRequestDTO {
    private String title;
    private String author;
    private String publisher;
    private Long publication_year;
    private String isbn;
    private List<String> category = new ArrayList<>();
    private Integer quantity;
    private Integer available_quantity;
}
