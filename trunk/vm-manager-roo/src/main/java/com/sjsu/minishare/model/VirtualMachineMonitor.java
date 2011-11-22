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

@RooJavaBean
@RooToString
@RooEntity
public class VirtualMachineMonitor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer monitorId;

    private Integer memoryConsumedInBytes;

    private Float cpuUsageInPercent;

    private Integer monitorInterval;

    @ManyToOne
    private VirtualMachineDetail virtualMachineDetail;
}
