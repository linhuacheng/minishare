// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sjsu.minishare.web;

import com.sjsu.minishare.model.CloudUser;
import com.sjsu.minishare.model.UserCredit;
import com.sjsu.minishare.model.VirtualMachineDetail;
import com.sjsu.minishare.model.VirtualMachineMonitor;
import java.lang.String;
import org.springframework.core.convert.converter.Converter;

privileged aspect ApplicationConversionServiceFactoryBean_Roo_ConversionService {
    
    public void ApplicationConversionServiceFactoryBean.afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
    
    static class com.sjsu.minishare.web.ApplicationConversionServiceFactoryBean.CloudUserConverter implements Converter<CloudUser, String> {
        public String convert(CloudUser cloudUser) {
            return new StringBuilder().append(cloudUser.getUserName()).append(" ").append(cloudUser.getFirstName()).append(" ").append(cloudUser.getLastName()).append(" ").append(cloudUser.getPassword()).toString();
        }
        
    }
    
    static class com.sjsu.minishare.web.ApplicationConversionServiceFactoryBean.UserCreditConverter implements Converter<UserCredit, String> {
        public String convert(UserCredit userCredit) {
            return new StringBuilder().append(userCredit.getTotalCredits()).append(" ").append(userCredit.getTotalCreditsUsed()).append(" ").append(userCredit.getAmount()).append(" ").append(userCredit.getPaymentTransaction()).toString();
        }
        
    }
    
    static class com.sjsu.minishare.web.ApplicationConversionServiceFactoryBean.VirtualMachineDetailConverter implements Converter<VirtualMachineDetail, String> {
        public String convert(VirtualMachineDetail virtualMachineDetail) {
            return new StringBuilder().append(virtualMachineDetail.getMachineName()).append(" ").append(virtualMachineDetail.getMachineStatus()).append(" ").append(virtualMachineDetail.getNumberCPUs()).append(" ").append(virtualMachineDetail.getMemory()).toString();
        }
        
    }
    
    static class com.sjsu.minishare.web.ApplicationConversionServiceFactoryBean.VirtualMachineMonitorConverter implements Converter<VirtualMachineMonitor, String> {
        public String convert(VirtualMachineMonitor virtualMachineMonitor) {
            return new StringBuilder().append(virtualMachineMonitor.getGuestMemoryUsage()).append(" ").append(virtualMachineMonitor.getOverallCpuUsage()).append(" ").append(virtualMachineMonitor.getMonitorInterval()).append(" ").append(virtualMachineMonitor.getMachineStatus()).toString();
        }
        
    }
    
}
