package com.example.Kun_uz.repository;

import com.example.Kun_uz.entity.EmailHistoryEntity;
import com.example.Kun_uz.entity.SmsHistoryEntity;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SmsHistoryRepository extends CrudRepository<SmsHistoryEntity,Integer> {

    List<SmsHistoryEntity> findByPhoneOrderByCreatedDateDesc(String phone);

    List<SmsHistoryEntity> findAllByCreatedDateBetweenOrderByCreatedDateAsc(LocalDateTime from, LocalDateTime to);

    Page<SmsHistoryEntity> findAllBy(Pageable pageable);

    Optional<SmsHistoryEntity> findTopByPhoneOrderByCreatedDateDesc(String phone);

    Integer countByPhoneOrderByCreatedDateDesc(String phone);
}
