package com.sjsu.minishare.model;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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

        return (List<VirtualMachineMonitorDto>)entityManager().createNamedQuery("VirtualMachineMonitorInfoByStatus")
                .setParameter("userId", userId).getResultList();
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
