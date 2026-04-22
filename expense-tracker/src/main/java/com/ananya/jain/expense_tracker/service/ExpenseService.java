package com.ananya.jain.expense_tracker.service;

import com.ananya.jain.expense_tracker.dto.CreateExpenseRequest;
import com.ananya.jain.expense_tracker.dto.ExpenseResponse;
import com.ananya.jain.expense_tracker.entity.Expense;
import com.ananya.jain.expense_tracker.entity.User;
import com.ananya.jain.expense_tracker.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseResponse createExpense(CreateExpenseRequest request){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        LocalDate expenseDate = Objects.nonNull(request.getExpenseDate())
                               ? request.getExpenseDate()
                               : LocalDate.now();

        Expense expense = Expense.builder().
                amount(request.getAmount()).
                category(request.getCategory()).
                description(request.getDescription()).
                expenseDate(expenseDate).
                user(user).
                build();

        Expense savedExpense = expenseRepository.save(expense);

        ExpenseResponse expenseResponse = ExpenseResponse.builder().
                id(savedExpense.getId()).
                amount(savedExpense.getAmount()).
                category(savedExpense.getCategory()).
                description(savedExpense.getDescription()).
                expenseDate(savedExpense.getExpenseDate()).
                createdAt(savedExpense.getCreatedAt()).
                build();

        return expenseResponse;
    }
    
    public List<ExpenseResponse> getUserExpenses(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        System.out.println(authentication.getPrincipal().getClass());
        
        List<Expense> userExpenses = expenseRepository.findByUser(user);
        
        return userExpenses.stream()
                .map(expense -> ExpenseResponse.builder()
                        .id(expense.getId())
                        .amount(expense.getAmount())
                        .category(expense.getCategory())
                        .description(expense.getDescription())
                        .expenseDate(expense.getExpenseDate())
                        .createdAt(expense.getCreatedAt())
                        .build())
                .toList();
    }
}
