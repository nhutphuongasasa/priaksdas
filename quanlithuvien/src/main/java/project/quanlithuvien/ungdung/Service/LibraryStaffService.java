package project.quanlithuvien.ungdung.Service;

import java.util.List;

import project.quanlithuvien.ungdung.DTO.LibraryStaffDTO;
import project.quanlithuvien.ungdung.DTO.LibraryStaffRequestDTO;

public interface LibraryStaffService {
    String addLibraryStaff(LibraryStaffRequestDTO libraryStaffRequestDTO);
    String deleteLibraryStaff(LibraryStaffRequestDTO libraryStaffRequestDTO);
    String updateLibraryStaff(LibraryStaffRequestDTO libraryStaffRequestDTO,String emailToUpdate);
    List<LibraryStaffDTO> findAllLibraryStaff(LibraryStaffRequestDTO libraryStaffRequestDTO);
    String Login(String userName,String password);
}
