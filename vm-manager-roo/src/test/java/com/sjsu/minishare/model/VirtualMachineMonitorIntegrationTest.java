package com.sjsu.minishare.model;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.test.RooIntegrationTest;

import java.util.Arrays;
import java.util.List;

@RooIntegrationTest(entity = VirtualMachineMonitor.class)
public class VirtualMachineMonitorIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

    @Test
    public void testFindByMachineStatus(){
    List<VirtualMachineMonitorDto> virtualMachineMonitorDtos= VirtualMachineMonitor.findVirtualMachineMonitorAggByMachineStatus(1);
//        assert virtualMachineList != null;
//        assert virtualMachineList.size() > 0;
    }
}
