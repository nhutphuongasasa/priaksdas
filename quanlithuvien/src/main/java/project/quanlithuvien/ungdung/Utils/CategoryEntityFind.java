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
    public CategoryEntity findAllByName(String id){
        String sql = "select * from categories where categories.category_id like '"+id+"'";
        Query query = entityManager.createNativeQuery(sql,CategoryEntity.class);
        try {
            // Lấy kết quả đầu tiên
            return (CategoryEntity) query.getSingleResult();
        } catch (NoResultException e) {
            // Nếu không tìm thấy kết quả, trả về null
            return null;
        }
    }
}
