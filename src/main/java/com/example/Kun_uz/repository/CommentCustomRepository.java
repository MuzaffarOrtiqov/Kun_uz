package com.example.Kun_uz.repository;

import com.example.Kun_uz.dto.Comment.CommentFilterDTO;
import com.example.Kun_uz.dto.FilterResponseDTO;
import com.example.Kun_uz.entity.CommentEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CommentCustomRepository {
    @Autowired
    private EntityManager entityManager;

    public FilterResponseDTO<CommentEntity> filter(CommentFilterDTO commentFilterDTO, int page, int size) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder query = new StringBuilder();
        query.append(" where c.visible = true  ");

        if (commentFilterDTO.getId() != null) {
            query.append(" and  c.id = :id ");
            params.put("id", commentFilterDTO.getId());
        }
        if (commentFilterDTO.getArticleId() != null) {
            query.append(" and  c.articleId = :articleId ");
            params.put("articleId", commentFilterDTO.getArticleId());
        }
        if (commentFilterDTO.getProfileId() != null) {
            query.append(" AND c.profileId = :profileId ");
            params.put("profileId", commentFilterDTO.getProfileId());
        }
        if (commentFilterDTO.getCreatedDateFrom() != null) {
            query.append(" AND c.createdDate >= :createdDateFrom ");
            params.put("createdDateFrom", commentFilterDTO.getCreatedDateFrom());
        }
        if (commentFilterDTO.getCreatedDateTo() != null) {
            query.append(" AND c.createdDate <= :createdDateTo ");
            params.put("createdDateTo", commentFilterDTO.getCreatedDateTo());
        }

        StringBuilder selectSql = new StringBuilder("FROM CommentEntity as c  ");
        StringBuilder countSql = new StringBuilder("SELECT COUNT(c) FROM CommentEntity as c  ");

        selectSql.append(query);
        countSql.append(query);

        Query selectQuery = entityManager.createQuery(selectSql.toString());
        Query countQuery = entityManager.createQuery(countSql.toString());

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            selectQuery.setParameter(entry.getKey(), entry.getValue());
            countQuery.setParameter(entry.getKey(), entry.getValue());
        }
        selectQuery.setFirstResult(page * size);
        selectQuery.setMaxResults(size);
        List<CommentEntity> comments = selectQuery.getResultList();
        Long count = (Long) countQuery.getSingleResult();

        return new FilterResponseDTO<>(comments, count);


    }
}
