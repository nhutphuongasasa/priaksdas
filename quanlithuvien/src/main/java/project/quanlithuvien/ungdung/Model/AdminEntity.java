package project.quanlithuvien.ungdung.Model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "admins")
@Getter
@Setter
public class AdminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; 

    @Column(name="userName",nullable = false, unique = true)
    private String userName;  

    @Column(name="password",nullable = false)
    private String password;  

    @Column(name="name",nullable = false)
    private String name;  

    @Column(name="email",nullable = false)
    private String email; 
    
    @Column(name="phone")
    private String phone;  

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="role_id")
    private RoleEntity role;
        
}

