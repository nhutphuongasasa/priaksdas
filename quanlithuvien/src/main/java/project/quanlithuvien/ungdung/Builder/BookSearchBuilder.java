package project.quanlithuvien.ungdung.Builder;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class BookSearchBuilder {
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private Integer publicationYearTo;
    private Integer publicationYearFrom;
    private String category;  
}