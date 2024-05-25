package com.example.Kun_uz.repository;

import com.example.Kun_uz.entity.EmailHistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EmailHistoryRepository extends CrudRepository<EmailHistoryEntity,Integer> {

    List<EmailHistoryEntity> findByEmailOrderByCreatedDate(String email);

    Long countByEmailAndCreatedDateBetween(String email, LocalDateTime from, LocalDateTime to);
    // select count(*) from email_history createdDate between :from and :to

    Optional<EmailHistoryEntity> findByEmail(String email);

    List<EmailHistoryEntity> findAllByCreatedDateBetweenOrderByCreatedDateAsc(LocalDateTime from,LocalDateTime to);

    Page<EmailHistoryEntity> findAllBy (Pageable pageable);

}