// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sjsu.minishare.model;

import com.sjsu.minishare.model.CloudUser;
import java.lang.Integer;
import java.lang.String;

privileged aspect UserCredit_Roo_JavaBean {
    
    public Integer UserCredit.getCreditId() {
        return this.creditId;
    }
    
    public void UserCredit.setCreditId(Integer creditId) {
        this.creditId = creditId;
    }
    
    public Integer UserCredit.getTotalCredits() {
        return this.totalCredits;
    }
    
    public void UserCredit.setTotalCredits(Integer totalCredits) {
        this.totalCredits = totalCredits;
    }
    
    public Integer UserCredit.getTotalCreditsUsed() {
        return this.totalCreditsUsed;
    }
    
    public void UserCredit.setTotalCreditsUsed(Integer totalCreditsUsed) {
        this.totalCreditsUsed = totalCreditsUsed;
    }
    
    public Integer UserCredit.getAmount() {
        return this.amount;
    }
    
    public void UserCredit.setAmount(Integer amount) {
        this.amount = amount;
    }
    
    public String UserCredit.getPaymentTransaction() {
        return this.paymentTransaction;
    }
    
    public void UserCredit.setPaymentTransaction(String paymentTransaction) {
        this.paymentTransaction = paymentTransaction;
    }
    
    public CloudUser UserCredit.getCloudUser() {
        return this.cloudUser;
    }
    
    public void UserCredit.setCloudUser(CloudUser cloudUser) {
        this.cloudUser = cloudUser;
    }
    
}
