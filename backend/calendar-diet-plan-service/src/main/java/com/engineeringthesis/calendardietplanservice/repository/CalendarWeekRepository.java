package com.engineeringthesis.calendardietplanservice.repository;

import com.engineeringthesis.calendardietplanservice.entity.CalendarWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarWeekRepository extends JpaRepository<CalendarWeek, Long> {
}
