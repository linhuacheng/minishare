package com.sjsu.minishare.util;

import java.util.ArrayList;
import java.util.List;

import com.sjsu.minishare.model.VirtualMachineTemplate;

public class VirtualMachineConstants {
	
	static List<VirtualMachineTemplate> virtualMachineTemplates;
	static List<Integer> numCPUs;
	static List<Integer> memories; 
	public static final String DEFAULT_USERNAME = "student";
	public static final String DEFAULT_PASSWORD = "welcome2sjsu";
	
	static {
		virtualMachineTemplates = new ArrayList<VirtualMachineTemplate>();
		
		VirtualMachineTemplate template1 = new VirtualMachineTemplate();
		template1.setId(1);
		template1.setMemory("512");
		template1.setNumberCPUs(1);
		template1.setTemplateName("Group1 - Debian Template1");
		template1.setDefaultUsername(DEFAULT_USERNAME);
		template1.setDefaultPassword(DEFAULT_PASSWORD);
		template1.setDefaultIpAddress("130.65.150.222");
		template1.setOperatingSystem("Debian");
		
		VirtualMachineTemplate template2 = new VirtualMachineTemplate();
		template2.setId(2);
		template2.setMemory("512");
		template2.setNumberCPUs(1);
		template2.setTemplateName("Group1 - Ubuntu Template1");
		template2.setDefaultUsername(DEFAULT_USERNAME);
		template2.setDefaultPassword(DEFAULT_PASSWORD);
		template2.setDefaultIpAddress("130.65.150.223");
		template2.setOperatingSystem("Ubuntu");
		
		VirtualMachineTemplate template3 = new VirtualMachineTemplate();
		template3.setId(3);
		template3.setMemory("1024");
		template3.setNumberCPUs(2);
		template3.setTemplateName("Group1 - Ubuntu Template2");
		template3.setDefaultUsername(DEFAULT_USERNAME);
		template3.setDefaultPassword(DEFAULT_PASSWORD);
		template3.setDefaultIpAddress("130.65.150.201");
		template3.setOperatingSystem("Ubuntu");
		
		virtualMachineTemplates.add(template1);
		virtualMachineTemplates.add(template2);
		virtualMachineTemplates.add(template3);
		
		//Chandu - 130.65.150.201 - Group1 - Ubuntu Template2
		// Priyanka - 130.65.150.203 - 
		// Nikitha - 130.65.150.222 - Group1 - Debian Template1
		// Cheng - 130.65.150.223 - Group1 - Ubuntu Template1
		
		numCPUs = new ArrayList<Integer>();
		numCPUs.add(Integer.valueOf(1));
		numCPUs.add(Integer.valueOf(2));
		numCPUs.add(Integer.valueOf(3));
		
		memories = new ArrayList<Integer>();
		memories.add(Integer.valueOf(512));
		memories.add(Integer.valueOf(1024));
		memories.add(Integer.valueOf(1536));
	}
	
	public static List<VirtualMachineTemplate> getVirtualMachineTemplates() {
		return virtualMachineTemplates;
	}
	 
	public static List<Integer> getCPUNums() {
		return numCPUs; 
	}
	
	public static List<Integer> getMemories() {
		return memories; 
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
