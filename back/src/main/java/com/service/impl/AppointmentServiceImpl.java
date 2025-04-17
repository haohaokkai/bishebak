package com.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.AppointmentDao;
import com.dao.ScheduleDao;
import com.entity.AppointmentEntity;
import com.entity.ScheduleEntity;
import com.entity.YishengEntity;
import com.service.AppointmentService;
import com.service.ScheduleService;
import com.service.YishengService;
import com.utils.PageUtils;
import com.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service("appointmentService")
public class AppointmentServiceImpl extends ServiceImpl<AppointmentDao, AppointmentEntity> implements AppointmentService {

    @Autowired
    private ScheduleService scheduleService;
    
    @Autowired
    private YishengService yishengService;

    @Override
    public String getDoctorName(Long doctorId) {
        if (doctorId == null) {
            return null;
        }
        YishengEntity yisheng = yishengService.selectById(doctorId);
        return yisheng != null ? yisheng.getYishengxingming() : null;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String doctorIdStr = (String) params.get("doctorId");
        String appointDateStr = (String) params.get("appointDate");
        String status = (String) params.get("status");
        
        Long doctorId = null;
        LocalDate appointDate = null;
        
        if (doctorIdStr != null && !doctorIdStr.isEmpty()) {
            doctorId = Long.parseLong(doctorIdStr);
        }
        
        if (appointDateStr != null && !appointDateStr.isEmpty()) {
            appointDate = LocalDate.parse(appointDateStr);
        }
        
        Page<AppointmentEntity> page = new Query<AppointmentEntity>(params).getPage();
        List<AppointmentEntity> list = this.baseMapper.selectAppointmentPage(page, doctorId, appointDate, status);
        page.setRecords(list);
        
        return new PageUtils(page);
    }
    
    @Override
    @Transactional
    public boolean saveAppointment(AppointmentEntity appointment) {
        // 设置预约状态为待确认
        appointment.setStatus("PENDING");
        
        // 查询医生排班信息
        EntityWrapper<ScheduleEntity> wrapper = new EntityWrapper<>();
        wrapper.eq("doctor_id", appointment.getDoctorId())
               .eq("work_date", appointment.getAppointDate())
               .eq("time_slot", appointment.getTimeSlot());
        
        ScheduleEntity schedule = scheduleService.selectOne(wrapper);
        
        if (schedule == null) {
            return false; // 医生在该时间段没有排班
        }
        
        if (schedule.getBookedCount() >= schedule.getMaxPatients()) {
            return false; // 预约人数已满
        }
        
        // 增加已预约人数
        schedule.setBookedCount(schedule.getBookedCount() + 1);
        scheduleService.updateById(schedule);
        
        // 保存预约记录
        return this.insert(appointment);
    }
    
    @Override
    @Transactional
    public boolean updateAppointmentStatus(Long appointmentId, String status) {
        AppointmentEntity appointment = this.selectById(appointmentId);
        if (appointment == null) {
            return false;
        }
        
        String oldStatus = appointment.getStatus();
        appointment.setStatus(status);
        
        // 如果是从非取消状态变为取消状态，需要减少排班表中的已预约人数
        if (!oldStatus.equals("CANCELED") && status.equals("CANCELED")) {
            EntityWrapper<ScheduleEntity> wrapper = new EntityWrapper<>();
            wrapper.eq("doctor_id", appointment.getDoctorId())
                   .eq("work_date", appointment.getAppointDate())
                   .eq("time_slot", appointment.getTimeSlot());
            
            ScheduleEntity schedule = scheduleService.selectOne(wrapper);
            if (schedule != null && schedule.getBookedCount() > 0) {
                schedule.setBookedCount(schedule.getBookedCount() - 1);
                scheduleService.updateById(schedule);
            }
        }
        
        return this.updateById(appointment);
    }
}