package project.quanlithuvien.ungdung.Service;

import java.util.List;

import project.quanlithuvien.ungdung.DTO.CategoryDTO;

public interface CategoryService {
    String addCategory(String name);
    String deleteCategory(String name);
    String updateCategory(String nameToUpdate ,String name);
    List<CategoryDTO> findAllCategory();
}
