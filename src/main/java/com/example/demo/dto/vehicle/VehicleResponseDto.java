package com.example.demo.dto.vehicle;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VehicleResponseDto {

    private Long id;
    private String brand;
    private String model;
    private int year;
    private int mileage;
    private String vin;
}