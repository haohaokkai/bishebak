package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.entity.ScheduleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 医生排班表
 */
@Mapper
public interface ScheduleDao extends BaseMapper<ScheduleEntity> {
    
    @Select("SELECT * FROM schedule WHERE doctor_id = #{doctorId} AND work_date >= CURDATE()")//大于等于当前日期
    List<ScheduleEntity> selectAvailableSchedules(@Param("doctorId") Long doctorId);

    @Update("UPDATE schedule SET booked_count = booked_count + 1 " +
            "WHERE id = #{id} AND booked_count < max_patients")
    int incrementBookedCount(@Param("id") Long id);
}