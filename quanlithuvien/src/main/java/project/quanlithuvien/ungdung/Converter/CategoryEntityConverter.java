package project.quanlithuvien.ungdung.Converter;

import org.springframework.stereotype.Component;

import project.quanlithuvien.ungdung.Model.CategoryEntity;
@Component
public class CategoryEntityConverter {
    public CategoryEntity toCategory(String s){
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(s);
        return categoryEntity;
    }
}
