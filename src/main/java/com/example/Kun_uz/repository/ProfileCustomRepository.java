package com.example.Kun_uz.repository;

import com.example.Kun_uz.dto.FilterResponseDTO;
import com.example.Kun_uz.dto.profile.ProfileFilterDTO;
import com.example.Kun_uz.entity.ProfileEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProfileCustomRepository {
    @Autowired
    private EntityManager entityManager;

    public FilterResponseDTO<ProfileEntity> filter(ProfileFilterDTO filter, int page, int size) {
        Map<String, Object> params = new HashMap<>();


        StringBuilder query = new StringBuilder();
        if (filter.getName() != null) {
            query.append(" and p.name=:name ");
            params.put("name", filter.getName());
        }
        if (filter.getSurname() != null) {
            query.append(" and p.surname=:surname ");
            params.put("surname", filter.getSurname());
        }
        if (filter.getPhone() != null) {
            query.append(" and p.phone=:phone ");
            params.put("phone", filter.getPhone());
        }
        if (filter.getRole() != null) {
            query.append(" and p.role=:role ");
            params.put("role", filter.getRole());
        }
        if (filter.getCreatedDateFrom() != null) {
            query.append(" and p.createdDate>=:created_date_from ");
            params.put("created_date_from", filter.getCreatedDateFrom());
        }
        if (filter.getCreatedDateTo() != null) {
            query.append(" and p.createdDate<=:created_date_to ");
            params.put("created_date_to", filter.getCreatedDateTo());
        }


        StringBuilder selectSql = new StringBuilder("From ProfileEntity p where p.visible = true ");
        StringBuilder countSql = new StringBuilder("select count(p) From ProfileEntity p where p.visible = true ");

        selectSql.append(query);
        countSql.append(query);

        // select
        Query selectQuery = entityManager.createQuery(selectSql.toString());
        Query countQuery = entityManager.createQuery(countSql.toString());

        for (Map.Entry<String, Object> entity : params.entrySet()) {
            selectQuery.setParameter(entity.getKey(), entity.getValue());
            countQuery.setParameter(entity.getKey(), entity.getValue());
        }
        selectQuery.setFirstResult(page * size); // offset
        selectQuery.setMaxResults(size); // limit
        List<ProfileEntity> profileEntityList = selectQuery.getResultList();
        // count
        Long totalCount = (Long) countQuery.getSingleResult();

        return new FilterResponseDTO<ProfileEntity>(profileEntityList, totalCount);
    }
}
