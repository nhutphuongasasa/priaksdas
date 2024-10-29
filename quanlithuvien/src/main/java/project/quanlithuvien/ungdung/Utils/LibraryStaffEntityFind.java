package project.quanlithuvien.ungdung.Utils;

import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import project.quanlithuvien.ungdung.Model.LibraryStaffEntity;
@Component
public class LibraryStaffEntityFind {
    @PersistenceContext
    private EntityManager entityManager;
    public LibraryStaffEntity findLibraryStaffEntityByEmail(String email){
        String sql = "select * from library_staff where library_staff.email like '"+email+"' ";
        Query query = entityManager.createNativeQuery(sql,LibraryStaffEntity.class);
        return (LibraryStaffEntity)query.getResultList().stream().findFirst().orElse(null);
    }

    public LibraryStaffEntity findLibraryStaffEntityByPhone(String phone){
        String sql = "select * from library_staff where library_staff.phone like '"+phone+"' ";
        Query query = entityManager.createNativeQuery(sql,LibraryStaffEntity.class);
        return (LibraryStaffEntity)query.getResultList().stream().findFirst().orElse(null);
    }

    public LibraryStaffEntity findLibraryStaffEntityByUserName(String user_name){
        String sql = "select * from library_staff where library_staff.user_name like '"+user_name+"' ";
        Query query = entityManager.createNativeQuery(sql,LibraryStaffEntity.class);
        return (LibraryStaffEntity)query.getResultList().stream().findFirst().orElse(null);
    }

}
