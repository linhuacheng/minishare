package com.sjsu.minishare.model;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import com.sjsu.minishare.model.CloudUser;
import javax.persistence.ManyToOne;

@RooJavaBean
@RooToString
@RooEntity
public class UserCredit {

    @NotNull
    private Integer creditId;

    private Integer totalCredits;

    private Integer totalCreditsUsed;

    @ManyToOne
    private CloudUser cloudUser;
}
