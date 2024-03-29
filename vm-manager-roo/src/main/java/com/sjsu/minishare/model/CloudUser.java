package com.sjsu.minishare.model;

import java.util.List;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.sjsu.minishare.model.CloudUser;

import javax.annotation.Generated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RooJavaBean
@RooToString
@RooEntity
public class CloudUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;

    @NotNull
    private String userName;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private Boolean active;

    @NotNull
    private String password;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String zip;

    private String userState;

    private String country;

    @NotNull
    @Size(min = 13, max = 16)
    private String cardNumber;

    private Integer cardExpirationMonth;

    private Integer cardExpirationYear;

    private Integer cardCvvNumber;

    public static CloudUser findCloudUserByUserNameAndPassword(String userName, String password){

        CloudUser cloudUser = (CloudUser)entityManager().createQuery("SELECT o FROM CloudUser o where o.userName = :userName AND o.password = :password")
                .setParameter("userName", userName)
                .setParameter("password", password)
                .getSingleResult();

        return cloudUser;
    }
    
	public static List<CloudUser> findCloudUserEntriesByName(String userName, int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM CloudUser o where o.userName = :userName", CloudUser.class).setFirstResult(firstResult).setMaxResults(maxResults).setParameter("userName", userName).getResultList();
    }

	
}
