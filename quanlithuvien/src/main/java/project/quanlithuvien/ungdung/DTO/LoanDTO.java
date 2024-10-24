package project.quanlithuvien.ungdung.DTO;

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
    public enum LoanStatus {
        ĐÃ_TRẢ,
        CHƯA_TRẢ,
    }
}
