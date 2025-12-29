package com.example.demo.services;

import com.example.demo.dto.expense.ExpenseRequestDto;
import com.example.demo.dto.expense.ExpenseResponseDto;

import java.util.List;

public interface ExpenseService {

    List<ExpenseResponseDto> getAllExpenses();

    ExpenseResponseDto addExpense(ExpenseRequestDto requestDto);

    boolean deleteExpense(Long id);
}