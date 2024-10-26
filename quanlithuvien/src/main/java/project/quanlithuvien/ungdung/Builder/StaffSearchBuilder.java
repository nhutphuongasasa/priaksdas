package project.quanlithuvien.ungdung.Builder;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class StaffSearchBuilder {
    private String name;
    private String email; 
    private String phone;
    private String position;
    private LocalDate hire_date;
}
