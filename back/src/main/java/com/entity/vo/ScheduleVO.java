package com.entity.vo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ScheduleVO {
    private Long id;
    private LocalDate workDate;
    private String timeSlot;
    private Integer available;
}