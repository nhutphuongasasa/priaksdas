package project.quanlithuvien.ungdung.Converter;

import org.springframework.stereotype.Component;

import project.quanlithuvien.ungdung.DTO.BookRequestDTO;
import project.quanlithuvien.ungdung.Model.BookEntity;

@Component
public class BookEntityConverter { 
    public BookEntity toBookEntity(BookRequestDTO bookRequestDTO,BookEntity bookEntityCopy){
        if (bookRequestDTO.getTitle() != null) {
            bookEntityCopy.setTitle(bookRequestDTO.getTitle());
        }
        if (bookRequestDTO.getAuthor() != null) {
            bookEntityCopy.setAuthor(bookRequestDTO.getAuthor());
        }
        if (bookRequestDTO.getPublisher() != null) {
            bookEntityCopy.setPublisher(bookRequestDTO.getPublisher());
        }
        if (bookRequestDTO.getIsbn() != null) {
            bookEntityCopy.setIsbn(bookRequestDTO.getIsbn());
        }
        if (bookRequestDTO.getPublication_year() != null) {
            bookEntityCopy.setPublication_year(bookRequestDTO.getPublication_year());;
        }
        if (bookRequestDTO.getQuantity() != null) {
            bookEntityCopy.setQuantity(bookRequestDTO.getQuantity());
        }
        if (bookRequestDTO.getAvailable_quantity() != null) {
            bookEntityCopy.setAvailable_quantity(bookRequestDTO.getAvailable_quantity());
        }
        return bookEntityCopy;
    }
}
