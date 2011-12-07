package com.sjsu.minishare.service;

import com.sjsu.minishare.model.CloudUser;
import com.sjsu.minishare.model.UserCredit;
import com.sjsu.minishare.model.VirtualMachineDetail;
import com.sjsu.minishare.model.VirtualMachineMonitor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

/**
 * user credit service implementation
 * User: ckempaiah
 * Date: 12/6/11
 * Time: 11:42 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class UserCreditsServiceImpl implements UserCreditsService {
    private static final Log log = LogFactory.getLog(UserCreditsServiceImpl.class);
    public static final float MEMORY_USAGE_CHARGE_PER_MB_PER_MINUTE_IN_CREDIT_UNITS = (0.5f) / 512;
    public static final float CPU_USAGE_CHARGE_PER_MGHZ_PER_MINUTE_IN_CREDIT_UNITS = 0.5f;

    @Override
    public int calculateMemoryCreditUsage(Integer memoryUsageMbs, Integer minutesUsed) {
        int creditsUsed = 0;

        if (memoryUsageMbs != null) {
            creditsUsed = Math.round(memoryUsageMbs * MEMORY_USAGE_CHARGE_PER_MB_PER_MINUTE_IN_CREDIT_UNITS * minutesUsed);
        }
        return creditsUsed;
    }

    @Override
    public int calculateCpuCreditUsage(Integer cpuUsageInMhz, Integer minutesUsed) {
        int creditsUsed = 0;
        if (cpuUsageInMhz != null) {
            creditsUsed = Math.round(cpuUsageInMhz * CPU_USAGE_CHARGE_PER_MGHZ_PER_MINUTE_IN_CREDIT_UNITS *minutesUsed);
        }
        return creditsUsed;
    }

    @Override
    public int calculateCreditsUsageForMemoryNCpu(Integer memoryUsageMbs, Integer cpuUsageInMhz, Integer minutesUsed) {
        int creditsUsed = calculateCpuCreditUsage(cpuUsageInMhz, minutesUsed);
        creditsUsed = creditsUsed  + calculateMemoryCreditUsage(memoryUsageMbs, minutesUsed);
        return creditsUsed;
    }

    /**
     * updates user credit based on the usage.
     *
     * @param machineDetail
     * @return
     */
    @Override
    public UserCredit updateUserCredit(VirtualMachineDetail machineDetail, Integer creditsUsed) {
        CloudUser cloudUser = machineDetail.getUserId();
        UserCredit userCredit = UserCredit.findCloudUser(cloudUser);
        if (creditsUsed > 0) {
            userCredit.setTotalCredits(userCredit.getTotalCredits() - creditsUsed);
        }
        log.debug(String.format("CreditsCharged=%s, CreditsRemaining=(%s)",
                creditsUsed, userCredit.getTotalCredits()));
        userCredit.merge();
        return userCredit;
    }
}
