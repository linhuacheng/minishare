// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sjsu.minishare.model;

import com.sjsu.minishare.model.UserCreditDataOnDemand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect UserCreditIntegrationTest_Roo_IntegrationTest {
    
    declare @type: UserCreditIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: UserCreditIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml");
    
    declare @type: UserCreditIntegrationTest: @Transactional;
    
    @Autowired
    private UserCreditDataOnDemand UserCreditIntegrationTest.dod;
    
    @Test
    public void UserCreditIntegrationTest.testCountUserCredits() {
        org.junit.Assert.assertNotNull("Data on demand for 'UserCredit' failed to initialize correctly", dod.getRandomUserCredit());
        long count = com.sjsu.minishare.model.UserCredit.countUserCredits();
        org.junit.Assert.assertTrue("Counter for 'UserCredit' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void UserCreditIntegrationTest.testFindUserCredit() {
        com.sjsu.minishare.model.UserCredit obj = dod.getRandomUserCredit();
        org.junit.Assert.assertNotNull("Data on demand for 'UserCredit' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'UserCredit' failed to provide an identifier", id);
        obj = com.sjsu.minishare.model.UserCredit.findUserCredit(id);
        org.junit.Assert.assertNotNull("Find method for 'UserCredit' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'UserCredit' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void UserCreditIntegrationTest.testFindAllUserCredits() {
        org.junit.Assert.assertNotNull("Data on demand for 'UserCredit' failed to initialize correctly", dod.getRandomUserCredit());
        long count = com.sjsu.minishare.model.UserCredit.countUserCredits();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'UserCredit', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<com.sjsu.minishare.model.UserCredit> result = com.sjsu.minishare.model.UserCredit.findAllUserCredits();
        org.junit.Assert.assertNotNull("Find all method for 'UserCredit' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'UserCredit' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void UserCreditIntegrationTest.testFindUserCreditEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'UserCredit' failed to initialize correctly", dod.getRandomUserCredit());
        long count = com.sjsu.minishare.model.UserCredit.countUserCredits();
        if (count > 20) count = 20;
        java.util.List<com.sjsu.minishare.model.UserCredit> result = com.sjsu.minishare.model.UserCredit.findUserCreditEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'UserCredit' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'UserCredit' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void UserCreditIntegrationTest.testFlush() {
        com.sjsu.minishare.model.UserCredit obj = dod.getRandomUserCredit();
        org.junit.Assert.assertNotNull("Data on demand for 'UserCredit' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'UserCredit' failed to provide an identifier", id);
        obj = com.sjsu.minishare.model.UserCredit.findUserCredit(id);
        org.junit.Assert.assertNotNull("Find method for 'UserCredit' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyUserCredit(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'UserCredit' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void UserCreditIntegrationTest.testMerge() {
        com.sjsu.minishare.model.UserCredit obj = dod.getRandomUserCredit();
        org.junit.Assert.assertNotNull("Data on demand for 'UserCredit' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'UserCredit' failed to provide an identifier", id);
        obj = com.sjsu.minishare.model.UserCredit.findUserCredit(id);
        boolean modified =  dod.modifyUserCredit(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        com.sjsu.minishare.model.UserCredit merged =  obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'UserCredit' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void UserCreditIntegrationTest.testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'UserCredit' failed to initialize correctly", dod.getRandomUserCredit());
        com.sjsu.minishare.model.UserCredit obj = dod.getNewTransientUserCredit(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'UserCredit' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'UserCredit' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'UserCredit' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void UserCreditIntegrationTest.testRemove() {
        com.sjsu.minishare.model.UserCredit obj = dod.getRandomUserCredit();
        org.junit.Assert.assertNotNull("Data on demand for 'UserCredit' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'UserCredit' failed to provide an identifier", id);
        obj = com.sjsu.minishare.model.UserCredit.findUserCredit(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'UserCredit' with identifier '" + id + "'", com.sjsu.minishare.model.UserCredit.findUserCredit(id));
    }
    
}
