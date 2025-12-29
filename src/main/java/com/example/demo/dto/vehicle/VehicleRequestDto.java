package com.example.demo.dto.vehicle;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleRequestDto {

    private String brand;
    private String model;
    private int year;
    private int mileage;
    private String vin;
}