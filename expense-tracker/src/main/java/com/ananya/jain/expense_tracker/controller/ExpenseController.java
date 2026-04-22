package com.ananya.jain.expense_tracker.controller;

import com.ananya.jain.expense_tracker.dto.CreateExpenseRequest;
import com.ananya.jain.expense_tracker.dto.ExpenseResponse;
import com.ananya.jain.expense_tracker.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<ExpenseResponse> getExpenses(){
        return expenseService.getUserExpenses();
    }
}
