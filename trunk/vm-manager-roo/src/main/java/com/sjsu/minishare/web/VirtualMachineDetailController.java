package com.sjsu.minishare.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import com.sjsu.minishare.model.MachineRequest;
import com.sjsu.minishare.model.MachineStatus;
import com.sjsu.minishare.model.VirtualMachineDetail;
import com.sjsu.minishare.model.VirtualMachineRequest;
import com.sjsu.minishare.model.VirtualMachineTemplate;
import com.sjsu.minishare.service.VirtualMachineService;
import com.sjsu.minishare.util.ApplicationUtil;
import com.sjsu.minishare.util.VirtualMachineConstants;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RooWebScaffold(path = "virtualmachinedetails", formBackingObject = VirtualMachineDetail.class)
@RequestMapping("/virtualmachinedetails")
@Controller
public class VirtualMachineDetailController {
	
	@Autowired
	@Qualifier("virtualMachineService")
	VirtualMachineService virtualMachineService;

	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String templateForm(Model uiModel) {
		uiModel.addAttribute("virtualMachineTemplates", VirtualMachineConstants.getVirtualMachineTemplates());
		return "virtualmachinedetails/template";
	}
	
    @RequestMapping(value = "/{machineId}", method = RequestMethod.GET)
    public String show(@PathVariable("machineId") Integer machineId, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("virtualmachinedetail", VirtualMachineDetail.findVirtualMachineDetail(machineId));
        uiModel.addAttribute("itemId", machineId);
        return "virtualmachinedetails/show";
    }
    
    @RequestMapping(value = "/{machineId}", params = "credential", method = RequestMethod.GET)
    public String showCredential(@PathVariable("machineId") Integer machineId, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("virtualmachinedetail", VirtualMachineDetail.findVirtualMachineDetail(machineId));
        uiModel.addAttribute("itemId", machineId);
        return "virtualmachinedetails/showCredential";
    }
    
	@RequestMapping(method = RequestMethod.POST)
	public String createRecord(@Valid VirtualMachineDetail virtualMachineDetail,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		String templateId = httpServletRequest.getParameter("templateId");
		String state = httpServletRequest.getParameter("state");
		if (templateId != null && templateId.length()>0 && !"CREATE".equalsIgnoreCase(state)) {
			 return createForm(virtualMachineDetail, bindingResult, uiModel,
						httpServletRequest);
		} else {
			 return create(virtualMachineDetail, bindingResult, uiModel,
						httpServletRequest);
		}
	}
	
	public String create(@Valid VirtualMachineDetail virtualMachineDetail,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("virtualMachineDetail", virtualMachineDetail);
			addDateTimeFormatPatterns(uiModel);
			return "virtualmachinedetails/create";
		}
		uiModel.asMap().clear();
		
		//Create the virtual Machine
		String templateId = httpServletRequest.getParameter("templateId");
		VirtualMachineTemplate template = VirtualMachineConstants.getVirtualMachineTemplateById(templateId);
						
		virtualMachineDetail.setUserId(ApplicationUtil.getLogonCloudUser());
		virtualMachineDetail.setDefaultUsername(template.getDefaultUsername());
		virtualMachineDetail.setDefaultPassword(template.getDefaultPassword());
		virtualMachineDetail.setIpAddress(template.getDefaultIpAddress());
		
		virtualMachineDetail.persist();
		
		processVirtualMachineRequest(virtualMachineDetail, "CREATE", template.getTemplateName());
		
		return "redirect:/virtualmachinedetails/"
				+ encodeUrlPathSegment(virtualMachineDetail.getMachineId()
						.toString(), httpServletRequest);
	}
    
	public String createForm(@Valid VirtualMachineDetail virtualMachineDetail,
				BindingResult bindingResult, Model uiModel,
				HttpServletRequest httpServletRequest) {
		
		VirtualMachineDetail vmDetail = new VirtualMachineDetail();
		vmDetail.setUsageInMinutes(new Float(0));
		vmDetail.setCreditsUsed(new Integer(0));
		vmDetail.setTotalCost(new Float(0));
		vmDetail.setMachineStatus(MachineStatus.Off.toString());
		
		String templateId = httpServletRequest.getParameter("templateId");
		for (VirtualMachineTemplate template: VirtualMachineConstants.getVirtualMachineTemplates()) {
			boolean same =template.getId().toString().equals(templateId);
			if (template.getId().toString().equals(templateId)) {
				
				vmDetail.setOperatingSystem(template.getOperatingSystem());
				vmDetail.setMemory(template.getMemory());
				vmDetail.setNumberCPUs(template.getNumberCPUs());
			}
		}
	
		uiModel.addAttribute("virtualMachineDetail", vmDetail);
		uiModel.addAttribute("numCPUs", VirtualMachineConstants.getCPUNums());
		uiModel.addAttribute("memories", VirtualMachineConstants.getMemories());
		uiModel.addAttribute("templateId", templateId);
		httpServletRequest.setAttribute("templateId", templateId);

		addDateTimeFormatPatterns(uiModel);
		return "virtualmachinedetails/create";
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String update(@Valid VirtualMachineDetail virtualMachineDetail,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		String state = httpServletRequest.getParameter("state");
		if (state != null && state.length()>0) {
			return updateState(virtualMachineDetail,
					bindingResult, uiModel,
					httpServletRequest);
		} else {
			return updateVirtualMachineDetail(virtualMachineDetail,
					 bindingResult, uiModel,
					 httpServletRequest);
		}
	}
	
	public String updateState(@Valid VirtualMachineDetail virtualMachineDetail,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {		

		String state = httpServletRequest.getParameter("state");
		String id = httpServletRequest.getParameter("id");
		
		VirtualMachineDetail tempMachineDetail = VirtualMachineDetail.findVirtualMachineDetail(new Integer(id));
		// Before start, check if current state is suspend or off
		if (state.equalsIgnoreCase("START") && 
				( MachineStatus.Off.toString().equalsIgnoreCase(tempMachineDetail.getMachineStatus()) || 
				  MachineStatus.Suspended.toString().equalsIgnoreCase(tempMachineDetail.getMachineStatus()) ) ) {
			tempMachineDetail.setMachineStatus(MachineStatus.On.toString());
			processVirtualMachineRequest(tempMachineDetail, state, "");
		// Before turning off, check if current state is on		
		} else 	if (state.equalsIgnoreCase("STOP") && 
				MachineStatus.On.toString().equalsIgnoreCase(tempMachineDetail.getMachineStatus()) ) {
			processVirtualMachineRequest(tempMachineDetail, state, "");
			tempMachineDetail.setMachineStatus(MachineStatus.Off.toString());
		// Before suspending, check if current state is on
		} else 	if (state.equalsIgnoreCase("PAUSE") &&
				MachineStatus.On.toString().equalsIgnoreCase(tempMachineDetail.getMachineStatus()) ) {
			tempMachineDetail.setMachineStatus(MachineStatus.Suspended.toString());
			processVirtualMachineRequest(tempMachineDetail, state, "");
		} else {
			// invalid request should not go here
			System.out.println("Invalid Request");
		}
		
		tempMachineDetail.persist();

		String page = httpServletRequest.getParameter("page");
		String size = httpServletRequest.getParameter("size");
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/virtualmachinedetails";
	}
	public String updateVirtualMachineDetail(@Valid VirtualMachineDetail virtualMachineDetail,
				BindingResult bindingResult, Model uiModel,
				HttpServletRequest httpServletRequest) {		
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("virtualMachineDetail", virtualMachineDetail);
			addDateTimeFormatPatterns(uiModel);
			return "virtualmachinedetails/update";
		}
		uiModel.asMap().clear();
		virtualMachineDetail.merge();

		return "redirect:/virtualmachinedetails/"
				+ encodeUrlPathSegment(virtualMachineDetail.getMachineId()
						.toString(), httpServletRequest);
	}

    @RequestMapping(value = "/{machineId}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("machineId") Integer machineId, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
    	VirtualMachineDetail virtualMachineDetail =  VirtualMachineDetail.findVirtualMachineDetail(machineId);
    	// check first if the vm is off before removing
    	if (MachineStatus.Off.toString().equalsIgnoreCase(virtualMachineDetail.getMachineStatus()) ) {
    		// Send Request to remove VM
    		processVirtualMachineRequest(virtualMachineDetail, "DELETE", "");
    		virtualMachineDetail.remove();
    	}
    	
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/virtualmachinedetails";
    }
	
	private void processVirtualMachineRequest(VirtualMachineDetail virtualMachineDetail, String state, String templateName) {
		VirtualMachineRequest req = new VirtualMachineRequest();
		req.setMachineName(virtualMachineDetail.getMachineName());
		
		if ("START".equalsIgnoreCase(state)) {
			req.setMachineRequest(MachineRequest.Start);
		} else 	if ("STOP".equalsIgnoreCase(state)) {
			req.setMachineRequest(MachineRequest.Stop);
		} else 	if ("PAUSE".equalsIgnoreCase(state)) {
			req.setMachineRequest(MachineRequest.Suspend);
		} else if ("DELETE".equalsIgnoreCase(state)) {
			req.setMachineRequest(MachineRequest.Remove);
		} else if ("CREATE".equalsIgnoreCase(state)) {
			req.setMemory(virtualMachineDetail.getMemory());
			req.setNumberCPUs(virtualMachineDetail.getNumberCPUs());
			req.setTemplateName(templateName);
			req.setMachineRequest(MachineRequest.Create);
		}
		
		try {
			virtualMachineService.processRequest(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
