package com.sjsu.minishare.service;

import com.sjsu.minishare.exception.VirtualMachineException;
import com.sjsu.minishare.model.VirtualMachineDetail;
import com.sjsu.minishare.model.VirtualMachineRequest;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.mo.PerformanceManager;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;

import java.rmi.RemoteException;
import java.util.List;

public interface VirtualMachineService {
	
	public void processRequest(VirtualMachineRequest req)  throws VirtualMachineException;
	
	public void cloneVM(VirtualMachineRequest req)  throws VirtualMachineException;
	
	public void startVM(VirtualMachineRequest req) throws VirtualMachineException;
	
	public void stopVM(VirtualMachineRequest req) throws VirtualMachineException;
	
	public void pauseVM(VirtualMachineRequest req) throws VirtualMachineException;
	
	public void removeVM(VirtualMachineRequest req) throws VirtualMachineException;
	
	public void updateVM(VirtualMachineRequest req) throws VirtualMachineException;

    List<VirtualMachineDetail> findAllVirtualMachineByOnAndSuspendState();

    VirtualMachine getVirtualMachine(String vmName)
            throws VirtualMachineException, InvalidProperty, RuntimeFault,
            RemoteException;

    PerformanceManager getPerformanceManager() throws VirtualMachineException;

    ServiceInstance getServiceInstance() throws VirtualMachineException;
}
