package com.entity.view;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AppointmentView {
    @NotNull
    private Long doctorId;
    
    @NotNull
    private Long scheduleId;
    
    @NotNull
    private Long userId;
    
    @NotBlank
    private String timeSlot;
}