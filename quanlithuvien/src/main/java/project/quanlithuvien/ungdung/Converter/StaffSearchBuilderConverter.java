package project.quanlithuvien.ungdung.Converter;

import org.springframework.stereotype.Component;

import project.quanlithuvien.ungdung.Builder.StaffSearchBuilder;
import project.quanlithuvien.ungdung.DTO.LibraryStaffRequestDTO;
@Component
public class StaffSearchBuilderConverter {
    public StaffSearchBuilder toStaffSearchBuilder(LibraryStaffRequestDTO libraryStaffRequestDTO){
        StaffSearchBuilder staffSearchBuilder = StaffSearchBuilder.builder()
                                                .name(libraryStaffRequestDTO.getName())
                                                .email(libraryStaffRequestDTO.getEmail())
                                                .phone(libraryStaffRequestDTO.getPhone())
                                                .position(libraryStaffRequestDTO.getPosition())
                                                .build();
        return staffSearchBuilder;
    }
}
