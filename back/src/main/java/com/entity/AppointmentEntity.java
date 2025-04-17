package com.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
/*预约记录实体*/
@Data
@TableName("appointment")
public class AppointmentEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long doctorId;
    private Long userId;
    
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate appointDate;
    
    private String timeSlot;
    private String status;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    
    @TableField(exist = false)
    private String yonghuxingming;  // 患者姓名，不对应数据库字段

    @TableField(exist = false)
    private String yishengxingming;  // 医生姓名，不对应数据库字段
}