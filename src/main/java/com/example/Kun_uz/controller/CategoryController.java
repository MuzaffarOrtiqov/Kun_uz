package com.example.Kun_uz.controller;

import com.example.Kun_uz.createDTO.CategoryCreateDTO;
import com.example.Kun_uz.dto.CategoryDTO;
import com.example.Kun_uz.enums.LanguageEnum;
import com.example.Kun_uz.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/category")
@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping("/create")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryCreateDTO categoryCreateDTO) {
        CategoryDTO categoryDTO = categoryService.createCategory(categoryCreateDTO);
        return ResponseEntity.ok().body(categoryDTO);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDTO> updateCategory (@PathVariable Integer id,
                                                       @Valid @RequestBody CategoryCreateDTO dto){
        CategoryDTO response =categoryService.updateCategory(id,dto);
        return ResponseEntity.ok().body(response);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteCategory (@PathVariable Integer id){
        return ResponseEntity.ok().body(categoryService.deleteCategory(id));
    }
    @GetMapping("/all")
    public ResponseEntity<List<CategoryDTO>> getAllCategory(){
       return ResponseEntity.ok().body(categoryService.getAllCategories()) ;
    }
    @GetMapping("/allWithLang")
    public ResponseEntity<List<CategoryDTO>> findAllWithLang(@RequestHeader (name = "Accept-Language",defaultValue = "UZ")LanguageEnum lang){
        return ResponseEntity.ok().body(categoryService.getCategoryByLanguage(lang));
    }

}
