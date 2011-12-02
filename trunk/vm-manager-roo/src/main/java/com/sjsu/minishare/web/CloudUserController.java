package com.sjsu.minishare.web;

import com.sjsu.minishare.model.CloudUser;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RooWebScaffold(path = "cloudusers", formBackingObject = CloudUser.class)
@RequestMapping("/cloudusers")
@Controller
public class CloudUserController {
	
	@RequestMapping(method = RequestMethod.GET)
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            uiModel.addAttribute("cloudusers", CloudUser.findCloudUserEntriesByName(username, page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
//            uiModel.addAttribute("cloudusers", CloudUser.findCloudUserEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) CloudUser.countCloudUsers() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
        	
    		uiModel.addAttribute("cloudusers", CloudUser.findCloudUser(username));
//            uiModel.addAttribute("cloudusers", CloudUser.findAllCloudUsers());
        }
        return "cloudusers/list";
    }


}
