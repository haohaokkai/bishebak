package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.entity.AppointmentEntity;
import com.utils.PageUtils;
import java.util.Map;

/**
 * 预约记录表 服务接口
 */
public interface AppointmentService extends IService<AppointmentEntity> {
    /**
     * 分页查询
     */
    PageUtils queryPage(Map<String, Object> params);
    
    /**
     * 创建预约并更新医生排班表中的已预约人数
     */
    boolean saveAppointment(AppointmentEntity appointment);
    
    /**
     * 更新预约状态
     */
    boolean updateAppointmentStatus(Long appointmentId, String status);

    /**
     * 获取医生姓名
     */
    String getDoctorName(Long doctorId);
}