package com.sjsu.minishare.web;

import com.sjsu.minishare.model.CloudUser;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RooWebScaffold(path = "registration", formBackingObject = CloudUser.class)
@RequestMapping("/registration")
@Controller
public class RegistrationController {

    @RequestMapping(method = RequestMethod.POST)
    public String create(@Valid CloudUser cloudUser, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("cloudUser", cloudUser);
            return "registration/create";
        }
        uiModel.asMap().clear();
        cloudUser.persist();
        return "registration/success";
    }
}
