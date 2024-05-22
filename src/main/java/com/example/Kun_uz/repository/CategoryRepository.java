package com.example.Kun_uz.repository;

import com.example.Kun_uz.Mapper.CategoryMapper;
import com.example.Kun_uz.entity.CategoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {
    List<CategoryEntity> findAllByVisibleTrueOrderByOrderNumberDesc();
    @Query(value = "SELECT id, order_number, " +
            "CASE :lang " +
            "WHEN 'UZ' THEN name_uz " +
            "WHEN 'RU' THEN name_ru " +
            "WHEN 'EN' THEN name_en " +
            "END AS name " +
            "FROM category " +
            "ORDER BY order_number DESC", nativeQuery = true)
    List<CategoryMapper> findAllByLanguage(@Param("lang") String lang);

}
