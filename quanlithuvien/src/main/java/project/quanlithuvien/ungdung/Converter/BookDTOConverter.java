package project.quanlithuvien.ungdung.Converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.quanlithuvien.ungdung.DTO.BookDTO;
import project.quanlithuvien.ungdung.Model.BookEntity;
import project.quanlithuvien.ungdung.Model.CategoryEntity;
@Component
public class BookDTOConverter {
    @Autowired
	private ModelMapper modelMapper;
    public BookDTO toBookDTO(BookEntity bookEntity){
        BookDTO bookDTO = modelMapper.map(bookEntity,BookDTO.class);
        List<CategoryEntity> categories = bookEntity.getCategories();
        String result =categories.stream().map(category -> category.getName()).collect(Collectors.joining("/"));
        bookDTO.setCategory(result);
        return bookDTO;
    }
}
