package com.sjsu.minishare.web;

import com.sjsu.minishare.model.VirtualMachineDetail;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "virtualmachinedetails", formBackingObject = VirtualMachineDetail.class)
@RequestMapping("/virtualmachinedetails")
@Controller
public class VirtualMachineDetailController {
}
