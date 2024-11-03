package project.quanlithuvien.ungdung.Repository;



import java.util.List;

import project.quanlithuvien.ungdung.DTO.CategoryDTO;

public interface  CategoryRepository {
    String addCategory(String name);
    String deleteCategory(String id);
    String updateCategory(String nameToUpdate ,String name);
    List<CategoryDTO> findAllCategory();
}
