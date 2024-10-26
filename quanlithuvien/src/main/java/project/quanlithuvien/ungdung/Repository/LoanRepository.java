package project.quanlithuvien.ungdung.Repository;

import java.util.List;

import project.quanlithuvien.ungdung.DTO.LoanDTO;
import project.quanlithuvien.ungdung.DTO.LoanRequestDTO;

public interface  LoanRepository {
    String addLoan(LoanRequestDTO loanRequestDTO);
    String completeLoan(LoanRequestDTO loanRequestDTO);
    List<LoanDTO> findAllLoan(LoanRequestDTO loanRequestDTO);
    String deleteLoan(String isbn,String email);
    String updateLoan(String isbn,String email,LoanRequestDTO loanRequestDTO);
}
