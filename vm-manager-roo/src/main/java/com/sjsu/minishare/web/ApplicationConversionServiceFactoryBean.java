package com.sjsu.minishare.web;

import com.sjsu.minishare.model.VirtualMachineMonitorDto;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.RooConversionService;
import org.springframework.core.convert.converter.Converter;

import com.sjsu.minishare.model.CloudUser;
import com.sjsu.minishare.model.VirtualMachineTemplate;

/**
 * A central place to register application converters and formatters. 
 */
@RooConversionService
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}

    public void installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(new CloudUserConverter());
        registry.addConverter(new StringToCloudUserConverter());
        registry.addConverter(new UserCreditConverter());
        registry.addConverter(new VirtualMachineDetailConverter());
        registry.addConverter(new VirtualMachineMonitorConverter());
         registry.addConverter(new VirtualMachineTemplateConverter());
        registry.addConverter(new VirtualMachineMonitorDtoIdToString());
    }
    
    static class VirtualMachineTemplateConverter implements Converter<VirtualMachineTemplate, String> {
        public String convert(VirtualMachineTemplate virtualMachineTemplate) {
            return new StringBuilder().append("OS: ").append(virtualMachineTemplate.getOperatingSystem()).append(" / CPU: ").append(virtualMachineTemplate.getNumberCPUs()).append(" / Memory: ").append(virtualMachineTemplate.getMemory()).append("MB").toString();
             
        }
        
    }
    
    static class StringToCloudUserConverter implements Converter<String, CloudUser> {
    	 public CloudUser convert(String userId) {
    	        return CloudUser.findCloudUser(Integer.valueOf(userId));
    	 }
    }

    static class VirtualMachineMonitorDtoIdToString implements Converter<VirtualMachineMonitorDto.VirtualMachineMonitorDtoId, String> {
    	 public String convert(VirtualMachineMonitorDto.VirtualMachineMonitorDtoId dtoId) {
    	        return new StringBuilder().append(dtoId.getMachineId()).append(" ").append(dtoId.getMachineStatus()).toString();
    	 }
    }

    static class CloudUserConverter implements Converter<CloudUser, String> {
        public String convert(CloudUser cloudUser) {
            return new StringBuilder().append(cloudUser.getUserName()).append(" ").append(cloudUser.getFirstName()).append(" ").append(cloudUser.getLastName()).append(" ").toString();
        }

    }

    
}
