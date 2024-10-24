package project.quanlithuvien.ungdung.Model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "library_staff")
public class LibraryStaffEntity implements Comparable<LibraryStaffEntity>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long staff_id; 

    @Column(name = "name", nullable = false)
    private String name; 

    @Column(name = "email", nullable = false, unique = true)
    private String email; 

    @Column(name = "phone")
    private String phone; 

    @Column(name = "position")
    private String position; 

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate; 

    @Column(name="status",nullable=false)
    private String status;

    @Override
    public int compareTo(LibraryStaffEntity o) {
        return this.getName().compareTo(o.getName());
    }


}
