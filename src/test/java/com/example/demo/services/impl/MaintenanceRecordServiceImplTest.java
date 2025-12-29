package com.example.demo.services.impl;

import com.example.demo.dto.maintenance.MaintenanceRecordRequestDto;
import com.example.demo.dto.maintenance.MaintenanceRecordResponseDto;
import com.example.demo.mapper.MaintenanceRecordMapper;
import com.example.demo.models.MaintenanceRecord;
import com.example.demo.models.Vehicle;
import com.example.demo.repositories.MaintenanceRecordRepository;
import com.example.demo.repositories.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MaintenanceRecordServiceImplTest {

    @Mock
    private MaintenanceRecordRepository recordRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private MaintenanceRecordMapper recordMapper;

    @InjectMocks
    private MaintenanceRecordServiceImpl maintenanceRecordService;

    @Test
    void addRecordTest() {
        // given
        MaintenanceRecordRequestDto requestDto = MaintenanceRecordRequestDto.builder()
                .vehicleId(1L)
                .description("Oil change")
                .build();

        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);

        MaintenanceRecord record = new MaintenanceRecord();
        record.setId(1L);

        MaintenanceRecordResponseDto responseDto = MaintenanceRecordResponseDto.builder().id(1L).build();

        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(recordMapper.toEntity(requestDto)).thenReturn(record);
        when(recordRepository.save(record)).thenReturn(record);
        when(recordMapper.toResponseDto(record)).thenReturn(responseDto);

        // when
        MaintenanceRecordResponseDto result = maintenanceRecordService.addRecord(requestDto);

        // then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(vehicle, record.getVehicle());
        verify(recordRepository).save(any());
    }

    @Test
    void addRecordVehicleNotFoundTest() {
        // given
        MaintenanceRecordRequestDto requestDto = MaintenanceRecordRequestDto.builder()
                .vehicleId(999L)
                .build();

        when(vehicleRepository.findById(999L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(RuntimeException.class, () -> maintenanceRecordService.addRecord(requestDto));
    }
}