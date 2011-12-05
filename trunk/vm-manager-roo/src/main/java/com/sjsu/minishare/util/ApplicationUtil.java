package com.sjsu.minishare.util;

import java.util.LinkedList;
import java.util.Queue;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.sjsu.minishare.model.CloudUser;
import com.sjsu.minishare.model.UserCredit;

public class ApplicationUtil {
	
	private static Queue<String> IP_ADDRESS = new LinkedList<String>();
	
	static {
		IP_ADDRESS.offer("130.65.150.201");
		IP_ADDRESS.offer("130.65.150.203");
		IP_ADDRESS.offer("130.65.150.222");
		IP_ADDRESS.offer("130.65.150.223");
	}

	public static String getLogonUsername() {	
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();

		return username;
	}
	
	public static boolean logonUserHaveCredits() {
		CloudUser user = getLogonCloudUser();
		UserCredit credit = UserCredit.findCloudUser(user);
		if (credit == null) {
			return false;
		} else {
			return (credit.getTotalCredits().intValue() > 0);
		}
	}
	

	public static CloudUser getLogonCloudUser() {
		return CloudUser.findCloudUser(getLogonUsername());
	}
	
	public static String getNextIpAddress() {
		if (IP_ADDRESS.isEmpty()) {
			return "";
		} else {
		    return IP_ADDRESS.poll();
		}
	}
}
