package project.quanlithuvien.ungdung.Converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.quanlithuvien.ungdung.DTO.LibraryStaffDTO;
import project.quanlithuvien.ungdung.Model.LibraryStaffEntity;
@Component
public class LibraryStaffDTOConverter {
    @Autowired
    private ModelMapper modelMapper;
    public LibraryStaffDTO toLibraryStaffDTO(LibraryStaffEntity libraryStaffEntity){
        LibraryStaffDTO libraryStaffDTO = modelMapper.map(libraryStaffEntity, LibraryStaffDTO.class);
        return libraryStaffDTO;
    }
}
