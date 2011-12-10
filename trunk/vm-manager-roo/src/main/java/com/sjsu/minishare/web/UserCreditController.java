package com.sjsu.minishare.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.sjsu.minishare.model.CloudUser;
import com.sjsu.minishare.model.CloudUserPrincipal;
import com.sjsu.minishare.model.UserCredit;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RooWebScaffold(path = "usercredits", formBackingObject = UserCredit.class)
@RequestMapping("/usercredits")
@Controller
public class UserCreditController {
	
	  @RequestMapping(method = RequestMethod.POST)
	    public String create(@Valid UserCredit userCredit, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
	      float amount, amt, amt_existing;
	      int totalCredits;
	      int tc;
		  if (bindingResult.hasErrors()) {
	            uiModel.addAttribute("userCredit", userCredit);
	            return "usercredits/create";
	      }
		  amount = userCredit.getAmount();
		  if(amount >= 1000){
	    	  userCredit.setPaymentTransaction("TRANSACTION REJECTED");
	    	  totalCredits = 0;
	    	  userCredit.setAmount(0);
	      }
	      else{
	    	  userCredit.setPaymentTransaction("TRANSACTION ACCEPTED");
	    	  totalCredits = (int) (((float)(Math.round(amount * 100.0) / 100.0)) * 100000);
	    	  userCredit.setAmount((float)(Math.round(amount * 100.0) / 100.0));
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
			  uiModel.addAttribute("usercredit",userCredit);
			  return "usercredits/showcredits";
//			  return "redirect:/usercredits/" + encodeUrlPathSegment(userCredit.getCreditId().toString(), httpServletRequest);
		  }
		  
		  else{
			  tc = ucredit.getTotalCredits();  //tc = 200
			  amt_existing = ucredit.getAmount();
			  amt_existing = (float)(Math.round((tc/100000.0)*100.0)/100.0);
			  amt = amt_existing + amount;
			  if(amt >= 1000){
		    	  ucredit.setPaymentTransaction("TRANSACTION REJECTED");
		    	  totalCredits = 0;
		    	  amt = amt_existing;
		      }
		      else{
		    	  ucredit.setPaymentTransaction("TRANSACTION ACCEPTED");
		    	  amount = (float)(Math.round(amount * 100.0) / 100.0);
		    	  totalCredits = (int) (amount * 100000); 
		      }
			  
			  totalCredits = totalCredits+tc; // 200 + 0
			  ucredit.setTotalCredits(totalCredits);
			  amt = (float)(Math.round(amt * 100.0) / 100.0);
			  ucredit.setAmount(amt);
			  
		      uiModel.asMap().clear();
		      ucredit.merge();
		      
		      uiModel.addAttribute("usercredit",ucredit);
			  return "usercredits/showcredits";
//		      return "redirect:/usercredits/" + encodeUrlPathSegment(ucredit.getCreditId().toString(), httpServletRequest);
		  }
		  
//	      return "redirect:/usercredits/" + encodeUrlPathSegment(ucredit.getCreditId().toString(), httpServletRequest);
	    }


    @RequestMapping(method = RequestMethod.GET)
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Object details = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //System.out.println("Get principal" + details.getClass());
        Integer userId = ((CloudUserPrincipal) details).getUserId();
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            uiModel.addAttribute("usercredits", UserCredit.findUserCreditEntriesByUserId(userId, page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) UserCredit.countUserCreditsByUserId(userId) / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("usercredits", UserCredit.findAllUserCreditsByUserId(userId));
        }
        return "usercredits/list";
    }
	
}


