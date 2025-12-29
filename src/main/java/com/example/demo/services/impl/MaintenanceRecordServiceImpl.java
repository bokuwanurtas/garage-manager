package com.example.demo.services.impl;

import com.example.demo.dto.maintenance.MaintenanceRecordRequestDto;
import com.example.demo.dto.maintenance.MaintenanceRecordResponseDto;
import com.example.demo.mapper.MaintenanceRecordMapper;
import com.example.demo.models.MaintenanceRecord;
import com.example.demo.models.Vehicle;
import com.example.demo.repositories.MaintenanceRecordRepository;
import com.example.demo.repositories.VehicleRepository;
import com.example.demo.services.MaintenanceRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceRecordServiceImpl implements MaintenanceRecordService {

    private final MaintenanceRecordRepository recordRepository;
    private final VehicleRepository vehicleRepository;
    private final MaintenanceRecordMapper recordMapper;

    @Override
    public List<MaintenanceRecordResponseDto> getAllRecords() {
        List<MaintenanceRecord> records = recordRepository.findAll();
        return recordMapper.toResponseDtoList(records);
    }

    @Override
    public MaintenanceRecordResponseDto getRecord(Long id) {
        return recordRepository.findById(id)
                .map(recordMapper::toResponseDto)
                .orElse(null);
    }

    @Override
    public MaintenanceRecordResponseDto addRecord(MaintenanceRecordRequestDto requestDto) {
        MaintenanceRecord record = recordMapper.toEntity(requestDto);

        // Устанавливаем связь с Vehicle по vehicleId из RequestDto
        Vehicle vehicle = vehicleRepository.findById(requestDto.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        record.setVehicle(vehicle);

        MaintenanceRecord saved = recordRepository.save(record);
        return recordMapper.toResponseDto(saved);
    }

    @Override
    public MaintenanceRecordResponseDto updateRecord(Long id, MaintenanceRecordRequestDto requestDto) {
        if (!recordRepository.existsById(id)) {
            return null;
        }

        MaintenanceRecord record = recordMapper.toEntity(requestDto);
        record.setId(id);  // важно для обновления

        Vehicle vehicle = vehicleRepository.findById(requestDto.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        record.setVehicle(vehicle);

        MaintenanceRecord updated = recordRepository.save(record);
        return recordMapper.toResponseDto(updated);
    }

    @Override
    public boolean deleteRecord(Long id) {
        if (recordRepository.existsById(id)) {
            recordRepository.deleteById(id);
            return true;
        }
        return false;
    }
}