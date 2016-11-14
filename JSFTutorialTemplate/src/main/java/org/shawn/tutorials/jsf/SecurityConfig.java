package org.shawn.tutorials.jsf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

/**
 * Created by Shawn on 2015/11/21.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String LOGIN_PROCESSING_URL = "/j_spring_security_check";
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                // Login
                .formLogin()
                .loginPage("/login.xhtml")
                .loginProcessingUrl(LOGIN_PROCESSING_URL)
                .failureUrl("/login.xhtml?error")
                .defaultSuccessUrl("/jsf/index.xhtml")
                .and()
                .logout()
                .logoutUrl("/j_spring_security_logout")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/login.xhtml")
                .and()
                .headers()
                .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
                .and()
                .csrf()
                .disable()
        ;
    }

    /**
     * <b>Remember me</b> implementation.
     *
     * @return The {@link PersistentTokenRepository}.
     */
    @Bean
    public PersistentTokenRepository tokenRepository() {
        return new InMemoryTokenRepositoryImpl();
    }

    ;
}
