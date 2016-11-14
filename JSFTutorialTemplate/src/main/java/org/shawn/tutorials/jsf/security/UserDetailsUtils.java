package org.shawn.tutorials.jsf.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Created by jiangliang on 2016/4/19.
 */
@Service
public class UserDetailsUtils {
    private UserDetailsUtils() {
    }

    public static UserDetails getCurrent() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext == null)
            return null;
        Authentication auth = securityContext.getAuthentication();
        if (auth == null || !auth.isAuthenticated()
                || auth instanceof AnonymousAuthenticationToken)
            return null;
        return (UserDetails) auth.getPrincipal();
    }

    public static boolean isAuthorized(String pathName) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext == null)
            return false;
        Authentication auth = securityContext.getAuthentication();
        for (GrantedAuthority authority : auth.getAuthorities()) {
            if (authority.getAuthority().equals(pathName.trim())) {
                return true;
            }
        }
        return false;
    }
}
