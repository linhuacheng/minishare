// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sjsu.minishare.model;

import java.lang.String;

privileged aspect VirtualMachineMonitor_Roo_ToString {
    
    public String VirtualMachineMonitor.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("GuestMemoryUsage: ").append(getGuestMemoryUsage()).append(", ");
        sb.append("MachineStatus: ").append(getMachineStatus()).append(", ");
        sb.append("MonitorId: ").append(getMonitorId()).append(", ");
        sb.append("MonitorInterval: ").append(getMonitorInterval()).append(", ");
        sb.append("OverallCpuUsage: ").append(getOverallCpuUsage()).append(", ");
        sb.append("StartTime: ").append(getStartTime()).append(", ");
	    sb.append("EndTime: ").append(getEndTime()).append(", ");
	    sb.append("CreditsCharged: ").append(getCreditsCharged()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("VirtualMachineDetail: ").append(getVirtualMachineDetail());
        return sb.toString();
    }
    
}
