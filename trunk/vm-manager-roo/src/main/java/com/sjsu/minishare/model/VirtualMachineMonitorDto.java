package com.sjsu.minishare.model;

import org.springframework.roo.addon.entity.RooEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: ckempaiah
 * Date: 12/7/11
 * Time: 1:11 AM
 * To change this template use File | Settings | File Templates.
 */
@NamedNativeQuery(name="VirtualMachineMonitorInfoByStatus",
        query= " SELECT machine_name, machine_id,  vmm.machine_status, " +
                " cast(AVG(overall_cpu_usage) as integer) as avg_cpu_usage, cast(AVG(guest_memory_usage) as integer) as avg_memory_usage, SUM(credits_charged) as total_credits " +
                ", SUM(monitor_interval) as total_monitor_interval " +
                " FROM virtual_machine_monitor vmm " +
                " JOIN virtual_machine_detail vmd on (vmm.virtual_machine_detail= vmd.machine_id) " +
                " WHERE vmd.user_id = :userId " +
                " GROUP BY vmd.machine_name, machine_id, vmm.machine_status ",

        resultSetMapping = "VirtualMachineMonitorInfoByStatusMapping"
)
@SqlResultSetMapping(name = "VirtualMachineMonitorInfoByStatusMapping",
        entities = @EntityResult(entityClass = VirtualMachineMonitorDto.class,
                fields = {
                        @FieldResult(name = "machineName", column = "machine_name")
                        ,@FieldResult(name = "id.machineId", column = "machine_id")
                        ,@FieldResult(name = "id.machineStatus", column = "machine_status")
                        ,@FieldResult(name = "monitorInterval", column = "total_monitor_interval")
                        ,@FieldResult(name = "avgMemoryUsed", column = "avg_memory_usage")
                        ,@FieldResult(name = "avgCpuUsed", column = "avg_cpu_usage")
                        ,@FieldResult(name = "totalCreditsCharged", column = "total_credits")
                })



)
@Entity
public class VirtualMachineMonitorDto {

    @EmbeddedId
    private VirtualMachineMonitorDtoId id;
    private String machineName;
    private Integer avgCpuUsed;
    private Integer avgMemoryUsed;
    private Integer totalCreditsCharged;
    private Integer monitorInterval;


    public VirtualMachineMonitorDtoId getId() {
        return id;
    }

    public void setId(VirtualMachineMonitorDtoId id) {
        this.id = id;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }


    public Integer getAvgCpuUsed() {
        return avgCpuUsed;
    }

    public void setAvgCpuUsed(Integer avgCpuUsed) {
        this.avgCpuUsed = avgCpuUsed;
    }

    public Integer getAvgMemoryUsed() {
        return avgMemoryUsed;
    }

    public void setAvgMemoryUsed(Integer avgMemoryUsed) {
        this.avgMemoryUsed = avgMemoryUsed;
    }

    public Integer getTotalCreditsCharged() {
        return totalCreditsCharged;
    }

    public void setTotalCreditsCharged(Integer totalCreditsCharged) {
        this.totalCreditsCharged = totalCreditsCharged;
    }

    public Integer getMonitorInterval() {
        return monitorInterval;
    }

    public void setMonitorInterval(Integer monitorInterval) {
        this.monitorInterval = monitorInterval;
    }

    public String getMachineStatus(){
        return id != null ? id.getMachineStatus(): null;
    }

    @Embeddable
    public static class VirtualMachineMonitorDtoId implements Serializable{
         private Integer machineId;
         private String machineStatus;

        public Integer getMachineId() {
            return machineId;
        }

        public void setMachineId(Integer machineId) {
            this.machineId = machineId;
        }

        public String getMachineStatus() {
            return machineStatus;
        }

        public void setMachineStatus(String machineStatus) {
            this.machineStatus = machineStatus;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            VirtualMachineMonitorDtoId that = (VirtualMachineMonitorDtoId) o;

            if (machineId != null ? !machineId.equals(that.machineId) : that.machineId != null) return false;
            if (machineStatus != null ? !machineStatus.equals(that.machineStatus) : that.machineStatus != null)
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = machineId != null ? machineId.hashCode() : 0;
            result = 31 * result + (machineStatus != null ? machineStatus.hashCode() : 0);
            return result;
        }
    }
}
