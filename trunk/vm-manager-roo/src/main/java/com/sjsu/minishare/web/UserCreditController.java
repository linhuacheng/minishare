package com.sjsu.minishare.web;

import com.sjsu.minishare.model.UserCredit;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "usercredits", formBackingObject = UserCredit.class)
@RequestMapping("/usercredits")
@Controller
public class UserCreditController {
}
