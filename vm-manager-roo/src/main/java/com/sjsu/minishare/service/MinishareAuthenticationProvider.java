package com.sjsu.minishare.service;


import com.sjsu.minishare.model.CloudUser;
import com.sjsu.minishare.model.CloudUserPrincipal;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NonUniqueResultException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ckempaiah
 * Date: 11/22/11
 * Time: 12:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class MinishareAuthenticationProvider  extends AbstractUserDetailsAuthenticationProvider{

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected UserDetails retrieveUser(String userName, UsernamePasswordAuthenticationToken authenticationToken) throws AuthenticationException {
        String password = (String) authenticationToken.getCredentials();
        CloudUser cloudUser;
        if (!StringUtils.hasText(password)) {
            throw new BadCredentialsException("Please enter password");
        }
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        try {
            cloudUser = CloudUser.findCloudUserByUserNameAndPassword(userName, password);
            authorities.add(new GrantedAuthorityImpl("ROLE_USER"));
        } catch (EmptyResultDataAccessException e) {
            throw new BadCredentialsException("Invalid username or password");
        } catch (EntityNotFoundException e) {
            throw new BadCredentialsException("Invalid user");
        } catch (NonUniqueResultException e) {
            throw new BadCredentialsException("Non-unique user, contact administrator");
        }
        return new CloudUserPrincipal(cloudUser.getUserId(), userName,password, true, // enabled
                true, // account not expired
                true, // credentials not expired
                true, // account not locked
                authorities);
    }
}
