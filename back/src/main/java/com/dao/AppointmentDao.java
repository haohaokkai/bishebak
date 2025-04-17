package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.entity.AppointmentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

/**
 * 预约记录表
 */
@Mapper
public interface AppointmentDao extends BaseMapper<AppointmentEntity> {
    
    /**
     * 查询医生某日期某时间段的预约数量
     */
    @Select("SELECT COUNT(*) FROM appointment WHERE doctor_id = #{doctorId} AND appoint_date = #{appointDate} AND time_slot = #{timeSlot} AND status != 'CANCELED'")
    int countDoctorAppointments(@Param("doctorId") Long doctorId, @Param("appointDate") LocalDate appointDate, @Param("timeSlot") String timeSlot);

    /**
     * 分页查询预约记录（包含患者信息）
     */
    List<AppointmentEntity> selectAppointmentPage(Pagination page, @Param("doctorId") Long doctorId, 
        @Param("appointDate") LocalDate appointDate, @Param("status") String status);
}