package com.example.demo.services;

import com.example.demo.dto.part.PartRequestDto;
import com.example.demo.dto.part.PartResponseDto;

import java.util.List;

public interface PartService {

    List<PartResponseDto> getAllParts();

    PartResponseDto getPart(Long id);

    PartResponseDto addPart(PartRequestDto requestDto);

    PartResponseDto updatePart(Long id, PartRequestDto requestDto);

    boolean deletePart(Long id);
}