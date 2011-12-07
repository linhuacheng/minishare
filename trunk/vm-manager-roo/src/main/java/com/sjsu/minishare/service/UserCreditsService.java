package com.sjsu.minishare.service;

import com.sjsu.minishare.model.UserCredit;
import com.sjsu.minishare.model.VirtualMachineDetail;

/**
 * Created by IntelliJ IDEA.
 * User: ckempaiah
 * Date: 12/6/11
 * Time: 11:40 PM
 * To change this template use File | Settings | File Templates.
 */
public interface UserCreditsService {


    int calculateMemoryCreditUsage(Integer memoryUsageMbs, Integer minutesUsed);

    int calculateCpuCreditUsage(Integer cpuUsageInMhz, Integer minutesUsed);

    int calculateCreditsUsageForMemoryNCpu(Integer memoryUsageMbs, Integer cpuUsageInMhz, Integer minutesUsed);

    UserCredit updateUserCredit(VirtualMachineDetail machineDetail, Integer creditsUsed);
}
