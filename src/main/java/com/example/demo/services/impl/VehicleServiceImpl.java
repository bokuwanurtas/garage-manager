package com.example.demo.services.impl;

import com.example.demo.dto.vehicle.VehicleRequestDto;
import com.example.demo.dto.vehicle.VehicleResponseDto;
import com.example.demo.mapper.VehicleMapper;
import com.example.demo.models.Vehicle;
import com.example.demo.repositories.VehicleRepository;
import com.example.demo.services.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    @Override
    public List<VehicleResponseDto> getAllVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        return vehicleMapper.toResponseDtoList(vehicles);
    }

    @Override
    public VehicleResponseDto getVehicle(Long id) {
        return vehicleRepository.findById(id)
                .map(vehicleMapper::toResponseDto)
                .orElse(null);
    }

    @Override
    public VehicleResponseDto addVehicle(VehicleRequestDto requestDto) {
        Vehicle vehicle = vehicleMapper.toEntity(requestDto);
        Vehicle saved = vehicleRepository.save(vehicle);
        return vehicleMapper.toResponseDto(saved);
    }

    @Override
    public VehicleResponseDto updateVehicle(Long id, VehicleRequestDto requestDto) {
        if (!vehicleRepository.existsById(id)) {
            return null;
        }
        Vehicle vehicle = vehicleMapper.toEntity(requestDto);
        vehicle.setId(id);  // важно установить id для обновления
        Vehicle updated = vehicleRepository.save(vehicle);
        return vehicleMapper.toResponseDto(updated);
    }

    @Override
    public boolean deleteVehicle(Long id) {
        if (vehicleRepository.existsById(id)) {
            vehicleRepository.deleteById(id);
            return true;
        }
        return false;
    }
}