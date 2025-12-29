package com.example.demo.mapper;

import com.example.demo.dto.maintenance.MaintenanceRecordRequestDto;
import com.example.demo.dto.maintenance.MaintenanceRecordResponseDto;
import com.example.demo.models.MaintenanceRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MaintenanceRecordMapper {

    @Mapping(source = "vehicle.id", target = "vehicleId")
    @Mapping(source = "vehicle.brand", target = "vehicleBrand")
    @Mapping(source = "vehicle.model", target = "vehicleModel")
    MaintenanceRecordResponseDto toResponseDto(MaintenanceRecord record);

    List<MaintenanceRecordResponseDto> toResponseDtoList(List<MaintenanceRecord> records);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vehicle", ignore = true)  // vehicle устанавливаем вручную в сервисе
    MaintenanceRecord toEntity(MaintenanceRecordRequestDto requestDto);
}