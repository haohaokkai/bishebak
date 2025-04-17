package com.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.entity.ScheduleEntity;
import com.service.ScheduleService;
import com.utils.MPUtil;
import com.utils.PageUtils;
import com.utils.R;

/**
 * 医生排班表 前后端交互接口
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;
    
    /**
     * 列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params) {
        PageUtils page = scheduleService.queryPage(params);
        return R.ok().put("data", page);
    }
    
    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        ScheduleEntity schedule = scheduleService.selectById(id);
        return R.ok().put("data", schedule);
    }
    
    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ScheduleEntity schedule) {
        scheduleService.saveSchedule(schedule);
        return R.ok();
    }
    
    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ScheduleEntity schedule) {
        scheduleService.updateScheduleById(schedule);
        return R.ok();
    }
    
    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        scheduleService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
} 