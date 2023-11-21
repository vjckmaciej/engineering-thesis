package com.engineeringthesis.visitservice.repository;

import com.engineeringthesis.visitservice.entity.CalendarWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CalendarWeekRepository extends JpaRepository<CalendarWeek, Long> {
    Optional<CalendarWeek> findByPregnancyWeek(Integer pregnancyWeek);
}
