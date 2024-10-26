package project.quanlithuvien.ungdung.DTO;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReaderDTO {
    private String name; 
    private String email; 
    private String phone; 
    private String address; 
    private LocalDate registration_date; 
    private String status;
}
