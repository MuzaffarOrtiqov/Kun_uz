package com.example.Kun_uz.service;


import com.example.Kun_uz.Mapper.CategoryMapper;
import com.example.Kun_uz.dto.createDTO.CategoryCreateDTO;
import com.example.Kun_uz.dto.CategoryDTO;
import com.example.Kun_uz.entity.CategoryEntity;
import com.example.Kun_uz.enums.LanguageEnum;
import com.example.Kun_uz.exp.ResourceNotFoundException;
import com.example.Kun_uz.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.LinkedList;
import java.util.List;


@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    public CategoryDTO createCategory(CategoryCreateDTO dto) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setOrderNumber(dto.getOrderNumber());
        categoryEntity.setNameEn(dto.getNameEn());
        categoryEntity.setNameRu(dto.getNameRu());
        categoryEntity.setNameUz(dto.getNameUz());
        categoryRepository.save(categoryEntity);
        return toDto(categoryEntity);

    }
    public CategoryDTO toDto(CategoryEntity entity){
        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setNameEn(entity.getNameEn());
        dto.setNameRu(entity.getNameRu());
        dto.setNameUz(entity.getNameUz());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public CategoryDTO updateCategory(Integer id, CategoryCreateDTO dto) {
     CategoryEntity categoryEntity = get(id);
     categoryEntity.setOrderNumber(dto.getOrderNumber());
     categoryEntity.setNameEn(dto.getNameEn());
     categoryEntity.setNameRu(dto.getNameRu());
     categoryEntity.setNameUz(dto.getNameUz());
     categoryRepository.save(categoryEntity);
     return toDto(categoryEntity);
    }
    public CategoryEntity get(Integer id){
       return categoryRepository.findById(id).orElseThrow(() -> {
            return new ResourceNotFoundException("Category not found");
        });
    }
    public Boolean deleteCategory(Integer id) {
        CategoryEntity categoryEntity = get(id);
        categoryRepository.deleteById(id);
        return true;
    }
    public List<CategoryDTO> getAllCategories() {
        Iterable<CategoryEntity> iterable = categoryRepository.findAll();
        List<CategoryDTO> dtoList = new LinkedList<>();
        iterable.forEach(entity -> {
           CategoryDTO dto = new CategoryDTO();
           dto.setId(entity.getId());
           dto.setOrderNumber(entity.getOrderNumber());
           dto.setNameEn(entity.getNameEn());
           dto.setNameRu(entity.getNameRu());
           dto.setNameUz(entity.getNameUz());
           dto.setCreatedDate(entity.getCreatedDate());
           dto.setVisible(entity.getVisible());
           dtoList.add(dto);
        });
        return dtoList;
    }
    public List<CategoryDTO> getCategoryByLang(LanguageEnum lang){
        List<CategoryEntity> list = categoryRepository.findAllByVisibleTrueOrderByOrderNumberDesc();
        List<CategoryDTO> dtoList = new LinkedList<>();
        list.forEach(entity -> {
            CategoryDTO dto = new CategoryDTO();
            dto.setId(entity.getId());
            dto.setOrderNumber(entity.getOrderNumber());
            switch (lang){
                case EN -> dto.setNameEn(entity.getNameEn());
                case RU -> dto.setNameRu(entity.getNameRu());
                case UZ -> dto.setNameUz(entity.getNameUz());
            }
            dtoList.add(dto);
        });
        return dtoList;
    }
    public List<CategoryDTO> getCategoryByLanguage(LanguageEnum lang){
        List<CategoryMapper> mapperList = categoryRepository.findAllByLanguage(lang.name());
        List<CategoryDTO>  dtoList = new LinkedList<>();
        mapperList.forEach(mapper -> {
            CategoryDTO dto = new CategoryDTO();
            dto.setId(mapper.getId());
            dto.setOrderNumber(mapper.getOrderNumber());
            dto.setName(mapper.getName());
            dtoList.add(dto);
        });
        return dtoList;
    }

}
