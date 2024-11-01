package project.quanlithuvien.ungdung.Repository;

import java.util.List;

import project.quanlithuvien.ungdung.Builder.StaffSearchBuilder;
import project.quanlithuvien.ungdung.DTO.LibraryStaffRequestDTO;
import project.quanlithuvien.ungdung.Model.LibraryStaffEntity;

public interface LibraryStaffRepository {
    String addLibraryStaff(LibraryStaffRequestDTO libraryStaffRequestDTO);
    String deleteLibraryStaff(String emailTodelete);
    String updateLibraryStaff(LibraryStaffRequestDTO libraryStaffRequestDTO,String emailToUpdate);
    List<LibraryStaffEntity> findAllLibraryStaff(StaffSearchBuilder staffSearchBuilder);
}
