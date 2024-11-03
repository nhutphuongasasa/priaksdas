package project.quanlithuvien.ungdung.Converter;

import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import project.quanlithuvien.ungdung.DTO.LoanDTO;
import project.quanlithuvien.ungdung.Model.LoanEntity;
@Component
public class LoanDTOConverter {
    public LoanDTO toLoanDTO(LoanEntity loanEntity){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setName(loanEntity.getReader().getName());
        loanDTO.setPhone(loanEntity.getReader().getPhone());
        loanDTO.setEmail(loanEntity.getReader().getEmail());
        loanDTO.setAuthor(loanEntity.getBook().getAuthor());
        loanDTO.setIsbn(loanEntity.getBook().getIsbn());
        loanDTO.setTitle(loanEntity.getBook().getTitle());
        loanDTO.setLoan_date(loanEntity.getLoan_date().format(formatter));
        loanDTO.setQuantity(loanEntity.getQuantity());
        loanDTO.setStatus(loanEntity.getStatus());
        loanDTO.setLoan_id(loanEntity.getLoan_id());
        if(loanEntity.getReturn_date()!=null){
            loanDTO.setReturn_date(loanEntity.getReturn_date().format(formatter));
        }else{
            loanDTO.setReturn_date(" ");
        }
        return loanDTO;
    }
}
