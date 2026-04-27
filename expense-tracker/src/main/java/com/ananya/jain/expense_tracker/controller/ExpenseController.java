package com.ananya.jain.expense_tracker.controller;

import com.ananya.jain.expense_tracker.dto.*;
import com.ananya.jain.expense_tracker.enums.Category;
import com.ananya.jain.expense_tracker.service.ExpenseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

import java.net.http.HttpResponse;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ExpenseResponse createExpense(@Valid @RequestBody CreateExpenseRequest createExpenseRequest){
        return expenseService.createExpense(createExpenseRequest);
    }

    @GetMapping
    public PaginatedResponse<ExpenseResponse> getUserExpenses(@RequestParam(required = false) Category category,
                                                              @RequestParam(required = false) LocalDate startDate,
                                                              @RequestParam(required = false) LocalDate endDate,
                                                              Pageable pageable){

        return expenseService.getUserExpenses(category, startDate, endDate, pageable);
    }

    @PatchMapping("/{id}")
    public ExpenseResponse updateExpense(@PathVariable Long id, @Valid @RequestBody UpdateExpenseRequest updateExpenseRequest){
        return expenseService.updateExpense(id, updateExpenseRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable Long id){
        return expenseService.deleteExpense(id);
    }

    @GetMapping("/summary")
    public SummaryResponse getSummary(@RequestParam(required = false) LocalDate startDate,
                                      @RequestParam(required = false) LocalDate endDate){
        return expenseService.getSummary(startDate, endDate);
    }
}
