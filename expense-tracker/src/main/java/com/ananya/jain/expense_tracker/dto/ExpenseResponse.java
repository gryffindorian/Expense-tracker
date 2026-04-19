package com.ananya.jain.expense_tracker.dto;

import com.ananya.jain.expense_tracker.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseResponse {

    private BigDecimal amount;

    private Category category;

    private String description;

    private LocalDate expenseDate;

    private LocalDateTime createdAt;

}
