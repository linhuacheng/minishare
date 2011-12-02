package com.sjsu.minishare.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.sjsu.minishare.model.CloudUser;
import com.sjsu.minishare.model.UserCredit;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RooWebScaffold(path = "usercredits", formBackingObject = UserCredit.class)
@RequestMapping("/usercredits")
@Controller
public class UserCreditController {
	
	  @RequestMapping(method = RequestMethod.POST)
	    public String create(@Valid UserCredit userCredit, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
	      int amount;
	      int totalCredits;
	      int tc, amt;
		  if (bindingResult.hasErrors()) {
	            uiModel.addAttribute("userCredit", userCredit);
	            return "usercredits/create";
	      }
		  amount = userCredit.getAmount();
		  if(amount >= 1000){
	    	  userCredit.setPaymentTransaction("TRANSACTION REJECTED");
	    	  totalCredits = 0;
	      }
	      else{
	    	  userCredit.setPaymentTransaction("TRANSACTION ACCEPTED");
	    	  totalCredits = amount * 100000; 
	      }
	    	  
	      userCredit.setTotalCredits(totalCredits);
	      
		  Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		  String username = ((UserDetails)principal).getUsername();
		  CloudUser cuser = CloudUser.findCloudUser(username);
		  UserCredit ucredit = UserCredit.findCloudUser(cuser);
		  userCredit.setCloudUser(cuser);
		  if(ucredit == null){
		      uiModel.asMap().clear();
			  userCredit.persist();
			  return "redirect:/usercredits/" + encodeUrlPathSegment(userCredit.getCreditId().toString(), httpServletRequest);
		  }
		  
		  else{
			  tc = ucredit.getTotalCredits();  //tc = 200
			  amt = ucredit.getAmount();
			  amt = amt + amount;
			  if(amt >= 1000){
		    	  ucredit.setPaymentTransaction("TRANSACTION REJECTED");
		    	  totalCredits = 0;
		    	  amt = amt - amount;
		      }
		      else{
		    	  userCredit.setPaymentTransaction("TRANSACTION ACCEPTED");
		    	  totalCredits = amount * 100000; 
		      }
			  
			  totalCredits = totalCredits+tc; // 200 + 0
			  ucredit.setTotalCredits(totalCredits);
			  ucredit.setAmount(amt);
			  
		      uiModel.asMap().clear();
		      ucredit.merge();
		      
		      return "redirect:/usercredits/" + encodeUrlPathSegment(ucredit.getCreditId().toString(), httpServletRequest);
		  }
		  
//	      return "redirect:/usercredits/" + encodeUrlPathSegment(ucredit.getCreditId().toString(), httpServletRequest);
	    }
	
}
