package com.example.demo.services.impl;

import com.example.demo.dto.vehicle.VehicleRequestDto;
import com.example.demo.dto.vehicle.VehicleResponseDto;
import com.example.demo.mapper.VehicleMapper;
import com.example.demo.models.Vehicle;
import com.example.demo.repositories.VehicleRepository;
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
class VehicleServiceImplTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private VehicleMapper vehicleMapper;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    @Test
    void getAllVehiclesTest() {
        // given
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        when(vehicleRepository.findAll()).thenReturn(List.of(vehicle));
        when(vehicleMapper.toResponseDtoList(any())).thenReturn(List.of(VehicleResponseDto.builder().id(1L).build()));

        // when
        List<VehicleResponseDto> result = vehicleService.getAllVehicles();

        // then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    void addVehicleTest() {
        // given
        VehicleRequestDto requestDto = VehicleRequestDto.builder()
                .brand("Toyota")
                .model("Camry")
                .build();

        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);

        VehicleResponseDto responseDto = VehicleResponseDto.builder().id(1L).build();

        when(vehicleMapper.toEntity(requestDto)).thenReturn(vehicle);
        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);
        when(vehicleMapper.toResponseDto(vehicle)).thenReturn(responseDto);

        // when
        VehicleResponseDto result = vehicleService.addVehicle(requestDto);

        // then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(vehicleRepository).save(any());
    }

    @Test
    void updateVehicleTest() {
        // given
        Long id = 1L;
        VehicleRequestDto requestDto = VehicleRequestDto.builder().brand("Updated").build();

        Vehicle existing = new Vehicle();
        existing.setId(id);

        when(vehicleRepository.existsById(id)).thenReturn(true);
        when(vehicleMapper.toEntity(requestDto)).thenReturn(existing);
        when(vehicleRepository.save(existing)).thenReturn(existing);
        when(vehicleMapper.toResponseDto(existing)).thenReturn(VehicleResponseDto.builder().id(id).build());

        // when
        VehicleResponseDto result = vehicleService.updateVehicle(id, requestDto);

        // then
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void updateVehicleNotFoundTest() {
        // given
        when(vehicleRepository.existsById(1L)).thenReturn(false);

        // when
        VehicleResponseDto result = vehicleService.updateVehicle(1L, VehicleRequestDto.builder().build());

        // then
        assertNull(result);
    }

    @Test
    void deleteVehicleTest() {
        // given
        when(vehicleRepository.existsById(1L)).thenReturn(true);

        // when
        boolean result = vehicleService.deleteVehicle(1L);

        // then
        assertTrue(result);
        verify(vehicleRepository).deleteById(1L);
    }

    @Test
    void deleteVehicleNotFoundTest() {
        // given
        when(vehicleRepository.existsById(1L)).thenReturn(false);

        // when
        boolean result = vehicleService.deleteVehicle(1L);

        // then
        assertFalse(result);
    }
}