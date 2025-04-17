package com.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
@TableName("yuyuejilu")
public class YuyuejiluEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    public YuyuejiluEntity() {

    }

    public YuyuejiluEntity(T t) {
        try {
            BeanUtils.copyProperties(this, t);
        } catch (IllegalAccessException | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private Long id;
    private Long yishengId;
    private Long yonghuId;
    private Date yuyueDate;
    private String timeSlot;
    private String status;
    private Date createTime;

    @Override
    public String toString() {
        return "YuyuejiluEntity{" +
                "id=" + id +
                ", yishengId=" + yishengId +
                ", yonghuId=" + yonghuId +
                ", yuyueDate=" + yuyueDate +
                ", timeSlot='" + timeSlot + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getYishengId() {
        return yishengId;
    }

    public void setYishengId(Long yishengId) {
        this.yishengId = yishengId;
    }

    public Long getYonghuId() {
        return yonghuId;
    }

    public void setYonghuId(Long yonghuId) {
        this.yonghuId = yonghuId;
    }

    public Date getYuyueDate() {
        return yuyueDate;
    }

    public void setYuyueDate(Date yuyueDate) {
        this.yuyueDate = yuyueDate;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String isStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public YuyuejiluEntity(Long id, Long yishengId, Long yonghuId, Date yuyueDate, String timeSlot, String status, Date createTime) {
        this.id = id;
        this.yishengId = yishengId;
        this.yonghuId = yonghuId;
        this.yuyueDate = yuyueDate;
        this.timeSlot = timeSlot;
        this.status = status;
        this.createTime = createTime;
    }
}
