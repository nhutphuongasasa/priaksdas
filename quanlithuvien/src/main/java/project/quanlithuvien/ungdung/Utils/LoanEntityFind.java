package project.quanlithuvien.ungdung.Utils;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import project.quanlithuvien.ungdung.Model.LoanEntity;

@Component
public class LoanEntityFind {
    @PersistenceContext
    private EntityManager entityManager;
    public List<LoanEntity> loanEntityFind(int book_id,int reader_id,LocalDate loan_date){
        String sql = "select * from loans lo where lo.book_id = "+book_id+" and lo.reader_id = "+reader_id+" ";
        Query query = entityManager.createNativeQuery(sql,LoanEntity.class);
        return query.getResultList();
    }
}
