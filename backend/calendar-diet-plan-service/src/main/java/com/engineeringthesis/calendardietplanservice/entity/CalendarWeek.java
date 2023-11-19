package com.engineeringthesis.calendardietplanservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CALENDAR_WEEKS")
public class CalendarWeek {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "calendarWeekId", nullable = false)
    private Long calendarWeekId;

    @Column
    private Integer pregnancyWeek;

    @Column
    private String description;
}
