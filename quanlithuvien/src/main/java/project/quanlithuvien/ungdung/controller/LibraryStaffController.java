package project.quanlithuvien.ungdung.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import project.quanlithuvien.ungdung.DTO.LibraryStaffDTO;
import project.quanlithuvien.ungdung.DTO.LibraryStaffRequestDTO;
import project.quanlithuvien.ungdung.Service.LibraryStaffService;

@RestController
@RequestMapping("/api/library-staff")
public class LibraryStaffController {
    @Autowired
    private LibraryStaffService libraryStaffService;

    @PostMapping
    public String addLibraryStaff(@RequestBody LibraryStaffRequestDTO libraryStaffRequestDTO) {
        return libraryStaffService.addLibraryStaff(libraryStaffRequestDTO);
    }

    @DeleteMapping("/{emailToDelete}")
    public String deleteLibraryStaff(@PathVariable String emailToDelete){
        return libraryStaffService.deleteLibraryStaff(emailToDelete);
    }
    
    @PutMapping("/{emailToUpdate}")
    public String updateLibraryStaff(@RequestBody LibraryStaffRequestDTO libraryStaffRequestDTO,@PathVariable String emailToUpdate) {
        return libraryStaffService.updateLibraryStaff(libraryStaffRequestDTO,emailToUpdate);
    }

    @GetMapping
    public List<LibraryStaffDTO> findAllLibraryStaff(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String email,
        @RequestParam(required = false) String phone,
        @RequestParam(required = false) String position,
        @RequestParam(required = false) String user_name,
        @RequestParam(required = false) String password) {

        LibraryStaffRequestDTO libraryStaffRequestDTO = new LibraryStaffRequestDTO();
        libraryStaffRequestDTO.setName(name);
        libraryStaffRequestDTO.setEmail(email);
        libraryStaffRequestDTO.setPhone(phone);
        libraryStaffRequestDTO.setPosition(position);
        libraryStaffRequestDTO.setUser_name(user_name);
        libraryStaffRequestDTO.setPassword(password);

        return libraryStaffService.findAllLibraryStaff(libraryStaffRequestDTO);
    }
}
