package com.sjsu.minishare.util;

import java.util.ArrayList;
import java.util.List;

import com.sjsu.minishare.model.VirtualMachineTemplate;

public class VirtualMachineConstants {
	
	static List<VirtualMachineTemplate> virtualMachineTemplates;
	static List<Integer> numCPUs; 
	
	static {
		virtualMachineTemplates = new ArrayList<VirtualMachineTemplate>();
		
		VirtualMachineTemplate template1 = new VirtualMachineTemplate();
		template1.setId(1);
		template1.setMemory("512");
		template1.setNumberCPUs(1);
		template1.setTemplateName("Debian Template Group1");
		template1.setOperatingSystem("Debian");
		
		VirtualMachineTemplate template2 = new VirtualMachineTemplate();
		template2.setId(2);
		template2.setMemory("512");
		template2.setNumberCPUs(1);
		template2.setTemplateName("Ubuntu Template Group1");
		template2.setOperatingSystem("Ubuntu");
		
		virtualMachineTemplates.add(template1);
		virtualMachineTemplates.add(template2);
		
		
		numCPUs = new ArrayList<Integer>();
		numCPUs.add(Integer.valueOf(1));
		numCPUs.add(Integer.valueOf(2));
		numCPUs.add(Integer.valueOf(3));
		numCPUs.add(Integer.valueOf(4));
				
	}
	
	public static List<VirtualMachineTemplate> getVirtualMachineTemplates() {
		return virtualMachineTemplates;
	}
	 
	public static List<Integer> getCPUNums() {
		return numCPUs; 
	}
	
	public static VirtualMachineTemplate getVirtualMachineTemplateById(String templateId) {
		for (VirtualMachineTemplate template: virtualMachineTemplates) {
			if (template.getId().toString().equals(templateId)) {
				return template;
			}
		}
		
		return null;
	}
} 
