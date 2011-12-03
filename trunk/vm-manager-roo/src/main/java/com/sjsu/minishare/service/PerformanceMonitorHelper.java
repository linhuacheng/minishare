package com.sjsu.minishare.service;

import com.sjsu.minishare.exception.VirtualMachineException;
import com.vmware.vim25.*;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.VirtualMachine;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.rmi.RemoteException;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: ckempaiah
 * Date: 12/3/11
 * Time: 12:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class PerformanceMonitorHelper {
     private static final Log log = LogFactory.getLog(PerformanceMonitorHelper.class);

     public static void printPerformanceCounterInfo(PerfCounterInfo perfCounterInfo) {
        log.debug(String.format("Name:%s, Group Info:%s, Unit Info:%s SummaryType:%s "
                , perfCounterInfo.getNameInfo().key
                , perfCounterInfo.getGroupInfo().key
                , perfCounterInfo.getUnitInfo().key
                , perfCounterInfo.getRollupType()));
    }

    public void printPerfMetric(PerfEntityMetric perfEntityMetric) {
        PerfMetricSeries[] metricSeries = perfEntityMetric.getValue();
        PerfSampleInfo[] perfSampleInfos = perfEntityMetric.getSampleInfo();
        log.debug("Sampling Times and Intervals:");

        for (int i = 0; perfSampleInfos != null && i < perfSampleInfos.length; i++) {
            log.debug("Sample time: " + perfSampleInfos[i].getTimestamp().getTime());
            log.debug("Sample interval (sec):" + perfSampleInfos[i].getInterval());
        }
        log.debug("Sample values:");
        for (int j = 0; metricSeries != null && j < metricSeries.length; ++j) {
            log.debug("Perf counter ID:" + metricSeries[j].getId().getCounterId());
            log.debug("Device instance ID:" + metricSeries[j].getId().getInstance());

            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            if (metricSeries[j] instanceof PerfMetricIntSeries) {
                PerfMetricIntSeries val = (PerfMetricIntSeries) metricSeries[j];

                long[] lValue = val.getValue();
                for (int k = 0; k < lValue.length; k++) {
                    printWriter.print(lValue[k] + " ");
                }
                printWriter.print(" Total:" + lValue.length);
                log.debug(stringWriter);
            } else if (metricSeries[j] instanceof PerfMetricSeriesCSV) {
                PerfMetricSeriesCSV val = (PerfMetricSeriesCSV) metricSeries[j];
                log.debug("CSV value:" + val.getValue());
            }
        }
    }

    private void displayValues(PerfEntityMetricBase[] pValues) {
        for (int a = 0; a < pValues.length; a++) {

            String entityDesc = pValues[a].getEntity().getType() + ":" + pValues[a].getEntity().get_value();
            log.debug("Entity Description " + entityDesc);
            if (pValues[a] instanceof PerfEntityMetric) {
                printPerfMetric((PerfEntityMetric) pValues[a]);
            }
        }
    }

    private void displayValue(PerfEntityMetricBase pValue) {
        String entityDesc = pValue.getEntity().getType() + ":" + pValue.getEntity().get_value();
        log.debug("Entity Description " + entityDesc);
        if (pValue instanceof PerfEntityMetric) {
            printPerfMetric((PerfEntityMetric) pValue);
        }

    }

    private void printPerfMetricId(PerfMetricId[] perfMetricIds) {

        int[] counterIds = new int[perfMetricIds.length];
        for (int i = 0; i < perfMetricIds.length; i++) {
            log.debug(String.format("Perf Metric id:%s, Name: %s", perfMetricIds[i].counterId, perfMetricIds[i].getInstance()));
            counterIds[i] = perfMetricIds[i].getCounterId();
        }
    }

//    public void getRealTimeCompositePerformanceMetrics() throws VirtualMachineException, InvalidProperty, RemoteException {
//
//        initialize();
//        VirtualMachine virtualMachine = virtualMachineService.getVirtualMachine("machine-db-new");
//        PerfProviderSummary perfProviderSummary = performanceManager.queryPerfProviderSummary(virtualMachine);
//        Integer refreshRate = perfProviderSummary.getRefreshRate();
//        PerfMetricId[] perfMetricIds = performanceManager.queryAvailablePerfMetric(virtualMachine, null, null, refreshRate);
//        log.debug("Available Metrics " + perfMetricIds.length);
//        //perfMetricIds = getCPUMemoryPerformanceMetricId();
//        printPerfMetricId(perfMetricIds);
//        PerfQuerySpec perfQuerySpec = new PerfQuerySpec();
//        perfQuerySpec.setEntity(virtualMachine.getMOR());
//        perfQuerySpec.setMetricId(perfMetricIds);
//        Calendar curTime = virtualMachineService.getServiceInstance().currentTime();
//        Calendar startTime = (Calendar)curTime.clone();
//        Calendar endTime = (Calendar)curTime.clone();
//        endTime.roll(Calendar.HOUR, -60);
//        startTime.roll(Calendar.DATE, -1);
//
//        //perfQuerySpec.setMaxSample();
//        //perfQuerySpec.setMaxSample(3);
//        perfQuerySpec.setStartTime(startTime);
//        perfQuerySpec.setEndTime(curTime);
//        perfQuerySpec.setIntervalId(refreshRate);
//
//        PerfCompositeMetric pv = performanceManager.queryPerfComposite(perfQuerySpec);
//        if (pv != null) {
//            displayValue(pv.getEntity());
//        }
//    }

//        //@Scheduled(fixedDelay = VM_MONITORING_INTERVAL_MS)
//    public void testVirtualMachineRuntimeInfo() throws Exception {
//
//        Folder rootFolder = virtualMachineService.getServiceInstance().getRootFolder();
//
//        String name = rootFolder.getName();
//        ManagedEntity vmEntity = new InventoryNavigator(rootFolder)
//                .searchManagedEntity("VirtualMachine", "machine-db-new");
//        VirtualMachine vm = (VirtualMachine) vmEntity;
//        VirtualMachineSummary summary = ((VirtualMachine) vmEntity).getSummary();
//
//        VirtualMachineQuickStats quickStats = summary.getQuickStats();
//        log.debug(String.format("Vm name %s, Vm status %s", vm.getName(), vm.getRuntime().getPowerState()));
//        log.debug(String.format("Memory Usage: %s CPU Usage %s", quickStats.getGuestMemoryUsage(), quickStats.getOverallCpuDemand()));
//        log.debug("Uptime in secs:" + quickStats.getUptimeSeconds());
//        //log.debug("Vm quick Stats" + quickStats);
//        //log.debug(String.format("Max cup %s, Max memory %s ", summary.getRuntime().getMaxCpuUsage(), summary.getRuntime().getMaxMemoryUsage()));
//    }
}
