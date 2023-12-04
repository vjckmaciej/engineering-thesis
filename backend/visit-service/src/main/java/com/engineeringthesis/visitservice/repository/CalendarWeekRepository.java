package com.engineeringthesis.visitservice.repository;

import com.engineeringthesis.visitservice.entity.CalendarWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CalendarWeekRepository extends JpaRepository<CalendarWeek, Long> {
    Optional<CalendarWeek> findByPregnancyWeek(Integer pregnancyWeek);

    @Query("SELECT cw FROM CalendarWeek cw ORDER BY cw.pregnancyWeek ASC")
    List<CalendarWeek> findAllOrderByPregnancyWeekAsc();
}
