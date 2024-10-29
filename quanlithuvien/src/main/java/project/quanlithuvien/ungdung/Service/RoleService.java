package project.quanlithuvien.ungdung.Service;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import project.quanlithuvien.ungdung.Model.RoleEntity;

public interface RoleService extends JpaRepository<RoleEntity,Integer>{
    Optional<RoleEntity> findByName(String name);
}
