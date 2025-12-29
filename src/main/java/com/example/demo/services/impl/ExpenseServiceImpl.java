package com.example.demo.services.impl;

import com.example.demo.dto.expense.ExpenseRequestDto;
import com.example.demo.dto.expense.ExpenseResponseDto;
import com.example.demo.mapper.ExpenseMapper;
import com.example.demo.models.Expense;
import com.example.demo.models.Vehicle;
import com.example.demo.repositories.ExpenseRepository;
import com.example.demo.repositories.VehicleRepository;
import com.example.demo.services.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final VehicleRepository vehicleRepository;
    private final ExpenseMapper expenseMapper;

    @Override
    public List<ExpenseResponseDto> getAllExpenses() {
        List<Expense> expenses = expenseRepository.findAll();
        return expenseMapper.toResponseDtoList(expenses);
    }

    @Override
    public ExpenseResponseDto addExpense(ExpenseRequestDto requestDto) {
        Expense expense = expenseMapper.toEntity(requestDto);

        // Устанавливаем связь с Vehicle по vehicleId из RequestDto
        Vehicle vehicle = vehicleRepository.findById(requestDto.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        expense.setVehicle(vehicle);

        Expense saved = expenseRepository.save(expense);
        return expenseMapper.toResponseDto(saved);
    }

    @Override
    public boolean deleteExpense(Long id) {
        if (expenseRepository.existsById(id)) {
            expenseRepository.deleteById(id);
            return true;
        }
        return false;
    }
}