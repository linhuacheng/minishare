// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sjsu.minishare.model;

import java.lang.String;

privileged aspect CloudUser_Roo_ToString {
    
    public String CloudUser.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Active: ").append(getActive()).append(", ");
        sb.append("AddressLine1: ").append(getAddressLine1()).append(", ");
        sb.append("AddressLine2: ").append(getAddressLine2()).append(", ");
        sb.append("CardCvvNumber: ").append(getCardCvvNumber()).append(", ");
        sb.append("CardExpirationMonth: ").append(getCardExpirationMonth()).append(", ");
        sb.append("CardExpirationYear: ").append(getCardExpirationYear()).append(", ");
        sb.append("CardNumber: ").append(getCardNumber()).append(", ");
        sb.append("City: ").append(getCity()).append(", ");
        sb.append("Country: ").append(getCountry()).append(", ");
        sb.append("FirstName: ").append(getFirstName()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("LastName: ").append(getLastName()).append(", ");
        sb.append("Password: ").append(getPassword()).append(", ");
        sb.append("UserId: ").append(getUserId()).append(", ");
        sb.append("UserName: ").append(getUserName()).append(", ");
        sb.append("UserState: ").append(getUserState()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Zip: ").append(getZip());
        return sb.toString();
    }
    
}
