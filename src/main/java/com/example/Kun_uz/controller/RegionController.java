package com.example.Kun_uz.controller;

import com.example.Kun_uz.dto.region.RegionCreateDTO;
import com.example.Kun_uz.dto.region.RegionDTO;
import com.example.Kun_uz.enums.LanguageEnum;
import com.example.Kun_uz.service.RegionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @PostMapping("/adm/create")
    public ResponseEntity<RegionDTO> create(@Valid @RequestBody RegionCreateDTO region) {
        RegionDTO response = regionService.create(region);
        return ResponseEntity.ok().body(response);
    }


    @PutMapping("/adm/update/{id}")
    public ResponseEntity<Boolean> updateRegion(@PathVariable("id") Integer id,
                                                @Valid @RequestBody RegionCreateDTO dto) {
        Boolean result = regionService.update(id, dto);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/adm/delete/{id}")
    public ResponseEntity<Boolean> deleteRegion(@PathVariable("id") Integer id) {
        Boolean result = regionService.delete(id);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/adm/all")
    public ResponseEntity<List<RegionDTO>> all() {
        return ResponseEntity.ok().body(regionService.getAll());
    }

    @GetMapping("/lang")
    public ResponseEntity<List<RegionDTO>> getAllByLang(@RequestHeader(name = "Accept-Language",defaultValue = "UZ") LanguageEnum lang) {
        List<RegionDTO> regionDTOList =  regionService.getAllByLang2(lang);
        return ResponseEntity.ok().body(regionDTOList);
    }
}
