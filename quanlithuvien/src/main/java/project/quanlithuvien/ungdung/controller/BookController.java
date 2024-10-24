package project.quanlithuvien.ungdung.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import project.quanlithuvien.ungdung.DTO.BookDTO;
import project.quanlithuvien.ungdung.DTO.BookRequestDTO;
import project.quanlithuvien.ungdung.Service.BookService;
@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping
    public String addBook(@RequestBody BookRequestDTO bookRequestDTO) {
        return bookService.addBook(bookRequestDTO);
    }

    @PutMapping
    public String updateBook(@RequestBody BookRequestDTO bookRequestDTO) {
        return bookService.updateBook(bookRequestDTO);
    }

    @DeleteMapping("/{isbn}")
    public String deleteBook(@PathVariable String isbn) {
        return bookService.deleteBook(isbn);
    }

    @GetMapping
    public List<BookDTO> findAllBook(@RequestParam Map<String, String> param) {
        return bookService.findAllBook(param);
    }
}

