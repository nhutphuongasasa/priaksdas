package project.quanlithuvien.ungdung.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.quanlithuvien.ungdung.DTO.LoanDTO;
import project.quanlithuvien.ungdung.DTO.LoanRequestDTO;
import project.quanlithuvien.ungdung.Repository.LoanRepository;
import project.quanlithuvien.ungdung.Service.LoanService;

@RestController
@RequestMapping("/loans")
public class LoanController {
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private LoanService LoanService;

    @PostMapping
    public String addLoan(@RequestBody LoanRequestDTO loanRequestDTO) {
        String result = loanRepository.addLoan(loanRequestDTO);
        return result;
    }

    @PutMapping
    public String completeLoan(@RequestBody List<Integer> loan_id) {
        String result = loanRepository.completeLoan(loan_id);
        return result;
    }

    @GetMapping
    public List<LoanDTO> findAllLoan(@RequestBody LoanRequestDTO loanRequestDTO) {
        List<LoanDTO> loans = LoanService.findAllLoan(loanRequestDTO); 
        return loans;
    }

    @DeleteMapping
    public ResponseEntity<String> deleteLoan(@RequestBody List<Integer> loan_id) {
        String result = loanRepository.deleteLoan(loan_id);
        return ResponseEntity.ok(result);
    }

    
}