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

@RooWebScaffold(path = "cloudusers", formBackingObject = CloudUser.class)
@RequestMapping("/cloudusers")
@Controller
public class CloudUserController {

}
