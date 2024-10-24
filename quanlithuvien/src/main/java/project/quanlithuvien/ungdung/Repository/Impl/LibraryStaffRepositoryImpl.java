package project.quanlithuvien.ungdung.Repository.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import project.quanlithuvien.ungdung.Converter.LibraryStaffEntityConverter;
import project.quanlithuvien.ungdung.DTO.LibraryStaffRequestDTO;
import project.quanlithuvien.ungdung.Model.LibraryStaffEntity;
import project.quanlithuvien.ungdung.Repository.LibraryStaffRepository;
@Repository
@Transactional
public class LibraryStaffRepositoryImpl implements LibraryStaffRepository{
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private LibraryStaffEntityConverter libraryStaffEntityConverter;
    @Override
    public String addLibraryStaff(LibraryStaffRequestDTO libraryStaffRequestDTO) {
        LibraryStaffEntity libraryStaffEntity = entityManager.find(LibraryStaffEntity.class, libraryStaffRequestDTO.getEmail());
        if(libraryStaffEntity!=null){
            return "email already exists";
        }
        LibraryStaffEntity libraryStaffEntityCopy = libraryStaffEntityConverter.toLibraryStaffEntity(libraryStaffRequestDTO);
        entityManager.persist(libraryStaffEntityCopy);
        return  "Successfull";
    }

    @Override
    public String deleteLibraryStaff(LibraryStaffRequestDTO libraryStaffRequestDTO) {
        LibraryStaffEntity libraryStaffEntity = entityManager.find(LibraryStaffEntity.class, libraryStaffRequestDTO.getEmail());
        if(libraryStaffEntity==null){
            return "Staff does not exists";
        }
        LibraryStaffEntity libraryStaffEntityCopy = libraryStaffEntityConverter.toLibraryStaffEntity(libraryStaffRequestDTO);
        libraryStaffEntityCopy.setStatus("Inactive");
        entityManager.merge(libraryStaffEntityCopy);
        return  "Successfull";
    }

    @Override
    public String updateLibraryStaff(LibraryStaffRequestDTO libraryStaffRequestDTO) {
        LibraryStaffEntity libraryStaffEntity = entityManager.find(LibraryStaffEntity.class, libraryStaffRequestDTO.getEmail());
        if(libraryStaffEntity==null){
            return "Staff does not exists";
        }
        LibraryStaffEntity libraryStaffEntityCopy = libraryStaffEntityConverter.toLibraryStaffEntity(libraryStaffRequestDTO);
        entityManager.merge(libraryStaffEntityCopy);
        return  "Successfull";
    }

    @Override
    public List<LibraryStaffEntity> findAllLibraryStaff() {
        String sql = "select * from library_staff ";
        Query query = entityManager.createNativeQuery(sql, LibraryStaffEntity.class);
        return query.getResultList();
    }
    
}
