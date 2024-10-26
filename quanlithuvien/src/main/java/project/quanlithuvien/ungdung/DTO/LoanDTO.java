package project.quanlithuvien.ungdung.DTO;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoanDTO {
    private String name;
    private String phone;
    private String email;
    private String title;
    private String author;
    private String isbn;
    private LoanStatus status; 
    private LocalDate loan_date;
    private LocalDate return_date;
    private String loan_dateCopy;
    private String return_dateCopy;
    public enum LoanStatus {
        ĐÃ_TRẢ,
        CHƯA_TRẢ,
    }
}
