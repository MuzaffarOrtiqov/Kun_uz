package com.example.Kun_uz.createDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryCreateDTO {
    @NotNull(message = "Order number is mandatory")
    private Integer orderNumber;
    @NotBlank(message = "Name Uz is mandatory")
    private String nameUz;
    @NotBlank(message = "Name Ru is mandatory")
    private String nameRu;
    @NotBlank(message = "Name En is mandatory")
    private String nameEn;
}
