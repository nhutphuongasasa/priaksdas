package project.quanlithuvien.ungdung.Repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import project.quanlithuvien.ungdung.Model.AdminEntity;


public interface AdminRepository extends JpaRepository<AdminEntity, Integer> {

    Optional<AdminEntity> findByUserName(String userName);

    boolean existsByUserName(String userName);

    Optional<AdminEntity> findByEmail(String email);

    Optional<AdminEntity> findByPhone(String phone);
}

