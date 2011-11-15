package com.sjsu.minishare.web;

import com.sjsu.minishare.model.CloudUser;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "cloudusers", formBackingObject = CloudUser.class)
@RequestMapping("/cloudusers")
@Controller
public class CloudUserController {
}
