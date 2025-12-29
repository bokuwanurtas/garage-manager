package com.example.demo.services.impl;

import com.example.demo.dto.expense.ExpenseRequestDto;
import com.example.demo.dto.expense.ExpenseResponseDto;
import com.example.demo.mapper.ExpenseMapper;
import com.example.demo.models.Expense;
import com.example.demo.models.Vehicle;
import com.example.demo.repositories.ExpenseRepository;
import com.example.demo.repositories.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceImplTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private ExpenseMapper expenseMapper;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    @Test
    void getAllExpensesTest() {
        // given
        Expense expense = new Expense();
        expense.setId(1L);
        when(expenseRepository.findAll()).thenReturn(List.of(expense));
        when(expenseMapper.toResponseDtoList(any())).thenReturn(List.of(ExpenseResponseDto.builder().id(1L).build()));

        // when
        List<ExpenseResponseDto> result = expenseService.getAllExpenses();

        // then
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void addExpenseTest() {
        // given
        ExpenseRequestDto requestDto = ExpenseRequestDto.builder()
                .vehicleId(1L)
                .description("Fuel")
                .amount(15000)
                .build();

        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setBrand("Toyota");

        Expense expense = new Expense();
        expense.setId(1L);

        ExpenseResponseDto responseDto = ExpenseResponseDto.builder()
                .id(1L)
                .vehicleBrand("Toyota")
                .build();

        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(expenseMapper.toEntity(requestDto)).thenReturn(expense);
        when(expenseRepository.save(expense)).thenReturn(expense);
        when(expenseMapper.toResponseDto(expense)).thenReturn(responseDto);

        // when
        ExpenseResponseDto result = expenseService.addExpense(requestDto);

        // then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Toyota", result.getVehicleBrand());
        assertEquals(vehicle, expense.getVehicle());
        verify(expenseRepository).save(any());
    }

    @Test
    void addExpenseVehicleNotFoundTest() {
        // given
        ExpenseRequestDto requestDto = ExpenseRequestDto.builder()
                .vehicleId(999L)
                .build();

        when(vehicleRepository.findById(999L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(RuntimeException.class, () -> expenseService.addExpense(requestDto));
    }

    @Test
    void deleteExpenseTest() {
        // given
        Long id = 1L;
        when(expenseRepository.existsById(id)).thenReturn(true);

        // when
        boolean result = expenseService.deleteExpense(id);

        // then
        assertTrue(result);
        verify(expenseRepository).deleteById(id);
    }

    @Test
    void deleteExpenseNotFoundTest() {
        // given
        when(expenseRepository.existsById(999L)).thenReturn(false);

        // when
        boolean result = expenseService.deleteExpense(999L);

        // then
        assertFalse(result);
    }
}