package com.sjsu.minishare.web;

import com.sjsu.minishare.model.VirtualMachineMonitor;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "virtualmachinemonitors", formBackingObject = VirtualMachineMonitor.class)
@RequestMapping("/virtualmachinemonitors")
@Controller
public class VirtualMachineMonitorController {
}
