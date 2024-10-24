package project.quanlithuvien.ungdung.Repository;

import java.util.List;

import project.quanlithuvien.ungdung.DTO.LibraryStaffRequestDTO;
import project.quanlithuvien.ungdung.Model.LibraryStaffEntity;

public interface LibraryStaffRepository {
    String addLibraryStaff(LibraryStaffRequestDTO libraryStaffRequestDTO);
    String deleteLibraryStaff(LibraryStaffRequestDTO libraryStaffRequestDTO);
    String updateLibraryStaff(LibraryStaffRequestDTO libraryStaffRequestDTO);
    List<LibraryStaffEntity> findAllLibraryStaff();
}
