package com.sjsu.minishare.model;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import com.sjsu.minishare.model.VirtualMachineDetail;

import java.sql.Timestamp;
import java.util.List;

@RooJavaBean
@RooToString
@RooEntity
public class VirtualMachineMonitor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer monitorId;

    private Integer guestMemoryUsage;

    private Integer overallCpuUsage;

    private Integer monitorInterval;

    private String machineStatus;

    private Integer creditsCharged;

    private Timestamp startTime;

    private Timestamp endTime;

    @ManyToOne
    private VirtualMachineDetail virtualMachineDetail;

    public static List<VirtualMachineMonitorDto> findVirtualMachineMonitorAggByMachineStatus(Integer userId){

        Query query = entityManager().createNamedQuery("VirtualMachineMonitorInfoByStatus");
        query.setParameter("userId", userId);
        List result = query.getResultList();

        
//        Query query = entityManager().createNativeQuery("SELECT machine_name, machine_id,  vmm.machine_status, " +
//                " AVG(overall_cpu_usage) as avg_cpu_usage, AVG(guest_memory_usage) as avg_memory_usage, SUM(credits_charged) as total_credits " +
//                " , SUM(monitor_interval) as total_monitor_interval " +
//                " FROM virtual_machine_monitor vmm " +
//                " JOIN virtual_machine_detail vmd on (vmm.virtual_machine_detail= vmd.machine_id) " +
//                " WHERE vmd.user_id = :userId " +
//                " GROUP BY vmd.machine_name, machine_id, vmm.machine_status ");
//        query.setParameter("userId", userId);
//        result = query.getResultList();

        if (!org.springframework.util.CollectionUtils.isEmpty(result)){
            for (Object obj : result){
                System.out.println("Obj:"+obj);
            }
        }
        return (List<VirtualMachineMonitorDto>)result;
    }

    public static List<VirtualMachineMonitor> findAllVirtualMachineMonitorsByUser(Integer userId) {
        return entityManager().createQuery("SELECT o FROM VirtualMachineMonitor o where o.virtualMachineDetail.userId.userId = :userId", VirtualMachineMonitor.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public static List<VirtualMachineMonitor> findVirtualMachineMonitorEntriesByUser(Integer userId, int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM VirtualMachineMonitor o where o.virtualMachineDetail.userId.userId = :userId", VirtualMachineMonitor.class)
                .setParameter("userId", userId)
                .setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
}
