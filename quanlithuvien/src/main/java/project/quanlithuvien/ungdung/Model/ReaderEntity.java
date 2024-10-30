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


@Table(name="readers")
@Entity

public class ReaderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reader_id; 

    @Column(name="name",nullable = false)
    private String name; 

    @Column(name="email",nullable = false, unique = true)
    private String email; 
    
    @Column(name="phone",nullable = false, unique = true)
    private String phone; 
    
    @Column(name="address",nullable = false)
    private String address; 

    @Column(name = "registration_date", nullable = false)
    private LocalDate registration_date=LocalDate.now(); 

    @Column(name = "status", nullable = false)
    private String status;

    @OneToMany(mappedBy = "reader",fetch=FetchType.LAZY)
    private List<LoanEntity> loans= new ArrayList<>(); 

    public Integer getReader_id() {
        return reader_id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public LocalDate getRegistration_date() {
        return registration_date;
    }

    public String getStatus() {
        return status;
    }

    public List<LoanEntity> getLoans() {
        return loans;
    }

    public void setReader_id(Integer reader_id) {
        this.reader_id = reader_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRegistration_date(LocalDate registration_date) {
        this.registration_date = registration_date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLoans(List<LoanEntity> loans) {
        this.loans = loans;
    }

    

}
