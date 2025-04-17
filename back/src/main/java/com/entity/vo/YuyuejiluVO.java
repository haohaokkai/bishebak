package com.entity.vo;

import java.io.Serializable;
import java.util.Date;

public class YuyuejiluVO  implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long yishengId;
    private Long yonghuId;
    private Date yuyueDate;
    private String timeSlot;
    private String status;
    private Date createTime;



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


}
