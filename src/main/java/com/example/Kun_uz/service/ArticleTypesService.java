package com.example.Kun_uz.service;

import com.example.Kun_uz.entity.ArticleTypesEntity;
import com.example.Kun_uz.repository.ArticleTypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ArticleTypesService {
    @Autowired
    private ArticleTypesRepository articleTypesRepository;

    public void create(String articleId, List<Integer> types) {

// articleTypesEntity.setArticleId(id);
        for (Integer typeId : types) {
           create(articleId,typeId);
        }

    }
    public void create(String articleId,Integer typeId) {
        ArticleTypesEntity articleTypesEntity = new ArticleTypesEntity();
        articleTypesEntity.setArticleId(articleId);
        articleTypesEntity.setTypesId(typeId);
        articleTypesRepository.save(articleTypesEntity);
    }

    public void merge(String articleId, List<Integer> newList) {
        Objects.requireNonNull(newList);

        List<Integer> oldList = articleTypesRepository.findTypesIdByArticleId(articleId);

        oldList.forEach(oldId-> {
            if(!newList.contains(oldId)){
                articleTypesRepository.deleteByArticleIdAndTypesId(articleId,oldId);
            }
        });

        newList.forEach(newId -> {
            if(!oldList.contains(newId)){
                create(articleId,newId);
            }
        });

    }
}
