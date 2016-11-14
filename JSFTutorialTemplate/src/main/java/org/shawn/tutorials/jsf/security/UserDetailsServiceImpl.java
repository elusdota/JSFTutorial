package org.shawn.tutorials.jsf.security;

import com.jrtech.templates.domain.Account;
import com.jrtech.templates.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by jiangliang on 2016/4/19.
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Account user = accountService.findOneByName(login);
        if (user == null)
            throw new UsernameNotFoundException(null);
        return new UserDetailsImpl(user);
    }

}