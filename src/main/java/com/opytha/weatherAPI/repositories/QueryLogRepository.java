package com.opytha.weatherAPI.repositories;

import com.opytha.weatherAPI.models.QueryLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueryLogRepository extends JpaRepository<QueryLog, Long> {
}
