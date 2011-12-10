package com.sjsu.minishare.service;

import com.sjsu.minishare.exception.VirtualMachineException;
import com.sjsu.minishare.model.*;
import com.vmware.vim25.*;
import com.vmware.vim25.mo.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    private static PerfCounterInfo[] perfCounterInfos;
    public static final String GROUP_CPU = "cpu";
    public static final String GROUP_MEM = "mem";
    public static final String COUNTER_USAGEMHZ = "usagemhz";
    public static final String COUNTER_CONSUMED = "consumed";
    public static final int KILO_BYTES = 1024;

    private PerformanceManager performanceManager;
    private PerfMetricId cpuUsageMetricId;
    private PerfMetricId memoryConsumedMetricId;

    @Autowired
    private VirtualMachineService virtualMachineService;

    @Autowired
    private UserCreditsService userCreditsService;

    private static final int VM_MONITORING_INTERVAL_MS = 60000;
    private static final int PER_MINUTE_CONVERSION_FACTOR = 60000;
    private static final Log log = LogFactory.getLog(VirtualMachineMonitorServiceImpl.class);

    /**
     * monitors virtual machines at scheduled intervals
     * updates monitoring information to database
     *
     * @throws VirtualMachineException
     * @throws RemoteException
     */
    @Scheduled(fixedDelay = VM_MONITORING_INTERVAL_MS)
    public void populateVirtualMachineMonitorInfo() throws VirtualMachineException, RemoteException {

        List<VirtualMachineDetail> virtualMachineDetailList = virtualMachineService.findAllVirtualMachineByOnAndSuspendState();
        List<PerformanceMetricBean> performanceMetricBeanList = null;
        if (virtualMachineDetailList != null && virtualMachineDetailList.size() > 0) {

            for (VirtualMachineDetail virtualMachineDetail : virtualMachineDetailList) {
                log.debug("Found following machines in ON / Suspend state: " + virtualMachineDetailList);

                VirtualMachine virtualMachine = virtualMachineService.getVirtualMachine(virtualMachineDetail.getMachineName());
                VirtualMachineSummary summary = virtualMachine.getSummary();

                int memory = Integer.parseInt(virtualMachineDetail.getMemory());
                int creditsUsed;
                VirtualMachineMonitor virtualMachineMonitor = null;

                if (MachineStatus.Suspended.name().equalsIgnoreCase(virtualMachineDetail.getMachineStatus())) {
                    //machine in suspended status update max memory as memory consumed
                    creditsUsed = userCreditsService.calculateMemoryCreditUsage(memory, VM_MONITORING_INTERVAL_MS / PER_MINUTE_CONVERSION_FACTOR);
                    Calendar endTime = Calendar.getInstance();
                    Calendar startTime = (Calendar)endTime.clone();
                    startTime.roll(Calendar.MINUTE, -(VM_MONITORING_INTERVAL_MS / PER_MINUTE_CONVERSION_FACTOR));

                    virtualMachineMonitor = createVirtualMachineMonitor(virtualMachineDetail
                                    , 0
                                    , memory, summary.getRuntime().getPowerState()
                                    , creditsUsed, startTime.getTime(), endTime.getTime());

                }  else {
                    performanceMetricBeanList = getRealTimePerformanceMetrics(virtualMachine);
                    if (performanceMetricBeanList != null && performanceMetricBeanList.size() > 0){
                        PerformanceMetricBean cpuMetricBean = getPerformanceMetricBeanByMetricId(performanceMetricBeanList, cpuUsageMetricId);
                        PerformanceMetricBean memoryMetricBean = getPerformanceMetricBeanByMetricId(performanceMetricBeanList, memoryConsumedMetricId);

                        log.debug(String.format("CPU utilization %s, Memory Utilization %s "
                                ,cpuMetricBean != null ? cpuMetricBean.getAvgValue(): 0
                                ,memoryMetricBean.getAvgValue()
                                ,cpuMetricBean != null ? cpuMetricBean.getStartTime(): 0
                                ,cpuMetricBean != null ? cpuMetricBean.getEndTime() : 0
                        ));
                        if (cpuMetricBean != null){

                            creditsUsed = userCreditsService.calculateCreditsUsageForMemoryNCpu(memory, (int)cpuMetricBean.getAvgValue()
                                                            , VM_MONITORING_INTERVAL_MS / PER_MINUTE_CONVERSION_FACTOR);
                            log.debug(String.format("CpuUsed=(%s), MemoryUsed=(%s), CreditsCharged=%s"
                                , cpuMetricBean.getAvgValue()
                                , memory, creditsUsed));
                            virtualMachineMonitor = createVirtualMachineMonitor(virtualMachineDetail
                                    , (int)cpuMetricBean.getAvgValue()
                                    , memory, summary.getRuntime().getPowerState(), creditsUsed, cpuMetricBean.getStartTime(), cpuMetricBean.getEndTime());
                        }

                    }
                }

                //update credits consumed and usage minutes
                if (virtualMachineMonitor != null){
                   UserCredit userCredit = userCreditsService.updateUserCredit(virtualMachineDetail, virtualMachineMonitor.getCreditsCharged());
                    if (userCredit.getTotalCredits() < 0){
                        log.debug("Must shutdown the virtual machine as user has utilized all the credits");
                        forcePowerOffVM(virtualMachineDetail);
                    }
                }
            }
        } else {
            log.debug("No VM Found in ON / Suspend state");
        }
    }

    /**
     * forcefully powers off VM due to low balance
     *
     * @param virtualMachineDetail
     * @throws VirtualMachineException
     */
    private void forcePowerOffVM(VirtualMachineDetail virtualMachineDetail) throws VirtualMachineException {
        VirtualMachineRequest vmr = new VirtualMachineRequest();
        vmr.setMachineName(virtualMachineDetail.getMachineName());
        if (MachineStatus.Suspended.name().equalsIgnoreCase(virtualMachineDetail.getMachineStatus())) {
            //for suspended vm power on the vm then
            vmr.setMachineRequest(MachineRequest.Start);
            virtualMachineService.processRequest(new VirtualMachineRequest());
        }
        vmr.setMachineRequest(MachineRequest.Stop);
        virtualMachineService.processRequest(vmr);
        virtualMachineDetail.setMachineStatus(MachineStatus.Off.name());
        virtualMachineDetail.merge();
        virtualMachineDetail.flush();
    }



    /**
     * create virtual machine monitor
     *
     * @param virtualMachineDetail
     * @param overallCpuUsage
     * @param guestMemoryUsage
     * @param powerState
     */
    private VirtualMachineMonitor createVirtualMachineMonitor(VirtualMachineDetail virtualMachineDetail
            , Integer overallCpuUsage
            , Integer guestMemoryUsage
            , VirtualMachinePowerState powerState
            , Integer creditsUsed
            , Date startTime
            , Date endTime ) {
        VirtualMachineMonitor virtualMachineMonitor = new VirtualMachineMonitor();
        virtualMachineMonitor.setMonitorInterval(VM_MONITORING_INTERVAL_MS / PER_MINUTE_CONVERSION_FACTOR);
        virtualMachineMonitor.setOverallCpuUsage(overallCpuUsage);
        virtualMachineMonitor.setGuestMemoryUsage(guestMemoryUsage);
        virtualMachineMonitor.setVirtualMachineDetail(virtualMachineDetail);
        virtualMachineMonitor.setCreditsCharged(creditsUsed);
        virtualMachineMonitor.setStartTime(new Timestamp(startTime.getTime()));
        virtualMachineMonitor.setEndTime(new Timestamp(endTime.getTime()));
        if (VirtualMachinePowerState.poweredOn.equals(powerState)) {
            virtualMachineMonitor.setMachineStatus(MachineStatus.On.name());
        } else if (VirtualMachinePowerState.suspended.equals(powerState)) {
            virtualMachineMonitor.setMachineStatus(MachineStatus.Off.name());
        }
        virtualMachineMonitor.persist();
        return virtualMachineMonitor;
    }

    /**
     * gets real time monitor information
     *
     * @throws VirtualMachineException
     * @throws InvalidProperty
     * @throws RemoteException
     */
    //@Scheduled(fixedDelay = VM_MONITORING_INTERVAL_MS)

    public List<PerformanceMetricBean> getRealTimePerformanceMetrics(VirtualMachine virtualMachine)
            throws VirtualMachineException, InvalidProperty, RemoteException {

        List<PerformanceMetricBean> performanceMetricBeanList= null;
        initialize();
        PerfProviderSummary perfProviderSummary = performanceManager.queryPerfProviderSummary(virtualMachine);
        Integer refreshRate = perfProviderSummary.getRefreshRate();
        PerfMetricId[] perfMetricIds;// = performanceManager.queryAvailablePerfMetric(virtualMachine, null, null, refreshRate);
        perfMetricIds = getCPUAndMemoryPerformanceMetricId();

        PerfQuerySpec perfQuerySpec = new PerfQuerySpec();
        perfQuerySpec.setEntity(virtualMachine.getMOR());
        perfQuerySpec.setMetricId(perfMetricIds);
        perfQuerySpec.setMaxSample(3);
        perfQuerySpec.setIntervalId(refreshRate);

        PerfEntityMetricBase[] pValues = performanceManager.queryPerf(new PerfQuerySpec[]{perfQuerySpec});

        if (pValues != null && pValues.length > 0) {
            log.debug("Pvalues length:" +pValues.length);
            if (pValues[0] instanceof  PerfEntityMetric) {
                performanceMetricBeanList = convertPerfMetricToBean((PerfEntityMetric)pValues[0]);

            }
        }
        return performanceMetricBeanList;
    }

    /**
     * initializes performance manager
     * @throws VirtualMachineException
     */
    private void initialize() throws VirtualMachineException {
        if (performanceManager == null) {
            performanceManager = virtualMachineService.getPerformanceManager();
        }
        if (perfCounterInfos == null) {
            perfCounterInfos = performanceManager.getPerfCounter();
        }
        if (cpuUsageMetricId == null || memoryConsumedMetricId == null) {
            for (int i = 0; i < perfCounterInfos.length; i++) {

                if (PerfSummaryType.average.equals(perfCounterInfos[i].getRollupType())){
                    if (perfCounterInfos[i].getGroupInfo().key.equalsIgnoreCase(GROUP_CPU) && perfCounterInfos[i].getNameInfo().key.equals(COUNTER_USAGEMHZ)){
                        cpuUsageMetricId = new PerfMetricId();
                        cpuUsageMetricId.setCounterId(perfCounterInfos[i].getKey());
                        cpuUsageMetricId.setInstance("");
                    } else if (perfCounterInfos[i].getGroupInfo().key.equalsIgnoreCase(GROUP_MEM) && perfCounterInfos[i].getNameInfo().key.equals(COUNTER_CONSUMED)) {
                        memoryConsumedMetricId = new PerfMetricId();
                        memoryConsumedMetricId.setCounterId(perfCounterInfos[i].getKey());
                        memoryConsumedMetricId.setInstance("");
                    }
                }
            }
        }
    }

    /**
     * gets cpu and memory performance metric id
     * @return
     */
    private PerfMetricId[] getCPUAndMemoryPerformanceMetricId() {

        List<PerfMetricId> perfMetricIdList = new ArrayList<PerfMetricId>();

        for (int i = 0; i < perfCounterInfos.length; i++) {

            if (PerfSummaryType.average.equals(perfCounterInfos[i].getRollupType())
                    && ((perfCounterInfos[i].getGroupInfo().key.equalsIgnoreCase(GROUP_CPU) && perfCounterInfos[i].getNameInfo().key.equals(COUNTER_USAGEMHZ))
                    || (perfCounterInfos[i].getGroupInfo().key.equalsIgnoreCase(GROUP_MEM) && perfCounterInfos[i].getNameInfo().key.equals(COUNTER_CONSUMED)))) {
                PerformanceMonitorHelper.printPerformanceCounterInfo(perfCounterInfos[i]);
                PerfMetricId pmid = new PerfMetricId();
                pmid.setCounterId(perfCounterInfos[i].getKey());
                pmid.setInstance("");
                perfMetricIdList.add(pmid);
            }
        }
        return perfMetricIdList.toArray(new PerfMetricId[]{});
    }

    /**
     * gets performance counter info for given counter id
     *
     * @param counterId
     * @return
     */
    private PerfCounterInfo getPerfCounterInfo(Integer counterId) {

        for (int i = 0; i < perfCounterInfos.length; i++) {
            if (counterId.equals(perfCounterInfos[i].getKey())) {
                return perfCounterInfos[i];
            }
        }
        return null;
    }

    /**
     * gets performance metric bean for give perf metric id
     *
     * @param performanceMetricBeans
     * @param perfMetricId
     * @return
     */
    private PerformanceMetricBean getPerformanceMetricBeanByMetricId(List<PerformanceMetricBean> performanceMetricBeans, PerfMetricId perfMetricId) {

        for (PerformanceMetricBean performanceMetricBean: performanceMetricBeans) {

            if (perfMetricId.getCounterId() == performanceMetricBean.getCounterId()) {
                return performanceMetricBean;
            }
        }
        return null;
    }

    /**
     * converts performance metric to performance metric bean
     *
     * @param perfEntityMetric
     * @return
     */
    private List<PerformanceMetricBean> convertPerfMetricToBean(PerfEntityMetric perfEntityMetric) {
        List<PerformanceMetricBean> performanceMetricBeanList = new ArrayList<PerformanceMetricBean>();
        PerfMetricSeries[] metricSeries = perfEntityMetric.getValue();
        PerfSampleInfo[] perfSampleInfos = perfEntityMetric.getSampleInfo();
        long aggValue = 0;
        Date startTime = null;
        Date endTime = null;

        for (int i = 0; perfSampleInfos != null && i < perfSampleInfos.length; i++) {
            if(i== 0){
                startTime = perfSampleInfos[i].getTimestamp().getTime();
            } else if (i == perfSampleInfos.length-1){
                endTime =   perfSampleInfos[i].getTimestamp().getTime();
            }
            log.debug("Sample time: " + perfSampleInfos[i].getTimestamp().getTime());
            log.debug("Sample interval (sec):" + perfSampleInfos[i].getInterval());
        }
        log.debug("Sample values:");

        for (int j = 0; metricSeries != null && j < metricSeries.length; ++j) {
            PerformanceMetricBean performanceMetricBean = new PerformanceMetricBean();
            PerfCounterInfo perfCounterInfo = getPerfCounterInfo(metricSeries[j].getId().getCounterId());
            performanceMetricBean.setPerfCounterInfo(perfCounterInfo);
            performanceMetricBean.setStartTime(startTime);
            performanceMetricBean.setEndTime(endTime);
            log.debug("Perf counter ID:" + metricSeries[j].getId().getCounterId());
            log.debug("Device instance ID:" + metricSeries[j].getId().getInstance());
            if (metricSeries[j] instanceof PerfMetricIntSeries) {
                PerfMetricIntSeries val = (PerfMetricIntSeries) metricSeries[j];

                long[] lValue = val.getValue();
                for (int k = 0; k < lValue.length; k++) {
                    log.debug("long value: " + lValue[k]);
                    aggValue += lValue[k];
                }
                aggValue = aggValue / lValue.length;
            } else {
                throw new IllegalArgumentException("Invalid metric format");
            }
            performanceMetricBean.setAvgValue(aggValue);
            performanceMetricBeanList.add(performanceMetricBean);
        }

        return performanceMetricBeanList;
    }

}
