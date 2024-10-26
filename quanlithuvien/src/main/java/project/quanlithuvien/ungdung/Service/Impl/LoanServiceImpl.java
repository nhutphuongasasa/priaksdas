package project.quanlithuvien.ungdung.Service.Impl;

import java.time.format.DateTimeFormatter;
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        for(LoanDTO item : result){
            if(item.getReturn_date()==null){
                item.setReturn_dateCopy("");
            }
            else{
                item.setReturn_dateCopy(item.getReturn_date().format(formatter));
            }
            item.setLoan_dateCopy(item.getLoan_date().format(formatter));
        }
        return result;
    }

    @Override
    public String deleteloan(String isbn, String email) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String updateLoan(String isbn, String email,LoanRequestDTO loanRequestDTO) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
}
