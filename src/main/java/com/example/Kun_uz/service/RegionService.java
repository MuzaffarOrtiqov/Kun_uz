package com.example.Kun_uz.service;

import com.example.Kun_uz.Mapper.RegionMapper;
import com.example.Kun_uz.dto.region.RegionCreateDTO;
import com.example.Kun_uz.dto.region.RegionDTO;
import com.example.Kun_uz.entity.RegionEntity;
import com.example.Kun_uz.enums.LanguageEnum;
import com.example.Kun_uz.exp.AppBadException;
import com.example.Kun_uz.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;


    public RegionDTO create(RegionCreateDTO dto) {
        RegionEntity entity = new RegionEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());

        regionRepository.save(entity);
        return toDTO(entity);
    }

    public Boolean update(Integer id, RegionCreateDTO dto) {
        RegionEntity entity = get(id);
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        regionRepository.save(entity);
        return true;
    }

    public Boolean delete(Integer id) {
        regionRepository.deleteById(id);
        return true;
    }

    public RegionEntity get(Integer id) {
        return regionRepository.findById(id).orElseThrow(() -> {
            throw new AppBadException("Region not found");
        });
    }

    public List<RegionDTO> getAll() {
        Iterable<RegionEntity> iterable = regionRepository.findAll();
        List<RegionDTO> dtoList = new LinkedList<>();
        for (RegionEntity entity : iterable) {
            dtoList.add(toDTO(entity));
        }
        return dtoList;
    }

    public List<RegionDTO> getAllByLang(LanguageEnum lang) {
        Iterable<RegionEntity> iterable = regionRepository.findAllByVisibleTrueOrderByOrderNumberDesc();
        List<RegionDTO> dtoList = new LinkedList<>();
        for (RegionEntity entity : iterable) {
            RegionDTO dto = new RegionDTO();
            dto.setId(entity.getId());
            switch (lang) {
                case EN -> dto.setNameEn(entity.getNameEn());
                case RU -> dto.setNameRu(entity.getNameRu());
                case UZ -> dto.setNameUz(entity.getNameUz());
            }
            dtoList.add(dto);
        }
        return dtoList;
    }

    public RegionDTO getRegion (Integer id, LanguageEnum lang) {
        RegionEntity entity = get(id);
        RegionDTO dto = new RegionDTO();
        dto.setId(entity.getId());
        switch (lang) {
            case EN -> dto.setNameEn(entity.getNameEn());
            case RU -> dto.setNameRu(entity.getNameRu());
            default -> dto.setNameUz(entity.getNameUz());
        }
        return dto;
    }

    public List<RegionDTO> getAllByLang2(LanguageEnum lang) {
        List<RegionMapper> mapperList = regionRepository.findAll(lang.name());
        List<RegionDTO> dtoList = new LinkedList<>();
        for (RegionMapper entity : mapperList) {
            RegionDTO dto = new RegionDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dtoList.add(dto);
        }
        return dtoList;
    }



    public RegionDTO toDTO(RegionEntity entity) {
        RegionDTO dto = new RegionDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameEn(entity.getNameEn());
        dto.setNameRu(entity.getNameRu());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

}
