package com.example.demo.dto.maintenance;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class MaintenanceRecordResponseDto {

    private Long id;
    private Long vehicleId;
    private String vehicleBrand;
    private String vehicleModel;
    private LocalDate date;
    private int mileageAtService;
    private String description;
    private int cost;
}