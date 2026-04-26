package com.ananya.jain.expense_tracker.service;

import com.ananya.jain.expense_tracker.dto.CreateExpenseRequest;
import com.ananya.jain.expense_tracker.dto.ExpenseResponse;
import com.ananya.jain.expense_tracker.dto.PaginatedResponse;
import com.ananya.jain.expense_tracker.dto.UpdateExpenseRequest;
import com.ananya.jain.expense_tracker.entity.Expense;
import com.ananya.jain.expense_tracker.entity.User;
import com.ananya.jain.expense_tracker.enums.Category;
import com.ananya.jain.expense_tracker.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private Expense expense;

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
    
    public PaginatedResponse<ExpenseResponse> getUserExpenses(Category category, LocalDate startDate, LocalDate endDate, Pageable pageable){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Page<Expense> expenses;

        if(Objects.nonNull(startDate) && Objects.nonNull(endDate)){
            if(Objects.isNull(category)){
                expenses = expenseRepository.findByUserAndExpenseDateBetween(user, startDate, endDate, pageable);
            }
            else {
                expenses = expenseRepository.findByUserAndCategoryAndExpenseDateBetween(user, category, startDate, endDate, pageable);
            }
        }
        else {
            if(Objects.isNull(category)){
                expenses = expenseRepository.findByUser(user, pageable);
            }
            else {
                expenses = expenseRepository.findByUserAndCategory(user, category, pageable);
            }
        }

        Page<ExpenseResponse> responsePage = expenses.map(expense -> ExpenseResponse.builder()
                .id(expense.getId())
                .amount(expense.getAmount())
                .category(expense.getCategory())
                .description(expense.getDescription())
                .expenseDate(expense.getExpenseDate())
                .createdAt(expense.getCreatedAt())
                .build());

        return new PaginatedResponse<>(
                responsePage.getContent(),
                responsePage.getNumber(),
                responsePage.getSize(),
                responsePage.getTotalElements(),
                responsePage.getTotalPages(),
                responsePage.isLast()
        );
    }

    public ExpenseResponse updateExpense(Long id, UpdateExpenseRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Optional<Expense> response = expenseRepository.findByIdAndUser(id, user);

        Expense expense = response.orElseThrow(() ->
                new RuntimeException("Expense not found or unauthorized"));

            if(request.getAmount() != null){
                expense.setAmount(request.getAmount());
            }
            if(request.getCategory() !=null){
                expense.setCategory(request.getCategory());
            }
            if(request.getDescription() !=null){
                expense.setDescription(request.getDescription());
            }
            if(request.getExpenseDate() !=null){
                expense.setExpenseDate(request.getExpenseDate());
            }

        Expense savedExpense = expenseRepository.save(expense);

        return ExpenseResponse.builder()
                .id(savedExpense.getId())
                .amount(savedExpense.getAmount())
                .category(savedExpense.getCategory())
                .description(savedExpense.getDescription())
                .expenseDate(savedExpense.getExpenseDate())
                .createdAt(savedExpense.getCreatedAt())
                .build();
    }

    public ResponseEntity<String> deleteExpense(Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Optional<Expense> response = expenseRepository.findByIdAndUser(id, user);

        Expense expense = response.orElseThrow(() ->
                new RuntimeException("Expense not found or unauthorized"));

        expenseRepository.delete(expense);

        return ResponseEntity.ok("Expense deleted succesfully");

    }
}
