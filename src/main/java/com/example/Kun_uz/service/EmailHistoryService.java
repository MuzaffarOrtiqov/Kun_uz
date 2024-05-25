package com.example.Kun_uz.service;


import com.example.Kun_uz.dto.EmailHistoryDTO;
import com.example.Kun_uz.entity.EmailHistoryEntity;
import com.example.Kun_uz.exp.AppBadException;
import com.example.Kun_uz.repository.EmailHistoryRepository;
import com.example.Kun_uz.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class EmailHistoryService {
    @Autowired
    private EmailHistoryRepository emailHistoryRepository;
    @Autowired
    private ProfileRepository profileRepository;

    public void create(String toEmail, String text) {
        EmailHistoryEntity entity = new EmailHistoryEntity();
        entity.setEmail(toEmail);
        entity.setMessage(text);
        emailHistoryRepository.save(entity);
    }

    public void checkEmailLimit(String email) { // 1 minute -3 attempt
        // 23/05/2024 19:01:13
        // 23/05/2024 19:01:23
        // 23/05/2024 19:01:33

        // 23/05/2024 19:00:55 -- (current -1)
        // 23/05/2024 19:01:55 -- current

        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from = to.minusMinutes(2);

        long count = emailHistoryRepository.countByEmailAndCreatedDateBetween(email, from, to);
        if (count >= 3) {
            throw new AppBadException("Sms limit reached. Please try after some time");
        }
    }

    public void isNotExpiredEmail(String email) {
        Optional<EmailHistoryEntity> optional = emailHistoryRepository.findByEmail(email);
        if (optional.isEmpty()) {
            throw new AppBadException("Email history not found");
        }
        EmailHistoryEntity entity = optional.get();
        if (entity.getCreatedDate().plusDays(1).isBefore(LocalDateTime.now())) {
            throw new AppBadException("Confirmation time expired");
        }
    }

    public List<EmailHistoryDTO> getByEmail(String email) {
        List<EmailHistoryEntity> emailHistoryEntityList = emailHistoryRepository.findByEmailOrderByCreatedDate(email);
        List<EmailHistoryDTO> emailHistoryDTOList = new LinkedList<>();
        for (EmailHistoryEntity emailHistoryEntity : emailHistoryEntityList) {
            EmailHistoryDTO dto = new EmailHistoryDTO();
            dto.setEmail(emailHistoryEntity.getEmail());
            dto.setMessage(emailHistoryEntity.getMessage());
            emailHistoryDTOList.add(dto);
        }
        return emailHistoryDTOList;
    }

    public List<EmailHistoryDTO> getEmailByGivenDate(LocalDate givenDate) {
        LocalDateTime from = givenDate.atStartOfDay();
        LocalDateTime to = from.plusDays(1);
        List<EmailHistoryEntity> emailHistoryEntityList = emailHistoryRepository.findAllByCreatedDateBetweenOrderByCreatedDateAsc(from, to);
        List<EmailHistoryDTO> emailHistoryDTOList = new LinkedList<>();
        emailHistoryEntityList.forEach(emailHistoryEntity -> {
            emailHistoryDTOList.add(toDTO(emailHistoryEntity));
        });
        return emailHistoryDTOList;
    }

    public EmailHistoryDTO toDTO(EmailHistoryEntity emailHistoryEntity) {
        EmailHistoryDTO dto = new EmailHistoryDTO();
        dto.setEmail(emailHistoryEntity.getEmail());
        dto.setMessage(emailHistoryEntity.getMessage());
        return dto;
    }

    public Page<EmailHistoryDTO> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EmailHistoryEntity> pageObj = emailHistoryRepository.findAllBy(pageable);
        List<EmailHistoryDTO> emailHistoryDTOList = new LinkedList<>();
        pageObj.forEach(emailHistoryEntity -> {
            emailHistoryDTOList.add(toDTO(emailHistoryEntity));
        });
        Long totalCount = pageObj.getTotalElements();
        return new PageImpl<>(emailHistoryDTOList, pageable, totalCount);
    }


}
