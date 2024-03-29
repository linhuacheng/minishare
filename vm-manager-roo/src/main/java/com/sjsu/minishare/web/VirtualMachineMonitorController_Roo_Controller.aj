// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sjsu.minishare.web;

import com.sjsu.minishare.model.VirtualMachineDetail;
import com.sjsu.minishare.model.VirtualMachineMonitor;
import java.io.UnsupportedEncodingException;
import java.lang.Integer;
import java.lang.String;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect VirtualMachineMonitorController_Roo_Controller {
    

    @RequestMapping(value = "/{monitorId}", method = RequestMethod.GET)
    public String VirtualMachineMonitorController.show(@PathVariable("monitorId") Integer monitorId, Model uiModel) {
        uiModel.addAttribute("virtualmachinemonitor", VirtualMachineMonitor.findVirtualMachineMonitor(monitorId));
        uiModel.addAttribute("itemId", monitorId);
        return "virtualmachinemonitors/show";
    }
    

    @ModelAttribute("virtualmachinedetails")
    public Collection<VirtualMachineDetail> VirtualMachineMonitorController.populateVirtualMachineDetails() {
        return VirtualMachineDetail.findAllVirtualMachineDetails();
    }
    
    @ModelAttribute("virtualmachinemonitors")
    public Collection<VirtualMachineMonitor> VirtualMachineMonitorController.populateVirtualMachineMonitors() {
        return VirtualMachineMonitor.findAllVirtualMachineMonitors();
    }
    
    String VirtualMachineMonitorController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        }
        catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
    
}
