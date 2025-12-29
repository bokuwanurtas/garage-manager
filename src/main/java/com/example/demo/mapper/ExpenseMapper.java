package com.example.demo.mapper;

import com.example.demo.dto.expense.ExpenseRequestDto;
import com.example.demo.dto.expense.ExpenseResponseDto;
import com.example.demo.models.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    @Mapping(source = "vehicle.id", target = "vehicleId")
    @Mapping(source = "vehicle.brand", target = "vehicleBrand")
    @Mapping(source = "vehicle.model", target = "vehicleModel")
    ExpenseResponseDto toResponseDto(Expense expense);

    List<ExpenseResponseDto> toResponseDtoList(List<Expense> expenses);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vehicle", ignore = true)  // vehicle устанавливаем вручную в сервисе
    Expense toEntity(ExpenseRequestDto requestDto);
}