package project.quanlithuvien.ungdung.Repository.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import project.quanlithuvien.ungdung.DTO.CategoryDTO;
import project.quanlithuvien.ungdung.Model.BookEntity;
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
    public String addCategory(String name) {//nguoi dung nhap buoc nhap name
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
    public String deleteCategory(String id) {//nguoi dung nhap name can xoa
        CategoryEntity categoryEntity = EntityManager.find(CategoryEntity.class, id);
        if(categoryEntity == null){
            return "category does not exist";
        }
        for(BookEntity item : categoryEntity.getBooks()){
            item.getCategories().remove(categoryEntity);
        }
        categoryEntity.getBooks().clear();
        EntityManager.remove(categoryEntity);
        return "Successfull";
    }

    @Override
    public List<CategoryDTO> findAllCategory() {//nguoi dung co the nhap name hoac khong
        String sql ="select ca.name,ca.category_id from categories ca ";
        Query query = EntityManager.createNativeQuery(sql, CategoryDTO.class);
        return query.getResultList();
    }

    @Override//nguoi dung nhap nam can update va name moi
    public String updateCategory(String nameToUpdate, String name) {
        CategoryEntity categoryEntity = categoryEntityFind.findAllByName(nameToUpdate);
        if(categoryEntity == null){
            return "category does not exist";
        }
        categoryEntity.setName(name);
        EntityManager.merge(categoryEntity);
        return "Successfull";
    }
    
}
