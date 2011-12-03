package com.sjsu.minishare.service;

import com.sjsu.minishare.exception.VirtualMachineException;

import java.rmi.RemoteException;

/**
 * Created by IntelliJ IDEA.
 * User: ckempaiah
 * Date: 11/29/11
 * Time: 1:07 AM
 * To change this template use File | Settings | File Templates.
 */
public interface VirtualMachineMonitorService {

    public void populateVirtualMachineMonitorInfo() throws VirtualMachineException, RemoteException;

}
