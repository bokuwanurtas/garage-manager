package com.example.demo.services;

import com.example.demo.dto.vehicle.VehicleRequestDto;
import com.example.demo.dto.vehicle.VehicleResponseDto;

import java.util.List;

public interface VehicleService {

    List<VehicleResponseDto> getAllVehicles();

    VehicleResponseDto getVehicle(Long id);

    VehicleResponseDto addVehicle(VehicleRequestDto requestDto);

    VehicleResponseDto updateVehicle(Long id, VehicleRequestDto requestDto);

    boolean deleteVehicle(Long id);
}