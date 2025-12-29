package com.example.demo.mapper;

import com.example.demo.dto.part.PartRequestDto;
import com.example.demo.dto.part.PartResponseDto;
import com.example.demo.models.Part;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PartMapper {

    PartResponseDto toResponseDto(Part part);

    List<PartResponseDto> toResponseDtoList(List<Part> parts);

    @Mapping(target = "id", ignore = true)
    Part toEntity(PartRequestDto requestDto);
}