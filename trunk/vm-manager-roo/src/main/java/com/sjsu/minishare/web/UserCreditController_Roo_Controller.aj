// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sjsu.minishare.web;

import com.sjsu.minishare.model.CloudUser;
import com.sjsu.minishare.model.UserCredit;
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

privileged aspect UserCreditController_Roo_Controller {
    
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String UserCreditController.createForm(Model uiModel) {
        uiModel.addAttribute("userCredit", new UserCredit());
        return "usercredits/create";
    }
    
    @RequestMapping(value = "/{creditId}", method = RequestMethod.GET)
    public String UserCreditController.show(@PathVariable("creditId") Integer creditId, Model uiModel) {
        uiModel.addAttribute("usercredit", UserCredit.findUserCredit(creditId));
        uiModel.addAttribute("itemId", creditId);
        return "usercredits/show";
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String UserCreditController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            uiModel.addAttribute("usercredits", UserCredit.findUserCreditEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) UserCredit.countUserCredits() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("usercredits", UserCredit.findAllUserCredits());
        }
        return "usercredits/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String UserCreditController.update(@Valid UserCredit userCredit, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("userCredit", userCredit);
            return "usercredits/update";
        }
        uiModel.asMap().clear();
        userCredit.merge();
        return "redirect:/usercredits/" + encodeUrlPathSegment(userCredit.getCreditId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{creditId}", params = "form", method = RequestMethod.GET)
    public String UserCreditController.updateForm(@PathVariable("creditId") Integer creditId, Model uiModel) {
        uiModel.addAttribute("userCredit", UserCredit.findUserCredit(creditId));
        return "usercredits/update";
    }
    
    @RequestMapping(value = "/{creditId}", method = RequestMethod.DELETE)
    public String UserCreditController.delete(@PathVariable("creditId") Integer creditId, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        UserCredit.findUserCredit(creditId).remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/usercredits";
    }
    
    @ModelAttribute("cloudusers")
    public Collection<CloudUser> UserCreditController.populateCloudUsers() {
        return CloudUser.findAllCloudUsers();
    }
    
    @ModelAttribute("usercredits")
    public Collection<UserCredit> UserCreditController.populateUserCredits() {
        return UserCredit.findAllUserCredits();
    }
    
    String UserCreditController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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