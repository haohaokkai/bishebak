package com.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.ScheduleDao;
import com.entity.ScheduleEntity;
import com.entity.vo.ScheduleVO;
import com.service.ScheduleService;
import com.utils.PageUtils;
import com.utils.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service("scheduleService")
public class ScheduleServiceImpl extends ServiceImpl<ScheduleDao, ScheduleEntity> implements ScheduleService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String doctorIdStr = (String) params.get("doctorId");
        String workDateStr = (String) params.get("workDate");
        
        Wrapper<ScheduleEntity> wrapper = new EntityWrapper<ScheduleEntity>();
        
        if (doctorIdStr != null && !doctorIdStr.isEmpty()) {
            wrapper.eq("doctor_id", Long.parseLong(doctorIdStr));
        }
        
        if (workDateStr != null && !workDateStr.isEmpty()) {
            wrapper.eq("work_date", LocalDate.parse(workDateStr));
        }
        
        Page<ScheduleEntity> page = this.selectPage(
            new Query<ScheduleEntity>(params).getPage(),
            wrapper
        );
        
        return new PageUtils(page);
    }
    
    @Override
    @Transactional
    public boolean saveSchedule(ScheduleEntity schedule) {
        // 初始化预约人数和乐观锁版本
        schedule.setBookedCount(0);
        schedule.setVersion(0);
        return this.insert(schedule);
    }
    
    @Override
    @Transactional
    public boolean updateScheduleById(ScheduleEntity schedule) {
        // 乐观锁在MyBatis-Plus中自动处理
        return this.updateById(schedule);
    }

    @Override
    public List<ScheduleVO> getAvailableSchedules(Long doctorId) {
        // 实现方法
        return null;
    }

    @Override
    public boolean tryBookSchedule(Long scheduleId) {
        // 实现方法
        return false;
    }
}