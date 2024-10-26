package project.quanlithuvien.ungdung.Utils;

import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import project.quanlithuvien.ungdung.Model.ReaderEntity;
@Component
public class ReaderEntityFind {
    @PersistenceContext
    private EntityManager entityManager;

    public ReaderEntity readerEntityFindByEmail(String email){
        String sql = "select * from readers re where re.email like '"+email+"' ";
        Query query = entityManager.createNativeQuery(sql,ReaderEntity.class);
        return (ReaderEntity)query.getResultList().stream().findFirst().orElse(null);
    }

    public ReaderEntity readerEntityFindByPhone(String phone){
        String sql = "select * from readers re where re.phone like '"+phone+"' ";
        Query query = entityManager.createNativeQuery(sql,ReaderEntity.class);
        return (ReaderEntity)query.getResultList().stream().findFirst().orElse(null);
    }
}
