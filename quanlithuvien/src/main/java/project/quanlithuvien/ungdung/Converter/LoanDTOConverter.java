package project.quanlithuvien.ungdung.Converter;

import org.springframework.stereotype.Component;

import project.quanlithuvien.ungdung.DTO.LoanDTO;
import project.quanlithuvien.ungdung.Model.LoanEntity;
@Component
public class LoanDTOConverter {
    public LoanDTO toLoanDTO(LoanEntity loanEntity){
        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setName(loanEntity.getReader().getName());
        loanDTO.setPhone(loanEntity.getReader().getPhone());
        loanDTO.setEmail(loanEntity.getReader().getEmail());
        loanDTO.setAuthor(loanEntity.getBook().getAuthor());
        loanDTO.setIsbn(loanEntity.getBook().getIsbn());
        loanDTO.setTitle(loanEntity.getBook().getTitle());
        loanDTO.setReturn_date(loanEntity.getReturn_date());
        loanDTO.setLoan_date(loanEntity.getLoan_date());
        loanDTO.setQuantity(loanEntity.getQuantity());
        loanDTO.setStatus(loanEntity.getStatus());
        loanDTO.setLoan_id(loanEntity.getLoan_id());
        return loanDTO;
    }
}
