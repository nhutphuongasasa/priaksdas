package project.quanlithuvien.ungdung.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name="readers")
@Entity
@Getter
@Setter
public class ReaderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reader_id; 

    @Column(name="name",nullable = false)
    private String name; 

    @Column(name="email",nullable = false, unique = true)
    private String email; 
    
    @Column(name="phone",nullable = false, unique = true)
    private String phone; 
    
    @Column(name="address",nullable = false, unique = true)
    private String address; 

    @Column(name = "registration_date", nullable = false)
    private LocalDate registration_date; 

    @Column(name = "active", nullable = false)
    private String active;

    @OneToMany(mappedBy = "reader",fetch=FetchType.LAZY)
    private List<LoanEntity> loans= new ArrayList<>(); 

}
