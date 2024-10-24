package project.quanlithuvien.ungdung.Utils;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import project.quanlithuvien.ungdung.Model.BookEntity;

@Transactional
@Component
public class BookEntityFind {
    @PersistenceContext
    private EntityManager entityManager;
    public BookEntity findByIsbn(String isbn){
        String sql = "select * from books where books.isbn like '"+isbn+"'";
        Query query = entityManager.createNativeQuery(sql,BookEntity.class);
        System.out.println("oke BookEntityFind");
        return (BookEntity) query.getResultList().stream().findFirst().orElse(null);
    }
}
