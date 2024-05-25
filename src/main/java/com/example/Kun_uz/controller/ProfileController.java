package com.example.Kun_uz.controller;

import com.example.Kun_uz.dto.createDTO.ProfileCreateDTo;
import com.example.Kun_uz.dto.profile.ProfileDTO;
import com.example.Kun_uz.dto.profile.ProfileFilterDTO;
import com.example.Kun_uz.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/profile")
@RestController
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    @PostMapping("/create")
    ResponseEntity<ProfileDTO> create (@Valid @RequestBody ProfileCreateDTo profileDTO) {
        return ResponseEntity.ok().body(profileService.create(profileDTO));
    }
    @PutMapping("/update/{id}")
    ResponseEntity<ProfileDTO> updateForUser ( @PathVariable  Integer id, @RequestBody ProfileCreateDTo profileDTO) {
        return ResponseEntity.ok().body(profileService.update(id,profileDTO));

    }
    @PutMapping("/updateForAdmin/{id}")
    ResponseEntity<ProfileDTO> updateForAdmin ( @PathVariable  Integer id, @RequestBody ProfileDTO profileDTO) {
        return ResponseEntity.ok().body(profileService.updateForAdmin(id,profileDTO));
    }

   @GetMapping("/page")
    ResponseEntity<Page<ProfileDTO>> getAllProfile (@RequestParam int page, @RequestParam int size){
        Page<ProfileDTO> pages = profileService.getAll(page-1,size);
        return ResponseEntity.ok().body(pages);
    }
    @DeleteMapping("/delete/{id}")
    ResponseEntity<Boolean> deleteProfile (@PathVariable Integer id) {
        return ResponseEntity.ok().body((profileService.deleleById(id)));
    }
    @PutMapping("/updatePhoto/{id}")
    ResponseEntity<ProfileDTO> updateProfilePhoto (@PathVariable Integer id, @RequestParam Integer photo_id) {
        return  ResponseEntity.ok().body(profileService.updatePhoto(id,photo_id));
    }
    @PostMapping("/filter")
    public ResponseEntity<PageImpl<ProfileDTO>> filter(@RequestParam(value = "page", defaultValue = "1") int page,
                                                       @RequestParam(value = "size", defaultValue = "10") int size,
                                                       @RequestBody ProfileFilterDTO filter) {
        PageImpl<ProfileDTO> profileDTOPage = profileService.filter(filter, page - 1, size);
        return ResponseEntity.ok().body(profileDTOPage);
    }


}
