package com.example.demo.dto.expense;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ExpenseResponseDto {

    private Long id;
    private Long vehicleId;
    private String vehicleBrand;
    private String vehicleModel;
    private LocalDate date;
    private String description;
    private int amount;
}