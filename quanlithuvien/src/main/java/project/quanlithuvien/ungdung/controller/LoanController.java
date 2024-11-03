package project.quanlithuvien.ungdung.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import project.quanlithuvien.ungdung.DTO.LoanDTO;
import project.quanlithuvien.ungdung.DTO.LoanRequestDTO;
import project.quanlithuvien.ungdung.Service.LoanService;

@RestController
@RequestMapping("/api/loans")
public class LoanController {
    @Autowired
    private LoanService LoanService;

    @PostMapping
    public String addLoan(@RequestBody LoanRequestDTO loanRequestDTO) {
        String result = LoanService.addLoan(loanRequestDTO);
        return result;
    }

    @DeleteMapping("/{loan_id}")
    public String completeLoan(@PathVariable Integer loan_id) {
        String result = LoanService.completeLoan(loan_id);
        return result;
    }

    @GetMapping
    public List<LoanDTO> findAllLoans(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String phone,
        @RequestParam(required = false) String email,
        @RequestParam(required = false) String title,
        @RequestParam(required = false) String author,
        @RequestParam(required = false) String isbn,
        @RequestParam(required = false) Integer quantity) {

        LoanRequestDTO loanRequestDTO = new LoanRequestDTO();
        loanRequestDTO.setName(name);
        loanRequestDTO.setPhone(phone);
        loanRequestDTO.setEmail(email);
        loanRequestDTO.setTitle(title);
        loanRequestDTO.setAuthor(author);
        loanRequestDTO.setIsbn(isbn);
        loanRequestDTO.setQuantity(quantity);

        return LoanService.findAllLoan(loanRequestDTO);
    }
    
}