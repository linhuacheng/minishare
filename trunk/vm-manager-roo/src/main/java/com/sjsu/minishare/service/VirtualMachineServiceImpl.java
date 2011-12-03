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

public class VirtualMachineServiceImpl implements VirtualMachineService {

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
		ServiceInstance si;
		try {
			si = new ServiceInstance(new URL(vmwareHostURL), vmwareLogin,
					vmwarePassword, true);
		} catch (Exception e) {
			e.printStackTrace();
			throw new VirtualMachineException(e.getMessage());
		}

		return si;
	}

	public void finalize() {
		try {
			ServiceInstance si = getServiceInstance();
			si.getServerConnection().logout();
		} catch (VirtualMachineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	@Override
	public void processRequest(VirtualMachineRequest req)  throws VirtualMachineException{
		MachineRequest machineReq = req.getMachineRequest();
		
		if (MachineRequest.Create == machineReq) {
			this.cloneVM(req);
		} else if (MachineRequest.Start == machineReq) {
			this.startVM(req);
		} else if (MachineRequest.Stop == machineReq) {
			this.stopVM(req);
		} else if (MachineRequest.Suspend == machineReq) {
			this.pauseVM(req);
		} else if (MachineRequest.Remove == machineReq) {
			this.removeVM(req);
		}
		
		
	}
	
	
	@Override
	public void startVM(VirtualMachineRequest req)
			throws VirtualMachineException {
		// TODO Auto-generated method stub
		System.out.println("================================");
		System.out.println("Command: Power On");
		try {
			VirtualMachine vm = getVirtualMachine(req.getMachineName());
			// Check if vm is powered off or suspend
			if (VirtualMachinePowerState.poweredOff.equals(getVmState(vm))
					|| VirtualMachinePowerState.suspended
							.equals(getVmState(vm))) {
				Task task = vm.powerOnVM_Task(null);
				while (task.getTaskInfo().getState() == TaskInfoState.running) {
					System.out.println(" Powering on in progress... ");
					Thread.currentThread().sleep(3000);
				}
			} else {
				System.out
						.println("Virtual Machine is not powered off or suspended");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
			throw new VirtualMachineException(e.getMessage());
		}

	}

	@Override
	public void stopVM(VirtualMachineRequest req) {
		System.out.println("================================");
		System.out.println("Command: Power Off");
		try {
			VirtualMachine vm = getVirtualMachine(req.getMachineName());
			// Check if vm is up
			if (VirtualMachinePowerState.poweredOn.equals(getVmState(vm))) {
				Task task = vm.powerOffVM_Task();
				while (task.getTaskInfo().getState() == TaskInfoState.running) {
					System.out.println(" Powering off in progress... ");
					Thread.currentThread().sleep(3000);
				}
			} else {
				System.out.println("Virtual Machine is not powered on");
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	@Override
	public void pauseVM(VirtualMachineRequest req) {
		System.out.println("================================");
		System.out.println("Command: Suspend");
		try {
			VirtualMachine vm = getVirtualMachine(req.getMachineName());
			// check if vm is powered on
			if (VirtualMachinePowerState.poweredOn.equals(getVmState(vm))) {
				Task task = vm.suspendVM_Task();
				while (task.getTaskInfo().getState() == TaskInfoState.running) {
					System.out.println(" Suspend in progress... ");
					Thread.currentThread().sleep(3000);
				}
			} else {
				System.out.println("Virtual Machine is not powered on");
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	@Override
	public void removeVM(VirtualMachineRequest req) {
		System.out.println("================================");
		System.out.println("Command: Remove");
		try {
			VirtualMachine vm = getVirtualMachine(req.getMachineName());
			// check if vm is powered on
			if (VirtualMachinePowerState.poweredOff.equals(getVmState(vm))) {
				Task task = vm.destroy_Task();
				while (task.getTaskInfo().getState() == TaskInfoState.running) {
					System.out.println(" Remove progress... ");
					Thread.currentThread().sleep(3000);
				}
			} else {
				System.out.println("Virtual Machine is not powered off");
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	private VirtualMachinePowerState getVmState(VirtualMachine vm) {
		VirtualMachineRuntimeInfo runtime = vm.getRuntime();
		return runtime.getPowerState();
	}

	private VirtualMachineCloneSpec createCloneSpec(VirtualMachineRequest req,
			boolean powerOn) throws InvalidProperty, RuntimeFault,
			RemoteException, VirtualMachineException {
		/*
		 * VirtualMachineCloneSpec cloneSpec = new VirtualMachineCloneSpec();
		 * cloneSpec.setLocation(new VirtualMachineRelocateSpec());
		 * cloneSpec.setPowerOn(false); cloneSpec.setTemplate(false);
		 */
		Folder rootFolder = getServiceInstance().getRootFolder();
		Datacenter dc = (Datacenter) new InventoryNavigator(rootFolder)
				.searchManagedEntity("Datacenter", this.vmwareDatacenter);
		HostSystem host = (HostSystem) new InventoryNavigator(rootFolder)
				.searchManagedEntity("HostSystem", this.vmwareHost);

		ResourcePool rp = null;
		ManagedEntity[] rps = new InventoryNavigator(dc)
				.searchManagedEntities("ResourcePool");

		for (int i = 0; i < rps.length; i++) {
			if (rps[i].getName().equals(this.vmwareResourcePool)) {
				rp = (ResourcePool) rps[i];
			}
		}

		Datastore ds = (Datastore) new InventoryNavigator(rootFolder)
				.searchManagedEntity("Datastore", this.vmwareDatastore);

		VirtualMachineCloneSpec cloneSpec = new VirtualMachineCloneSpec();
		VirtualMachineRelocateSpec relocateSpec = new VirtualMachineRelocateSpec();
		relocateSpec.setHost(host.getMOR());
		relocateSpec.setPool(rp.getMOR());
		relocateSpec.setDatastore(ds.getMOR());

		VirtualMachineConfigSpec vmSpec = new VirtualMachineConfigSpec();
		vmSpec.setName(req.getMachineName());
		vmSpec.setMemoryMB(Long.valueOf(req.getMemory().toString()));
		vmSpec.setNumCPUs(req.getNumberCPUs());

		cloneSpec.setConfig(vmSpec);
		cloneSpec.setLocation(relocateSpec);
		cloneSpec.setPowerOn(powerOn);
		cloneSpec.setTemplate(false);

		return cloneSpec;

	}

	@Override
	public void cloneVM(VirtualMachineRequest req)
			throws VirtualMachineException {

		Folder rootFolder = getServiceInstance().getRootFolder();
		VirtualMachine vm;
		try {
			vm = (VirtualMachine) new InventoryNavigator(rootFolder)
					.searchManagedEntity("VirtualMachine", req.getTemplateName());
			Folder teamFolder = (Folder) new InventoryNavigator(rootFolder)
					.searchManagedEntity("Folder", this.vmwareTeamFolder);
			VirtualMachineCloneSpec cloneSpec = createCloneSpec(req, false);

			Task task = vm.cloneVM_Task(teamFolder, req.getMachineName(),
					cloneSpec);
			while (task.getTaskInfo().getState() == TaskInfoState.running) {
				System.out.println(" Cloning in progress... ");
				Thread.currentThread().sleep(3000);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new VirtualMachineException(e.getMessage());
		}

	}

	@Override
    public VirtualMachine getVirtualMachine(String vmName)
			throws VirtualMachineException, InvalidProperty, RuntimeFault,
			RemoteException {
		VirtualMachine vm = null;
		Folder rootFolder = getServiceInstance().getRootFolder();
		String name = rootFolder.getName();
		ManagedEntity[] mes = new InventoryNavigator(rootFolder)
				.searchManagedEntities("VirtualMachine");
		if (mes == null || mes.length == 0) {
			return null;
		}
		for (ManagedEntity me : mes) {
			//System.out.println(" Virtual Machine:" + me.getName());
			if (me.getName().equalsIgnoreCase(vmName)) {
				vm = (VirtualMachine) me;
				break;
			}
		}
		return vm;
	}

    /**
     * finds all virtual machine by on and suspended state
     * @return
     */
    @Override
    public List<VirtualMachineDetail> findAllVirtualMachineByOnAndSuspendState(){

        List<MachineStatus> machineStatusList= new ArrayList<MachineStatus>();
        machineStatusList.add(MachineStatus.On);
        machineStatusList.add(MachineStatus.Suspended);
        List<VirtualMachineDetail> virtualMachineDetailList = VirtualMachineDetail.findByMachineStatus(machineStatusList);

        return virtualMachineDetailList;
    }

    @Override
    public PerformanceManager getPerformanceManager() throws VirtualMachineException {
        return getServiceInstance().getPerformanceManager();
    }

}