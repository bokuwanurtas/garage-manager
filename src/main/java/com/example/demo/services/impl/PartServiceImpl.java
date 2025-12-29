package com.example.demo.services.impl;

import com.example.demo.dto.part.PartRequestDto;
import com.example.demo.dto.part.PartResponseDto;
import com.example.demo.mapper.PartMapper;
import com.example.demo.models.Part;
import com.example.demo.repositories.PartRepository;
import com.example.demo.services.PartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;
    private final PartMapper partMapper;

    @Override
    public List<PartResponseDto> getAllParts() {
        List<Part> parts = partRepository.findAll();
        return partMapper.toResponseDtoList(parts);
    }

    @Override
    public PartResponseDto getPart(Long id) {
        return partRepository.findById(id)
                .map(partMapper::toResponseDto)
                .orElse(null);
    }

    @Override
    public PartResponseDto addPart(PartRequestDto requestDto) {
        Part part = partMapper.toEntity(requestDto);
        Part saved = partRepository.save(part);
        return partMapper.toResponseDto(saved);
    }

    @Override
    public PartResponseDto updatePart(Long id, PartRequestDto requestDto) {
        if (!partRepository.existsById(id)) {
            return null;
        }
        Part part = partMapper.toEntity(requestDto);
        part.setId(id);  // важно для обновления
        Part updated = partRepository.save(part);
        return partMapper.toResponseDto(updated);
    }

    @Override
    public boolean deletePart(Long id) {
        if (partRepository.existsById(id)) {
            partRepository.deleteById(id);
            return true;
        }
        return false;
    }
}