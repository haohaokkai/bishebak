package com.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.entity.ScheduleEntity;
import com.entity.vo.ScheduleVO;
import com.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 医生排班表 服务接口
 */
public interface ScheduleService extends IService<ScheduleEntity> {
    /**
     * 分页查询
     */
    PageUtils queryPage(Map<String, Object> params);
    
    /**
     * 保存排班并处理乐观锁
     */
    boolean saveSchedule(ScheduleEntity schedule);
    
    /**
     * 更新排班并处理乐观锁
     */
    boolean updateScheduleById(ScheduleEntity schedule);

    List<ScheduleVO> getAvailableSchedules(Long doctorId);
    boolean tryBookSchedule(Long scheduleId);
}