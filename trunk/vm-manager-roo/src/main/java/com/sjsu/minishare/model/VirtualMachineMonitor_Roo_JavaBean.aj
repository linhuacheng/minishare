// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sjsu.minishare.model;

import com.sjsu.minishare.model.VirtualMachineDetail;
import java.lang.Integer;
import java.lang.String;
import java.sql.Timestamp;

privileged aspect VirtualMachineMonitor_Roo_JavaBean {
    
    public Integer VirtualMachineMonitor.getMonitorId() {
        return this.monitorId;
    }
    
    public void VirtualMachineMonitor.setMonitorId(Integer monitorId) {
        this.monitorId = monitorId;
    }
    
    public Integer VirtualMachineMonitor.getGuestMemoryUsage() {
        return this.guestMemoryUsage;
    }
    
    public void VirtualMachineMonitor.setGuestMemoryUsage(Integer guestMemoryUsage) {
        this.guestMemoryUsage = guestMemoryUsage;
    }
    
    public Integer VirtualMachineMonitor.getOverallCpuUsage() {
        return this.overallCpuUsage;
    }
    
    public void VirtualMachineMonitor.setOverallCpuUsage(Integer overallCpuUsage) {
        this.overallCpuUsage = overallCpuUsage;
    }
    
    public Integer VirtualMachineMonitor.getMonitorInterval() {
        return this.monitorInterval;
    }
    
    public void VirtualMachineMonitor.setMonitorInterval(Integer monitorInterval) {
        this.monitorInterval = monitorInterval;
    }
    
    public String VirtualMachineMonitor.getMachineStatus() {
        return this.machineStatus;
    }
    
    public void VirtualMachineMonitor.setMachineStatus(String machineStatus) {
        this.machineStatus = machineStatus;
    }
    
    public VirtualMachineDetail VirtualMachineMonitor.getVirtualMachineDetail() {
        return this.virtualMachineDetail;
    }
    
    public void VirtualMachineMonitor.setVirtualMachineDetail(VirtualMachineDetail virtualMachineDetail) {
        this.virtualMachineDetail = virtualMachineDetail;
    }

    public Integer VirtualMachineMonitor.getCreditsCharged() {
        return this.creditsCharged;
    }

    public void VirtualMachineMonitor.setCreditsCharged(Integer creditsCharged) {
        this.creditsCharged = creditsCharged;
    }

    public Timestamp VirtualMachineMonitor.getStartTime() {
        return this.startTime;
    }

    public void VirtualMachineMonitor.setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp VirtualMachineMonitor.getEndTime() {
        return this.endTime;
    }

    public void VirtualMachineMonitor.setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    
}
