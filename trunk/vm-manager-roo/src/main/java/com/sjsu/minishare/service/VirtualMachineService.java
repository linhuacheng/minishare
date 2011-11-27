package com.sjsu.minishare.service;

import com.sjsu.minishare.exception.VirtualMachineException;
import com.sjsu.minishare.model.VirtualMachineRequest;

public interface VirtualMachineService {
	
	public void processRequest(VirtualMachineRequest req)  throws VirtualMachineException;
	
	public void cloneVM(VirtualMachineRequest req)  throws VirtualMachineException;
	
	public void startVM(VirtualMachineRequest req) throws VirtualMachineException;
	
	public void stopVM(VirtualMachineRequest req) throws VirtualMachineException;
	
	public void pauseVM(VirtualMachineRequest req) throws VirtualMachineException;
	
	public void removeVM(VirtualMachineRequest req) throws VirtualMachineException;
}
