package com.example.Kun_uz.controller;

import com.example.Kun_uz.createDTO.TypeCreateDTO;
import com.example.Kun_uz.dto.TypesDTO;
import com.example.Kun_uz.enums.LanguageEnum;
import com.example.Kun_uz.service.TypesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequestMapping("/types")
@RestController
public class TypesController {
    //  order_number,name_uz, name_ru, name_en
    @Autowired
    private TypesService typesService;
    @PostMapping("/create")
    public ResponseEntity<TypesDTO> create(@Valid @RequestBody TypeCreateDTO typeCreateDTO) {
      TypesDTO response = typesService.create(typeCreateDTO);
      return ResponseEntity.ok().body(response);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<TypesDTO> update(@PathVariable Integer id, @Valid @RequestBody TypeCreateDTO dto) {
       TypesDTO response =  typesService.update(id,dto);
       return ResponseEntity.ok().body(response);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        Boolean response = typesService.delete(id);
        return ResponseEntity.ok().body(response);
    }
    @PostMapping("/all")
    public ResponseEntity<PageImpl<TypesDTO>> pagination(@Valid @RequestParam (name = "page", defaultValue = "1")int page,
                                                     @Valid @RequestParam (name = "size",defaultValue = "10")int size) {
      PageImpl<TypesDTO>  pages = typesService.findAll(page-1,size);
      return ResponseEntity.ok().body(pages);
    }
    @GetMapping("/getByLanguage")
    public ResponseEntity<List<TypesDTO>> getByLanguage(@RequestHeader (name = "Accept-Language",defaultValue = "UZ")LanguageEnum lang) {
        List<TypesDTO> response = typesService.getByLanguage(lang);
        return ResponseEntity.ok().body(response);
    }

}
