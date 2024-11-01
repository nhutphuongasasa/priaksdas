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
import org.springframework.web.bind.annotation.RestController;

import project.quanlithuvien.ungdung.DTO.ReaderDTO;
import project.quanlithuvien.ungdung.DTO.ReaderRequestDTO;
import project.quanlithuvien.ungdung.Service.ReaderService;

@RestController
@RequestMapping("/api/readers")
public class ReaderController {

    @Autowired
    private ReaderService readerService; // Tiêm service

    @PostMapping
    public String addReader(@RequestBody ReaderRequestDTO readerRequestDTO) {
        String response = readerService.addReader(readerRequestDTO);
        return response;
    }

    @PutMapping("/{emailToUpdate}")
    public String updateReader(@RequestBody ReaderRequestDTO readerRequestDTO, 
                                                @PathVariable String emailToUpdate) {
        String response = readerService.updateReader(readerRequestDTO, emailToUpdate);
        return response;
    }

    @DeleteMapping("/{emailToDelete}")
    public String deleteReader(@PathVariable String emailToDelete) {
        String response = readerService.deleteReader(emailToDelete);
        return response;
    }

    @GetMapping
    public List<ReaderDTO> findAllReaders(@RequestBody ReaderRequestDTO readerRequestDTO) {
        List<ReaderDTO> readers = readerService.findAllReader(readerRequestDTO);
        return readers;
    }
}
