package com.example.Kun_uz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FilterResponseDTO <T> {
    private List<T> content;
    private Long totalCount;
}
