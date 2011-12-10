package com.sjsu.minishare.model;

import java.util.List;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import com.sjsu.minishare.model.CloudUser;
import javax.persistence.ManyToOne;

@RooJavaBean
@RooToString
@RooEntity
public class UserCredit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer creditId;

    private Integer totalCredits;

    private Integer totalCreditsUsed;
    
    private float amount;
    
    private String paymentTransaction;

    @ManyToOne
    private CloudUser cloudUser;
    
    @SuppressWarnings("finally")
	public static UserCredit findCloudUser(CloudUser cloudUser) {
    	UserCredit ucredit = null;
    	try{
    	ucredit = (UserCredit)entityManager().createQuery("SELECT o FROM UserCredit o where o.cloudUser = :cloudUser", UserCredit.class)
        		.setParameter("cloudUser", cloudUser)
        		.getSingleResult();
    	System.out.println("User Credit ... "+ucredit);
    	return ucredit;
    	}
    	catch(Exception e){
    		System.out.println("Exception is ... "+e);
    	}
    	
    	finally{
    		return ucredit;
    	}
    }

    public void updateUserCredit(VirtualMachineDetail machineDetail, VirtualMachineMonitor virtualMachineMonitor){

    }

    /**
     * finds all user credits for given user
     * @param userId
     * @return
     */
    public static List<UserCredit> findAllUserCreditsByUserId(int userId) {
        return entityManager().createQuery("SELECT o FROM UserCredit o where o.cloudUser.userId=:userId", UserCredit.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    /**
     * finds user credit entries by user id
     * @param firstResult
     * @param maxResults
     * @param userId
     * @return
     */
    public static List<UserCredit> findUserCreditEntriesByUserId(int firstResult, int maxResults, int userId) {
        return entityManager().createQuery("SELECT o FROM UserCredit o where o.cloudUser.userId=:userId", UserCredit.class)
                .setParameter("userId", userId)
                .setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    /**
     * count user credits by user id
     * @param userId
     * @return
     */
    public static long countUserCreditsByUserId(int userId) {
        return entityManager().createQuery("SELECT COUNT(o) FROM UserCredit o where o.cloudUser.userId=:userId", Long.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }
}
