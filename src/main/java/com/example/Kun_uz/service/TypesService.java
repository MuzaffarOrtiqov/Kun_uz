package com.example.Kun_uz.service;

import com.example.Kun_uz.Mapper.TypesMapper;
import com.example.Kun_uz.dto.types.TypeCreateDTO;
import com.example.Kun_uz.dto.types.TypesDTO;
import com.example.Kun_uz.entity.TypesEntity;

import com.example.Kun_uz.enums.LanguageEnum;
import com.example.Kun_uz.exp.ResourceNotFoundException;
import com.example.Kun_uz.repository.TypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class TypesService {
    @Autowired
    private TypesRepository typesRepository;

    public TypesDTO create(TypeCreateDTO dto) {
        TypesEntity typesEntity = new TypesEntity();
        typesEntity.setOrderNumber(dto.getOrderNumber());
        typesEntity.setNameEn(dto.getNameEn());
        typesEntity.setNameUz(dto.getNameUz());
        typesEntity.setNameRu(dto.getNameRu());

        typesRepository.save(typesEntity);
        return toDto(typesEntity);
    }

    public TypesDTO toDto(TypesEntity typesEntity) {
        TypesDTO typesDTO = new TypesDTO();
        typesDTO.setId(typesEntity.getId());
        typesDTO.setNameEn(typesEntity.getNameEn());
        typesDTO.setNameRu(typesEntity.getNameRu());
        typesDTO.setNameUz(typesEntity.getNameUz());
        typesDTO.setOrderNumber(typesEntity.getOrderNumber());
        typesDTO.setCreatedDate(typesEntity.getCreatedDate());
        return typesDTO;
    }

    public TypesDTO update(Integer id, TypeCreateDTO dto) {
        TypesEntity typesEntity = get(id);
        typesEntity.setOrderNumber(dto.getOrderNumber());
        typesEntity.setNameUz(dto.getNameUz());
        typesEntity.setNameRu(dto.getNameRu());
        typesEntity.setNameEn(dto.getNameEn());

        typesRepository.save(typesEntity);
        return toDto(typesEntity);
    }

    public TypesEntity get(Integer id) {
        Optional<TypesEntity> optional = typesRepository.findById(id);
        optional.orElseThrow(() -> {
            return new ResourceNotFoundException("No Types found with id: " + id);
        });
        return optional.get();
    }

    public Boolean delete(Integer id) {
        typesRepository.deleteById(id);
        return true;
    }

    public PageImpl<TypesDTO> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TypesEntity> pages = typesRepository.findAll(pageable);
        List<TypesDTO> dtoList = new LinkedList<>();

        pages.forEach(typesEntity -> {
            dtoList.add(toDto(typesEntity));
        });

        Long totalElements = pages.getTotalElements();
        return new PageImpl<>(dtoList, pageable, totalElements);
    }

    public List<TypesDTO> getByLanguage(LanguageEnum language) {
        Iterable<TypesEntity> iterable = typesRepository.findAllByVisibleTrueOrderByOrderNumberDesc();
        List<TypesDTO> dtoList = new LinkedList<>();
        iterable.forEach(typesEntity -> {
            TypesDTO dto = new TypesDTO();
            dto.setId(typesEntity.getId());
            switch (language) {
                case EN -> dto.setNameEn(typesEntity.getNameEn());
                case RU -> dto.setNameRu(typesEntity.getNameRu());
                case UZ -> dto.setNameUz(typesEntity.getNameUz());
            }
            dtoList.add(dto);
        });
        return dtoList;
    }

    public List<TypesDTO> getByLanguage2(LanguageEnum language) {
        List<TypesMapper> mapperList = typesRepository.findAllBy(language.name());
        List<TypesDTO> dtoList = new LinkedList<>();
        mapperList.forEach(typesMapper -> {
            TypesDTO dto = new TypesDTO();
            dto.setId(typesMapper.getId());
            dto.setName(typesMapper.getName());
            dtoList.add(dto);
        });
        return dtoList;
    }


}
