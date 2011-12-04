// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sjsu.minishare.model;

import com.sjsu.minishare.model.VirtualMachineDetailDataOnDemand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect VirtualMachineDetailIntegrationTest_Roo_IntegrationTest {
    
    declare @type: VirtualMachineDetailIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: VirtualMachineDetailIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml");
    
    declare @type: VirtualMachineDetailIntegrationTest: @Transactional;
    
    @Autowired
    private VirtualMachineDetailDataOnDemand VirtualMachineDetailIntegrationTest.dod;
    
    @Test
    public void VirtualMachineDetailIntegrationTest.testCountVirtualMachineDetails() {
        org.junit.Assert.assertNotNull("Data on demand for 'VirtualMachineDetail' failed to initialize correctly", dod.getRandomVirtualMachineDetail());
        long count = com.sjsu.minishare.model.VirtualMachineDetail.countVirtualMachineDetails();
        org.junit.Assert.assertTrue("Counter for 'VirtualMachineDetail' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void VirtualMachineDetailIntegrationTest.testFindVirtualMachineDetail() {
        com.sjsu.minishare.model.VirtualMachineDetail obj = dod.getRandomVirtualMachineDetail();
        org.junit.Assert.assertNotNull("Data on demand for 'VirtualMachineDetail' failed to initialize correctly", obj);
        java.lang.Integer id = obj.getMachineId();
        org.junit.Assert.assertNotNull("Data on demand for 'VirtualMachineDetail' failed to provide an identifier", id);
        obj = com.sjsu.minishare.model.VirtualMachineDetail.findVirtualMachineDetail(id);
        org.junit.Assert.assertNotNull("Find method for 'VirtualMachineDetail' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'VirtualMachineDetail' returned the incorrect identifier", id, obj.getMachineId());
    }
    
    @Test
    public void VirtualMachineDetailIntegrationTest.testFindAllVirtualMachineDetails() {
        org.junit.Assert.assertNotNull("Data on demand for 'VirtualMachineDetail' failed to initialize correctly", dod.getRandomVirtualMachineDetail());
        long count = com.sjsu.minishare.model.VirtualMachineDetail.countVirtualMachineDetails();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'VirtualMachineDetail', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<com.sjsu.minishare.model.VirtualMachineDetail> result = com.sjsu.minishare.model.VirtualMachineDetail.findAllVirtualMachineDetails();
        org.junit.Assert.assertNotNull("Find all method for 'VirtualMachineDetail' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'VirtualMachineDetail' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void VirtualMachineDetailIntegrationTest.testFindVirtualMachineDetailEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'VirtualMachineDetail' failed to initialize correctly", dod.getRandomVirtualMachineDetail());
        long count = com.sjsu.minishare.model.VirtualMachineDetail.countVirtualMachineDetails();
        if (count > 20) count = 20;
        java.util.List<com.sjsu.minishare.model.VirtualMachineDetail> result = com.sjsu.minishare.model.VirtualMachineDetail.findVirtualMachineDetailEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'VirtualMachineDetail' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'VirtualMachineDetail' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void VirtualMachineDetailIntegrationTest.testFlush() {
        com.sjsu.minishare.model.VirtualMachineDetail obj = dod.getRandomVirtualMachineDetail();
        org.junit.Assert.assertNotNull("Data on demand for 'VirtualMachineDetail' failed to initialize correctly", obj);
        java.lang.Integer id = obj.getMachineId();
        org.junit.Assert.assertNotNull("Data on demand for 'VirtualMachineDetail' failed to provide an identifier", id);
        obj = com.sjsu.minishare.model.VirtualMachineDetail.findVirtualMachineDetail(id);
        org.junit.Assert.assertNotNull("Find method for 'VirtualMachineDetail' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyVirtualMachineDetail(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'VirtualMachineDetail' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void VirtualMachineDetailIntegrationTest.testMerge() {
        com.sjsu.minishare.model.VirtualMachineDetail obj = dod.getRandomVirtualMachineDetail();
        org.junit.Assert.assertNotNull("Data on demand for 'VirtualMachineDetail' failed to initialize correctly", obj);
        java.lang.Integer id = obj.getMachineId();
        org.junit.Assert.assertNotNull("Data on demand for 'VirtualMachineDetail' failed to provide an identifier", id);
        obj = com.sjsu.minishare.model.VirtualMachineDetail.findVirtualMachineDetail(id);
        boolean modified =  dod.modifyVirtualMachineDetail(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        com.sjsu.minishare.model.VirtualMachineDetail merged =  obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getMachineId(), id);
        org.junit.Assert.assertTrue("Version for 'VirtualMachineDetail' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void VirtualMachineDetailIntegrationTest.testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'VirtualMachineDetail' failed to initialize correctly", dod.getRandomVirtualMachineDetail());
        com.sjsu.minishare.model.VirtualMachineDetail obj = dod.getNewTransientVirtualMachineDetail(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'VirtualMachineDetail' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'VirtualMachineDetail' identifier to be null", obj.getMachineId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'VirtualMachineDetail' identifier to no longer be null", obj.getMachineId());
    }
    
    @Test
    public void VirtualMachineDetailIntegrationTest.testRemove() {
        com.sjsu.minishare.model.VirtualMachineDetail obj = dod.getRandomVirtualMachineDetail();
        org.junit.Assert.assertNotNull("Data on demand for 'VirtualMachineDetail' failed to initialize correctly", obj);
        java.lang.Integer id = obj.getMachineId();
        org.junit.Assert.assertNotNull("Data on demand for 'VirtualMachineDetail' failed to provide an identifier", id);
        obj = com.sjsu.minishare.model.VirtualMachineDetail.findVirtualMachineDetail(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'VirtualMachineDetail' with identifier '" + id + "'", com.sjsu.minishare.model.VirtualMachineDetail.findVirtualMachineDetail(id));
    }
    
}