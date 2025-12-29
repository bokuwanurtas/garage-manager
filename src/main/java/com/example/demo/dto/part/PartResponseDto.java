package com.example.demo.dto.part;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PartResponseDto {

    private Long id;
    private String name;
    private String article;
    private int price;
}