package com.sjsu.minishare.model;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;

@RooJavaBean
@RooToString
@RooEntity
public class CloudUser {

    @NotNull
    private Integer userId;

    @NotNull
    private String userName;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private Boolean active;

    private String password;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String zip;

    private String userState;

    private String country;

    private String cardNumber;

    private Integer cardExpirationMonth;

    private Integer cardExpirationYear;

    private Integer cardCvvNumber;
}
