package com.example.Kun_uz.repository;

import com.example.Kun_uz.Mapper.TypesMapper;
import com.example.Kun_uz.entity.TypesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypesRepository extends CrudRepository<TypesEntity, Integer>, PagingAndSortingRepository<TypesEntity, Integer> {
    Page<TypesEntity> findAll(Pageable pageable);
    List<TypesEntity> findAllByVisibleTrueOrderByOrderNumberDesc();
    @Query( value = " select id, " +
            " CASE :lang " +
            "   WHEN 'UZ' THEN name_uz " +
            "   WHEN 'EN' THEN name_en " +
            "   WHEN 'RU' THEN name_ru " +
            "  END as name " +
            "from article_types order by order_number desc ; ",nativeQuery = true)
    List<TypesMapper> findAllBy(@Param("lang") String lang);
}
