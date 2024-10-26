package project.quanlithuvien.ungdung.DTO;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoanDTO {
    private Integer loan_id;
    private String name;
    private String phone;
    private String email;
    private String title;
    private String author;
    private String isbn;
    private String status; 
    private LocalDate loan_date;
    private LocalDate return_date;
    private Integer quantity;
    // public enum LoanStatus {
    //     ĐÃ_TRẢ,
    //     CHƯA_TRẢ,
    // }
    
}
