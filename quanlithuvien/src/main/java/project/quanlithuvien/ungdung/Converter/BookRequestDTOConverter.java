package project.quanlithuvien.ungdung.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.quanlithuvien.ungdung.DTO.BookRequestDTO;
import project.quanlithuvien.ungdung.Model.BookEntity;
import org.modelmapper.ModelMapper;

@Component
public class BookRequestDTOConverter {
    @Autowired
	private ModelMapper modelMapper;
    public BookEntity toBookEntity(BookRequestDTO bookRequestDTO){
        BookEntity bookEntity = modelMapper.map(bookRequestDTO,BookEntity.class);
       return bookEntity;
    }
}
