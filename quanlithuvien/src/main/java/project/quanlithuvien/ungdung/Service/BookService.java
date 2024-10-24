package project.quanlithuvien.ungdung.Service;

import java.util.List;
import java.util.Map;

import project.quanlithuvien.ungdung.DTO.BookDTO;
import project.quanlithuvien.ungdung.DTO.BookRequestDTO;

public interface BookService {
    String addBook(BookRequestDTO bookRequestDTO);
    String updateBook(BookRequestDTO bookRequestDTO);
    String deleteBook(String isbn);
    List<BookDTO> findAllBook(Map<String,String> param);

}
