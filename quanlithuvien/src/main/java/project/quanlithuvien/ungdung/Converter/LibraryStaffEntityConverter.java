package project.quanlithuvien.ungdung.Converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.quanlithuvien.ungdung.DTO.LibraryStaffRequestDTO;
import project.quanlithuvien.ungdung.Model.LibraryStaffEntity;
@Component
public class LibraryStaffEntityConverter {
    @Autowired
	private ModelMapper modelMapper;
    public LibraryStaffEntity toLibraryStaffEntity(LibraryStaffRequestDTO libraryStaffRequestDTO){
        LibraryStaffEntity libraryStaffEntity = modelMapper.map(libraryStaffRequestDTO,LibraryStaffEntity.class);
        libraryStaffEntity.setStatus("active");
        return libraryStaffEntity;
    }
}
