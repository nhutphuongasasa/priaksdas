package project.quanlithuvien.ungdung.Repository;

import java.util.List;

import project.quanlithuvien.ungdung.DTO.LoanRequestDTO;
import project.quanlithuvien.ungdung.Model.LoanEntity;

public interface  LoanRepository {
    String addLoan(LoanRequestDTO loanRequestDTO);
    String completeLoan(Integer loan_id);
    List<LoanEntity> findAllLoan(LoanRequestDTO loanRequestDTO);
    String deleteLoan(Integer loan_id);
}
