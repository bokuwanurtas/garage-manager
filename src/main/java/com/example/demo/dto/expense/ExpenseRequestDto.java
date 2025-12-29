package com.example.demo.dto.expense;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseRequestDto {

    private Long vehicleId;
    private LocalDate date;
    private String description;
    private int amount;
}