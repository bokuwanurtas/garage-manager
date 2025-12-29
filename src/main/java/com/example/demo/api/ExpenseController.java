package com.example.demo.api;

import com.example.demo.dto.expense.ExpenseRequestDto;
import com.example.demo.dto.expense.ExpenseResponseDto;
import com.example.demo.services.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping
    public ResponseEntity<List<ExpenseResponseDto>> getAllExpenses() {
        List<ExpenseResponseDto> expenses = expenseService.getAllExpenses();
        if (expenses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    public ResponseEntity<ExpenseResponseDto> addExpense(@RequestBody ExpenseRequestDto requestDto) {
        ExpenseResponseDto created = expenseService.addExpense(requestDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    public ResponseEntity<Void> deleteExpense(@PathVariable(name = "id") Long id) {
        boolean deleted = expenseService.deleteExpense(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}