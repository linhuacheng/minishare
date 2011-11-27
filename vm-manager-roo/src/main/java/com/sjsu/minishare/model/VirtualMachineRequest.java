package com.sjsu.minishare.model;

import javax.validation.constraints.Size;

public class VirtualMachineRequest {

	private String machineName;

	private MachineRequest machineRequest;

	private Integer numberCPUs;

	private Integer memory;

	private String templateName;

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public MachineRequest getMachineRequest() {
		return machineRequest;
	}

	public void setMachineRequest(MachineRequest machineRequest) {
		this.machineRequest = machineRequest;
	}

	public Integer getNumberCPUs() {
		return numberCPUs;
	}

	public void setNumberCPUs(Integer numberCPUs) {
		this.numberCPUs = numberCPUs;
	}

	public Integer getMemory() {
		return memory;
	}

	public void setMemory(Integer memory) {
		this.memory = memory;
	}

	public void setMemory(String memory) {
		this.memory = new Integer(memory);
	}
	
	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

}
