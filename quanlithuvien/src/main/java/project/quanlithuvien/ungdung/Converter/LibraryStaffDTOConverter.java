package project.quanlithuvien.ungdung.Converter;

import java.time.format.DateTimeFormatter;

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LibraryStaffDTO libraryStaffDTO = modelMapper.map(libraryStaffEntity, LibraryStaffDTO.class);
        libraryStaffDTO.setHire_date(libraryStaffEntity.getHireDate().format(formatter));
        libraryStaffDTO.setActive(libraryStaffEntity.getStatus());
        return libraryStaffDTO;
    }
}
