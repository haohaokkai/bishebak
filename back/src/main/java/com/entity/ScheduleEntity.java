package com.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
/*排班实体*/
@Data
@TableName("schedule")
public class ScheduleEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long doctorId;
    
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate workDate;
    
    private String timeSlot;
    private Integer maxPatients;
    private Integer bookedCount;
    
    @Version
    private Integer version; // 乐观锁版本号
}