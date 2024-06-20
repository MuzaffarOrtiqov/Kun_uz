package com.example.Kun_uz.service;

import com.example.Kun_uz.dto.history.SmsHistoryDTO;
import com.example.Kun_uz.entity.SmsHistoryEntity;
import com.example.Kun_uz.exp.AppBadException;
import com.example.Kun_uz.repository.SmsHistoryRepository;
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
public class SmsHistoryService {
    @Autowired
    private SmsHistoryRepository smsHistoryRepository;

    public void save(String phoneNumber, String message) {
        SmsHistoryEntity smsHistoryEntity = new SmsHistoryEntity();
        smsHistoryEntity.setPhone(phoneNumber);
        smsHistoryEntity.setMessage(message);
        smsHistoryEntity.setCreatedDate(LocalDateTime.now());
        smsHistoryRepository.save(smsHistoryEntity);
    }

    public List<SmsHistoryDTO> getByPhone(String phone) {
        List<SmsHistoryEntity> smsHistoryEntityList = smsHistoryRepository.findByPhoneOrderByCreatedDateDesc(phone);
        List<SmsHistoryDTO> smsHistoryDTOList = new LinkedList<>();
        for (SmsHistoryEntity smsHistoryEntity : smsHistoryEntityList) {
          smsHistoryDTOList.add(toDTO(smsHistoryEntity));
        }
        return smsHistoryDTOList;
    }

    public SmsHistoryDTO toDTO (SmsHistoryEntity smsHistoryEntity) {
        SmsHistoryDTO smsHistoryDTO = new SmsHistoryDTO();
        smsHistoryDTO.setId(smsHistoryEntity.getId());
        smsHistoryDTO.setPhone(smsHistoryEntity.getPhone());
        smsHistoryDTO.setMessage(smsHistoryEntity.getMessage());
        smsHistoryDTO.setCreatedDate(smsHistoryEntity.getCreatedDate());
        return smsHistoryDTO;
    }

    public List<SmsHistoryDTO> getSmsByGivenDate(LocalDate givenDate) {
        LocalDateTime from = givenDate.atStartOfDay();
        LocalDateTime to = from.plusDays(1);
        List<SmsHistoryEntity> smsHistoryEntityList = smsHistoryRepository.findAllByCreatedDateBetweenOrderByCreatedDateAsc(from, to);
        List<SmsHistoryDTO> smsHistoryDTOList = new LinkedList<>();
        smsHistoryEntityList.forEach(smsHistoryEntity -> {
          smsHistoryDTOList.add(toDTO(smsHistoryEntity)) ;
        });
        return smsHistoryDTOList;
    }

    public Page<SmsHistoryDTO> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SmsHistoryEntity> pageObj = smsHistoryRepository.findAllBy(pageable);
        List<SmsHistoryDTO> smsHistoryDTOList = new LinkedList<>();
        pageObj.forEach(smsHistoryEntity -> {
            smsHistoryDTOList.add(toDTO(smsHistoryEntity));
        });
        Long totalCount = pageObj.getTotalElements();
        return new PageImpl<>(smsHistoryDTOList, pageable, totalCount);
    }

    public void isNotExpiredCode (String phoneNumber) {
      Optional<SmsHistoryEntity>  optional =  smsHistoryRepository.findTopByPhoneOrderByCreatedDateDesc(phoneNumber);
        if (optional.isEmpty()) {
            throw new AppBadException("No record found for phone number " + phoneNumber);
        }
        SmsHistoryEntity entity = optional.get();
        LocalDateTime now = LocalDateTime.now();
        if (entity.getCreatedDate().plusMinutes(2).isBefore(now)) {
            throw new AppBadException("Code has expired");
        }

    }

    public void countSms (String phoneNumber) {
        Optional<SmsHistoryEntity> optional = smsHistoryRepository.findTopByPhoneOrderByCreatedDateDesc(phoneNumber);
        if (optional.isEmpty()) {
            throw new AppBadException("No record found for phone number " + phoneNumber);
        }
        SmsHistoryEntity entity = optional.get();
        if (entity.getCreatedDate().plusMinutes(2).isAfter(LocalDateTime.now())) {
           Integer count = smsHistoryRepository.countByPhoneOrderByCreatedDateDesc(phoneNumber);
           if(count>=3){
               throw new AppBadException("Too many attempts. Try again later ");
           }
        }

    }

}
