package project.quanlithuvien.ungdung.Service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.quanlithuvien.ungdung.Builder.BookSearchBuilder;
import project.quanlithuvien.ungdung.Converter.BookDTOConverter;
import project.quanlithuvien.ungdung.Converter.BookRequestDTOConverter;
import project.quanlithuvien.ungdung.Converter.BookSearchBuilderConverter;
import project.quanlithuvien.ungdung.Converter.CategoryEntityConverter;
import project.quanlithuvien.ungdung.DTO.BookDTO;
import project.quanlithuvien.ungdung.DTO.BookRequestDTO;
import project.quanlithuvien.ungdung.Model.BookEntity;
import project.quanlithuvien.ungdung.Model.CategoryEntity;
import project.quanlithuvien.ungdung.Repository.BookRepository;
import project.quanlithuvien.ungdung.Service.BookService;
@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRequestDTOConverter bookRequestDTOConverter;
    @Autowired
    private BookRepository BookRepository;
    @Autowired
    private BookSearchBuilderConverter bookSearchBuilderConverter;
    @Autowired
    private BookDTOConverter bookDTOConverter;
    @Autowired
    private CategoryEntityConverter categoryEntityConverter;
    @Override
    public String addBook(BookRequestDTO bookRequestDTO) {
        BookEntity bookEntity = bookRequestDTOConverter.toBookEntity(bookRequestDTO);
        System.out.println("oke1");
        List<CategoryEntity> categoryEntities = new ArrayList<>();
        for(String item : bookRequestDTO.getCategory()){
            categoryEntities.add(categoryEntityConverter.toCategory(item));
        }
        String result = BookRepository.addBook(bookEntity,categoryEntities);
        System.out.println("oke3");
        return result;
    }

    @Override
    public String updateBook(BookRequestDTO bookRequestDTO) {
        // BookEntity bookEntity = bookRequestDTOConverter.toBookEntity(bookRequestDTO);
        System.out.println("vao ham service update");
        List<CategoryEntity> categoryEntities = new ArrayList<>();
        for(String item : bookRequestDTO.getCategory()){
            categoryEntities.add(categoryEntityConverter.toCategory(item));
        }
        String result = BookRepository.updateBook(bookRequestDTO, categoryEntities);
        return result;
    }

    @Override
    public String deleteBook(String isbn) {
        String result = BookRepository.deleteBook(isbn);
        return result;
    }

    @Override
    public List<BookDTO> findAllBook(Map<String, String> param) {
        BookSearchBuilder bookSearchBuilder = bookSearchBuilderConverter.toBookSearchBuilder(param);
        System.out.println(bookSearchBuilder.getPublicationYearFrom());
        List<BookEntity> bookEntities = BookRepository.findAllBook(bookSearchBuilder);
        List<BookDTO> result = new ArrayList<>();
        for(BookEntity item : bookEntities){
            BookDTO bookDTO = bookDTOConverter.toBookDTO(item);
            result.add(bookDTO);
        }
        return result;
    }
}
