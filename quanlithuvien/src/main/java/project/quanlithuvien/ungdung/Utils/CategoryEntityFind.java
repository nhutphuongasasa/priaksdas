package project.quanlithuvien.ungdung.Utils;

import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import project.quanlithuvien.ungdung.Model.CategoryEntity;
@Component
public class CategoryEntityFind {
    @PersistenceContext
    private EntityManager entityManager;
    public CategoryEntity findAllByName(String name){
        String sql = "select * from categories where categories.name like '"+name+"'";
        Query query = entityManager.createNativeQuery(sql,CategoryEntity.class);
        System.out.println("oke CategoryEntityFind");
        try {
            // Lấy kết quả đầu tiên
            return (CategoryEntity) query.getSingleResult();
        } catch (NoResultException e) {
            // Nếu không tìm thấy kết quả, trả về null
            return null;
        }
    }
}
