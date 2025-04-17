package com.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.entity.AppointmentEntity;
import com.entity.view.AppointmentView;
import com.service.AppointmentService;
import com.utils.MPUtil;
import com.utils.PageUtils;
import com.utils.R;

/**
 * 预约记录表 前后端交互接口
 */
@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;
    
    /**
     * 列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params) {
        PageUtils page = appointmentService.queryPage(params);
        List<AppointmentEntity> list = (List<AppointmentEntity>)page.getList();
        // 遍历列表，为每个预约记录添加医生名字
        for(AppointmentEntity appointment : list) {
            String doctorName = appointmentService.getDoctorName(appointment.getDoctorId());
            appointment.setYishengxingming(doctorName);
        }
        return R.ok().put("data", page);
    }
    
    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        AppointmentEntity appointment = appointmentService.selectById(id);
        return R.ok().put("data", appointment);
    }
    
    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AppointmentEntity appointment) {
        boolean success = appointmentService.saveAppointment(appointment);
        if (success) {
            return R.ok();
        } else {
            return R.error("预约失败，该时段可能已约满或医生未排班");
        }
    }
    
    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AppointmentEntity appointment) {
        if (appointment.getStatus() != null) {
            boolean success = appointmentService.updateAppointmentStatus(appointment.getId(), appointment.getStatus());
            if (success) {
                return R.ok();
            } else {
                return R.error("更新状态失败");
            }
        } else {
            appointmentService.updateById(appointment);
            return R.ok();
        }
    }
    
    /**
     * 取消预约
     */
    @RequestMapping("/cancel/{id}")
    public R cancel(@PathVariable("id") Long id) {
        boolean success = appointmentService.updateAppointmentStatus(id, "CANCELED");
        if (success) {
            return R.ok();
        } else {
            return R.error("取消预约失败");
        }
    }
    
    /**
     * 确认预约
     */
    @RequestMapping("/confirm/{id}")
    public R confirm(@PathVariable("id") Long id) {
        boolean success = appointmentService.updateAppointmentStatus(id, "CONFIRMED");
        if (success) {
            return R.ok();
        } else {
            return R.error("确认预约失败");
        }
    }
    
    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        appointmentService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
}