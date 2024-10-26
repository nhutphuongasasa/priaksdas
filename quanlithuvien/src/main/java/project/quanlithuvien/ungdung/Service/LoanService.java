package project.quanlithuvien.ungdung.Service;

import java.util.List;

import project.quanlithuvien.ungdung.DTO.LoanDTO;
import project.quanlithuvien.ungdung.DTO.LoanRequestDTO;

public interface  LoanService {
    String addLoan(LoanRequestDTO loanRequestDTO);
    String completeLoan(List<Integer> loan_id);
    String deleteloan(List<Integer> loan_id);
    List<LoanDTO> findAllLoan(LoanRequestDTO loanRequestDTO);
}
