package project.quanlithuvien.ungdung.Service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.quanlithuvien.ungdung.Converter.LoanDTOConverter;
import project.quanlithuvien.ungdung.DTO.LoanDTO;
import project.quanlithuvien.ungdung.DTO.LoanRequestDTO;
import project.quanlithuvien.ungdung.Model.LoanEntity;
import project.quanlithuvien.ungdung.Repository.LoanRepository;
import project.quanlithuvien.ungdung.Service.LoanService;
@Service
public class LoanServiceImpl implements LoanService{
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private LoanDTOConverter loanDTOConverter;
    @Override
    public String addLoan(LoanRequestDTO loanRequestDTO) {
        String result = loanRepository.addLoan(loanRequestDTO);
        return result;
    }

    @Override
    public String completeLoan(Integer loan_id) {
        String result = loanRepository.completeLoan(loan_id);
        return  result;
    }
    
    @Override
    public List<LoanDTO> findAllLoan(LoanRequestDTO loanRequestDTO){
        List<LoanEntity> loanEntities = loanRepository.findAllLoan(loanRequestDTO);
        List<LoanDTO> result = new ArrayList<>();
        for(LoanEntity item : loanEntities){
            result.add(loanDTOConverter.toLoanDTO(item));
        }
        return result;
    }

    @Override
    public String deleteloan(Integer loan_id) {
        String result = loanRepository.deleteLoan(loan_id);
        return result;
    }

    
}
