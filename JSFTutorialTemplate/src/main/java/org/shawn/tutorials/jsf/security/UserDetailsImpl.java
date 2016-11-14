package org.shawn.tutorials.jsf.security;

import com.jrtech.templates.domain.Account;
import com.jrtech.templates.domain.GrantedAuthorityImpl;
import com.jrtech.templates.domain.Role;
import com.jrtech.templates.services.GrantedAuthorityService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jiangliang on 2016/4/19.
 */
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1;
    private final Account user;

    UserDetailsImpl(Account user) {
        this.user = user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthorityImpl> grantedAuths = new HashSet<>();
        for (Role role : user.getRoles()) {
            grantedAuths.addAll(role.getAuthorities());
        }
        return grantedAuths;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}