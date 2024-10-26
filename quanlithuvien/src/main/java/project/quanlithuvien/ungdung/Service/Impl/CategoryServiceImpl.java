package project.quanlithuvien.ungdung.Service.Impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.quanlithuvien.ungdung.DTO.CategoryDTO;
import project.quanlithuvien.ungdung.Repository.CategoryRepository;
import project.quanlithuvien.ungdung.Service.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public String addCategory(String name) {
        String result = categoryRepository.addCategory(name);
        return result;
    }
    

    @Override
    public String deleteCategory(String name) {
        String result = categoryRepository.deleteCategory(name);
        return result;
    }

    @Override
    public List<CategoryDTO> findAllCategory() {
        List<CategoryDTO> CategoryDTOs = categoryRepository.findAllCategory();
        Collections.sort(CategoryDTOs);
        return CategoryDTOs;
    }

    @Override
    public String updateCategory(String nameToUpdate, String name) {
        String result = categoryRepository.updateCategory(nameToUpdate, name);
        return result;
    }
    
}
