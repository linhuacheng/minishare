package com.sjsu.minishare.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import com.sjsu.minishare.model.MachineStatus;
import com.sjsu.minishare.model.VirtualMachineDetail;
import com.sjsu.minishare.model.VirtualMachineTemplate;

@RooWebScaffold(path = "virtualmachinedetails", formBackingObject = VirtualMachineDetail.class)
@RequestMapping("/virtualmachinedetails")
@Controller
public class VirtualMachineDetailController {
	
	static List<VirtualMachineTemplate> virtualMachineTemplates;
	static List<Integer> numCPUs; 
	
	static {
		virtualMachineTemplates = new ArrayList<VirtualMachineTemplate>();
		
		VirtualMachineTemplate template1 = new VirtualMachineTemplate();
		template1.setId(1);
		template1.setMemory("512");
		template1.setNumberCPUs(1);
		template1.setOperatingSystem("Debian");
		
		VirtualMachineTemplate template2 = new VirtualMachineTemplate();
		template2.setId(2);
		template2.setMemory("512");
		template2.setNumberCPUs(1);
		template2.setOperatingSystem("Oracle Linux");
		
		virtualMachineTemplates.add(template1);
		virtualMachineTemplates.add(template2);
		
		
		numCPUs = new ArrayList<Integer>();
		numCPUs.add(Integer.valueOf(1));
		numCPUs.add(Integer.valueOf(2));
		numCPUs.add(Integer.valueOf(3));
		numCPUs.add(Integer.valueOf(4));
				
	}
	
	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String templateForm(Model uiModel) {
		uiModel.addAttribute("virtualMachineTemplates", virtualMachineTemplates);
		return "virtualmachinedetails/template";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String createRecord(@Valid VirtualMachineDetail virtualMachineDetail,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		String templateId = httpServletRequest.getParameter("templateId");
		if (templateId != null && templateId.length()>0) {
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
		virtualMachineDetail.persist();
		return "redirect:/virtualmachinedetails/"
				+ encodeUrlPathSegment(virtualMachineDetail.getMachineId()
						.toString(), httpServletRequest);
	}
    
	public String createForm(@Valid VirtualMachineDetail virtualMachineDetail,
				BindingResult bindingResult, Model uiModel,
				HttpServletRequest httpServletRequest) {
		
		VirtualMachineDetail vmDetail = new VirtualMachineDetail();
		vmDetail.setCreditsUsed(new Integer(0));
		vmDetail.setTotalCost(new Float(0));
		vmDetail.setMachineStatus(MachineStatus.Off.toString());
		
		String templateId = httpServletRequest.getParameter("templateId");
		for (VirtualMachineTemplate template: virtualMachineTemplates) {
			boolean same =template.getId().toString().equals(templateId);
			if (template.getId().toString().equals(templateId)) {
				
				vmDetail.setOperatingSystem(template.getOperatingSystem());
				vmDetail.setMemory(template.getMemory());
				vmDetail.setNumberCPUs(template.getNumberCPUs());
			}
		}
	
		uiModel.addAttribute("virtualMachineDetail", vmDetail);
		uiModel.addAttribute("numCPUs", numCPUs);

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
		if (state.equalsIgnoreCase("START")) {
			tempMachineDetail.setMachineStatus(MachineStatus.On.toString());
		} else 	if (state.equalsIgnoreCase("STOP")) {
			tempMachineDetail.setMachineStatus(MachineStatus.Off.toString());
		} else 	if (state.equalsIgnoreCase("PAUSE")) {
			tempMachineDetail.setMachineStatus(MachineStatus.Suspended.toString());
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


}
