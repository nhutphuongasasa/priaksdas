package project.quanlithuvien.ungdung.Repository.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import project.quanlithuvien.ungdung.Model.CategoryEntity;
import project.quanlithuvien.ungdung.Repository.CategoryRepository;
import project.quanlithuvien.ungdung.Utils.CategoryEntityFind;
@Repository
@Transactional
public class CategoryRepositoryImpl implements CategoryRepository{
    @PersistenceContext
    private EntityManager EntityManager;
    @Autowired
    private CategoryEntityFind categoryEntityFind;
    @Override
    public String addCategory(String name) {
        CategoryEntity categoryEntity = categoryEntityFind.findAllByName(name);
        if(categoryEntity != null){
            return "category already exists";
        }
        CategoryEntity categoryEntityCopy = new CategoryEntity();
        categoryEntityCopy.setName(name);
        EntityManager.merge(categoryEntityCopy);
        return "Successfull";
    }

    @Override
    public String deleteCategory(String name) {
        System.out.println(name);
        CategoryEntity categoryEntity = categoryEntityFind.findAllByName(name);
        if(categoryEntity == null){
            return "category does not exist";
        }
        EntityManager.remove(categoryEntity);
        return "Successfull";
    }

    @Override
    public List<CategoryEntity> findAllCategory() {
        String sql ="select ca.name , ca.category_id from categories ca ";
        Query query = EntityManager.createNativeQuery(sql, CategoryEntity.class);
        return query.getResultList();
    }

    @Override
    public String updateCategory(String nameToUpdate, String name) {
        CategoryEntity categoryEntity = EntityManager.find(CategoryEntity.class, nameToUpdate);
        if(categoryEntity == null){
            return "category does not exist";
        }
        CategoryEntity categoryEntityCopy = new CategoryEntity();
        EntityManager.merge(categoryEntityCopy);
        return "Successfull";
    }
    
}
