package com.example.Kun_uz.service;

import com.example.Kun_uz.createDTO.ProfileCreateDTo;
import com.example.Kun_uz.dto.FilterResponseDTO;
import com.example.Kun_uz.dto.ProfileDTO;
import com.example.Kun_uz.dto.ProfileFilterDTO;
import com.example.Kun_uz.entity.ProfileEntity;
import com.example.Kun_uz.exp.ResourceNotFoundException;
import com.example.Kun_uz.repository.ProfileCustomRepository;
import com.example.Kun_uz.repository.ProfileRepository;
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
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileCustomRepository profileCustomRepository;


    public ProfileDTO create(ProfileCreateDTo profileDTO) {
        //name,surname,email,phone,password,status,role
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setName(profileDTO.getName());
        profileEntity.setSurname(profileDTO.getSurname());
        profileEntity.setEmail(profileDTO.getEmail());
        profileEntity.setPhone(profileDTO.getPhone());
        profileEntity.setPassword(profileDTO.getPassword());
        profileEntity.setStatus(profileDTO.getStatus());
        profileEntity.setRole(profileDTO.getRole());

        profileRepository.save(profileEntity);
        return toDTOAmin(profileEntity);
    }

    public ProfileDTO toDTOAmin(ProfileEntity profileEntity) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(profileEntity.getId());
        profileDTO.setName(profileEntity.getName());
        profileDTO.setSurname(profileEntity.getSurname());
        profileDTO.setEmail(profileEntity.getEmail());
        profileDTO.setPhone(profileEntity.getPhone());
        profileDTO.setPassword(profileEntity.getPassword());
        profileDTO.setStatus(profileEntity.getStatus());
        profileDTO.setRole(profileEntity.getRole());
        profileDTO.setVisible(profileEntity.getVisible());
        profileDTO.setCreatedDate(profileEntity.getCreatedDate());
        profileDTO.setPhotoId(profileEntity.getPhotoId());
        return profileDTO;

    }

    public ProfileDTO toDTOUser(ProfileEntity profileEntity) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(profileEntity.getId());
        profileDTO.setName(profileEntity.getName());
        profileDTO.setSurname(profileEntity.getSurname());
        profileDTO.setEmail(profileEntity.getEmail());
        profileDTO.setPhone(profileEntity.getPhone());
        profileDTO.setPassword(profileEntity.getPassword());
        profileDTO.setStatus(profileEntity.getStatus());
        profileDTO.setRole(profileEntity.getRole());
        return profileDTO;
    }

    public ProfileDTO update(Integer id, ProfileCreateDTo profileDTO) {
        ProfileEntity profileEntity = get(id);
        profileEntity.setName(profileDTO.getName());
        profileEntity.setSurname(profileDTO.getSurname());
        profileEntity.setEmail(profileDTO.getEmail());
        profileEntity.setPhone(profileDTO.getPhone());
        profileEntity.setPassword(profileDTO.getPassword());
        profileRepository.save(profileEntity);
        return toDTOUser(profileEntity);
    }

     public ProfileDTO  updateForAdmin (Integer id, ProfileDTO profileDTO) {
        ProfileEntity profileEntity = get(id);
        profileEntity.setName(profileDTO.getName());
        profileEntity.setSurname(profileDTO.getSurname());
        profileEntity.setEmail(profileDTO.getEmail());
        profileEntity.setPhone(profileDTO.getPhone());
        profileEntity.setPassword(profileDTO.getPassword());
        profileEntity.setRole(profileDTO.getRole());
        profileEntity.setStatus(profileDTO.getStatus());
        profileEntity.setVisible(profileDTO.getVisible());
        profileRepository.save(profileEntity);
        return  toDTOAmin(profileEntity);
     }

    public ProfileEntity get(Integer id) {
        Optional<ProfileEntity> profileEntity = profileRepository.findById(id);
        if (profileEntity.isPresent()) {
            return profileEntity.get();
        }
        return profileEntity.orElseThrow(() -> new ResourceNotFoundException("Profile not found"));
    }

    public PageImpl<ProfileDTO> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProfileEntity> pageObj = profileRepository.findAll(pageable);
        List<ProfileDTO> profileDTOList = new LinkedList<>();
        pageObj.forEach(profileEntity -> {
            profileDTOList.add(toDTOAmin(profileEntity));
        });
        Long total = pageObj.getTotalElements();
        return new PageImpl<>(profileDTOList, pageable, total);
    }

    public Boolean deleleById(Integer id) {
        profileRepository.deleteById(id);
        return true;
    }

    public ProfileDTO updatePhoto(Integer id, Integer photo_id) {
        ProfileEntity profileEntity = get(id);
        if (profileEntity.getPhotoId()!=null) {
            profileEntity.setId(null);
        }
        profileEntity.setPhotoId(photo_id);
        profileRepository.save(profileEntity);
        return toDTOAmin(profileEntity);
    }

    // TODO: HOW TO implement filter for createdDateFrom and CreatedDateTo

    public PageImpl<ProfileDTO> filter(ProfileFilterDTO filter, int page, int size) {
        FilterResponseDTO<ProfileEntity> filterResponse = profileCustomRepository.filter(filter, page, size);

        List<ProfileDTO> dtoList = new LinkedList<>();
        for (ProfileEntity entity : filterResponse.getContent()) {
            ProfileDTO dto = new ProfileDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setSurname(entity.getSurname());
            dtoList.add(dto);
        }
        return new PageImpl<ProfileDTO>( dtoList, PageRequest.of(page,size), filterResponse.getTotalCount());
    }





}
