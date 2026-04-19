package com.ananya.jain.expense_tracker.dto;

import com.ananya.jain.expense_tracker.enums.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateExpenseRequest {

    @NotNull
    private BigDecimal amount;
    @NotNull
    private Category category;

    private LocalDate expenseDate;

    private String description;

}
