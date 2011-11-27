package com.sjsu.minishare.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.sjsu.minishare.model.CloudUser;

public class ApplicationUtil {

	public static String getLogonUsername() {	
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();

		return username;
	}

	public static CloudUser getLogonCloudUser() {
		return CloudUser.findCloudUser(getLogonUsername());
	}
}
