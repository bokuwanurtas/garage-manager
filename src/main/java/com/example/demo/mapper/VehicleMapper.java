package com.example.demo.mapper;

import com.example.demo.dto.vehicle.VehicleRequestDto;
import com.example.demo.dto.vehicle.VehicleResponseDto;
import com.example.demo.models.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    VehicleResponseDto toResponseDto(Vehicle vehicle);

    List<VehicleResponseDto> toResponseDtoList(List<Vehicle> vehicles);

    @Mapping(target = "id", ignore = true)
    Vehicle toEntity(VehicleRequestDto requestDto);
}