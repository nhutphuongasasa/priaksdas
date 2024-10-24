package project.quanlithuvien.ungdung.DTO;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class LibraryStaffRequestDTO {
    private String name;
    private String email; 
    private String phone;
    private String position; 
    private LocalDate hire_date;
}
