package project.quanlithuvien.ungdung.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.quanlithuvien.ungdung.DTO.LibraryStaffDTO;
import project.quanlithuvien.ungdung.DTO.LibraryStaffRequestDTO;
import project.quanlithuvien.ungdung.Service.LibraryStaffService;

@RestController
@RequestMapping("/api/library-staff")
public class LibraryStaffRepositoryController {
    @Autowired
    private LibraryStaffService libraryStaffService;

    @PostMapping
    public String addLibraryStaff(@RequestBody LibraryStaffRequestDTO libraryStaffRequestDTO) {
        return libraryStaffService.addLibraryStaff(libraryStaffRequestDTO);
    }

    @DeleteMapping
    public String deleteLibraryStaff(@RequestBody LibraryStaffRequestDTO libraryStaffRequestDTO) {
        return libraryStaffService.deleteLibraryStaff(libraryStaffRequestDTO);
    }

    @PutMapping
    public String updateLibraryStaff(@RequestBody LibraryStaffRequestDTO libraryStaffRequestDTO) {
        return libraryStaffService.updateLibraryStaff(libraryStaffRequestDTO);
    }

    @GetMapping
    public List<LibraryStaffDTO> findAllLibraryStaff() {
        return libraryStaffService.findAllLibraryStaff();
    }
}
