package com.ananya.jain.expense_tracker.repository;

import com.ananya.jain.expense_tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByEmail(String email);

    public Boolean existsByEmail(String email);
}
