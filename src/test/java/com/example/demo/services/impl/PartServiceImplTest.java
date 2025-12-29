package com.example.demo.services.impl;

import com.example.demo.dto.part.PartRequestDto;
import com.example.demo.dto.part.PartResponseDto;
import com.example.demo.mapper.PartMapper;
import com.example.demo.models.Part;
import com.example.demo.repositories.PartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PartServiceImplTest {

    @Mock
    private PartRepository partRepository;

    @Mock
    private PartMapper partMapper;

    @InjectMocks
    private PartServiceImpl partService;

    @Test
    void getAllPartsTest() {
        // given
        Part part = new Part();
        part.setId(1L);
        when(partRepository.findAll()).thenReturn(List.of(part));
        when(partMapper.toResponseDtoList(any())).thenReturn(List.of(PartResponseDto.builder().id(1L).build()));

        // when
        List<PartResponseDto> result = partService.getAllParts();

        // then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    void getPartTest() {
        // given
        Long id = 1L;
        Part part = new Part();
        part.setId(id);
        PartResponseDto responseDto = PartResponseDto.builder().id(id).build();

        when(partRepository.findById(id)).thenReturn(Optional.of(part));
        when(partMapper.toResponseDto(part)).thenReturn(responseDto);

        // when
        PartResponseDto result = partService.getPart(id);

        // then
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void getPartNotFoundTest() {
        // given
        when(partRepository.findById(999L)).thenReturn(Optional.empty());

        // when
        PartResponseDto result = partService.getPart(999L);

        // then
        assertNull(result);
    }

    @Test
    void addPartTest() {
        // given
        PartRequestDto requestDto = PartRequestDto.builder()
                .name("Oil Filter")
                .article("W712/93")
                .price(8500)
                .build();

        Part part = new Part();
        part.setId(1L);

        PartResponseDto responseDto = PartResponseDto.builder().id(1L).build();

        when(partMapper.toEntity(requestDto)).thenReturn(part);
        when(partRepository.save(part)).thenReturn(part);
        when(partMapper.toResponseDto(part)).thenReturn(responseDto);

        // when
        PartResponseDto result = partService.addPart(requestDto);

        // then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(partRepository).save(any());
    }

    @Test
    void updatePartTest() {
        // given
        Long id = 1L;
        PartRequestDto requestDto = PartRequestDto.builder()
                .name("Updated Filter")
                .price(9000)
                .build();

        Part existingPart = new Part();
        existingPart.setId(id);

        when(partRepository.existsById(id)).thenReturn(true);
        when(partMapper.toEntity(requestDto)).thenReturn(existingPart);
        when(partRepository.save(existingPart)).thenReturn(existingPart);
        when(partMapper.toResponseDto(existingPart)).thenReturn(PartResponseDto.builder().id(id).build());

        // when
        PartResponseDto result = partService.updatePart(id, requestDto);

        // then
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(partRepository).save(any());
    }

    @Test
    void updatePartNotFoundTest() {
        // given
        when(partRepository.existsById(999L)).thenReturn(false);

        // when
        PartResponseDto result = partService.updatePart(999L, PartRequestDto.builder().build());

        // then
        assertNull(result);
    }

    @Test
    void deletePartTest() {
        // given
        Long id = 1L;
        when(partRepository.existsById(id)).thenReturn(true);

        // when
        boolean result = partService.deletePart(id);

        // then
        assertTrue(result);
        verify(partRepository).deleteById(id);
    }

    @Test
    void deletePartNotFoundTest() {
        // given
        when(partRepository.existsById(999L)).thenReturn(false);

        // when
        boolean result = partService.deletePart(999L);

        // then
        assertFalse(result);
    }
}