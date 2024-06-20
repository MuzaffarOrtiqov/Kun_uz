package com.example.Kun_uz.controller;

import com.example.Kun_uz.dto.article.ArticleCreateDTO;
import com.example.Kun_uz.dto.article.ArticleDTO;
import com.example.Kun_uz.dto.article.ArticleResponseDTO;
import com.example.Kun_uz.dto.article.ArticleUpdateDTO;
import com.example.Kun_uz.enums.LanguageEnum;
import com.example.Kun_uz.service.ArticleService;
import jakarta.validation.Valid;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequestMapping("/article")
@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /* CREATE (Moderator) status(NotPublished)
        (title,description,content,image_id, region_id,category_id, articleType(array))*/
 //   @PreAuthorize("hasRole(ROLE_MODERATOR)")
    @PostMapping("/create")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<ArticleDTO> create(@Valid @RequestBody ArticleCreateDTO articleDTO) {
        return ResponseEntity.ok(articleService.create(articleDTO));
    }
    @PutMapping("/update")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<ArticleDTO> update(@Valid @RequestBody ArticleUpdateDTO articleDTO) {
        return ResponseEntity.ok(articleService.update(articleDTO));
    }
    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<Boolean> delete (@RequestParam(name = "articleId") String articleId) {
        return ResponseEntity.ok(articleService.delete(articleId));
    }

    @PutMapping("/changeStatus")
    @PreAuthorize("hasRole('PUBLISH')")
    public ResponseEntity<String> changeStatus(@RequestParam(name = "articleId") String articleId){
        return ResponseEntity.ok(articleService.changeStatus(articleId));
    }

    @GetMapping("/getLast5")
    @PreAuthorize("permitAll")
    public ResponseEntity<List<ArticleResponseDTO>> getLast5ByTypesId(@RequestParam(name = "typesId") Integer typesId){
        return ResponseEntity.ok(articleService.getLast5ByTypes(typesId));
    }
    @GetMapping("/getLast3")
    @PreAuthorize("permitAll")
    public ResponseEntity<List<ArticleResponseDTO>> getLast3ByTypesId(@RequestParam(name = "typesId") Integer typesId){
        return ResponseEntity.ok(articleService.getLast3ByTypes(typesId));
    }

    @PostMapping("/last/eight")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<ArticleResponseDTO>> get8NotInByList(@RequestBody List<String> articleIds){
        return ResponseEntity.ok(articleService.get8ArticlesNotIn(articleIds));
    }

    @GetMapping("/get/id/lang")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<ArticleResponseDTO>> getArticleByIdAndLang(@RequestParam String articleId, @RequestHeader("Accept-Language") LanguageEnum lang){
        return ResponseEntity.ok(articleService.getArticleByIdAndLang(articleId,lang));
    }

    @GetMapping("/types/last/four")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<ArticleResponseDTO>> getLast4ByTypesExcludingOneArticle(@RequestParam(name = "typesId")Integer typesId,@RequestParam(name = "articleId")String articleId ){
        return ResponseEntity.ok(articleService.getLast4ByTypesExcludingOneArticle(typesId,articleId));
    }




}
