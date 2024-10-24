package project.quanlithuvien.ungdung.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.quanlithuvien.ungdung.DTO.LoanDTO;
import project.quanlithuvien.ungdung.DTO.LoanRequestDTO;
import project.quanlithuvien.ungdung.Repository.LoanRepository;
import project.quanlithuvien.ungdung.Service.LoanService;
@Service
public class LoanServiceImpl implements LoanService{
    @Autowired
    private LoanRepository loanRepository;
    
    @Override
    public String addLoan(LoanRequestDTO loanRequestDTO) {
        String result = loanRepository.addLoan(loanRequestDTO);
        return result;
    }

    @Override
    public String completeLoan(LoanRequestDTO loanRequestDTO) {
        String result = loanRepository.completeLoan(loanRequestDTO);
        return  result;
    }
    
    @Override
    public List<LoanDTO> findAllLoan(LoanRequestDTO loanRequestDTO){
        List<LoanDTO> result = loanRepository.findAllLoan(loanRequestDTO);
        return result;
    }
}
