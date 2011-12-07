package com.sjsu.minishare.service;

import com.sjsu.minishare.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: ckempaiah
 * Date: 11/24/11
 * Time: 4:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class DataInitializationService {


    public void initialize() throws Exception {
        CloudUser cu = new CloudUser();
        cu.setActive(true);
        cu.setAddressLine1("303 2nd st ");
        cu.setAddressLine2("suite 800N");
        cu.setCardCvvNumber(777);
        cu.setCardExpirationMonth(10);
        cu.setCardExpirationYear(2014);
        cu.setCardNumber("12345678123456789");
        cu.setCity("San Francisco");
        cu.setCountry("USA");
        cu.setFirstName("test");
        cu.setLastName("user");
        cu.setUserName("testuser");
        cu.setPassword("test");
        cu.setUserState("CA");
        cu.setZip("94536");
        cu.persist();
        cu.flush();

//        UserCredit userCredit = new UserCredit();
//        userCredit.setCloudUser(cu);
//        userCredit.setTotalCredits(100);
//        userCredit.setTotalCreditsUsed(50);
//        userCredit.persist();
//        userCredit.flush();
//
//        VirtualMachineDetail vmd = new VirtualMachineDetail();
//        vmd.setLastLogin(new Date());
//        vmd.setCreditsUsed(20);
//        vmd.setLastLogout(new Date());
//        vmd.setMachineName("machine1");
//        vmd.setMachineStatus(MachineStatus.Off.name());
//        vmd.setMemory("512");
//        vmd.setNumberCPUs(1);
//        vmd.setOperatingSystem("Ubuntu");
//        vmd.setUsageInMinutes(60.0f);
//        vmd.setUserId(cu);
//        vmd.persist();
//        vmd.flush();
//
//        VirtualMachineMonitor vmm = new VirtualMachineMonitor();
//        vmm.setMonitorInterval(1);
//        vmm.setVirtualMachineDetail(vmd);
//        vmm.setOverallCpuUsage(100);
//        vmm.setGuestMemoryUsage(512);
//        vmm.setCreditsCharged(51);
//        vmm.setMonitorInterval(1);
//        vmm.persist();
//        vmm.flush();
//
//        vmm = new VirtualMachineMonitor();
//        vmm.setVirtualMachineDetail(vmd);
//        vmm.setOverallCpuUsage(150);
//        vmm.setGuestMemoryUsage(512);
//        vmm.setCreditsCharged(60);
//        vmm.setMonitorInterval(1);
//        vmm.persist();
//        vmm.flush();
    }
}
