package com.sjsu.minishare.web;

import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.RooConversionService;
import org.springframework.core.convert.converter.Converter;

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
        registry.addConverter(new UserCreditConverter());
        registry.addConverter(new VirtualMachineDetailConverter());
        registry.addConverter(new VirtualMachineMonitorConverter());
        registry.addConverter(new VirtualMachineTemplateConverter());
    }
    
    static class VirtualMachineTemplateConverter implements Converter<VirtualMachineTemplate, String> {
        public String convert(VirtualMachineTemplate virtualMachineTemplate) {
            return new StringBuilder().append("OS: ").append(virtualMachineTemplate.getOperatingSystem()).append(" / CPU: ").append(virtualMachineTemplate.getNumberCPUs()).append(" / Memory: ").append(virtualMachineTemplate.getMemory()).append("MB").toString();
             
        }
        
    }
}
