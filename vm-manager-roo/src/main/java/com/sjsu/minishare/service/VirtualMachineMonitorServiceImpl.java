package com.sjsu.minishare.service;

import com.sjsu.minishare.exception.VirtualMachineException;
import com.sjsu.minishare.model.MachineStatus;
import com.sjsu.minishare.model.VirtualMachineDetail;
import com.sjsu.minishare.model.VirtualMachineMonitor;
import com.vmware.vim25.*;
import com.vmware.vim25.mo.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * virtual machine monitoring service
 * User: ckempaiah
 * Date: 11/29/11
 * Time: 1:07 AM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class VirtualMachineMonitorServiceImpl implements VirtualMachineMonitorService {
    @Autowired
    private VirtualMachineService virtualMachineService;

    private static final int VM_MONITORING_INTERVAL_MS = 5000;
    private static final Log log = LogFactory.getLog(VirtualMachineServiceImpl.class);
    //CK commenting the scheduling as quick stats doesn't seem to update the required monitoring info
    //@Scheduled(fixedDelay = VM_MONITORING_INTERVAL_MS)
    public void populateVirtualMachineStats() throws VirtualMachineException, RemoteException {

        List<VirtualMachineDetail> virtualMachineDetailList = virtualMachineService.findAllVirtualMachineByOnAndSuspendState();

        if (virtualMachineDetailList != null && virtualMachineDetailList.size() > 0){
            for (VirtualMachineDetail virtualMachineDetail: virtualMachineDetailList) {
                log.debug("Found following machines in ON / Suspend state: " + virtualMachineDetailList);

                VirtualMachine virtualMachine = virtualMachineService.getVirtualMachine(virtualMachineDetail.getMachineName());
                VirtualMachineSummary summary = virtualMachine.getSummary();
                VirtualMachineQuickStats quickStats = summary.getQuickStats();
                log.debug(String.format("Memory Usage: %s CPU Usage %s", quickStats.getGuestMemoryUsage(), quickStats.getOverallCpuUsage()));
                crateVirtualMachineMonitor(virtualMachineDetail, quickStats.getOverallCpuUsage()
                        , quickStats.getGuestMemoryUsage(), summary.getRuntime().getPowerState());
            }
        } else {
           log.debug("No VM Found in ON / Suspend state");
        }
    }

    /**
     * create virtual machine monitor
     * @param virtualMachineDetail
     * @param overallCpuUsage
     * @param guestMemoryUsage
     * @param powerState
     */
    private void crateVirtualMachineMonitor(VirtualMachineDetail virtualMachineDetail
            ,Integer overallCpuUsage
            , Integer guestMemoryUsage
            , VirtualMachinePowerState powerState){
        VirtualMachineMonitor virtualMachineMonitor = new VirtualMachineMonitor();
        virtualMachineMonitor.setMonitorInterval(VM_MONITORING_INTERVAL_MS/1000);
        virtualMachineMonitor.setOverallCpuUsage(overallCpuUsage);
        virtualMachineMonitor.setGuestMemoryUsage(guestMemoryUsage);
        virtualMachineMonitor.setVirtualMachineDetail(virtualMachineDetail);
        if(VirtualMachinePowerState.poweredOn.equals(powerState)){
            virtualMachineMonitor.setMachineStatus(MachineStatus.On.name());
        } else if (VirtualMachinePowerState.suspended.equals(powerState)) {
            virtualMachineMonitor.setMachineStatus(MachineStatus.Off.name());
        }
        virtualMachineMonitor.persist();
    }

    //@Scheduled(fixedDelay = VM_MONITORING_INTERVAL_MS)
    public void testVirtualMachineRuntimeInfo() throws Exception {

        Folder rootFolder= virtualMachineService.getServiceInstance().getRootFolder();

        String name = rootFolder.getName();
		ManagedEntity vmEntity = new InventoryNavigator(rootFolder)
				.searchManagedEntity("VirtualMachine", "machine-db-new");
        VirtualMachine vm = (VirtualMachine)vmEntity;
        VirtualMachineSummary summary = ((VirtualMachine)vmEntity).getSummary();

        VirtualMachineQuickStats quickStats = summary.getQuickStats();
        log.debug(String.format("Vm name %s, Vm status %s", vm.getName(), vm.getRuntime().getPowerState()));
        log.debug(String.format("Memory Usage: %s CPU Usage %s", quickStats.getGuestMemoryUsage(), quickStats.getOverallCpuDemand()));
        log.debug("Uptime in secs:" + quickStats.getUptimeSeconds());
        //log.debug("Vm quick Stats" + quickStats);
        //log.debug(String.format("Max cup %s, Max memory %s ", summary.getRuntime().getMaxCpuUsage(), summary.getRuntime().getMaxMemoryUsage()));
    }


    //@Scheduled(fixedDelay = VM_MONITORING_INTERVAL_MS)
    public void getPerformanceMetrics() throws VirtualMachineException, InvalidProperty, RemoteException {

        PerformanceManager performanceManager = virtualMachineService.getPerformanceManager();
        VirtualMachine virtualMachine = virtualMachineService.getVirtualMachine("machine-db-new");
        PerfMetricId[] perfMetricIds = performanceManager.queryAvailablePerfMetric(virtualMachine, null, null, 60);
//        int [] counterIds = new int[perfMetricIds.length];
//        for (int i=0; i < perfMetricIds.length; i++){
//            log.debug(String.format("Perf Metric id %s name %s" , perfMetricIds[i].counterId, perfMetricIds[i].getInstance()));
//            counterIds[i] = perfMetricIds[i].getCounterId();
//        }

        PerfCounterInfo [] perfCounterInfos = performanceManager.getPerfCounter();
        List<PerfMetricId> perfMetricIdList = new ArrayList<PerfMetricId>();
        for (int i=0;i < perfCounterInfos.length; i ++){

            if (PerfSummaryType.average.equals(perfCounterInfos[i].getRollupType())
                    && (perfCounterInfos[i].getNameInfo().key.equals("usagemhz") || perfCounterInfos[i].getNameInfo().key.equals("consumed"))) {
                log.debug(String.format("name %s, groupinfo %s, unit info %s summaryType %s"
                    , perfCounterInfos[i].getNameInfo().key
                    , perfCounterInfos[i].getGroupInfo().key
                    , perfCounterInfos[i].getUnitInfo().key
                    , perfCounterInfos[i].getRollupType()));
                PerfMetricId pmid = new PerfMetricId();
                pmid.setCounterId(perfCounterInfos[i].getKey());
                pmid.setInstance("");
                perfMetricIdList.add(pmid);
            }
        }

        PerfQuerySpec perfQuerySpec = new PerfQuerySpec();
        perfQuerySpec.setEntity(virtualMachine.getMOR());
        perfQuerySpec.setMetricId((PerfMetricId[])perfMetricIdList.toArray(new PerfMetricId[]{}));

        //perfQuerySpec.setMaxSample();
        perfQuerySpec.setMaxSample(1);
        perfQuerySpec.setIntervalId(60);

        PerfEntityMetricBase[] pValues = performanceManager.queryPerf(new PerfQuerySpec[]{perfQuerySpec});
        if (pValues != null) {
          printPerformanceMetrics(pValues);
        }


    }

    private void printPerformanceMetrics(PerfEntityMetricBase[] pValues) {
        for (int a = 0; a < pValues.length; a++) {
            String entityDesc = pValues[a].getEntity().getType() + ":" + pValues[a].getEntity().get_value();
            log.debug("Entity Description " + entityDesc);
            if (pValues[a] instanceof PerfEntityMetric) {
                PerfMetricSeries[] metricSeries = ((PerfEntityMetric) pValues[a]).getValue();
                PerfSampleInfo[] perfSampleInfos = ((PerfEntityMetric) pValues[a]).getSampleInfo();
                System.out.println("Sampling Times and Intervales:");

                for (int i = 0; perfSampleInfos != null && i < perfSampleInfos.length; i++) {
                    System.out.println("Sample time: " + perfSampleInfos[i].getTimestamp().getTime());
                    System.out.println("Sample interval (sec):" + perfSampleInfos[i].getInterval());
                }
                System.out.println("Sample values:");
                for (int j = 0; metricSeries != null && j < metricSeries.length; ++j) {
                    System.out.println("Perf counter ID:" + metricSeries[j].getId().getCounterId());
                    System.out.println("Device instance ID:" + metricSeries[j].getId().getInstance());

                    if (metricSeries[j] instanceof PerfMetricIntSeries) {
                        PerfMetricIntSeries val = (PerfMetricIntSeries) metricSeries[j];
                        long[] longs = val.getValue();
                        for (int k = 0; k < longs.length; k++) {
                            System.out.print(longs[k] + " ");
                        }
                        System.out.println("Total:" + longs.length);
                    } else if (metricSeries[j] instanceof PerfMetricSeriesCSV) { // it is not likely coming here...
                        PerfMetricSeriesCSV val = (PerfMetricSeriesCSV) metricSeries[j];
                        System.out.println("CSV value:" + val.getValue());
                    }
                }

            }

        }

    }
}
