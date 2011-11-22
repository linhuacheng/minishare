package com.sjsu.minishare.model;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Temporal;
import javax.persistence.ManyToOne;
import javax.persistence.TemporalType;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import com.sjsu.minishare.model.CloudUser;

@RooJavaBean
@RooToString
@RooEntity
public class VirtualMachineDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)    @Column(name="machineId")
    private Integer machineId;

    @Size(max = 256)
    private String machineName;

    @Size(max = 36)
    private String machineStatus;

    private Integer numberCPUs;

    @Size(max = 36)
    private String memory;

    @Size(max = 100)
    private String operatingSystem;

    private Float usageInMinutes;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date lastLogin;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date lastLogout;

    private Integer creditsUsed;

    private Float totalCost;

    @ManyToOne
    private CloudUser userId;
}
