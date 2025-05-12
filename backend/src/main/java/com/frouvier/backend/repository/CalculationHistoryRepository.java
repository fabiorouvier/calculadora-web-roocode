package com.frouvier.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.frouvier.backend.model.CalculationHistory;
import com.frouvier.backend.model.User;

import java.util.List;

@Repository
public interface CalculationHistoryRepository extends JpaRepository<CalculationHistory, Long> {
    List<CalculationHistory> findByUserOrderByTimestampDesc(User user);
}