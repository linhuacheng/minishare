package com.sjsu.minishare.model;

import com.vmware.vim25.PerfCounterInfo;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: ckempaiah
 * Date: 12/3/11
 * Time: 12:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class PerformanceMetricBean {

    private Date startTime;
    private Date endTime;
    private long avgValue;
    private PerfCounterInfo perfCounterInfo;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getCounterId() {
        return perfCounterInfo.getKey();
    }

    public String getMetricName() {
        return perfCounterInfo.getNameInfo().key;
    }

    public String getGroupName(){
        return perfCounterInfo.getGroupInfo().key;
    }

    public long getAvgValue() {
        return avgValue;
    }

    public void setAvgValue(long avgValue) {
        this.avgValue = avgValue;
    }

    public PerfCounterInfo getPerfCounterInfo() {
        return perfCounterInfo;
    }

    public void setPerfCounterInfo(PerfCounterInfo perfCounterInfo) {
        this.perfCounterInfo = perfCounterInfo;
    }
}
