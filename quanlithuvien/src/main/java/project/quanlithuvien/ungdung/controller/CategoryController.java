package project.quanlithuvien.ungdung.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.quanlithuvien.ungdung.DTO.CategoryDTO;
import project.quanlithuvien.ungdung.Service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public String addCategory(@RequestBody String name) {
        String namecopy = name.substring(1,name.length()-1);
        return categoryService.addCategory(namecopy);
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable String id) {
        return categoryService.deleteCategory(id);
        
    }

    @PutMapping("/{nameToUpdate}")
    public String updateCategory(@PathVariable String nameToUpdate, @RequestBody String name) {
        String namecopy = name.substring(1,name.length()-1);
        return categoryService.updateCategory(nameToUpdate, namecopy);
        
    }

    @GetMapping
    public List<CategoryDTO> findAllCategory() {
        return categoryService.findAllCategory();
    }
}
