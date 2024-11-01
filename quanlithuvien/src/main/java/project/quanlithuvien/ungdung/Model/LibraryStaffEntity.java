package project.quanlithuvien.ungdung.Model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "library_staff")
public class LibraryStaffEntity implements Comparable<LibraryStaffEntity>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer staff_id; 

    @Column(name = "name", nullable = false)
    private String name; 

    @Column(name = "email", nullable = false, unique = true)
    private String email; 

    @Column(name = "phone", nullable = false,unique = true)
    private String phone; 

    @Column(name = "position")
    private String position; 

    @Column(name = "hire_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate hireDate=LocalDate.now(); 

    @Column(name="status",nullable=false)
    private String status;

    @Column(name="user_name",nullable=false)
    private String user_name;

    @Column(name="password",nullable=false)
    private String password;

    @ManyToOne
    @JoinColumn(name="role_id")
    private RoleEntity role;

    @Override
    public int compareTo(LibraryStaffEntity o) {
        return this.getName().compareTo(o.getName());
    }

    public void setStaff_id(Integer staff_id) {
        this.staff_id = staff_id;
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

    public void setPosition(String position) {
        this.position = position;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public Integer getStaff_id() {
        return staff_id;
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

    public String getPosition() {
        return position;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public String getStatus() {
        return status;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getPassword() {
        return password;
    }

    public RoleEntity getRole() {
        return role;
    }



}
