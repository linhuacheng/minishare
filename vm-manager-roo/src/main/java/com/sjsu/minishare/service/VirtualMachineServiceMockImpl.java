package com.sjsu.minishare.service;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import com.sjsu.minishare.exception.VirtualMachineException;
import com.sjsu.minishare.model.*;
import com.vmware.vim25.*;
import com.vmware.vim25.mo.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.*;
import org.springframework.scheduling.annotation.Scheduled;

public class VirtualMachineServiceMockImpl implements VirtualMachineService {

	private String vmwareHostURL; // "https://cmpe-admin3.engr.sjsu.edu/sdk"
	private String vmwareLogin; // "student" ; }
	private String vmwarePassword; // "welcome2sjsu"
	private String vmwareDatacenter; // "SJSU VM LAB"
	private String vmwareHost; // "cmpe-pool3.engr.sjsu.edu"
	private String vmwareResourcePool; // "Lab"
	private String vmwareDatastore; // "datastore2"
	private String vmwareTeamFolder; // "team1-Chandu_Cheng_ Priyanka_Niktha"

	public String getVmwareHostURL() {
		return vmwareHostURL;
	}

	public void setVmwareHostURL(String vmwareHostURL) {
		this.vmwareHostURL = vmwareHostURL;
	}

	public String getVmwareLogin() {
		return vmwareLogin;
	}

	public void setVmwareLogin(String vmwareLogin) {
		this.vmwareLogin = vmwareLogin;
	}

	public String getVmwarePassword() {
		return vmwarePassword;
	}

	public void setVmwarePassword(String vmwarePassword) {
		this.vmwarePassword = vmwarePassword;
	}

	public String getVmwareDatacenter() {
		return vmwareDatacenter;
	}

	public void setVmwareDatacenter(String vmwareDatacenter) {
		this.vmwareDatacenter = vmwareDatacenter;
	}

	public String getVmwareHost() {
		return vmwareHost;
	}

	public void setVmwareHost(String vmwareHost) {
		this.vmwareHost = vmwareHost;
	}

	public String getVmwareResourcePool() {
		return vmwareResourcePool;
	}

	public void setVmwareResourcePool(String vmwareResourcePool) {
		this.vmwareResourcePool = vmwareResourcePool;
	}

	public String getVmwareDatastore() {
		return vmwareDatastore;
	}

	public void setVmwareDatastore(String vmwareDatastore) {
		this.vmwareDatastore = vmwareDatastore;
	}

	public String getVmwareTeamFolder() {
		return vmwareTeamFolder;
	}

	public void setVmwareTeamFolder(String vmwareTeamFolder) {
		this.vmwareTeamFolder = vmwareTeamFolder;
	}

	@Override
	public ServiceInstance getServiceInstance() throws VirtualMachineException {
		return null;
	}

	@Override
	public void processRequest(VirtualMachineRequest req)
			throws VirtualMachineException {

	}

	@Override
	public void startVM(VirtualMachineRequest req)
			throws VirtualMachineException {
		// TODO Auto-generated method stub
	}

	@Override
	public void stopVM(VirtualMachineRequest req) {
	}

	@Override
	public void pauseVM(VirtualMachineRequest req) {
	}

	@Override
	public void removeVM(VirtualMachineRequest req) {
	}

	@Override
	public void cloneVM(VirtualMachineRequest req)
			throws VirtualMachineException {

	}

	@Override
	public VirtualMachine getVirtualMachine(String vmName)
			throws VirtualMachineException, InvalidProperty, RuntimeFault,
			RemoteException {
		VirtualMachine vm = null;
		return vm;
	}

	/**
	 * finds all virtual machine by on and suspended state
	 * 
	 * @return
	 */
	@Override
	public List<VirtualMachineDetail> findAllVirtualMachineByOnAndSuspendState() {

		List<MachineStatus> machineStatusList = new ArrayList<MachineStatus>();
		machineStatusList.add(MachineStatus.On);
		machineStatusList.add(MachineStatus.Suspended);
		List<VirtualMachineDetail> virtualMachineDetailList = VirtualMachineDetail
				.findByMachineStatus(machineStatusList);

		return virtualMachineDetailList;
	}

	@Override
	public PerformanceManager getPerformanceManager()
			throws VirtualMachineException {
		return getServiceInstance().getPerformanceManager();
	}

	@Override
	public void updateVM(VirtualMachineRequest req)
			throws VirtualMachineException {
		
	}

}