package org.shawn.tutorials.jsf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.jsf.el.SpringBeanFacesELResolver;

import javax.el.ELResolver;

/** Java configuration for {@code faces-config.xml} file. */
@Configuration
public class FacesConfig {

    @Bean
    public ELResolver elResolver() {
        return new SpringBeanFacesELResolver();
    }

}