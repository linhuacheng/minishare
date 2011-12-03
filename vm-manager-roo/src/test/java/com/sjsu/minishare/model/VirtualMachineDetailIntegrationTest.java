package com.sjsu.minishare.model;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.test.RooIntegrationTest;

import java.util.Arrays;
import java.util.List;

@RooIntegrationTest(entity = VirtualMachineDetail.class)
public class VirtualMachineDetailIntegrationTest {
    @Autowired
    VirtualMachineDetailDataOnDemand virtualMachineDetailDataOnDemand;

    @Test
    public void testMarkerMethod() {
    }

    @Test
    public void testFindByMachineStatus(){
        org.junit.Assert.assertNotNull("Data on demand for 'VirtualMachineDetail' failed to initialize correctly", virtualMachineDetailDataOnDemand.getRandomVirtualMachineDetail());
        List<VirtualMachineDetail> virtualMachineList = VirtualMachineDetail.findByMachineStatus(Arrays.asList(new MachineStatus[]{MachineStatus.On, MachineStatus.Suspended}));
//        assert virtualMachineList != null;
//        assert virtualMachineList.size() > 0;
    }
}
