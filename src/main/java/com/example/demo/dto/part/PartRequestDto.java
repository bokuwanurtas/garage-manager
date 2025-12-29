package com.example.demo.dto.part;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartRequestDto {

    private String name;
    private String article;
    private int price;
}