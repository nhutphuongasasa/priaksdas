package project.quanlithuvien.ungdung.Converter;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import project.quanlithuvien.ungdung.Model.BookEntity;
import project.quanlithuvien.ungdung.Model.LoanEntity;
import project.quanlithuvien.ungdung.Model.ReaderEntity;
@Component
public class LoanEntityConverter {
    public LoanEntity toLoanEntity(BookEntity bookEntity,ReaderEntity readerEntity,int quantity){
        LocalDate currentDate = LocalDate.now();
        LoanEntity loanEntity = new LoanEntity();
        loanEntity.setBook(bookEntity);
        loanEntity.setReader(readerEntity);
        loanEntity.setLoan_date(currentDate);
        loanEntity.setStatus("CHƯA_TRẢ");
        loanEntity.setReturn_date(null);
        loanEntity.setQuantity(quantity);
        return loanEntity;
    }
}
