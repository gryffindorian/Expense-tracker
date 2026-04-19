package com.ananya.jain.expense_tracker.repository;

import com.ananya.jain.expense_tracker.entity.Expense;
import com.ananya.jain.expense_tracker.entity.User;
import com.ananya.jain.expense_tracker.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUser(User user);

    List<Expense> findByUserAndCategory(User user, Category category);

    List<Expense> findByUserAndExpenseDateBetween(User user, LocalDate startDate, LocalDate endDate);
}
