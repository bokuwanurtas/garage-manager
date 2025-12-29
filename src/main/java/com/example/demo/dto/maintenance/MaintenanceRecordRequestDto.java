package com.example.demo.dto.maintenance;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceRecordRequestDto {

    private Long vehicleId;
    private LocalDate date;
    private int mileageAtService;
    private String description;
    private int cost;
}