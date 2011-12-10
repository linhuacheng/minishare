package com.sjsu.minishare.web;

import com.sjsu.minishare.model.CloudUserPrincipal;

import com.sjsu.minishare.model.VirtualMachineMonitor;
import com.sjsu.minishare.model.VirtualMachineMonitorDto;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Iterator;
import java.util.List;

@RooWebScaffold(path = "virtualmachinemonitors", formBackingObject = VirtualMachineMonitor.class)
@RequestMapping("/virtualmachinemonitors")
@Controller
public class VirtualMachineMonitorController {

    @RequestMapping(method = RequestMethod.GET)
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Object details = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //System.out.println("Get principal" + details.getClass());
        Integer userId = ((CloudUserPrincipal)details).getUserId();
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            uiModel.addAttribute("virtualmachinemonitors", VirtualMachineMonitor.findVirtualMachineMonitorEntriesByUser(userId, page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) VirtualMachineMonitor.countVirtualMachineMonitors() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("virtualmachinemonitors", VirtualMachineMonitor.findAllVirtualMachineMonitorsByUser(userId));
        }
        return "virtualmachinemonitors/list";
    }

    @RequestMapping(value = "/viewUsageByStatus")
    public String listMonitorByStatus(Model uiModel) {
        Object details = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Integer userId = ((CloudUserPrincipal) details).getUserId();
        List<VirtualMachineMonitorDto> virtualMachineMonitorDtoList = VirtualMachineMonitor.findVirtualMachineMonitorAggByMachineStatus(userId);
        Iterator<VirtualMachineMonitorDto> dtoItr = virtualMachineMonitorDtoList.iterator();
        //Remove null values if any
        while (dtoItr.hasNext()){
            VirtualMachineMonitorDto monitorDto = dtoItr.next();
            if (monitorDto == null){
                dtoItr.remove();
            }
        }
        uiModel.addAttribute("virtualmachinemonitordtos", virtualMachineMonitorDtoList);

        return "virtualmachinemonitors/usageReportByStatus";
    }
}
