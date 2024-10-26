package project.quanlithuvien.ungdung.Converter;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import project.quanlithuvien.ungdung.Model.BookEntity;
import project.quanlithuvien.ungdung.Model.LoanEntity;
import project.quanlithuvien.ungdung.Model.LoanEntity.LoanStatus;
import project.quanlithuvien.ungdung.Model.ReaderEntity;
@Component
public class LoanEntityConverter {
    public LoanEntity toLoanEntity(BookEntity bookEntity,ReaderEntity readerEntity){
        LocalDate currentDate = LocalDate.now();
        LoanEntity loanEntity = LoanEntity.builder()
                                        .book(bookEntity)
                                        .reader(readerEntity)
                                        .loan_date(currentDate)
                                        .status(LoanStatus.CHƯA_TRẢ)
                                        .build();
        return loanEntity;
    }
}
