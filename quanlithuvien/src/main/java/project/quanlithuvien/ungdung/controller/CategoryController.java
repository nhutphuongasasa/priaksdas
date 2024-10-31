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
        return categoryService.addCategory(name);
    }

    @DeleteMapping("/{name}")
    public String deleteCategory(@PathVariable String name) {
        return categoryService.deleteCategory(name);
        
    }

    @PutMapping("/{nameToUpdate}")
    public String updateCategory(@PathVariable String nameToUpdate, @RequestBody String name) {
        System.out.println(name);
        return categoryService.updateCategory(nameToUpdate, name);
        
    }

    @GetMapping
    public List<CategoryDTO> findAllCategory() {
        return categoryService.findAllCategory();
    }
}
