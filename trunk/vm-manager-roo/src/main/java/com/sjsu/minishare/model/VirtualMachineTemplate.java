package com.sjsu.minishare.model;

import org.springframework.roo.addon.tostring.RooToString;

@RooToString
public class VirtualMachineTemplate {

	private Integer id;

	private Integer numberCPUs;

	private String memory;

	private String operatingSystem;

	private String templateName;

	private String defaultUsername;
	private String defaultPassword;
	private String defaultIpAddress;

	public String getDefaultUsername() {
		return defaultUsername;
	}

	public void setDefaultUsername(String defaultUsername) {
		this.defaultUsername = defaultUsername;
	}

	public String getDefaultPassword() {
		return defaultPassword;
	}

	public void setDefaultPassword(String defaultPassword) {
		this.defaultPassword = defaultPassword;
	}

	public String getDefaultIpAddress() {
		return defaultIpAddress;
	}

	public void setDefaultIpAddress(String defaultIpAddress) {
		this.defaultIpAddress = defaultIpAddress;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumberCPUs() {
		return numberCPUs;
	}

	public void setNumberCPUs(Integer numberCPUs) {
		this.numberCPUs = numberCPUs;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	public String toString() {
		return "OS: " + operatingSystem + " / CPU: " + numberCPUs
				+ " / Memory: " + memory + "MB";
	}

}
