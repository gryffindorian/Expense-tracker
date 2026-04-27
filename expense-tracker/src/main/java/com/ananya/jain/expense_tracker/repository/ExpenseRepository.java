package com.ananya.jain.expense_tracker.repository;

import com.ananya.jain.expense_tracker.entity.Expense;
import com.ananya.jain.expense_tracker.entity.User;
import com.ananya.jain.expense_tracker.enums.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Page<Expense> findByUser(User user, Pageable pageable);

    Page<Expense> findByUserAndCategory(User user, Category category, Pageable pageable);

    Page<Expense> findByUserAndExpenseDateBetween(User user, LocalDate startDate, LocalDate endDate, Pageable pageable);

    Page<Expense> findByUserAndCategoryAndExpenseDateBetween(User user, Category category, LocalDate startDate, LocalDate endDate, Pageable pageable);

    Optional<Expense> findByIdAndUser(Long id, User user);

    List<Expense> findByUser(User user);

    List<Expense> findByUserAndExpenseDateBetween(User user, LocalDate startDate, LocalDate endDate);
}
