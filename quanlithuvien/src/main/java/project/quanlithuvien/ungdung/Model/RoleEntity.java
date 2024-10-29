package project.quanlithuvien.ungdung.Model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer role_id; 

    @Column(name="name",nullable = false, unique = true)
    private String name; 

    @OneToOne(mappedBy="role",fetch=FetchType.EAGER)
    private AdminEntity admin;

    @OneToMany(mappedBy="role")
    private List<LibraryStaffEntity> library_staff;
}
