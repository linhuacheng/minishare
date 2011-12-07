package com.sjsu.minishare.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * custom principle object
 * User: ckempaiah
 * Date: 12/7/11
 * Time: 2:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class CloudUserPrincipal extends User{
    private Integer userId;

    public CloudUserPrincipal(Integer userId, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
