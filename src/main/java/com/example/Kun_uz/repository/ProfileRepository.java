package com.example.Kun_uz.repository;

import com.example.Kun_uz.entity.ProfileEntity;
import com.example.Kun_uz.enums.ProfileStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer> {

    Page<ProfileEntity> findAll(Pageable pageable);

    Optional<ProfileEntity> findByEmailAndVisibleTrue(String email);
    Optional<ProfileEntity> findByPhoneAndVisibleTrue(String phone);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set status =?2 where id =?1")
    int updateStatus(Integer profileId, ProfileStatus status);





}
