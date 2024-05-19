package com.example.Kun_uz.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TypeCreateDTO {
    @NotNull(message = "Order number is mandatory")
    private Integer orderNumber;
    @NotBlank(message = "Name uz is mandatory")
    private String nameUz;
    @NotBlank(message = "Name ru is mandatory")
    private String nameRu;
    @NotBlank(message = "Name en is mandatory")
    private String nameEn;
}
