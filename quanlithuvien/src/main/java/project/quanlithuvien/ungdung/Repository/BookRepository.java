package project.quanlithuvien.ungdung.Repository;

import java.util.List;

import project.quanlithuvien.ungdung.Builder.BookSearchBuilder;
import project.quanlithuvien.ungdung.DTO.BookRequestDTO;
import project.quanlithuvien.ungdung.Model.BookEntity;
import project.quanlithuvien.ungdung.Model.CategoryEntity;

public interface BookRepository {
    String addBook(BookEntity bookEntity,List<CategoryEntity> CategoryEntities);
    String deleteBook(String isbn);
    String updateBook(String isbnToUpdate,BookRequestDTO bookRequestDTO,List<CategoryEntity> CategoryEntities);
    List<BookEntity> findAllBook(BookSearchBuilder bookSearchBuilder);
}
