package com.example.Kun_uz.service;

import com.example.Kun_uz.Mapper.ArticleShortInfoMapper;
import com.example.Kun_uz.dto.article.ArticleCreateDTO;
import com.example.Kun_uz.dto.article.ArticleDTO;
import com.example.Kun_uz.dto.article.ArticleResponseDTO;
import com.example.Kun_uz.dto.article.ArticleUpdateDTO;
import com.example.Kun_uz.entity.*;
import com.example.Kun_uz.enums.ArticleStatus;
import com.example.Kun_uz.enums.LanguageEnum;
import com.example.Kun_uz.exp.AppBadException;
import com.example.Kun_uz.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ArticleService {
    @Value("${server.url}")
    private String serverUrl;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleTypesService articleTypesService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private CategoryService categoryService;


    public ArticleDTO create(ArticleCreateDTO articleDTO) {
        ArticleEntity articleEntity = new ArticleEntity();
        //  title,description,content,image_id, region_id,category_id, articleType(array)

        articleEntity.setTitle(articleDTO.getTitle());
        articleEntity.setDescription(articleDTO.getDescription());
        articleEntity.setContent(articleDTO.getContent());
        articleEntity.setImageId(articleDTO.getImageId());
        articleEntity.setRegionId(articleEntity.getRegionId());
        articleEntity.setCategoryId(articleDTO.getCategoryId());
        articleEntity.setStatus(ArticleStatus.NOT_PUBLISHED);

        articleRepository.save(articleEntity);
        articleTypesService.create(articleEntity.getId(), articleDTO.getTypes());

        return toDTO(articleEntity);
    }

    public ArticleDTO update(ArticleUpdateDTO articleDTO) {
        // title,description,content,shared_count,image_id, region_id,category_id
        ArticleEntity articleEntity = get(articleDTO.getArticleId());
        articleEntity.setTitle(articleDTO.getTitle());
        articleEntity.setDescription(articleDTO.getDescription());
        articleEntity.setContent(articleDTO.getContent());
        articleEntity.setImageId(articleDTO.getImageId());
        articleEntity.setRegionId(articleDTO.getRegionId());
        articleEntity.setCategoryId(articleDTO.getCategoryId());
        articleEntity.setStatus(ArticleStatus.NOT_PUBLISHED);
        articleRepository.save(articleEntity);

        articleTypesService.merge(articleDTO.getArticleId(), articleDTO.getTypes());

        return toDTO(articleEntity);
    }

    public Boolean delete(String articleId) {
        ArticleEntity articleEntity = get(articleId);
        if (articleEntity != null) {
            articleEntity.setVisible(false);
            articleRepository.save(articleEntity);
            return true;
        }
        return false;
    }

    public ArticleEntity get(String articleId) {
        Optional<ArticleEntity> optional = articleRepository.findByIdIs(articleId);
        if (optional.isEmpty()) {
            throw new AppBadException("Article Not Found");
        }
        return optional.get();
    }

    public String changeStatus(String articleId) {
        ArticleEntity articleEntity = get(articleId);
        if (articleEntity == null) {
            throw new AppBadException("Article Not Found");
        }
        if (articleEntity.getStatus() == ArticleStatus.PUBLISHED) {
            throw new AppBadException("Article Published earlier");
        }
        if (articleEntity.getVisible().equals(false)) {
            throw new AppBadException("Article Not Visible");
        }
        articleEntity.setStatus(ArticleStatus.PUBLISHED);
        articleEntity.setPublishedDate(LocalDateTime.now());
        articleRepository.save(articleEntity);
        return "Article Published successfully";
    }

    public List<ArticleResponseDTO> getLast5ByTypes(Integer typesId) {
        // id(uuid),title,description,image(id,url),published_date
        List<ArticleShortInfoMapper> mapperList = articleRepository.findLast5ByArticleTypes(typesId, 5);
        return mapperList.stream()
                .map(mapper -> mapperToDTO(mapper))
                .collect(Collectors.toList());
    }

    public List<ArticleResponseDTO> getLast3ByTypes(Integer typesId) {
        // id(uuid),title,description,image(id,url),published_date
        List<ArticleShortInfoMapper> mapperList = articleRepository.findLast5ByArticleTypes(typesId, 3);
        return mapperList.stream()
                .map(mapper -> mapperToDTO(mapper))
                .collect(Collectors.toList());
    }

    public ArticleResponseDTO mapperToDTO(ArticleShortInfoMapper mapper) {
        ArticleResponseDTO articleResponse = new ArticleResponseDTO();
        articleResponse.setId(mapper.getId());
        articleResponse.setTitle(mapper.getTitle());
        articleResponse.setDescription(mapper.getDescription());
        articleResponse.setPublishedDate(mapper.getPublishedDate());
        articleResponse.setImageUrl(serverUrl + "/" + mapper.getImageId());  //TODO rasmni Dto shaklida olib kelish kk
        return articleResponse;
    }

    public ArticleDTO toDTO(ArticleEntity articleEntity) {

        ArticleDTO dto = new ArticleDTO();
        dto.setId(articleEntity.getId());
        dto.setTitle(articleEntity.getTitle());
        dto.setSharedCount(articleEntity.getSharedCount());
        dto.setDescription(articleEntity.getDescription());
        dto.setContent(articleEntity.getContent());
        dto.setImageId(articleEntity.getImageId());
        dto.setRegion(articleEntity.getRegion());

        return dto;
    }

    public List<ArticleResponseDTO> get8ArticlesNotIn(List<String> articleIds) {
        List<ArticleShortInfoMapper> mapperList = articleRepository.find8ArticlesNotIn(articleIds);
        return mapperList.stream()
                .map(mapper -> mapperToDTO(mapper))
                .collect(Collectors.toList());
    }

    public List<ArticleResponseDTO> getArticleByIdAndLang(String articleId, LanguageEnum lang) {
        ArticleEntity articleEntity = get(articleId);
        if (articleEntity == null) {
            throw new AppBadException("Article Not Found");
        }
        /*    id(uuid),title,description,content,shared_count,
        region(key,name),category(key,name),published_date,view_count,like_count,
        tagList(name)*/
        ArticleResponseDTO dto = new ArticleResponseDTO();
        dto.setId(articleEntity.getId());
        dto.setTitle(articleEntity.getTitle());
        dto.setDescription(articleEntity.getDescription());
        dto.setContent(articleEntity.getContent());
        dto.setSharedCount(articleEntity.getSharedCount());

        dto.setRegion(regionService.getRegion(articleEntity.getRegionId(), lang));
        dto.setCategory(categoryService.getCategory(articleEntity.getCategoryId(), lang));
        dto.setPublishedDate(articleEntity.getPublishedDate());

        dto.setViewsCount(articleEntity.getViewCount());


        return null;
        //TODO DTO ni qolgan qismlarini tugallash kerak

    }


    public List<ArticleResponseDTO> getLast4ByTypesExcludingOneArticle(Integer typesId, String articleId) {
     //   List<ArticleShortInfoMapper> mapperList = articleRepository.find4ArticleByTypesExcludingOne(typesId, articleId);
        return articleRepository.find4ArticleByTypesExcludingOne(typesId, articleId)
                .stream()
                .map(this::mapperToDTO)
                .toList();
    }
}
