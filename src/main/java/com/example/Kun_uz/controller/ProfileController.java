package com.example.Kun_uz.controller;

import com.example.Kun_uz.dto.profile.ProfileCreateDTo;
import com.example.Kun_uz.dto.profile.ProfileDTO;
import com.example.Kun_uz.dto.profile.ProfileFilterDTO;
import com.example.Kun_uz.dto.profile.ProfileUpdateDTO;
import com.example.Kun_uz.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/profile")
@RestController
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping("/adm/create")
        //For admin
    ResponseEntity<ProfileDTO> create(@Valid @RequestBody ProfileCreateDTo profileDTO) {
        //  SecurityUtil.getJwtDTO(token, ProfileRole.ROLE_ADMIN);
        //  JwtDTO dto = HttpRequestUtil.getJwtDTO(request,ProfileRole.ROLE_ADMIN);
        ProfileDTO response = profileService.create(profileDTO);

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/current/{id}")
    ResponseEntity<Boolean> updateUser(@PathVariable(name = "id") Integer userId,
                                       @RequestBody ProfileUpdateDTO profileDTO) {

        return ResponseEntity.ok().body(profileService.update(userId, profileDTO));

    }

    @PutMapping("/adm/update/{id}")
    ResponseEntity<ProfileDTO> updateForAdmin(@PathVariable Integer userId, @RequestBody ProfileDTO profileDTO) {
        //SecurityUtil.getJwtDTO(token,ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok().body(profileService.updateForAdmin(userId, profileDTO));
    }

    @GetMapping("/adm/page")
    ResponseEntity<Page<ProfileDTO>> getAllProfile(@RequestParam int page, @RequestParam int size) {
        Page<ProfileDTO> pages = profileService.getAll(page - 1, size);
        return ResponseEntity.ok().body(pages);
    }

    @DeleteMapping("/adm/delete/{id}")
    ResponseEntity<Boolean> deleteProfile(@PathVariable Integer id) {
        return ResponseEntity.ok().body((profileService.deleleById(id)));
    }

    @PutMapping("/updatePhoto/{id}")
    ResponseEntity<ProfileDTO> updateProfilePhoto(@PathVariable Integer id, @RequestParam Integer photo_id) {
        return ResponseEntity.ok().body(profileService.updatePhoto(id, photo_id));
    }

    @PostMapping("/adm/filter")
    @PreAuthorize("hasRole('ROLE_ADMIN ')")
    public ResponseEntity<PageImpl<ProfileDTO>> filter(@RequestParam(value = "page", defaultValue = "1") int page,
                                                       @RequestParam(value = "size", defaultValue = "10") int size,
                                                       @RequestBody ProfileFilterDTO filter) {
        PageImpl<ProfileDTO> profileDTOPage = profileService.filter(filter, page - 1, size);
        return ResponseEntity.ok().body(profileDTOPage);
    }


}
