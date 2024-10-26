package project.quanlithuvien.ungdung.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.quanlithuvien.ungdung.DTO.LoanDTO;
import project.quanlithuvien.ungdung.DTO.LoanRequestDTO;
import project.quanlithuvien.ungdung.Repository.LoanRepository;

@RestController
@RequestMapping("/loans")
public class LoanController {
    @Autowired
    private LoanRepository loanRepository;

    @PostMapping
    public String addLoan(@RequestBody LoanRequestDTO loanRequestDTO) {
        String result = loanRepository.addLoan(loanRequestDTO);
        return result;
    }

    @PutMapping
    public String completeLoan(@RequestBody LoanRequestDTO loanRequestDTO) {
        String result = loanRepository.completeLoan(loanRequestDTO);
        return result;
    }

    @GetMapping
    public List<LoanDTO> findAllLoan(@RequestBody LoanRequestDTO loanRequestDTO) {
        List<LoanDTO> loans = loanRepository.findAllLoan(loanRequestDTO);
        return loans;
    }

    @DeleteMapping("/{isbn}/{email}")
    public ResponseEntity<String> deleteLoan(@PathVariable String isbn, @PathVariable String email) {
        String result = loanRepository.deleteLoan(isbn, email);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{isbn}/{email}")
    public ResponseEntity<String> updateLoan(@PathVariable String isbn, @PathVariable String email, @RequestBody LoanRequestDTO loanRequestDTO) {
        String result = loanRepository.updateLoan(isbn, email, loanRequestDTO);
        return ResponseEntity.ok(result);
    }
}